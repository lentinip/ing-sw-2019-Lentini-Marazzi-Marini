package it.polimi.sw2019.model;

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

    private Weapon[] weapons = new Weapon[3];

    private Powerup[] powerups = new Powerup[3];



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

    /**
     * You can know if a player is visible from this player
     * @param player The player you wanna know if is visible
     * @return true if is visible
     */
    public boolean canISee(Player player){

        //TODO implement

        //return false
        return true;
    }

    /**
     * Removes a weapon from the weapons array
     *
     * @param i The index of the weapon in the array
     */
    public void discardWeapon(int i){

        //TODO implement

    }

    /**
     * Adds a weapon into the weapons array
     *
     * @param weapon The weapon that has to be added to the array
     */
    public void addWeapon(Weapon weapon){ //Adds a weapon into the weapons array

        //TODO implement

    }

    public void useWeapon(Weapon weapon){

        //TODO implement

    }

    public void usePoweup(Powerup powerup){

        //TODO implement

    }

    public void addPowerup(Powerup powerup){

        //TODO implement

    }

    /**
     * Removes a powerup from the powerups array for using it as an ammo
     * @param i The powerup you want to discard
     */
    public void discardPowerup(int i){

        //TODO implement

    }
}
