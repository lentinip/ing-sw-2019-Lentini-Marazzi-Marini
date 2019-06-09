package it.polimi.sw2019.model;

import java.util.*;

public class Score extends Tokens {

    /**
     * Customized constructor
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
                    return 0;
                }
            }
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     * Method that returns a Map of Characters that represents the game LeaderBoard
     */
    public Map<Character, Integer> getRankingMap() {
        Map<Character, Integer> sortedMap = sortByValues(charactersMap, Collections.reverseOrder(new RankingComparator()));

        Map<Character, Integer> ranking = new LinkedHashMap<>();
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(sortedMap.entrySet());

        int rank = 1;
        Map.Entry<Character, Integer> previousEntry = list.get(0);
        RankingComparator rankingComparator = new RankingComparator();

        for (int i=0; i<list.size(); i++){
            if (rankingComparator.compare(previousEntry, list.get(i))==0){
                ranking.put(list.get(i).getKey(), rank);
            }
            else {
                previousEntry = list.get(i);
                rank++;
                ranking.put(list.get(i).getKey(), rank);
            }
        }

        return ranking;
    }
}
