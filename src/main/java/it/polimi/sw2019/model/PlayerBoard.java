package it.polimi.sw2019.model;

import java.util.ArrayList;


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

    private boolean flipped = false;

    private int numOfDeaths = 0;

    private Ammo ammo;


    /* Methods */

    public boolean getFirstPlayer(){
        return this.firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public int getNumOfDeaths() {
        return numOfDeaths;
    }

    public void setNumOfDeaths(int numOfDeaths) {
        this.numOfDeaths = numOfDeaths;
    }

    public void setDamage(DamageTokens damage) {
        this.damage = damage;
    }

    public DamageTokens getDamage() {
        return damage;
    }

    /**
     * update the score when the board is not flipped
     * @param score
     */
    public void updateScore(Score score){

        ArrayList<Character> ranking;
        ranking = damage.getRanking();
        int points;

        /*
         * giving 1 point for the first blood
         */
        score.addPoints(1, damage.getDamageSequence().get(0) );

        for (int i = 0; i < ranking.size(); i++){

         /* points added to the player */

           points = 8 - numOfDeaths*2 - 2*i;

           if (points <= 0) {

               score.addPoints(1,ranking.get(i));
           }

           else {

               score.addPoints(points, ranking.get(i));

           }
        }
    }

    /**
     * update the score when the board is flipped in frenzy
     * @param score
     */
    public void updateFrenzyScore(Score score){

        ArrayList<Character> ranking;
        ranking = damage.getRanking();

        for (int i = 0; i < ranking.size(); i++){

            if (i < 2){

                score.addPoints(2, ranking.get(i));

            }

            else{

                score.addPoints(1, ranking.get(i));
            }
        }

    }

}
