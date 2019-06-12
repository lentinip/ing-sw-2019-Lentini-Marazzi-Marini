package it.polimi.sw2019.model;

import it.polimi.sw2019.network.messages.BoardCoord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    /**
     * default constructor
     */
    public Board(){
        //default constructor
    }

    /* Attributes */

    private List<Cell> field = new ArrayList<>();

    private KillTokens killTrack;

    private List<Weapon> weaponsDeck = new ArrayList<>(); //Size: 21

    private List<Powerup> powerupsDeck = new ArrayList<>(); //Size: 24

    private List<Powerup> powerupsDiscarded = new ArrayList<>(); //Size: 24

    private List<AmmoTile> ammoDeck = new ArrayList<>(); //Size: 36

    private List<AmmoTile> ammoDiscarded = new ArrayList<>(); //Size: 36

    private List<Room> rooms = new ArrayList<>();


    /* Methods */

    public void setKillTrack(KillTokens killTrack) {
        this.killTrack = killTrack;
    }

    public KillTokens getKillTrack() {
        return killTrack;
    }

    public void setField(List<Cell> field) {
        this.field = field;
    }

    public List<Cell> getField() {
        return field;
    }

    public void setAmmoDeck(List<AmmoTile> ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    public void setPowerupsDeck(List<Powerup> powerupsDeck) {
        this.powerupsDeck = powerupsDeck;
    }

    public void setWeaponsDeck(List<Weapon> weaponsDeck) {
        this.weaponsDeck = weaponsDeck;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Weapon> getWeaponsDeck() {
        return weaponsDeck;
    }

    public List<Powerup> getPowerupsDeck() {
        return powerupsDeck;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Return a reference of Cell giving the row and the column
     * @param cellRow cell's row
     * @param cellColumn cell's column
     * @return a reference of Cell (can be null)
     */
    public Cell getCell(int cellRow, int cellColumn){
        for(Cell cell: field){
            if(cell.getRow()==cellRow && cell.getColumn()==cellColumn){
                return cell;
            }
        }
        return null;
    }

    public Cell getCell(BoardCoord boardCoord){
        int cellRow = boardCoord.getRow();
        int cellColumn = boardCoord.getColumn();
        return getCell(cellRow,cellColumn);
    }

    /**
     * Return a reference of Room giving a color (Colors)
     * @param color color of the room
     * @return the room with the input color
     */
    public Room getRoomByColor(Colors color){
        for (Room room : rooms){
            if (room.getColor()==color){
                return room;
            }
        }
        return null;
    }

    public boolean weaponsDeckIsEmpty(){
        return weaponsDeck.isEmpty();
    }

    public Weapon drawWeapon(){
        if (!weaponsDeck.isEmpty()){
            Weapon result = weaponsDeck.get(weaponsDeck.size()-1);
            weaponsDeck.remove(result);
            return result;
        }
        else {
            return null;
        }
    }

    /**
     * Draws a powerup from the powerupDeck and than if is empty it restores it (shuffling it)
     * @return a Powerup
     */
    public Powerup drawPowerup(){

        if (!powerupsDeck.isEmpty()){
            Powerup result = powerupsDeck.get(powerupsDeck.size()-1);
            powerupsDeck.remove(result);

            //If the pile of powerups is empty the powerupDeck needs to be restored
            if (powerupsDeck.isEmpty()){
                powerupsDeck.addAll(powerupsDiscarded);

                //Let's shuffle the powerupsDeck
                Collections.shuffle(powerupsDeck);

                //Let's clear the discarded pile
                powerupsDiscarded.clear();
            }

            return result;
        }

        else {
            return null;
        }
    }

    /**
     * Draws an AmmoTile from the ammoDeck and than if is empty it restores it (shuffling it)
     * @return ammotile
     */
    public AmmoTile drawAmmo(){

        if (!ammoDeck.isEmpty()){
            AmmoTile result = ammoDeck.get(ammoDeck.size()-1);
            ammoDeck.remove(result);

            //If the pile of AmmoTile is empty the AmmoTileDeck needs to be restored
            if (ammoDeck.isEmpty()){
                ammoDeck.addAll(ammoDiscarded);

                //Let's shuffle the ammoDeck
                Collections.shuffle(ammoDeck);

                //Let's clear the discarded pile
                ammoDiscarded.clear();
            }

            return result;
        }

        else {
            return null;
        }
    }

    /**
     * Adds a powerup to the discarded pile
     * @param powerupToDiscard powerup to discard
     */
    public void discardPowerup(Powerup powerupToDiscard){

        //Adds the powerup to the discarded ones
        powerupsDiscarded.add(powerupToDiscard);
    }

    /**
     * Adds an AmmoTile to the discarded pile
     * @param ammoTileToDiscard AmmoTile to discard
     */
    public void discardAmmo(AmmoTile ammoTileToDiscard){

        //Adds the ammo to the discarded ones
        ammoDiscarded.add(ammoTileToDiscard);
    }

    /**
     * updates the kill track after a player dies
     * @param deadPlayer dead player
     */
    public void updateKillTrack (Player deadPlayer) {

        if(deadPlayer == null) {
            throw new NullPointerException("updateKillTrack parameter can't be null");
        }

        if (deadPlayer.getPlayerBoard().getDamage().getTotalDamage() == 11){

            killTrack.addKill(deadPlayer.getPlayerBoard().getDamage().getDamageSequence().get(10));

        }
         else if (deadPlayer.getPlayerBoard().getDamage().getTotalDamage() == 12){

            killTrack.addOverkill(deadPlayer.getPlayerBoard().getDamage().getDamageSequence().get(11));

        }

    }
}
