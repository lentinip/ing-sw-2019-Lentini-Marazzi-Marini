package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class KillTokens extends Tokens{

    /**
     * Default constructor
     */

    public KillTokens(List<Character> charactersInGame) {
        super(charactersInGame);
    }

    /* Attributes */

    /**
     * ArrayList that represents the killshot track status
     */
    private List<Character> killSequence = new ArrayList<>();

    /**
     * array to track the overkills in the killshot track
     */
    private List<Boolean> overkillSequence = new ArrayList<>();

    private int totalKills = 0;

    /* Methods */

    public List<Character> getKillSequence() {
        return killSequence;
    }

    public List<Boolean> getOverkillSequence() {
        return overkillSequence;
    }

    public int getTotalKills() {
        return totalKills;
    }

    /**
     *
     * @param killer Player who killed someone
     */
    public void addKill(Character killer) {

        addTokens(1, killer);
        killSequence.add(killer);
        overkillSequence.add(false);

        totalKills++; //Updates the number of totalKills

    }

    /**
     * Method that signs if a player overkilled somebody (it manages also the kill)
     * @param killer player who did the overkill
     */
    public void addOverkill(Character killer) {

        addTokens(2, killer);
        killSequence.add(killer);
        overkillSequence.add(true);

        totalKills++;
    }

    /**
     *  method called by the class Score to update the score when the game ends
     */
    public void updateScore(Score score){

        //TODO implement

    }

    /**
     * Comparator for KillTokens points in the charactersMap of Tokens
     */
    private class RankingComparator implements Comparator<Map.Entry<Character, Integer>> {

        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {

            //Checks if the two Characters have the same amount of tokens in the killTrack
            if (o1.getValue().equals(o2.getValue())){
                //If o1 killed someone in the killSequence before o2 he is going to be the greater
                if (killSequence.indexOf(o1.getKey()) > killSequence.indexOf(o2.getKey())){
                    return 1;
                }
                if (killSequence.indexOf(o1.getKey()) < killSequence.indexOf(o2.getKey())){
                    return -1;
                }
            }
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     *
     * @return an ArrayList of Character ordered by the rules of the KillTrack
     */
    public List<Character>  getRanking(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }


}
