package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Player;

/**
 *  Context of state pattern
 */

public class ActionHandler {

    /**
     * Default constructor
     */

    public ActionHandler(){}

    /* Attributes */

    /**
     * used to track the state of the player and know the action it can performs
     */

    private ComplexAction stateAction;
    private Player player;

    /* Methods */

    public void setStateAction(ComplexAction stateAction) {
        this.stateAction = stateAction;
    }

    public ComplexAction getStateAction() {
        return stateAction;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void moveAction(){

        //TODO implement
    }

    public void moveAndGrab(){

        //TODO implement
    }

    public void shoot(){

        //TODO implement
    }

    /**
     * asks player if he wants to do a move, moveandgrab or moveandshoot and calls the method
     */
    public void askKindOfAction(){

        //TODO implement here
    }
}
