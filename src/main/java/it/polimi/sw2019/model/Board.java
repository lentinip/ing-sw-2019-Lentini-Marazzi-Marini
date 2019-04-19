package it.polimi.sw2019.model;

public class Board {

    /**
     * Default constructor
     */

    public Board(){

    }

    /* Attributes */

    private Cell[][] field = new Cell[4][3];

    private KillTokens killTrack;

    private Weapon[] weaponsDeck = new Weapon[21];

    private Powerup[] powerupsDeck = new Powerup[24];

    private Powerup[] powerupsDiscarded = new Powerup[24];

    private int powerupUsed = 0;

    private AmmoTile[] ammoDeck = new AmmoTile[36];

    private AmmoTile[] ammoDiscarded = new AmmoTile[36];

    private int ammoTileUsed = 0;

    private Room[] rooms = new Room[6];


    /* Methods */

    public int getPowerupUsed() {
        return powerupUsed;
    }

    public void setPowerupUsed(int powerupUsed) {
        this.powerupUsed = powerupUsed;
    }

    public int getAmmoTileUsed() {
        return ammoTileUsed;
    }

    public void setAmmoTileUsed(int ammoTileUsed) {
        this.ammoTileUsed = ammoTileUsed;
    }

    /**
     * Set the game board
     * @param i index of the board wanted for the match
     */
    public void setField(int i) {

            //TODO implement
    }

    public Weapon drawWeapon(){

        Weapon result = new Weapon();
        int index;

        for(index = 21; index>0; index--) {

            if(weaponsDeck[index] != null) {

                result = weaponsDeck[index];
                index = 1;
            }
        }

        return result;
    }

    public Powerup drawPowerup(){

        Powerup result = new Powerup();

        int index;

        for(index = 24; index>0; index--) {

            if(powerupsDeck[index] != null) {

                result = powerupsDeck[index];
                index = 1;
            }
        }

        return result;
    }

    public AmmoTile drawAmmo(){

        AmmoTile result = new AmmoTile();

        int index;

        for(index = 36; index>0; index--) {

            if(ammoDeck[index] != null) {

                result = ammoDeck[index];
                index = 1;
            }
        }

        return result;
    }

    public void updateKillTrack (Player deadPlayer) {

        //TODO implement
    }

}
