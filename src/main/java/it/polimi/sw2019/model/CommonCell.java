package it.polimi.sw2019.model;

public class CommonCell extends Cell {

    /**
     * Default constructor
     */

    public CommonCell() {

    }

    /* Attributes */

    private AmmoTile ammo;

    private boolean isEmpty;

    /* Methods */

    public AmmoTile getAmmo() {
        return ammo;
    }

    public void setAmmo(AmmoTile ammoTile){ this.ammo = ammoTile; }

    public boolean isEmpty () {
        return isEmpty;
    }

    public void setIsEmpty(boolean empty) {
        this.isEmpty = empty;
    }
}
