package it.polimi.sw2019.model;

public class MoveEffect {


    /**
     * Default Constructor
     */
    public MoveEffect(){};

    /* Attributes */

    private int moveYou; /* how many spaces you can move */

    private int moveTargets; /* how many spaces you can move every target chosen */

    private boolean moveTargetBefore; /* true if you have to move the target before the shoot*/

    private boolean moveTargetAfter; /* true if you have to move the target after the shoot */

    private boolean moveTargetSameDirection; /* true if you have to move the target in the same direction */

    private boolean moveYouBefore; /* true if you have to move before the shoot action */

    private boolean moveYouAfter; /* true if you have to move after the shoot action */

    private boolean moveYouSameDirection; /* true if you have to move in the same direction */

    private boolean obligatoryYou; /* true if you must move yourself */

    private boolean obligatoryTarget; /* true if you must move your target */


    /* Methods */

    public int getMoveYou() {
        return moveYou;
    }

    public void setMoveYou(int moveYou) {
        this.moveYou = moveYou;
    }

    public int getMoveTargets() {
        return moveTargets;
    }

    public void setMoveTargets(int moveTargets) {
        this.moveTargets = moveTargets;
    }

    public boolean isMoveTargetBefore() {
        return moveTargetBefore;
    }

    public void setMoveTargetBefore(boolean moveTargetBefore) {
        this.moveTargetBefore = moveTargetBefore;
    }

    public boolean isMoveTargetAfter() {
        return moveTargetAfter;
    }

    public void setMoveTargetAfter(boolean moveTargetAfter) {
        this.moveTargetAfter = moveTargetAfter;
    }

    public boolean isMoveTargetSameDirection() {
        return moveTargetSameDirection;
    }

    public void setMoveTargetSameDirection(boolean moveTargetSameDirection) {
        this.moveTargetSameDirection = moveTargetSameDirection;
    }

    public boolean isMoveYouBefore() {
        return moveYouBefore;
    }

    public void setMoveYouBefore(boolean moveYouBefore) {
        this.moveYouBefore = moveYouBefore;
    }

    public boolean isMoveYouAfter() {
        return moveYouAfter;
    }

    public void setMoveYouAfter(boolean moveYouAfter) {
        this.moveYouAfter = moveYouAfter;
    }

    public boolean isMoveYouSameDirection() {
        return moveYouSameDirection;
    }

    public void setMoveYouSameDirection(boolean moveYouSameDirection) {
        this.moveYouSameDirection = moveYouSameDirection;
    }

    public boolean isObligatoryYou() {
        return obligatoryYou;
    }

    public void setObligatoryYou(boolean obligatoryYou) {
        this.obligatoryYou = obligatoryYou;
    }

    public boolean isObligatoryTarget() {
        return obligatoryTarget;
    }

    public void setObligatoryTarget(boolean obligatoryTarget) {
        this.obligatoryTarget = obligatoryTarget;
    }

    /**
     * Method that tells you if you can choose when to do the move
     */
    public boolean iCanChooseWhenMoveTarget(){

        if (!moveTargetBefore && !moveTargetAfter ) { return true; }

        else { return false; }
    }

    public boolean iCanChooseWhenMove(){

        if (!moveYouBefore && !moveYouAfter) { return true; }

        else { return false; }
    }

    public boolean iCanMoveBefore(){

        if (moveYouBefore || iCanChooseWhenMove()) { return true; }

        else { return false; }
    }

    public boolean iCanMoveTargetBefore(){

        if (moveTargetBefore || iCanChooseWhenMoveTarget()) { return true; }

        else { return false; }
    }
}

