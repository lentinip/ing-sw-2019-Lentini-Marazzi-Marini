package it.polimi.sw2019.model;

/**
 * @author lentinip
 * this class is used as a structure to contain infos about the tiles on the board
 */
public class AmmoTile extends Ammo {

    /**
     * Default constructor
     */
    public AmmoTile(){

        setBlue(0);
        setRed(0);
        setYellow(0);
    }

    /**
     * customized constructor
     * @param blue ammo blue
     * @param red ammo red
     * @param yellow ammo yellow
     * @param powerup if it has a powerup or not
     */
    public AmmoTile(int blue, int red, int yellow, boolean powerup) {

        setBlue(blue);
        setRed(red);
        setYellow(yellow);
        setPowerup(powerup);
    }

    /* Attributes */
    /**
     * Is true if there is a powerup in the ammo tile
     */
    private boolean powerup = false;

    private String ammoTileImg;

    /* Methods */

    public boolean isPowerup(){
        return this.powerup;
    }

    public void setPowerup(boolean powerup){
        this.powerup = powerup;
    }

    public String getAmmoTileImg() {
        return ammoTileImg;
    }

}
