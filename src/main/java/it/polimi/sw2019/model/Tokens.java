package it.polimi.sw2019.model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author lentinip
 * class that is extended by marks, damage tokens, score
 * contains methods used by all these classes
 */
public class Tokens {

    /**
     * Default constructor
     * @param charactersInGame list of characters in game
     */
    public Tokens(List<Character> charactersInGame) {

        for (Character character : charactersInGame) {
            charactersMap.put(character, 0);
        }
    }

    /* Attributes */

    protected Map<Character, Integer> charactersMap = new EnumMap<>(Character.class);

   /* Methods */

    public Map<Character, Integer> getMap() {
        return charactersMap;
    }

    /**
     * Adds the number of the new tokens of a Character
     * @param numerOfTokens number of new tokens
     * @param ownerOfTheTokens owner of the tokens
     */
    public void addTokens(int numerOfTokens, Character ownerOfTheTokens){
        charactersMap.replace(ownerOfTheTokens, charactersMap.get(ownerOfTheTokens) + numerOfTokens);
    }

    /**
     * to know how many tokens a character has
     * @param player player we are interested in
     * @return the number of tokens of the player
     */
    public int getTokensOfCharacter(Character player){
        return charactersMap.get(player);
    }

    /**
     * @return  an ArrayList of Characters in decreasing order by tokens they have (it doesn't manage players with the same tokens), if a player has 0 tokens isn't gonna be in the array
     */
    public ArrayList<Character>orderArray(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }

    /**
     * order the list of characters
     * @param mapComparator Comparator Map.Entry Character, Integer
     * @param map map analyzed
     * @return an ArrayList of Characters in decreasing order by the enumMapComparator
     */
    protected ArrayList<Character> orderArrayByComparator(Map<Character, Integer> map, Comparator<Map.Entry<Character, Integer>> mapComparator) {

        ArrayList<Character> result = new ArrayList<>();

        Map<Character, Integer> ranking;

        ranking = sortByValues(map, Collections.reverseOrder(mapComparator));

        //This for sets the keys of the map in the Character array
        for(Map.Entry entry : ranking.entrySet()){
            //With this condition there are NOT gonna be the players with 0 tokens in the array
            if ((Integer) entry.getValue()!=0) {
                result.add((Character) entry.getKey());
            }
        }

        return result;
    }

    /**
     * Comparator for a Map.Entry<Character, Integer> that orders by the values in ascending order
     */
    private class RankingComparator implements Comparator<Map.Entry<Character, Integer>> {

        public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2){
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     * Sorts an EnumMap Character, Integer by values (Integer)
     * @param mapComparator map comparator
     * @param map Map that is going to be sorted
     * @return an HashMap sorted
     */
    protected Map<Character, Integer> sortByValues(Map<Character, Integer> map, Comparator<Map.Entry<Character,Integer>> mapComparator){

        //This method first creates a list with the entries of the map (an entry contains the key and the value)
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());

        //Than sorts the list using the comparator implemented before
        list.sort(mapComparator);

        //Creates a new empty EnumMap
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();

        //For each entry in the list that now is ordered, it uses the method put() of the EnumMap to fill sortedMap
        for(Map.Entry entry : list){
            sortedMap.put((Character) entry.getKey(), (Integer) entry.getValue());
        }

        return sortedMap;
    }

}

