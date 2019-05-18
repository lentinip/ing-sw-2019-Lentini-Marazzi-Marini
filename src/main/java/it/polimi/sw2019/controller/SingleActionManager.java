package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.BoardCoord;
import it.polimi.sw2019.network.messages.GrabWeapon;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

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
        this.shootingChoices = new ShootingChoices(match, view);
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private AtomicActions atomicActions;

    private TurnManager turnManager;

    private ShootingChoices shootingChoices;

    /* Methods */

    public Match getMatch() {
        return match;
    }

    public ShootingChoices getShootingChoices() {
        return shootingChoices;
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
                grabWeaponHandler(message);
                break;
            case MOVEBEFORESHOOT:
                moveBeforeShootHandler(message);
                break;
            case MOVEAFTERSHOOT:
                moveAfterShootHandler(message);
                break;
            case USEPOWERUP:
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
        atomicActions.grab(player, match.getBoard().getCell(selection));

        reducePlayerNumberOfActions();
    }

    public void grabWeaponHandler(Message message){
        Player player = match.getPlayerByUsername(message.getUsername());
        //TODO implement

    }

    public void moveBeforeShootHandler(Message message){
        //TODO implement
    }

    public void moveAfterShootHandler(Message message){
        //TODO implement
    }



    //The following methods do the action calling the action function in the AtomicAction class

    /*
    public void grab(){

        if (selectedCell.isCommon()){
            //Grab
            atomicActions.grab(currentPlayer, selectedCell);
            //Add the cell to a list of empty cells (for the end of the turn)
            turnManager.getEmptyCommonCells().add(selectedCell);
        }

        else {
            //TODO manage if the player has to replace a weapon
            //Grab
            atomicActions.grabWeapon(currentPlayer, selectedCell, weaponIndex);
            //Add the cell to a list of empty cells (for the end of the turn)
            turnManager.getEmptySpawnCells().add(selectedCell);
        }
    }


     */

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

}
