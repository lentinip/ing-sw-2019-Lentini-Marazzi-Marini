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

    public void setAmmoTileImg(String ammoTileImg) {
        this.ammoTileImg = ammoTileImg;
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
