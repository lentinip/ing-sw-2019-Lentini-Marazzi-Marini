package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.BoardCoord;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Choices {

    /**
     * Default constructor
     */
    public Choices(Match match, VirtualView view, Payment payment, AtomicActions atomicActions, SingleActionManager singleActionManager) {
        this.match = match;
        this.view = view;
        this.payment = payment;
        this.atomicActions = atomicActions;
        this.singleActionManager = singleActionManager;
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private Payment payment;

    private AtomicActions atomicActions;

    private SingleActionManager singleActionManager;

    private Weapon selectedWeapon;

    private Powerup selectedPowerup;

    private Effect currentEffect;

    private List<Effect> usedEffect = new ArrayList<>();

    private Cell moveCell;

    private List<Player> movedPlayers = new ArrayList<>();

    private List<Player> shootedPlayers = new ArrayList<>();

    private List<Player> damagedPlayers = new ArrayList<>(); //Useful for powerups

    private List<Cell> shootedCells = new ArrayList<>(); // here I save the list of the cells already selected

    private Player selectedPlayer; // here I save the player that I'm going to move

    private static final Logger LOGGER = Logger.getLogger("choices");

    /* Methods */

    public void setShootedPlayers(List<Player> shootedPlayers) {
        this.shootedPlayers = shootedPlayers;
    }

    public List<Player> getShootedPlayers() {
        return shootedPlayers;
    }

    public void setUsedEffect(List<Effect> usedEffect) {
        this.usedEffect = usedEffect;
    }

    public List<Effect> getUsedEffect() {
        return usedEffect;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public Effect getCurrentEffect() {
        return currentEffect;
    }

    public void setCurrentEffect(Effect currentEffect) {
        this.currentEffect = currentEffect;
    }

    public Cell getMoveCell() {
        return moveCell;
    }

    public void setMoveCell(Cell moveCell) {
        this.moveCell = moveCell;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public List<Player> getMovedPlayers() {
        return movedPlayers;
    }

    public void setMovedPlayers(List<Player> movedPlayers) {
        this.movedPlayers = movedPlayers;
    }

    public Powerup getSelectedPowerup() {
        return selectedPowerup;
    }

    /**
     * This method resets all the attributes to avoid errors the next time I receive a new payment message
     * I have to reset everything when a new effect is used, but I don't have to reset the selectedWeapon, cause
     * it is overwritten every time a player select one (selectedWeapon has to be set to null after the player ends the shoot action)
     */
    public void reset() {

        selectedPowerup = null;
        currentEffect = null;
        moveCell = null;
        movedPlayers.clear();
        shootedPlayers.clear();
        damagedPlayers.clear();
        shootedCells.clear();
        selectedPlayer = null;
    }

    /**
     * this method reset all the choices attributes in the class
     */
    public void resetEverything(){

        selectedWeapon = null;
        usedEffect.clear();
        reset();
    }

    /**
     * this method filters all the selection kind messages
     * @param message sent to the method that reads the message
     */
    @SuppressWarnings("squid:S3776")
    public void selectionHandler(Message message){

        switch (message.getTypeOfMessage()){
            case SELECTED_CARD:
                selectionCardHandler(message);
                break;
            case SELECTED_COLOR:
                payment.paymentHandler(message);
                break;
            case SELECTED_EFFECT:

                IndexMessage effectIndex = message.deserializeIndexMessage();

                System.out.print("\nSELECTED EFFECT: index = " + effectIndex.getSelectionIndex() + "\n" );

                //useful for effect that need to shoot different players see cyberblade optional effect
                Player alreadyShooted = null;

                if (!shootedPlayers.isEmpty()){
                    alreadyShooted = shootedPlayers.get(0);
                }

                // clearing all the selections
                reset();


                // case he does not want to use an effect because he wants to stop
                if (effectIndex.getSelectionIndex() < 0){

                    // the shoot action is ended, unloading the weapon and setting it to null
                    selectedWeapon.unloadWeapon();
                    selectedWeapon = null;
                    singleActionManager.reducePlayerNumberOfActions();
                }

                else if ( effectIndex.getSelectionIndex() >= 0){

                    currentEffect = selectedWeapon.getEffects().get(effectIndex.getSelectionIndex());

                    // adding the already shooted player to shootedPlayers, saved before the reset, if it is needed
                    if ( currentEffect.getTargets().isDifferentPlayers()){

                        shootedPlayers.add(alreadyShooted);
                    }
                }

                // effect is free and I analyze it
                if ( currentEffect != null && currentEffect.getCost() == null){
                    effectAnalyzer();
                }

                // the effect has a cost and I enter the payment session
                else if (currentEffect != null && currentEffect.getCost()!=null) {
                    payment.paymentStarter(message);
                }

                break;
            case SELECTED_CELL:

                BoardCoord boardCoord = message.deserializeBoardCoord();
                Cell selectedCell = match.getBoard().getCell(boardCoord);

                // when I'm shooting with an ALL_TARGET effect
                if ( currentEffect.getType() != EffectsKind.MOVE) {
                    shootedCells.add(selectedCell);

                    //checking if I can choose a new cell
                    if (shootedCells.size() < currentEffect.getTargets().getMaxTargets()){

                        updateVisibilityAllTarget();
                    }

                    else {
                        applyAllTargetEffect();
                    }
                }

                // the current effect is a move type effect
                else {
                    atomicActions.move(match.getCurrentPlayer(), selectedCell);
                    singleActionManager.endShootingAction();
                }
                break;
            case SELECTED_PLAYER:
                selectionPlayerHandler(message);
                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;
        }
    }

    /**
     * method that filters the card selection messages
     * @param message sent to the method that read the message
     */
    public void selectionCardHandler(Message message){

        switch (message.getTypeOfAction()){

            case SPAWN:
                //stopping the timer because a choice has been made
                view.getSpawningChoiceTimer().cancel();
                IndexMessage index = message.deserializeIndexMessage();
                singleActionManager.getTurnManager().spawn(match.getPlayerByUsername(message.getUsername()), index.getSelectionIndex());
                break;
            case USEPOWERUP:

                //stopping the timer because we got a response
                view.getResponseTimer().cancel();

                // the player has actually selected a real powerup
                if (message.deserializeIndexMessage().getSelectionIndex() >= 0){

                    powerupEffectHandler(message);
                }
                // the player does not want to use a powerup anymore (index < 0)
                else {
                    // I now watch who has sent me the message
                    Player sender = match.getPlayerByUsername(message.getUsername());

                    // if the sender is not the current player it means that I asked a player if he wants to use tagback and he answered "no" so I have to remove him from the list
                    if (sender != match.getCurrentPlayer()) {

                        damagedPlayers.remove(sender);
                    }

                    // now I check if other players have tagback and continue the turn
                    playersWithCounterAttackPowerup();
                }
                break;
            case SHOOT:
                IndexMessage weaponIndex = message.deserializeIndexMessage();
                Player shooter = match.getCurrentPlayer();
                selectedWeapon = shooter.getWeaponFromIndex(weaponIndex.getSelectionIndex());
                //if the weapon is unloaded we are in frenzy and I have to reload it
                if (!selectedWeapon.getIsLoaded()){
                    payment.setReloadInFrenzy(true);
                    message.setTypeOfAction(TypeOfAction.RELOAD);
                    payment.paymentStarter(message);
                }
                weaponHandler(message);
                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;

        }
    }

    /**
     * method that filters the player selection
     * @param message sent to the method that reads the message
     */
    public void selectionPlayerHandler(Message message){

        switch (message.getTypeOfAction()){

            case USEPOWERUP:
                usePowerupHandler(message);
                break;
            case MOVEBEFORESHOOT:
                moveBeforeShootHandler(message);
                break;
            case SHOOT:
                IndexMessage playerChosen = message.deserializeIndexMessage();

                // he does not want to shoot anymore
                if (playerChosen.getSelectionIndex() < 0){

                    applyEffect();
                }

                else {

                    shootedPlayers.add(match.getPlayers().get(playerChosen.getSelectionIndex()));
                    //case he can choose other players
                    if (shootedPlayers.size() < currentEffect.getTargets().getMaxTargets()) {

                        updateVisibilityMultiple();
                    }

                    // if he can't choose another target we apply the effect
                    else {
                        applyEffect();
                    }
                }

                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;
        }

    }

    /**
     * this method handles the selection of a player in an use powerup action
     * @param message message received
     */
    public  void usePowerupHandler(Message message){

        Message answer = new Message(message.getUsername());
        selectedPlayer = match.getPlayers().get(message.deserializeIndexMessage().getSelectionIndex());

        // if newton I'll return the cells where I can move the selected player
        if (selectedPowerup.isDuringYourTurn() && !selectedPowerup.isDuringDamageAction() && selectedPowerup.getMove() != null){

            List<Cell> availableCells = selectedPowerup.getMove().availableCellsMoveTarget(selectedPlayer);
            List<BoardCoord> options = new ArrayList<>();
            for (Cell cell: availableCells){

                options.add(cell.getCoord());
            }
            answer.createAvailableCellsMessage(TypeOfAction.USEPOWERUP, options);
            view.display(answer);
        }

        // if targeting scope I'll have to add the damage and show him if he can use another targeting scope
        else if (selectedPowerup.isDuringDamageAction()){

            atomicActions.dealDamage(match.getCurrentPlayer(), selectedPlayer, selectedPowerup.getValue());

            // removing the powerup from the player hand and putting it into the discarded pile
            match.getCurrentPlayer().usePowerup(selectedPowerup);
            match.getBoard().discardPowerup(selectedPowerup);
            match.notifyPrivateHand(match.getCurrentPlayer());

            // checking if the player has other targeting scope powerups
            List<Powerup> availablePowerups = match.getCurrentPlayer().getPowerupsAfterShoot();

            // case where he does not have targeting scope I ask to the damaged players for the tagback use
            if (availablePowerups.isEmpty()){

                playersWithCounterAttackPowerup();
            }

            //if he has targeting scope I'll show him the options
            else {

                List<IndexMessage> options = new ArrayList<>();
                for (Powerup powerup: availablePowerups){

                    options.add(new IndexMessage(match.getCurrentPlayer().getPowerupIndex(powerup)));
                }
                answer.createAvailableCardsMessage(TypeOfAction.USEPOWERUP, options, false);
                view.display(answer);
            }

        }

    }

    /**
     * this method handles the selection of a player in a move before shoot action
     * @param message message received
     */
    @SuppressWarnings("squid:S3776")
    public void moveBeforeShootHandler(Message message){

        // see if the index is < 0 and then show him the available cells
        IndexMessage selection = message.deserializeIndexMessage();

        //case the player does not want to move anymore
        if (selection.getSelectionIndex() < 0){

            System.out.print("\n MoveBeforeShootHandler - index <0\n");

            effectHandler();
        }

        // I'll have to give him options or move the player if it is not the first one (case vortex)
        else{

            selectedPlayer = match.getPlayers().get(selection.getSelectionIndex());
            MoveEffect move = currentEffect.getMove();

            //if is the first player to be moved
            if (movedPlayers.isEmpty()){

                System.out.print("\n MoveBeforeShootHandler - first player to be moved\n");

                // case tractor beam second effect
                if (move.isMoveTargetOnYourSquare()){

                    System.out.print("\n MoveBeforeShootHandler - tractor beam second effect\n");

                    atomicActions.move(selectedPlayer, match.getCurrentPlayer().getPosition());
                    movedPlayers.add(selectedPlayer);
                    effectHandler();
                }

                // case vortex or tractor beam first effect
                else {

                    System.out.print("\n MoveBeforeShootHandler - sending cells for vortex o tractor beam\n");

                    //showing only the cells that the shooter can see and where the target can be moved in
                    List<BoardCoord> availableCells = new ArrayList<>();
                    List<Cell> options = selectedPlayer.getPosition().reachableCells(move.getMoveTargets());
                    List<Cell> visibleCells = match.getCurrentPlayer().visibleCells();

                    for (Cell cell: options){

                        if (visibleCells.contains(cell)){

                            availableCells.add(cell.getCoord());
                        }
                    }

                    message.createAvailableCellsMessage(TypeOfAction.MOVEBEFORESHOOT, availableCells);
                    view.display(message);
                }

            }

            //case moved players is not empty vortex second effect
            else {

                System.out.print("\n MoveBeforeShootHandler - moved players is not empty\n");

                atomicActions.move(selectedPlayer, moveCell);
                movedPlayers.add(selectedPlayer);

                // I can move someone else
                if (movedPlayers.size() < move.getMaxTargets()){

                    //giving the players he can move into the move cell
                    List<Character> options = new ArrayList<>();
                    List<Player> availablePlayers = new ArrayList<>();
                    List<Cell> cells = moveCell.reachableCells(move.getMoveTargets());

                    for (Cell cell: cells){

                        availablePlayers.addAll(cell.playersInCell());
                    }

                    availablePlayers.remove(match.getCurrentPlayer());
                    availablePlayers.removeAll(movedPlayers);

                    for (Player player: availablePlayers){

                        options.add(player.getCharacter());
                    }

                    if (options.isEmpty()){
                        effectHandler();
                    }
                    else {
                        message.createAvailablePlayers(TypeOfAction.MOVEBEFORESHOOT, options);
                        view.display(message);
                    }
                }

                // I cannot move anymore
                 else {

                     effectHandler();
                 }
            }
        }
    }


    /**
     * this method analyzes the powerup selected and sends messages to the view basing on the kind of powerup the player is using
     * @param message with the selected powerup
     */
    public void powerupEffectHandler(Message message){

        IndexMessage indexMessage = message.deserializeIndexMessage();
        Player player = match.getPlayerByUsername(message.getUsername());
        selectedPowerup = player.getPowerupFromIndex(indexMessage.getSelectionIndex());
        Message answer = new Message(message.getUsername());

        // entering the payment session if I'm using a powerup with a cost (targeting scope)
        if (selectedPowerup.isiNeedToPay()){
            payment.paymentStarter(message);
        }

        // if I'm using teleporter
        else if (selectedPowerup.isDuringYourTurn() && selectedPowerup.getMove() == null) {
            List<BoardCoord> availableCells = new ArrayList<>();
            List<Cell> cells = match.getBoard().getField();
            for (Cell cell : cells) {
                availableCells.add(cell.getCoord());
            }
            answer.createAvailableCellsMessage(TypeOfAction.USEPOWERUP, availableCells);
            view.display(answer);
        }

        // if I'm using newton
        else if (selectedPowerup.getMove() != null && selectedPowerup.getMove().getVisibility() == KindOfVisibility.ALL){

           List<Player> availablePlayers = new ArrayList<>(match.getPlayers());
           availablePlayers.remove(player);
           List<Player> playerList = new ArrayList<>(availablePlayers);

           for (Player p : playerList){
               if (p.getPosition()==null){
                   availablePlayers.remove(p);
               }
           }

           List<Character> availableCharacters = new ArrayList<>();

           for (Player character: availablePlayers){

               availableCharacters.add(character.getCharacter());
           }
           answer.createAvailablePlayers(TypeOfAction.USEPOWERUP, availableCharacters);
           view.display(answer);
        }

        // case tagback grenade, I have to apply the mark to the current player and go on with the turn
        else if (selectedPowerup.isDuringDamageAction() && !selectedPowerup.isDuringYourTurn()){

            // applying the mark to the player who was shooting to the owner of tagback grenade
            atomicActions.mark(player, match.getCurrentPlayer(), selectedPowerup.getValue());

            // removing the powerup from the player hand
            player.getPowerups().remove(selectedPowerup);

            // adding that powerup to the discarded pile
            match.getBoard().discardPowerup(selectedPowerup);

            match.notifyPrivateHand(player);

            damagedPlayers.remove(player);

            playersWithCounterAttackPowerup();
        }

    }

    /**
     * this method doSomething to the view the list of the available players that can be targeted by Targeting Scope powerup
     */
    public void damagedTargets(){

        List<Character> targets = new ArrayList<>();

        for (Player player: damagedPlayers){
            targets.add(player.getCharacter());
        }

        Message message = new Message(match.getCurrentPlayer().getName());
        message.createAvailablePlayers(TypeOfAction.USEPOWERUP, targets);
        view.display(message);
    }

    /**
     * Method that receive the weapon and analyze it to doSomething options to the view
     */
    public void weaponHandler(Message message){

        IndexMessage weaponIndex = message.deserializeIndexMessage();
        Player shooter = match.getCurrentPlayer();
        selectedWeapon = shooter.getWeaponFromIndex(weaponIndex.getSelectionIndex());

        // if the weapon is single effect I don't have to ask to the player to choose an effect
        if ( selectedWeapon.getType() == WeaponsType.SINGLE_EFFECT){
            currentEffect = selectedWeapon.getEffects().get(0);
            effectAnalyzer();
        }

        // otherwise I have to show the available effects to the player so he can choose one to execute
        else {

            List<Effect> availableEffects = selectedWeapon.usableEffects(match.getPlayers());
            List<String> names = new ArrayList<>();
            List<IndexMessage> effectIndex = new ArrayList<>();

            // for cyberblade (only weapon that has an order for the moment) I want to remove the last effect beacuse I need to show only the free one and the move
            if (selectedWeapon.hasAnOrder() && availableEffects.size()==3){

                availableEffects.remove(2);
            }

            for (Effect effect: availableEffects){

                effectIndex.add(new IndexMessage(selectedWeapon.getIndexByEffect(effect)));
                names.add(effect.getName());
            }

            Message answer = new Message(message.getUsername());
            answer.createAvailableEffects(effectIndex, names);
            view.display(answer);
        }
    }

    /**
     * handles moves after shoot and powerups options
     * called after the effect execution, it shows the other effects available or continue the turn if the player cannot
     * perform any other effect
     */
    public void afterEffectHandler(){

        usedEffect.add(currentEffect);
        MoveEffect move = currentEffect.getMove();
        Message options = new Message(match.getCurrentPlayer().getName());

        // check if there is a move after
        if ( move != null && move.iHaveAMoveAfter()){

            //if I have to move targets after
            if (move.isMoveTargetAfter()){

                List<Cell> moveOptions = move.availableCellsMoveTarget(shootedPlayers.get(0));
                List<BoardCoord> coord = new ArrayList<>();

                for (Cell cell: moveOptions){

                    coord.add(cell.getCoord());
                }

                // sending the message to the view
                options.createAvailableCellsMessage(TypeOfAction.MOVEAFTERSHOOT, coord);
                view.display(options);
            }

            // this case is actually used only for power glove movement
            if (move.isMoveYouAfter() && move.isObligatoryYou()){

                //moving the player in the cell of the last shooted targets
                atomicActions.move(match.getCurrentPlayer(), shootedPlayers.get(shootedPlayers.size()-1).getPosition());

                //showing the powerups option
                powerupsAfterShoot();
            }
        }

        else {
            powerupsAfterShoot();
        }
    }

    /**
     * this method handle the possible use of powerups after a shooting action
     */
    public void powerupsAfterShoot(){

        List<Powerup> afterShootPowerups = match.getCurrentPlayer().getPowerupsAfterShoot();
        List<IndexMessage> powerupIndexes = new ArrayList<>();
        Message options = new Message(match.getCurrentPlayer().getName());

        // if the shooter can use targeting scope
        if ( !afterShootPowerups.isEmpty() && !damagedPlayers.isEmpty()){

            for (Powerup powerup: afterShootPowerups){

                powerupIndexes.add(new IndexMessage(match.getCurrentPlayer().getPowerupIndex(powerup)));
            }

            options.createAvailableCardsMessage(TypeOfAction.USEPOWERUP, powerupIndexes, false);
            view.display(options);
        }

        // if the damaged players can use tag back grenade
        else if( !damagedPlayers.isEmpty() ){

            playersWithCounterAttackPowerup();
        }

        else {

            singleActionManager.endShootingAction();
        }
    }

    /**
     * apply the effect and continue the turn
     * called by moveBeforeShoot after the move,
     * by SingleActionManager, by effectAnalyzer
     * doSomething to the view the possible selections
     */
    @SuppressWarnings("squid:S3776")
    public void effectHandler(){

        System.out.print("\nWe're in effect handler\n");

        // if I moved someone I have to apply the effect to the moved players see vortex or tractor beam
        if (!movedPlayers.isEmpty()){

            System.out.print("\nEffect handler - applying damage to moved players\n");

            shootedPlayers = movedPlayers;
            applyEffect();
        }

        // case where the player don't have to choose
        else if (currentEffect.getTargets().isForcedChoice()){

            System.out.print("\nEffect handler - effect with forced choice\n");

            applyForcedChoiceEffect();
        }

        // if the player has not selected a target yet
        else if (shootedPlayers.isEmpty() && shootedCells.isEmpty()){

            Message options = new Message(match.getCurrentPlayer().getName());

            //showing cells if it is an all target effect
            if (currentEffect.getType() == EffectsKind.ALL_TARGET) {

                List<Cell> targets = currentEffect.shootableCells(match.getCurrentPlayer());
                List<BoardCoord> reachableCells = new ArrayList<>();

                for (Cell cell: targets){

                    reachableCells.add(cell.getCoord());
                }
                options.createAvailableCellsMessage(TypeOfAction.SHOOT, reachableCells);
            }

            else {
                //calling the method that returns the targets you can shoot
                List<Player> targets = currentEffect.reachablePlayers(match.getCurrentPlayer());
                List<Character> reachableCharacters = new ArrayList<>();

                for (Player player : targets) {

                    reachableCharacters.add(player.getCharacter());
                }

                options.createAvailablePlayers(TypeOfAction.SHOOT, reachableCharacters);
            }

            System.out.print("\nEffect handler - sending available players\n");

            view.display(options);
        }

        // a player is already selected
        else {

            System.out.print("\nEffect handler - the player is already selected\n");

            // sending new options for a multiple target effect
            if (currentEffect.getType() == EffectsKind.MULTIPLE_TARGET || currentEffect.getType() == EffectsKind.SINGLE_TARGET){

                updateVisibilityMultiple();
            }

            // sending new options for an all target effect
            else if (currentEffect.getType() == EffectsKind.ALL_TARGET){

                updateVisibilityAllTarget();
            }
        }
    }

    /**
     * this method sends new options to the player by watching the other players chosen and the effect that is being executed
     * done for multiple target effect (options are players)
     */
    @SuppressWarnings("squid:S3776")
    public void updateVisibilityMultiple(){

        List<Player> newTargets;
        List<Character> updatedOptions = new ArrayList<>();

        //updating the options for VISIBLE multi targets effects
        if (currentEffect.getVisibility() == KindOfVisibility.VISIBLE){

            newTargets = currentEffect.reachablePlayers(match.getCurrentPlayer());
            newTargets.removeAll(shootedPlayers);

            // removing the players on the same squares of the shooted Players
            if (currentEffect.getTargets().isDifferentSquares()){

                List<Player> playerList = new ArrayList<>(newTargets);
                for (Player player: playerList){

                    for (Player shootedPlayer: shootedPlayers){

                        if ( player.getPosition() == shootedPlayer.getPosition()){

                            newTargets.remove(player);
                            break; //exit from the second for
                        }
                    }
                }
            }

            // newTargets become the target in the same direction adjacent square of the shooted player
            if (currentEffect.isSameDirection()){

                newTargets.clear();
                Cell cell = currentEffect.getVisibilityClass().cellInSameDirection(match.getCurrentPlayer().getPosition(), shootedPlayers.get(0).getPosition());
                if (cell!=null){
                    newTargets.addAll(cell.playersInCell());
                }
            }
        }

        else if (currentEffect.getVisibility() == KindOfVisibility.THOR){

            //players that can see the last shooted player
            newTargets = shootedPlayers.get(shootedPlayers.size() -1).visibilePlayer();
            newTargets.removeAll(shootedPlayers);
            newTargets.remove(match.getCurrentPlayer());
        }

        else if (currentEffect.getVisibility() == KindOfVisibility.RAILGUN){

            newTargets = currentEffect.getVisibilityClass().railgunUpdate(shootedPlayers.get(0), match.getCurrentPlayer());
        }

        // case for exceptions
        else {

            newTargets = new ArrayList<>();
        }

        // no targets available then I apply the effect
        if (newTargets.isEmpty()){

            applyEffect();
        }

        else {

            for (Player player : newTargets) {

                updatedOptions.add(player.getCharacter());
            }

            Message options = new Message(match.getCurrentPlayer().getName());
            options.createAvailablePlayers(TypeOfAction.SHOOT, updatedOptions);
            view.display(options);
        }


    }

    /**
     * this method sends new options to the player by watching the other players chosen and the effect that is being executed
     * done for all target effect (options are Cell)
     */
    public void updateVisibilityAllTarget(){

        // the only all target effect that needs this method is flamethrower second effect
        if (currentEffect.isSameDirection()){


            //adding the adjacent cell in the same direction to the shooted cells and applying the effect
            Cell shooterCell = match.getCurrentPlayer().getPosition();
            Cell nextCell;
            nextCell = currentEffect.getVisibilityClass().cellInSameDirection(shooterCell, shootedCells.get(0));

            if (nextCell != null) {
                shootedCells.add(nextCell);
            }
            applyAllTargetEffect();
        }
    }

    /**
     * apply the demage in cells chosen for all target effects
     */
    public void applyAllTargetEffect(){

        Target targets = currentEffect.getTargets();

        // case furnace effect 1, I'm dealing damage to all targets in the room
        if ( targets.isRoom()){

             damagedPlayers = shootedCells.get(0).getRoom().playersInside();

             for (Player player: damagedPlayers){

                 atomicActions.dealDamage(match.getCurrentPlayer(), player, targets.getDamages()[0]);
                 atomicActions.mark(match.getCurrentPlayer(), player, targets.getMarks()[0]);
             }
        }

        // normal all target effect
        else {

            for (int i = 0; i < shootedCells.size(); i++){

                // removing the current player in case he is dealing damage to all players on his square
                List<Player> hittedPlayers = shootedCells.get(shootedCells.size() - 1 - i).playersInCell();
                hittedPlayers.remove(match.getCurrentPlayer());

                for (Player shootedPlayer: hittedPlayers){

                    shootedPlayers.add(shootedPlayer);

                    if ( targets.getDamages()[i] > 0){

                        damagedPlayers.add(shootedPlayer);
                        atomicActions.dealDamage(match.getCurrentPlayer(), shootedPlayer, targets.getDamages()[i]);
                    }

                    atomicActions.mark(match.getCurrentPlayer(), shootedPlayer, targets.getMarks()[i]);
                }
            }
        }

        afterEffectHandler();
    }

    /**
     * apply the damage to the players for weapon effects that don't need the user to choose someone
     */
    public void applyForcedChoiceEffect(){

        Target targets = currentEffect.getTargets();
        shootedPlayers.addAll(currentEffect.reachablePlayers(match.getCurrentPlayer()));

        // dealing damage to all the targets available
        for (Player shootedPlayer: shootedPlayers){

            if (targets.getDamages()[0] > 0){

                atomicActions.dealDamage(match.getCurrentPlayer(), shootedPlayer, targets.getDamages()[0]);
                damagedPlayers.add(shootedPlayer);
            }

            atomicActions.mark(match.getCurrentPlayer(), shootedPlayer, targets.getMarks()[0]);
        }

        afterEffectHandler();
    }

    /**
     * by reading the target class this method apply the exact number of damage and mark to every target
     */
    public void applyEffect(){

        Target targets = currentEffect.getTargets();

        for (int i = 0; i < shootedPlayers.size(); i++){

            if ( targets.getDamages()[i] > 0) {
                System.out.println("\n giving " + targets.getDamages()[i] + "damages to " + shootedPlayers.get(i).getCharacter() +"\n");
                atomicActions.dealDamage(match.getCurrentPlayer(), shootedPlayers.get(shootedPlayers.size() - 1 - i), targets.getDamages()[i]);
                damagedPlayers.add(shootedPlayers.get(i));
            }

            atomicActions.mark(match.getCurrentPlayer(), shootedPlayers.get(shootedPlayers.size() -1 -i), targets.getMarks()[i]);
        }

        //this case is to deal an all target damage/mark like in hellion or rocket launcher second effect
        if (targets.isSameSquare()){

            List<Player> newTargets = shootedPlayers.get(0).getPosition().playersInCell();

            for (Player player: newTargets){

                if ( targets.getDamages()[1] > 0) {
                    atomicActions.dealDamage(match.getCurrentPlayer(), player, targets.getDamages()[1]);
                    damagedPlayers.add(player);
                }

                atomicActions.mark(match.getCurrentPlayer(), player, targets.getMarks()[1]);
            }
        }

        afterEffectHandler();
    }

    /**
     * called after the payment session ( if the effect is not free), analyze the effect and see if there is a moveBefore inside it
     * called the first time I select the effect
     */
    @SuppressWarnings("squid:S3776")
    public void effectAnalyzer(){

        System.out.print("\nWe're in effect analyzer\n");

        Message options = new Message(match.getCurrentPlayer().getName());

        // if the effect contains a moveBefore I have to show to the client the available choices
        if (currentEffect.getMove() != null && currentEffect.getMove().iHaveAMoveBefore()){

            System.out.print("\nEffect analyzer - move before\n");

            // if I have to choose to move a target then I'll show the targets available
            if (currentEffect.getMove().isMoveTargetBefore()){

                List<Character> availablePlayers = currentEffect.getMove().availablePlayersToMove(match.getPlayers(), match.getCurrentPlayer());
                options.createAvailablePlayers(TypeOfAction.MOVEBEFORESHOOT, availablePlayers);
                view.display(options);
            }
        }

        else if (currentEffect.getType() == EffectsKind.MOVE){

            System.out.print("\nEffect analyzer - the weapon has a move\n");

            List<Cell> reachableCells = match.getCurrentPlayer().getPosition().reachableCells(currentEffect.getMove().getMoveYou());

            // the player must move
            reachableCells.remove(match.getCurrentPlayer().getPosition());

            List<BoardCoord> cells = new ArrayList<>();

            //remember that if the player has not already shooted I'll have to show only the cells where he can shoot from
            //in this if I can move wherever I want (respecting the number of moves allowed by the effect)
            if (!usedEffect.isEmpty()){

                for (Cell cell: reachableCells){

                    cells.add(cell.getCoord());
                }
            }

            //in this if, I can move only in the cells where I can shoot someone from
            else {

                List<Effect> weaponsEffect = new ArrayList<>(selectedWeapon.getEffects());
                weaponsEffect.removeAll(usedEffect);

                Player currentPlayer = match.getCurrentPlayer();
                Cell startPosition = currentPlayer.getPosition();


                // I put a copy of the player in every reachable cell and if he can use one of his effects from there I'll add the cell to the list
                for (Cell cell: reachableCells){

                    currentPlayer.setPosition(cell);

                    for (Effect effect: weaponsEffect){

                        if ( effect.getType() != EffectsKind.MOVE && effect.usableEffect(currentPlayer, match.getPlayers())){

                                cells.add(cell.getCoord());
                                break; // to avoid duplicates
                        }
                    }
                }

                currentPlayer.setPosition(startPosition);
            }

            // after the effect is executed (case move effect) it is added to the usedEffects
            usedEffect.add(currentEffect);

            options.createAvailableCellsMessage(TypeOfAction.SHOOT, cells);
            view.display(options);
        }

        // if I can't move before I go to effectHandler
        else {
            effectHandler();
        }

    }

    /**
     * method used for the vortex move effect
     * @return characters you can move
     */
    public List<Character> availablePlayersToMove(){
        List<Player> players = new ArrayList<>(match.getPlayers());
        List<Character> characters = new ArrayList<>();

        players.removeAll(movedPlayers);
        players.remove(match.getCurrentPlayer()); //removing myself so I can't choose my character in vortex effect

        for (Player player : players){
            if (player.getPosition() != null && player.getPosition().reachableCells(1).contains(moveCell)){
                characters.add(player.getCharacter());
            }
        }
        return characters;
    }

    /**
     * this method check the damaged players and sends a message to the view with the ones that have tagback grenade powerup to ask
     * them if they want to use it
     */
    public void playersWithCounterAttackPowerup(){

        List<Player> nextPlayers = new ArrayList<>();

        for (Player player: damagedPlayers){

            for (Powerup powerup: player.getPowerups()){

                if (powerup.isDuringDamageAction() && !powerup.isDuringYourTurn()){

                    nextPlayers.add(player);
                    break; // to avoid duplicates
                }
            }
        }

        // no more players can use tagback grenade
        if (nextPlayers.isEmpty()){

            view.setMessageSender(match.getCurrentPlayer().getName());
            singleActionManager.endShootingAction();
        }

        // I ask to the first player of the list if he wants to use tagback
        else {

            Message message = new Message(nextPlayers.get(0).getName());
            List<IndexMessage> usablePowerups = new ArrayList<>();
            for (Powerup powerup: nextPlayers.get(0).getPowerups()){

                if (powerup.isDuringDamageAction() && !powerup.isDuringYourTurn()){

                    usablePowerups.add(new IndexMessage(nextPlayers.get(0).getPowerups().indexOf(powerup)));
                }
            }

            //updating the message sender in the view so we can manage the case he does not reply and starting the timer
            view.setMessageSender(message.getUsername());
            view.startResponseMessage();
            message.createAvailableCardsMessage(TypeOfAction.USEPOWERUP, usablePowerups, false);
            view.display(message);
        }
    }

    /**
     * tells if the player can do a shoot action, useful after a player uses a powerup
     * @return boolean
     */
    public  Boolean canIshoot(){

        Player currentPlayer = match.getCurrentPlayer();
        if(match.getCurrentPlayerLeftActions()>0){
            return currentPlayer.canIshootBeforeComplexAction();
        }

        else {
            return false;
        }
    }
}
