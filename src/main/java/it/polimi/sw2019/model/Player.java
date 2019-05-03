package it.polimi.sw2019.model;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;

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

    private State state;

    private Boolean isDead;


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

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
    }

    public Boolean isDead() {
        return isDead;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
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

        if(weapon == null)
            throw new NullPointerException("'weapon' can't be null");

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
     * @param powerupIndex The powerup you want to discard
     */
    public void discardPowerup(int powerupIndex){

        if (powerups.size() > 0) {
               powerups.remove(powerupIndex);
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
     *
     * @return true if I can reach and shoot any player (considering move actions and reload in FRENZY action)
     */
    public Boolean canIshootBeforeComplexAction() {

        List<Player> allPlayers = position.getRoom().getPlayers();

        if (state == State.FRENZYAFTERFIRST || state == State.FRENZYBEFOREFIRST) {

           for (Weapon weapon: weapons){

               if( weapon.usableWeaponBeforeComplexAction(allPlayers) ){

                   if ( weapon.getIsLoaded()){

                       return true;
                   }

                   else {

                       if (canIPay(weapon.getReloadCost())){

                           return true;
                       }
                   }
               }
           }

           return false;
        }

        else {

            for (Weapon weapon : loadedWeapons()) {

                if (weapon.usableWeaponBeforeComplexAction(allPlayers)) {

                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Checks if a weapon is loaded and if there are targets available for the weapons
     */
    public List<Weapon> availableWeapons(){

        List<Weapon> availableWeapons = new ArrayList<>();
        List<Weapon> loadedWeapons = loadedWeapons();

        for (int i = 0; i < loadedWeapons.size(); i++ ) {

            if (loadedWeapons.get(i).usableWeapon(position.getRoom().getPlayers())) {

                availableWeapons.add(loadedWeapons.get(i));
            }
        }

        if (availableWeapons.isEmpty()) {

            throw new NullPointerException("Can't use any weapon to shoot in this position");
        }

        return availableWeapons;
    }

    public List<Cell> visibleCells(){

        List<Cell> visibleCells = new ArrayList<>();
        Colors positionColor = position.getRoom().getColor();

        visibleCells.addAll(position.getRoom().getRoomCells());

        /*
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

    /**
     * I wrote this method to simplify the method canIPay() it considers the powerUps in the total count of the Ammo
     * @return an array of 3 int: (index 0: total red Ammo, index 1: total blue Ammo, index 2: total yellowAmmo)
     */
    public int[] virtualAmmo(){

        int virtualAmmo[] = new int[3];

        virtualAmmo[0] = playerBoard.getAmmo().getRed();
        virtualAmmo[1] = playerBoard.getAmmo().getBlue();
        virtualAmmo[2] = playerBoard.getAmmo().getYellow();

        if (!powerups.isEmpty()) {

            for(int i = 0; i < powerups.size(); i++){

                if (powerups.get(i).getColor() == Colors.RED) {

                    virtualAmmo[0]++;
                }

                else if (powerups.get(i).getColor() == Colors.BLUE){

                    virtualAmmo[1]++;
                }

                else if (powerups.get(i).getColor() == Colors.YELLOW){

                    virtualAmmo[2]++;
                }
            }
        }

        return virtualAmmo;
    }

    /**
     *
     * @param cost the cost I have to pay
     * @return true if I can pay the cost (considering also powerup)
     */
    public Boolean canIPay(Ammo cost){

      int virtualAmmo[] = virtualAmmo();

      if(virtualAmmo[0] >= cost.getRed() && virtualAmmo[1] >= cost.getBlue() && virtualAmmo[2] >= cost.getYellow()){

          return true;
      }

      else return false;
    }

    /**
     * this method has to be called only when I'm sure I can pay the cost ( canIpay returns true )
     * @param cost
     * @return the powerups that I can use to pay a cost, returns an empty list if I don't have powerups or they are not of the right color
     */
    public List<Powerup> payingPoweups(Ammo cost){

        List<Powerup> payingPoweups = new ArrayList<>();

        for (int i = 0; i < powerups.size(); i++){

            if ( powerups.get(i).useToPay(cost) ){

                payingPoweups.add(powerups.get(i));
            }
        }

        return payingPoweups;
    }
}
