package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.BoardCoord;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;
import it.polimi.sw2019.view.ViewInterface;

import java.util.ArrayList;
import java.util.List;

public class Choices {

    /**
     * Default constructor
     */
    public Choices(Match match, VirtualView view, Payment payment, AtomicActions atomicActions) {
        this.match = match;
        this.view = view;
        this.payment = payment;
        this.atomicActions = atomicActions;
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private Payment payment;

    private AtomicActions atomicActions;

    private Weapon selectedWeapon;

    private Powerup selectedPowerup;

    private Effect currentEffect;

    private List<Effect> usedEffect = new ArrayList<>();

    private Cell moveCell;

    private List<Player> movedPlayers = new ArrayList<>();

    private List<Player> shootedPlayers = new ArrayList<>();

    private List<Player> damagedPlayers = new ArrayList<>(); //Useful for powerups

    private List<Cell> shootedCells = new ArrayList<>(); // here I save the list of the cells already selected

    private Player selectedPlayer;

    /* Methods */

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

    /**
     * This method resets all the attributes to avoid errors the next time I receive a new payment message
     * I have to reset everything when a new effect is used, but I don't have to reset the selectedWeapon, cause
     * it is overwritten every time a player select one (selectedWeapon has to be set to null after the player ends the shoot action
     */
    public void reset() {

        //TODO implement
        return;
    }

    public void selectionHandler(Message message){

        switch (message.getTypeOfMessage()){
            case SELECTED_CARD:
                selectionCardHandler(message);
                break;
            case SELECTED_COLOR:
                payment.paymentHandler(message);
                break;
            case SELECTED_EFFECT:

                // clearing all the selections
                reset();
                IndexMessage effectIndex = message.deserializeIndexMessage();
                currentEffect = selectedWeapon.getEffects().get(effectIndex.getSelectionIndex());

                // effect is free and I analyze it
                if ( currentEffect.getCost() == null){
                    effectAnalizer();
                }

                // the effect has a cost and I enter the payment session
                else {
                    payment.paymentStarter(message);
                }

                break;
            case SELECTED_CELL:

                BoardCoord boardCoord = message.deserializeBoardCoord();
                Cell selectedCell = match.getBoard().getCell(boardCoord);

                // when I'm shooting with an ALL_TARGET effect
                if ( currentEffect.getType() != EffectsKind.MOVE) {
                    shootedCells.add(selectedCell);
                    effectHandler();
                }

                // the current effect is a move type effect
                else {
                    atomicActions.move(match.getCurrentPlayer(), selectedCell);
                    afterEffectHandler();
                }
                break;
            case SELECTED_PLAYER:
                selectionPlayerHandler(message);
                break;
            default:
                break;
        }
    }

    public void selectionCardHandler(Message message){

        switch (message.getTypeOfAction()){

            case SPAWN:
                //TODO implement the call to the spawn method
                break;
            case USEPOWERUP:
                powerupEffectHandler(message);
                break;
            case SHOOT:
                weaponHandler(message);
                break;
            default:
                break;

        }
    }

    public void selectionPlayerHandler(Message message){

        switch (message.getTypeOfAction()){

            case USEPOWERUP:
                break;
            case MOVEBEFORESHOOT:
                break;
            case SHOOT:
                break;
            default:
                break;
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
                availableCells.add(new BoardCoord(cell.getRow(), cell.getColumn()));
            }
            answer.createAvailableCellsMessage(TypeOfAction.USEPOWERUP, availableCells);
            view.display(answer);
        }

        // if I'm using newton
        else if (selectedPowerup.getMove() != null && selectedPowerup.getMove().getVisibility() == KindOfVisibility.ALL){

           List<Player> availablePlayers = match.getPlayers();
           availablePlayers.remove(player);
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

            damagedPlayers.remove(player);
            List<Player> nextPlayers = playersWithCounterAttackPowerup();

            // no more players can use tagback grenade
            if (nextPlayers.isEmpty()){
                afterEffectHandler();
            }

            // I ask to the first player of the list if he wants to use tagback
            else {

                message.setUsername(nextPlayers.get(0).getName());
                List<IndexMessage> usablePowerups = new ArrayList<>();
                for (Powerup powerup: nextPlayers.get(0).getPowerups()){

                    if (powerup.isDuringDamageAction() && !powerup.isDuringYourTurn()){

                        usablePowerups.add(new IndexMessage(nextPlayers.get(0).getPowerups().indexOf(powerup)));
                    }
                }

                message.createAvailableCardsMessage(TypeOfAction.USEPOWERUP, usablePowerups, false);
                view.display(message);
            }

        }

    }

