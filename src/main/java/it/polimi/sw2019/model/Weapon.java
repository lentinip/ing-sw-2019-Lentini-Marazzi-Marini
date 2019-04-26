package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

    /**
     * Default constructor
     */

    public Weapon() {

    }

    /* Attributes */

    private String name;

    private String description;

    private Ammo grabCost;

    private Ammo reloadCost;

    private boolean isLoaded;

    private Player owner;

    private WeaponsType type;

    private List<Effect> effects = new ArrayList<>();

    private boolean hasAnOrder;          /* weapon's effects must be executed in a precise order */

    private boolean hasAMoveTypeEffect;  /* There is an only move optional effect */

    /* Methods */

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
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

    public void setReloadCost(Ammo reloadCost) {
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

    public WeaponsType getType() {
        return type;
    }

    public void setType(WeaponsType type) {
        this.type = type;
    }

    public boolean isOrdered() {
        return hasAnOrder;
    }

    public void setHasAnOrder(boolean hasAnOrder) {
        this.hasAnOrder = hasAnOrder;
    }

    public boolean getHasAMoveTypeEffect() {
        return hasAMoveTypeEffect;
    }

    public void setHasAMoveTypeEffect(boolean hasAMoveTypeEffect) {
        this.hasAMoveTypeEffect = hasAMoveTypeEffect;
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
