package it.polimi.sw2019.model;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DamageTokens extends Tokens {

    /**
     * Default constructor
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

    public int getTotalDamage() {
        return totalDamage;
    }

    /**
     *
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
        //TODO exception
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

    private class RankingComparator implements Comparator<Map.Entry<Character, Integer>> {

        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {

            //Checks if the two Characters have the same amount of tokens in the damageSequence
            if (o1.getValue().equals(o2.getValue())){
                //If o1 killed someone in the damageSequence before o2 he is going to be the greater
                if (damageSequence.indexOf(o1.getKey()) > damageSequence.indexOf(o2.getKey())){
                    return 1;
                }
                if (damageSequence.indexOf(o1.getKey()) < damageSequence.indexOf(o2.getKey())){
                    return -1;
                }
            }
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     *
     * @return an ArrayList of Character ordered by the rules of the damageSequence
     */
    public ArrayList<Character>  getRanking(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }
}
