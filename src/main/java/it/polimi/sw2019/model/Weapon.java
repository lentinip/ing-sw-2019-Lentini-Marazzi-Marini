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

    private boolean isLoaded = true;

    private Player owner = null;

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
     * useful in usableWeapon and probably in future too...
     * @return the MOVE typer effect if the weapon has one
     */
    public Effect getMoveTypeEffect(){

        for (Effect effect: effects){

            if (effect.getType() == EffectsKind.MOVE){

                return effect;
            }
        }

        throw new NullPointerException("This Weapon has no Move type effect");
    }

    /**
     * Call this method before showing the possibility to do a shooting action
     * @param allPlayers
     * @return
     */
    public boolean usableWeaponBeforeComplexAction(List<Player> allPlayers){

        State currentState = owner.getState();

        if (currentState == State.ADRENALINIC2 || currentState == State.FRENZYBEFOREFIRST){

            Cell startingPosition = owner.getPosition(); /* saving the starting position */

            Player copy = new Player();

            copy.setPosition(startingPosition);

            for (Cell reachableCell: startingPosition.reachableCells(1)){

                copy.setPosition(reachableCell);

                if (usableWeapon(allPlayers)){

                    return true;
                }
            }

            return false;
        }

        else if ( currentState == State.FRENZYAFTERFIRST){

            Cell startingPosition = owner.getPosition(); /* saving the starting position */

            Player copy = new Player();

            copy.setPosition(startingPosition);

            for (Cell reachableCell: startingPosition.reachableCells(2)){

                copy.setPosition(reachableCell);

                if (usableWeapon(allPlayers)){

                    return true;
                }
            }
            return false;
        }

        else return usableWeapon(allPlayers);

    }

    /**
     * Calculates if is possible to use this weapon by the owner
     * @param allPlayers all the players in the game, passed by the Player when the method is called
     * @return true if the weapon is usable, false if not
     */
    public boolean usableWeapon(List<Player> allPlayers){

        if ( !hasAMoveTypeEffect ){

            return hasOneUsableEffect(allPlayers);
        }

        else {

            if ( !hasOneUsableEffect(allPlayers) ){

                int moves = getMoveTypeEffect().getMove().getMoveYou();
                Cell startingPosition = owner.getPosition();    /* saving the starting position */

                Player copy = new Player();

                copy.setPosition(startingPosition);

                for (Cell reachableCell: startingPosition.reachableCells(moves)){

                    copy.setPosition(reachableCell);

                    if ( hasOneUsableEffect(allPlayers) ){

                        return true;
                    }
                }
            }

            else { return true; }
        }

        return false;
    }


    /**
     * this method is useful to implement usableWeapon()
     * @param allPlayers
     * @return true if the weapon has at least one usable effect (excluding move type effect)
     */
    public boolean hasOneUsableEffect(List<Player> allPlayers){

        for(Effect effect: effects){

            if (effect.getType() != EffectsKind.MOVE && effect.usableEffect(owner, allPlayers)){

                return true;
            }
        }

        return false;
    }



}
