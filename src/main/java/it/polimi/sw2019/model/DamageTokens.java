package it.polimi.sw2019.model;

public class DamageTokens extends Tokens {

    /**
     * Default constructor
     */

    public DamageTokens() {

    }

    /* Attributes */

    private Character[] damageSequence = new Character[12];

    private int totalDamage;

    /* Methods */

    public Character[] getDamageSequence() {
        return damageSequence;
    }

    public void setDamageSequence(Character[] damageSequence) {
        this.damageSequence = damageSequence;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    /**
     *
     * @param i number of damage you are adding
     * @param opponent Player who is doing damage
     */

    public void addDamage(int i, Character opponent) {

        //TODO implement
    }

    /**
     *  method to reset damage values when a player dies
     */

    public void reset() {

        //TODO implement
    }
}
