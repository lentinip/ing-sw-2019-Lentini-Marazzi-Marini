package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Character;

import java.util.List;

/**
 * @author poligenius
 * this class contains infos about a player board
 */
public class PlayerBoardMessage {

    public PlayerBoardMessage(){}

    /* Attributes */

    private List<Character> damageSequence;

    private List<Character> markSequence;

    private boolean firstPlayer;

    private boolean flipped;

    private int numOfDeaths;

    private int redAmmo;

    private int blueAmmo;

    private int yellowAmmo;

    /* Methods */

    public int getBlueAmmo() {
        return blueAmmo;
    }

    public int getNumOfDeaths() {
        return numOfDeaths;
    }

    public int getRedAmmo() {
        return redAmmo;
    }

    public int getYellowAmmo() {
        return yellowAmmo;
    }

    public List<Character> getDamageSequence() {
        return damageSequence;
    }

    public List<Character> getMarkSequence() {
        return markSequence;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setDamageSequence(List<Character> damageSequence) {
        this.damageSequence = damageSequence;
    }

    public void setMarkSequence(List<Character> markSequence) {
        this.markSequence = markSequence;
    }

    public void setBlueAmmo(int blueAmmo) {
        this.blueAmmo = blueAmmo;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setNumOfDeaths(int numOfDeaths) {
        this.numOfDeaths = numOfDeaths;
    }

    public void setRedAmmo(int redAmmo) {
        this.redAmmo = redAmmo;
    }

    public void setYellowAmmo(int yellowAmmo) {
        this.yellowAmmo = yellowAmmo;
    }
}
