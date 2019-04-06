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

    public void setAmmo(AmmoTile ammoTile){   //draw an Ammo card and put it in the Cell

        //TODO implement

    }

    public boolean getIsEmpty () {
        return isEmpty;
    }

    public void setIsEmpty(boolean empty) {
        this.isEmpty = empty;
    }
}
