package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.BoardCoord;
import it.polimi.sw2019.network.messages.GrabWeapon;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles a single action
 */
public class SingleActionManager {

    /**
     * Default Constructor
     */
    public SingleActionManager(Match match, VirtualView view, TurnManager turnManager) {
        this.match = match;
        this.view = view;
        this.turnManager = turnManager;
        this.atomicActions = new AtomicActions(match);
        this.choices = new Choices(match, view, payment, atomicActions);
        this.payment = new Payment(match, view, this);
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private AtomicActions atomicActions;

    private TurnManager turnManager;

    private Choices choices;

    private Payment payment;

    /* Methods */

    public Match getMatch() {
        return match;
    }

    public AtomicActions getAtomicActions() {
        return atomicActions;
    }

    public Choices getChoices() {
        return choices;
    }

    public Payment getPayment() {
        return payment;
    }

    public void timer()  {

        //TODO implement
        return;
    }

    public void reset()  {

        //TODO implement
        return;
    }

    public void singleActionHandler(Message message){

        switch (message.getTypeOfAction()){
            case MOVE:
                moveHandler(message);
                break;
            case GRAB:
                grabHandler(message);
                break;
            case GRABWEAPON:
            case RELOAD:
                payment.paymentStarter(message);
                break;
            case MOVEBEFORESHOOT:
                moveBeforeShootHandler(message);
                break;
            case MOVEAFTERSHOOT:
                moveAfterShootHandler(message);
                break;
            case USEPOWERUP:
                usePowerupHandler(message);
                break;
            default:
        }
    }

    /**
     * Handles the move and reduces the number of the player actions (handles the message too)
     * @param message
     */
    public void moveHandler(Message message){

        Player player = match.getPlayerByUsername(message.getUsername());
        BoardCoord selection = message.deserializeBoardCoord();
        atomicActions.move(player, match.getBoard().getCell(selection));

        reducePlayerNumberOfActions();
    }

    public void grabHandler(Message message){

        Player player = match.getPlayerByUsername(message.getUsername());
        BoardCoord selection = message.deserializeBoardCoord();
        Cell selectedCell = match.getBoard().getCell(selection);
        atomicActions.grab(player, selectedCell);

        //Add the cell to a list of empty cells (for the end of the turn)
        turnManager.getEmptyCommonCells().add(selectedCell);

        reducePlayerNumberOfActions();
    }

    public void grabWeaponHandler(Message message){

        Player player = match.getPlayerByUsername(message.getUsername());
        GrabWeapon grabWeapon = message.deserializeGrabWeapon();
        Cell spawnCell = match.getBoard().getCell(grabWeapon.getSpawnCell());


        //If there's a weapon to discard
        if(grabWeapon.getDiscardedWeapon()>=0){
            atomicActions.grabWeaponAndReplace(player,spawnCell, grabWeapon.getGrabbedWeapon(), grabWeapon.getDiscardedWeapon());
        }
        else {
            atomicActions.grabWeapon(player, spawnCell, grabWeapon.getGrabbedWeapon());
        }

        //Add the cell to a list of empty cells (for the end of the turn)
        turnManager.getEmptySpawnCells().add(spawnCell);

        reducePlayerNumberOfActions();

    }

    public void moveBeforeShootHandler(Message message){

        Message answer = new Message(message.getUsername());

        //First I deserialize the message
        Player player = match.getPlayerByUsername(message.getUsername());
        BoardCoord boardCoord = message.deserializeBoardCoord();
        Cell selectedCell = match.getBoard().getCell(boardCoord);

        //Check if is a Move&Shoot
        if (choices.getSelectedWeapon()==null){
            atomicActions.move(player, selectedCell);
            List<IndexMessage> indexMessageList = createShootMessage(player);
            answer.createAvailableCardsMessage(TypeOfAction.SHOOT, indexMessageList, true);

            view.display(answer);
        }

        // check if I have to move myself
        else if (choices.getCurrentEffect().getMove().isMoveYouBefore()){
            atomicActions.move(player, selectedCell);
            //TODO check after implementation of effectHandler
            choices.effectHandler();
        }

        // check if I have to move other players
        else if (choices.getCurrentEffect().getMove().isMoveTargetBefore()){
            //Sets the moveCell in Choices
            choices.setMoveCell(selectedCell);

            //Moves the selectedPlayer
            atomicActions.move(choices.getSelectedPlayer(), selectedCell);

            //The player than is added to the movedPlayers
            choices.getMovedPlayers().add(choices.getSelectedPlayer());

            //Than calculates the player that can be moved
            if (choices.getMovedPlayers().size() < choices.getCurrentEffect().getMove().getMaxTargets()){
                List<Character> characterList = choices.availablePlayersToMove();
                answer.createAvailablePlayers(TypeOfAction.MOVEBEFORESHOOT, characterList);
                view.display(answer);
            }
            else {
                //TODO check after implementation of effectHandler
                choices.effectHandler();
            }
        }

    }

    public void moveAfterShootHandler(Message message){
        //TODO implement
    }

    public void usePowerupHandler(Message message){
        //TODO implement
    }

    /**
     * This method reduces the current player left actions and display if the player can shoot
     */
    public void reducePlayerNumberOfActions(){

        Player player = match.getCurrentPlayer();

        match.setCurrentPlayerLeftActions(match.getCurrentPlayerLeftActions()-1);

        Message message = new Message(player.getName());

        if(match.getCurrentPlayerLeftActions()>0){
            message.createMessageCanIShoot(player.canIshootBeforeComplexAction());
        }
        else {
            message.createMessageCanIShoot(false);
        }

        view.display(message);
    }

    public List<IndexMessage> createReloadMessage(Player currentPlayer){
        List<Weapon> reloadableWeapons = currentPlayer.reloadableWeapons();
        List<IndexMessage> indexMessageList = new ArrayList<>();

        for (Weapon weapon : reloadableWeapons){
            indexMessageList.add(new IndexMessage(currentPlayer.getWeaponIndex(weapon)));
        }

        return indexMessageList;
    }

    public List<IndexMessage> createShootMessage(Player currentPlayer){
        List<Weapon> usableWeapon = currentPlayer.availableWeapons();
        List<IndexMessage> indexMessageList = new ArrayList<>();

        for (Weapon weapon : usableWeapon){
            indexMessageList.add(new IndexMessage(currentPlayer.getWeaponIndex(weapon)));
        }

        return indexMessageList;
    }

    public void reloadHandler(Message message){
        IndexMessage indexMessage = message.deserializeIndexMessage();
        Player player = match.getCurrentPlayer();
        Weapon weapon = player.getWeaponFromIndex(indexMessage.getSelectionIndex());

        atomicActions.reload(player, weapon);

        List<IndexMessage> indexMessageList = createReloadMessage(player);

        Message answer = new Message(player.getName());

        //The view checks if the indexMessageList is empty
        answer.createAvailableCardsMessage(TypeOfAction.RELOAD, indexMessageList, true);

        view.display(answer);
    }

}
