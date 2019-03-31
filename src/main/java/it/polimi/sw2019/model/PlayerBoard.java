package it.polimi.sw2019.model;

public class PlayerBoard {

    /**
     * Default constructor
     */
    public PlayerBoard(){

    }

    /* Attributes */

    private DamageTokens damage;

    private Marks marks;

    private boolean firstPlayer = false;

    private int numOfDeaths = 0;

    private Ammo ammo;


    /* Methods */

    public boolean getFirstPlayer(){
        return this.firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public int getNumOfDeaths() {
        return numOfDeaths;
    }

    public void setNumOfDeaths(int numOfDeaths) {
        this.numOfDeaths = numOfDeaths;
    }

    /**
     * Updates the score in the Score class
     */
    public void updateScore(){

        //TODO implement

        return;
    }

}
