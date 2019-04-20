package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Match {

    /**
     * Default constructor
     */
    public Match() {

    }

    /* Attributes */

    private int idPartita;

    /**
     * tells in what kind of board players are going to play
     */
    private int boardType;

    private Board board;

    /**
     * max 5 players
     */
    private List<Player> players = new ArrayList<>();

    private int numberOfPlayers;

    private Player currentPlayer;

    private Score score;

    private Player lastPlayer;


    /**
     * set at the start of the game when you choose if you want to play frenzy
     */
    private boolean iWantFrenzyMode;

    /**
     * set 'true' when last player dies only if iWantFrenzyMode is 'true'
     */
    private boolean frenzyMode;

    /* Methods */

    public int getIdPartita() {
        return idPartita;
    }

    public void setIdPartita(int idPartita) {
        this.idPartita = idPartita;
    }

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public boolean getIWantFrenzyMode() {
        return iWantFrenzyMode;
    }

    public void setIWantFrenzyMode(boolean iWantFrenzyMode) {
        this.iWantFrenzyMode = iWantFrenzyMode;
    }

    public boolean getFrenzyMode() {
        return frenzyMode;
    }

    public void setFrenzyMode(boolean frenzyMode) {
        this.frenzyMode = frenzyMode;
    }

    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public void initializeMatch() {

      //TODO implement

    }

      public void endMatch() {

      //TODO implement

     }

     public void setNextPlayer(){

        //TODO implement
     }

     public void flipBoards(){

        //TODO implement
         return;
     }


    public void endTurn() {

         PlayerBoard playerBoard;
         KillTokens killTokens;

        /**
         * verifies if players are dead or not (if true resets the damages)
         */
        for(int index = 0; index<players.size(); index++) {

             if(this.players.get(index).isDead()) {

                 playerBoard = this.players.get(index).getPlayerBoard();
                 playerBoard.updateScore(this.score);
                 playerBoard.getDamage().reset();
                 this.players.get(index).setDead(false);
             }
         }
        //TODO implement exceptions

        /**
         * calls endMatch at the end
         */
         if(frenzyMode) {

             if(currentPlayer == lastPlayer) {
                 endMatch();
             }
         }

        /**
         * initializes frenzy mode
         */
        killTokens = this.board.getKillTrack();
         if(killTokens.getTotalKills() >= 8) {

             if(lastPlayer == null) {
                 frenzyMode = true;
                 this.lastPlayer = this.currentPlayer;
                 flipBoards();
             }
         }
         //TODO implement exceptions

         setNextPlayer();
         return;
     }

     public void respawn(Player deadPlayer){

        //TODO implement
     }

}

