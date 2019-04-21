package it.polimi.sw2019.model;

import java.util.*;

public class Tokens {

    /**
     * Default constructor
     */

    public Tokens(List<Character> charactersInGame) {

        for (Character character : charactersInGame) {
            charactersMap.put(character, 0);
        }
    }

    /* Attributes */

    protected EnumMap<Character, Integer> charactersMap = new EnumMap<>(Character.class);

   /* Methods */

    /**
     * Adds the number of the new tokens of a Character
     * @param numerOfTokens number of new tokens
     * @param ownerOfTheTokens owner of the tokens
     */
    public void addTokens(int numerOfTokens, Character ownerOfTheTokens){
        charactersMap.replace(ownerOfTheTokens, charactersMap.get(ownerOfTheTokens) + numerOfTokens);
    }

    /**
     *
     * @param player
     * @return the number of tokens of the player
     */
    public int getTokensOfCharacter(Character player){
        return charactersMap.get(player);
    }

    /**
     * @return  an ArrayList of Characters in decreasing order by tokens they have (it doesn't manage players with the same tokens), if a player has 0 tokens isn't gonna be in the array
     */
    public List<Character>orderArray(){
        return orderArrayByComparator(charactersMap, new RankingComparator());
    }

    /**
     *
     * @param enumMapComparator Comparator<Map.Entry<Character, Integer>>
     * @return an ArrayList of Characters in decreasing order by the enumMapComparator
     */
    protected List<Character> orderArrayByComparator(Map<Character, Integer> map, Comparator<Map.Entry<Character, Integer>> enumMapComparator) {

        List<Character> result = new ArrayList<>();

        EnumMap<Character, Integer> ranking;

        ranking = sortByValues(map, enumMapComparator);

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
     * Sorts an EnumMap<Character, Integer> by values (Integer)
     * @param map EnumMap that is going to be sorted
     * @return an EnumMap sorted
     */
    private EnumMap<Character, Integer> sortByValues(Map<Character, Integer> map, Comparator<Map.Entry<Character,Integer>> enumMapComparator){

        //This method first creates a list with the entries of the map (an entry contains the key and the value)
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());

        //Than sorts the list using the comparator implemented before
        list.sort(Collections.reverseOrder(enumMapComparator));

        //Creates a new empty EnumMap
        EnumMap<Character, Integer> sortedMap = new EnumMap<>(Character.class);

        //For each entry in the list that now is ordered, it uses the method put() of the EnumMap to fill sortedMap
        for(Map.Entry entry : list){
            sortedMap.put((Character) entry.getKey(), (Integer) entry.getValue());
        }

        return sortedMap;
    }

}

