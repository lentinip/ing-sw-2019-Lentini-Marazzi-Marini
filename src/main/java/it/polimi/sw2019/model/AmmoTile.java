package it.polimi.sw2019.model;

public class AmmoTile extends Ammo {

    /**
     * Default constructor
     */
    public AmmoTile(){

        setBlue(0);
        setRed(0);
        setYellow(0);
    }

    /* Attributes */
    /**
     * Is true if there is a powerup in the ammo tile
     */
    private boolean powerup = false;

    /* Methods */

    public boolean isPowerup(){
        return this.powerup;
    }

    public void setPowerup(boolean powerup){
        this.powerup = powerup;
    }


    /**
     * used by the constructor class Factory
     * @param red
     * @param blue
     * @param yellow
     * @param powerup
     */
    public void setAmmoTileStructure(int red, int blue, int yellow, boolean powerup){

        this.setRed(red);
        this.setBlue(blue);
        this.setYellow(yellow);
        this.setPowerup(powerup);
    }
}
