package it.polimi.sw2019.model;

public class Weapon {

    /**
     * Default constructor
     */

    public Weapon() {

    }

    /* Attributes */

    private String name;

    private Ammo grabCost;

    private Ammo reloadCost;

    private boolean isLoaded;

    private Player owner;

    /* Methods */

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Ammo getGrabCost() {
        return grabCost;
    }

    public void setGrabCost(Ammo grabCost) {
        this.grabCost = grabCost;
    }

    public Ammo getReloadCost() {
        return reloadCost;
    }

    public void setReloadCost(Ammo grabCost) {
        this.reloadCost = reloadCost;
    }

    public boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void effect() {  // use the weapon effect

        //TODO implement

        return;
    }

    /**
     * Calculates if is possible to use this weapon by the owner
     *
     * @return true if the weapon is usable, false if not
     */
    public boolean usableWeapon(){

        boolean result = false;

        //TODO implement

        return result;
    }

    /**
     * Calculates the cells where this weapon can shoot
     *
     * @return array of Cell with the cells where the owner can shoot
     */
    public Cell[] shootableCells(){

        Cell[] result = new Cell[1];

        //TODO implement

        return result;
    }
}
