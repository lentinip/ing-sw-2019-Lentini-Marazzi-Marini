package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author lentinip
 * this clas was made to keep track of the damage tokens
 */
public class DamageTokens extends Tokens {

    /**
     * Default constructor
     * @param charactersInGame list of the characters in game
     */
    public DamageTokens(List<Character> charactersInGame) {
        super(charactersInGame);
    }

    /* Attributes */

    private List<Character> damageSequence = new ArrayList<>();

    private int totalDamage = 0;

    /* Methods */

    public List<Character> getDamageSequence() {
        return damageSequence;
    }

    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    public void setDamageSequence(List<Character> damageSequence) {
        this.damageSequence = damageSequence;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    /**
     * method to add damage
     * @param i number of damage you are adding
     * @param opponent Player who is doing damage
     */
    public void addDamage(int i, Character opponent) {

        if (totalDamage<12){
            int newTotalDamage = totalDamage + i;
            if (newTotalDamage>12){
                i = 12-totalDamage;
            }
            addTokens(i, opponent);
            for (int eachDamage = 0; eachDamage<i; eachDamage++){
                damageSequence.add(opponent);
            }
            totalDamage = totalDamage + i;
        }
    }

    /**
     *  method to reset damage values when a player dies
     */
    public void reset() {

        //Set to 0 the value of tokens of each player
        for (Map.Entry<Character, Integer> entry : charactersMap.entrySet()){
            entry.setValue(0);
        }

        damageSequence.clear();
        totalDamage=0;
    }

    @SuppressWarnings("Duplicates")
    private class RankingComparator implements Comparator<Map.Entry<Character, Integer>> {

        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {

            //Checks if the two Characters have the same amount of tokens in the damageSequence
            if (o1.getValue().equals(o2.getValue())){
                //If o1 killed someone in the damageSequence before o2 he is going to be the greater
                if (damageSequence.indexOf(o1.getKey()) > damageSequence.indexOf(o2.getKey())){
                    return -1;
                }
                if (damageSequence.indexOf(o1.getKey()) < damageSequence.indexOf(o2.getKey())){
                    return 1;
                }
            }
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     * used to know a list ordered by the player who did more damages
     * @return an ArrayList of Character ordered by the rules of the damageSequence
     */
    public List<Character>  getRanking(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }
}
