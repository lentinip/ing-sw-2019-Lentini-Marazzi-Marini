package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class MoveEffect {


    /**
     * Default Constructor
     */
    public MoveEffect(){};

    /* Attributes */

    private int moveYou; /* how many spaces you can move */

    private int moveTargets; /* how many spaces you can move every target chosen */

    private int maxTargets; /* number of targets you can move */

    private boolean moveTargetBefore; /* true if you have to move the target before the shoot*/

    private boolean moveTargetAfter; /* true if you have to move the target after the shoot */

    private boolean moveTargetSameDirection; /* true if you have to move the target in the same direction */

    private boolean moveTargetOnYourSquare; /* useful for tractorBeam second effect, tells if you have to move the target on your square */

    private KindOfVisibility visibility;  /* used to know which targets you can move */

    private boolean moveYouBefore; /* true if you have to move before the shoot action */

    private boolean moveYouAfter; /* true if you have to move after the shoot action for POWER GLOVE (in last target square)*/

    private boolean moveYouSameDirection; /* true if you have to move in the same direction */

    private boolean obligatoryYou; /* true if you must move yourself */

    private boolean obligatoryTarget; /* true if you must move your target */


    /* Methods */

    public void setMaxTargets(int maxTargets) {
        this.maxTargets = maxTargets;
    }

    public int getMaxTargets() {
        return maxTargets;
    }

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

    public KindOfVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(KindOfVisibility visibility) {
        this.visibility = visibility;
    }

    public boolean isMoveTargetOnYourSquare() {
        return moveTargetOnYourSquare;
    }

    public void setMoveTargetOnYourSquare(boolean moveTargetOnYourSquare) {
        this.moveTargetOnYourSquare = moveTargetOnYourSquare;
    }

    /**
     * Method that tells you if you can choose when to move the targets
     */
    public boolean iCanChooseWhenMoveTarget(){

        if (!moveTargetBefore && !moveTargetAfter && moveTargets > 0 ) { return true; }

        else { return false; }
    }

    /**
     * Method that tells you if you can choose when to do the move
     */
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

    /**
     * tells if the class contains a moveBefore effect
     * @return boolean
     */
    public boolean iHaveAMoveBefore(){

        if (moveTargetBefore || moveYouBefore){

            return true;
        }

        return false;
    }

    /**
     * tells if class contains a move after effect
     */
    public boolean iHaveAMoveAfter(){

        if ( moveTargetAfter || moveYouAfter){

            return true;
        }

        return false;
    }

    /**
     * called by Choices used for newton powerup and moveAfter effects
     * @param selectedPlayer the player to move
     * @return the list of cells where you can move the selected player
     */
    public List<Cell> availableCellsMoveTarget(Player selectedPlayer){

        Cell playerPosition = selectedPlayer.getPosition();
        List<Cell> availableCells = playerPosition.reachableCells(moveTargets);

        // if I have to move the target of at least one spot I remove the target starting position from the list
        if (obligatoryTarget){
            availableCells.remove(playerPosition);
        }

        // if I have to move the player in the same direction I accept only the cells that have the same column or the same row of the starting position cell
        if (moveTargetSameDirection){

            for (Cell cell: availableCells){

                if ( cell.getRow() != playerPosition.getRow() && cell.getColumn() != playerPosition.getColumn()){
                    availableCells.remove(cell);
                }
            }
        }

        return availableCells;
    }

    /**
     * this method is called by controller (Choices)
     * used for the moveBeforeShoot
     * @param allPlayers all Players
     * @param  owner the owner of the weapon
     * @return a list of characters that can be moved applying the move effect
     */
    public List<Character> availablePlayersToMove(List<Player> allPlayers, Player owner){

        List<Character> availablePlayers = new ArrayList<>();

        /* removing the owner from the player in a new list */
        List<Player> targets = new ArrayList<>(allPlayers);
        targets.remove(owner);

        for (Player player: targets){

            List<Cell> reachableCells = player.getPosition().reachableCells(moveTargets);

            /* returning all the characters that can be moved on a cell you can see */
            if (visibility == KindOfVisibility.VISIBLE && !moveTargetOnYourSquare){

                for (Cell cell: reachableCells){

                    if (player.visibleCells().contains(cell)){

                        availablePlayers.add(player.getCharacter());
                        break; // to avoid duplicates
                    }
                }
            }

            /* returning all the characters that can be moved on your square */
            else if (moveTargetOnYourSquare){

                for (Cell cell: reachableCells){

                    if (cell == owner.getPosition()){

                        availablePlayers.add(player.getCharacter());
                        break; // to avoid duplicates
                    }
                }
            }

        }

        return availablePlayers;
    }
}

