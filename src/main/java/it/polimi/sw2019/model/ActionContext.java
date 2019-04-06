package it.polimi.sw2019.model;

/**
 *  Context of state pattern
 */

public class ActionContext {

    /**
     * Default constructor
     */

    public ActionContext(){}

    /* Attributes */

    /**
     * used to track the state of the player and know the action it can performs
     */

    private ComplexAction stateAction;

    /* Methods */

    public void setStateAction(ComplexAction stateAction) {
        this.stateAction = stateAction;
    }

    public ComplexAction getStateAction() {
        return stateAction;
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
}
