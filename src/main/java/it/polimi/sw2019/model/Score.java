package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Score extends Tokens {

    /**
     * Default constructor
     */

    public Score(List<Character> charactersInGame, KillTokens killTrack) {
        super(charactersInGame);
        this.killTrack = killTrack;
    }

    /* Attributes */

    private KillTokens killTrack;


    /* Methods */

    public void addPoints(int points, Character character){
        addTokens(points, character);
    }

    /**
     * Comparator for Score points in the charactersMap of Tokens
     */
    private class RankingComparator implements Comparator<Map.Entry<Character, Integer>> {

        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {

            //Checks if the two Characters have the same amount of points
            if (o1.getValue().equals(o2.getValue())){
                //If o1 killed more in the killSequence than o2 he is going to be the greater
                if (killTrack.getTokensOfCharacter(o1.getKey()) > killTrack.getTokensOfCharacter(o2.getKey())){
                    return 1;
                }
                if (killTrack.getTokensOfCharacter(o1.getKey()) < killTrack.getTokensOfCharacter(o2.getKey())){
                    return -1;
                }
                else {
                    //TODO exception still tied
                }
            }
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     * Method that returns a descending array of Characters that represents the game LeaderBoard
     */
    public ArrayList<Character> getRanking(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }
}
