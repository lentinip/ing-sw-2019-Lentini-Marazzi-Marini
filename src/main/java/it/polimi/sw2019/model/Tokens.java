package it.polimi.sw2019.model;

public class Tokens {

    /**
     * Default constructor
     */

    public Tokens() {

    }

    /* Attributes */

    private int green;

    private int yellow;

    private int blue;

    private int grey;

    private int purple;


   /* Methods */

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGrey() {
        return grey;
    }

    public void setGrey(int grey) {
        this.grey = grey;
    }

    public int getPurple() {
        return purple;
    }

    public void setPurple(int purple) {
        this.purple = purple;
    }

    /**
     * returns an array of characters in descending order based on who did more kills/marks/damage/points
     */

    public Character[] getRanking() {

        Character[] result = new Character[5];

          //TODO implement

        return result;
    }

}

