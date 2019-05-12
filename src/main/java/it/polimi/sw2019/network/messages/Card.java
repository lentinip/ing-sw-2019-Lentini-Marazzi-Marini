package it.polimi.sw2019.network.messages;

public class Card {

    /**
     * Default Constructor
     */
    public Card(){}

    /* Attributes */
    private boolean isWeapon;
    private int index;

    /* Methods */

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isWeapon() {
        return isWeapon;
    }

    public void setWeapon(boolean weapon) {
        isWeapon = weapon;
    }

}
