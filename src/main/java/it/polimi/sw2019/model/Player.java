package it.polimi.sw2019.model;
import java.util.ArrayList;
import java.util.List;

public class Player {

    /**
     *  Default constructor
     */
    public Player(){

    }



    /* Attributes */

    private String name;

    private Character character;

    private PlayerBoard playerBoard;

    private Cell position;

    private List<Weapon> weapons = new ArrayList<>();

    private List<Powerup> powerups = new ArrayList<>();

    private int numberOfActions = 2;

    private ActionContext action;


    /* Methods */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public int getNumberOfActions() {
        return numberOfActions;
    }

    public void setNumberOfActions(int numberOfActions) {
        this.numberOfActions = numberOfActions;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    /**
     * Removes a weapon from the weapons array
     *
     * @param i The index of the weapon in the array
     */
    public void discardWeapon(int i){

        weapons.remove(i);

    }

    /**
     * Adds a weapon into the weapons array
     *
     * @param weapon The weapon that has to be added to the array
     */
    public void addWeapon(Weapon weapon) { //Adds a weapon into the weapons array

        if (weapons.size() < 3)

           { weapons.add(weapon); }

        //TODO implement exceptions

    }

    public void useWeapon(Weapon weapon){

        weapon.setIsLoaded(false);

        //TODO implement exceptions

    }

    public void usePoweup(Powerup powerup){

        powerups.remove(powerup);

        //TODO implement exceptions

    }

    public void addPowerup(Powerup powerup){

        if (powerups.size() < 3)

           { powerups.add(powerup); }

        //TODO implements exceptions

    }

    /**
     * Removes a powerup from the powerups array for using it as an ammo
     * @param i The powerup you want to discard
     */
    public void discardPowerup(int i){

        if (powerups.size() > 0) {

               //TODO implement the reduction of the cost

               powerups.remove(i);
        }

    }

    public List<Weapon> loadedWeapons(){

      List<Weapon> loadedWeapons = new ArrayList<>();

      for (int i = 0; i < weapons.size(); i++ ) {

          if (weapons.get(i).getIsLoaded()) {

              loadedWeapons.add(weapons.get(i));

          }
      }
      return loadedWeapons;

      //TODO implement exceptions
    }

    /**
     * Checks if a weapon is loaded and if there are targets available for the weapons
     */
    public List<Weapon> availableWeapons(){

        List<Weapon> availableWeapons = new ArrayList<>();
        List<Weapon> loadedWeapons = loadedWeapons();

        for (int i = 0; i < loadedWeapons.size(); i++ ) {

            if (loadedWeapons.get(i).usableWeapon()) {

                availableWeapons.add(loadedWeapons.get(i));
            }
        }

        //TODO implement exceptions

        return availableWeapons;
    }

    public List<Cell> visibleCells(){

        List<Cell> visibleCells = new ArrayList<>();
        Colors positionColor = position.getRoom().getColor();

        visibleCells.addAll(position.getRoom().getRoomCells());

        /**
         * with these if condition I'm checking if the player is near one or more doors,
         * if the answer is "yes", I'm adding to visibleCells the cells inside the rooms he can watch in.
         */

        if (position.getUp().getRoom().getColor() != positionColor ) {

            visibleCells.addAll(position.getUp().getRoom().getRoomCells());
        }

        if (position.getLeft().getRoom().getColor() != positionColor ) {

            visibleCells.addAll(position.getLeft().getRoom().getRoomCells());
        }

        if (position.getDown().getRoom().getColor() != positionColor ) {

            visibleCells.addAll(position.getDown().getRoom().getRoomCells());
        }

        if (position.getRight().getRoom().getColor() != positionColor ) {

            visibleCells.addAll(position.getRight().getRoom().getRoomCells());
        }

        //TODO case where at up, down, left, right there is a wall and then Cell is null
        return visibleCells;
    }
}
