package it.polimi.sw2019.model;

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
     * returns an array of characters in descending order based on who did more kills/marks/damage/points
     */

    public Character[] getRanking() {

        Character[] result = new Character[5];

          //TODO implement

        return result;
    }

    public Character[] orderArray() {

        Character[] result = new Character[1];
        //TODO implement
        return result;
    }

}

