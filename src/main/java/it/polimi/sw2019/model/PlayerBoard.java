package it.polimi.sw2019.model;

import it.polimi.sw2019.network.messages.PlayerBoardMessage;

import java.util.List;

/**
 * @author poligenius
 * this class contains infos about the playerboard
 */
public class PlayerBoard {

    /**
     * Default constructor
     */
    public PlayerBoard(){}

    /**
     * customized constructor
     * @param charactersInGame players
     */
    public PlayerBoard(List<Character> charactersInGame){

        damage = new DamageTokens(charactersInGame);
        marks = new Marks(charactersInGame);
    }

    /* Attributes */

    private DamageTokens damage;

    private Marks marks;

    private boolean firstPlayer = false;

    private boolean flipped = false;

    private int numOfDeaths = 0;

    private Ammo ammo = new Ammo(1,1,1);


    /* Methods */

    public boolean isFirstPlayer(){
        return this.firstPlayer;
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

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public Marks getMarks() {
        return marks;
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    /**
     *  used to know if the player can pay targeting scope
     * @return true if the player has at least one ammo
     */
    public boolean hasAtLeastOneAmmo(){
        if (ammo.getRed()>0||ammo.getYellow()>0||ammo.getBlue()>0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * update the score when the board is not flipped
     * @param score score class
     */
    public void updateScore(Score score){

        List<Character> ranking;
        ranking = damage.getRanking();
        int points;


        if (ranking.isEmpty()){

            return;
        }

        /* giving 1 point for the first blood */
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
     * updates the score when the board is flipped in frenzy
     * @param score class score
     */
    public void updateFrenzyScore(Score score){

        List<Character> ranking;
        ranking = damage.getRanking();

        if (ranking.isEmpty()){

            return;
        }

        for (int i = 0; i < ranking.size(); i++){

            if (i < 2){

                score.addPoints(2, ranking.get(i));

            }

            else{

                score.addPoints(1, ranking.get(i));
            }
        }

    }

    /**
     * creade a playerBoardMessage included in the match state sent to the view
     * @return PlayerBoardMessage
     */
    public PlayerBoardMessage createPlayerBoard(){

        PlayerBoardMessage playerBoardMessage = new PlayerBoardMessage();

        playerBoardMessage.setDamageSequence(damage.getDamageSequence());
        playerBoardMessage.setMarkSequence(marks.getMarkSequence());
        playerBoardMessage.setFirstPlayer(isFirstPlayer());
        playerBoardMessage.setFlipped(isFlipped());
        playerBoardMessage.setNumOfDeaths(numOfDeaths);
        playerBoardMessage.setRedAmmo(ammo.getRed());
        playerBoardMessage.setBlueAmmo(ammo.getBlue());
        playerBoardMessage.setYellowAmmo(ammo.getYellow());

        return playerBoardMessage;
    }

}
