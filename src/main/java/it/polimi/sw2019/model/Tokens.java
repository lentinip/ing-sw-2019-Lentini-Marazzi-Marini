package it.polimi.sw2019.model;

import java.util.*;

import static it.polimi.sw2019.model.Character.*;

public class Tokens {

    /**
     * Default constructor
     */

    public Tokens() {

    }

    /* Attributes */

    private int distructor;

    private int banshee;

    private int dozer;

    private int violet;

    private int sprog;


   /* Methods */

    public void setDistructor(int distructor) {
        this.distructor = distructor;
    }

    public int getDistructor() {
        return distructor;
    }

    public void setBanshee(int banshee) {
        this.banshee = banshee;
    }

    public int getBanshee() {
        return banshee;
    }

    public void setDozer(int dozer) {
        this.dozer = dozer;
    }

    public int getDozer() {
        return dozer;
    }

    public void setViolet(int violet) {
        this.violet = violet;
    }

    public int getViolet() {
        return violet;
    }

    public void setSprog(int sprog) {
        this.sprog = sprog;
    }

    public int getSprog() {
        return sprog;
    }

    /**
     * @return  an array of Characters in decreasing order by tokens they have (it doesn't manage players with the same tokens), if a player has 0 tokens isn't gonna be in the array
     */
    public Character[] orderArray() {

        Character[] result = new Character[5];

        EnumMap<Character, Integer> ranking = new EnumMap<>(Character.class);
        ranking.put(BANSHEE, banshee);
        ranking.put(DISTRUCTOR, distructor);
        ranking.put(DOZER, dozer);
        ranking.put(SPROG, sprog);
        ranking.put(VIOLET, violet);

        ranking = sortByValues(ranking);

        int i = 0;

        //This for sets the keys of the map in the Character array
        for(Map.Entry entry : ranking.entrySet()){
            //With this condition there are NOT gonna be the players with 0 tokens in the array
            if ((Integer) entry.getValue()!=0) {
                result[i] = (Character) entry.getKey();
                i++;
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
    private EnumMap<Character, Integer> sortByValues(EnumMap<Character, Integer> map){

        //This method first creates a list with the entries of the map (an entry contains the key and the value)
        List<Map.Entry<Character, Integer>> list = new LinkedList<>(map.entrySet());

        //Than sorts the list using the comparator implemented before
        list.sort(Collections.reverseOrder(new RankingComparator()));

        //Creates a new empty EnumMap
        EnumMap<Character, Integer> sortedMap = new EnumMap<>(Character.class);

        //For each entry in the list that now is ordered, it uses the method put() of the EnumMap to fill sortedMap
        for(Map.Entry entry : list){
            sortedMap.put((Character) entry.getKey(), (Integer) entry.getValue());
        }

        return sortedMap;
    }

}