    /**
     * this method send to the view the list of the available players that can be targeted by Targeting Scope powerup
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
     *
     */
    public void weaponHandler(Message message){

        IndexMessage weaponIndex = message.deserializeIndexMessage();
        Player shooter = match.getCurrentPlayer();
        selectedWeapon = shooter.getWeaponFromIndex(weaponIndex.getSelectionIndex());

        // if the weapon is single effect I don't have to ask to the player to choose an effect
        if ( selectedWeapon.getType() == WeaponsType.SINGLE_EFFECT){
            currentEffect = selectedWeapon.getEffects().get(0);
            effectAnalizer();
        }

        // otherwise I have to show the available effects to the player so he can choose one to execute
        else {

            List<Effect> availableEffects = selectedWeapon.usableEffects(match.getPlayers());
            List<IndexMessage> effectIndex = new ArrayList<>();

            for (Effect effect: availableEffects){

                effectIndex.add(new IndexMessage(selectedWeapon.getIndexByEffect(effect)));
            }

            Message answer = new Message(message.getUsername());
            answer.createAvailableEffects(effectIndex);
            view.display(answer);
        }
    }

    /**
     * called after the effect execution, it shows the other effects available or continue the turn if the player cannot
     * perform any other effect
     */
    public void afterEffectHandler(){

    }

    /**
     * called by moveBeforeShoot after the move send to the view the possible selections
     */
    public void effectHandler(){
        //TODO implement
    }

    /**
     * called after the payment session ( if the effect is not free), analyze the effect and see if there is a moveBefore inside it
     * called the first time I select the effect
     */
    public void effectAnalizer(){

        Message options = new Message(match.getCurrentPlayer().getName());

        // if the effect contains a moveBefore I have to show to the client the available choices
        if (currentEffect.getMove() != null && currentEffect.getMove().iHaveAMoveBefore()){

            // if I have to choose to move a target then I'll show the targets available
            if (currentEffect.getMove().isMoveTargetBefore()){

                List<Character> availablePlayers = currentEffect.getMove().availablePlayersToMove(match.getPlayers(), match.getCurrentPlayer());
                options.createAvailablePlayers(TypeOfAction.SHOOT, availablePlayers);
                view.display(options);
            }
        }

        else if (currentEffect.getType() == EffectsKind.MOVE){

            List<Cell> reachableCells = match.getCurrentPlayer().getPosition().reachableCells(currentEffect.getMove().getMoveYou());

            // the player must move
            reachableCells.remove(match.getCurrentPlayer().getPosition());

            List<BoardCoord> cells = new ArrayList<>();

            // after the effect is executed (case move effect) it is added to the usedEffects
            usedEffect.add(currentEffect);

            //remember that if the player has not already shooted I'll have to show only the cells where he can shoot in
            //in this if I can move wherever I want (respecting the number of moves allowed by the effect)
            if (!shootedPlayers.isEmpty()){

                for (Cell cell: reachableCells){

                    cells.add(cell.getCoord());
                }
            }

            //in this if, I can move only in the cells where I can shoot someone from
            else if (shootedPlayers.isEmpty()){

                List<Effect> weaponsEffect = new ArrayList<>(selectedWeapon.getEffects());
                weaponsEffect.removeAll(usedEffect);

                Player copy = new Player();
                copy.setPosition(match.getCurrentPlayer().getPosition());

                // I put a copy of the player in every reachable cell and if he can use one of his effects from there I'll add te cell to the list
                for (Cell cell: reachableCells){

                    copy.setPosition(cell);

                    for (Effect effect: weaponsEffect){

                        // && condition is to avoid duplicates in the list
                        if (effect.usableEffect(copy, match.getPlayers()) && !cells.contains(cell.getCoord())){

                                cells.add(cell.getCoord());
                        }
                    }
                }
            }

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
     * @return
     */
    public List<Character> availablePlayersToMove(){
        List<Player> players = new ArrayList<>(match.getPlayers());
        List<Character> characters = new ArrayList<>();

        players.removeAll(movedPlayers);

        for (Player player : players){
            if (player.getPosition().reachableCells(1).contains(moveCell)){
                characters.add(player.getCharacter());
            }
        }
        return characters;
    }

    /**
     * this method check the damaged players and returns the ones that have tagback grenade powerup to ask
     * them if they want to use it
     * @return
     */
    public List<Player> playersWithCounterAttackPowerup(){

        List<Player> nextPlayers = new ArrayList<>();

        for (Player player: damagedPlayers){

            for (Powerup powerup: player.getPowerups()){

                if (powerup.isDuringDamageAction() && !powerup.isDuringYourTurn()){

                    nextPlayers.add(player);
                }
            }
        }
        return nextPlayers;
    }

}
