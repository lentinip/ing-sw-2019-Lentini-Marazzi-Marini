package it.polimi.sw2019.model;

public class AmmoTile extends Ammo {

    /**
     * Default constructor
     */
    public AmmoTile(){

    }

    /* Attributes */
    /**
     * Is true if there is a powerup in the ammo tile
     */
    private boolean powerup = false;

    /* Methods */

    public boolean getPowerup(){
        return this.powerup;
    }

    public void setPowerup(boolean powerup){
        this.powerup = powerup;
    }
}
