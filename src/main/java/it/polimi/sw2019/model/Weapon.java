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

    private boolean hasAnOrder;          /* there are some effects that upgrade the basic one */

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

    public boolean hasAnOrder() {
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

    public void addEffect(Effect effect){

        effects.add(effect);
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
     * called by controller to doSomething info to the view
     * @param effect the selected effect
     * @return the index
     */
    public int getIndexByEffect(Effect effect){
        return effects.indexOf(effect);
    }

    public void unloadWeapon(){

        setIsLoaded(false);
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

                for (Cell reachableCell: startingPosition.reachableCells(moves)){

                    owner.setPosition(reachableCell);

                    if ( hasOneUsableEffect(allPlayers) ){
                        owner.setPosition(startingPosition);

                        return true;
                    }
                }

                owner.setPosition(startingPosition);
            }

            else { return true; }
        }

        return false;
    }

    /**
     * this method is called by controller
     * @param allPlayers all the players in the game
     * @return all effects that are usable
     */
    public List<Effect> usableEffects(List<Player> allPlayers){

        List<Effect> usableEffects = new ArrayList<>();

        for(Effect effect: effects){

            if (effect.getType() != EffectsKind.MOVE) {

                if (effect.usableEffect(owner, allPlayers)) {

                    usableEffects.add(effect);
                }
            }

            else usableEffects.add(effect);
        }

        return usableEffects;
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
