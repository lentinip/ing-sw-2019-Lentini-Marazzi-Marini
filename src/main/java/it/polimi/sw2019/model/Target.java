package it.polimi.sw2019.model;

/**
 * this class contains information about the target choices that the weapon user can make
 */
public class Target {

    /**
     * Default Constructor
     */
    public Target(){}

    /* Attributes */

    private int[] damages;

    private int[] marks;

    private boolean differentSquares; /* true if the targets have to be on different squares */

    private boolean sameSquare; /* true if the targets have to be on the same square SEE HELLION */

    private boolean isRoom; /* true if you have to choose a room SEE FURNACE */

    private boolean differentPlayers = false; /* true if you have to shoot to a different player */

    private int maxTargets; /* max number of targets you can hit */

    private boolean forcedChoice; /* if the player can't choose the targets SEE SHOCKWAVE tsunami mode */

    /* methods */

    public int[] getDamages() {
        return damages;
    }

    public void setDamages(int[] damages) {
        this.damages = damages;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public boolean isDifferentSquares() {
        return differentSquares;
    }

    public void setDifferentSquares(boolean differentSquares) {
        this.differentSquares = differentSquares;
    }

    public boolean isDifferentPlayers() {
        return differentPlayers;
    }

    public void setDifferentPlayers(boolean differentPlayers) {
        this.differentPlayers = differentPlayers;
    }

    public int getMaxTargets() {
        return maxTargets;
    }

    public void setMaxTargets(int maxTargets) {
        this.maxTargets = maxTargets;
    }

    public void setSameSquare(boolean sameSquare) {
        this.sameSquare = sameSquare;
    }

    public boolean isSameSquare() {
        return sameSquare;
    }

    public void setForcedChoice(boolean forcedChoice) {
        this.forcedChoice = forcedChoice;
    }

    public boolean isForcedChoice() {
        return forcedChoice;
    }

    public boolean isRoom() {
        return isRoom;
    }

    public void setRoom(boolean room) {
        isRoom = room;
    }
}
