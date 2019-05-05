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

    private int boardType; /* tells in what kind of board players are going to play */

    private Board board;

    private List<Player> players = new ArrayList<>(); /* max five players */

    private int numberOfPlayers;

    private Player currentPlayer;

    private Score score;

    private Player lastPlayer;

    private Factory factory = new Factory();

    private boolean iWantFrenzyMode; /* set at the start of the game when you choose if you want to play frenzy */

    private boolean frenzyMode; /* set 'true' when last player dies only if iWantFrenzyMode is 'true' */

    private boolean isEnded = false; /* true when the game is ended */

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

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }


    /**
     * Returns the Player with a specific Character
     * @param character THe Character of the player
     * @return the Player reference (Can be null)
     */
    public Player getPlayerFromCharacter(Character character){
        for(Player player : players){
            if (player.getCharacter()==character){
                return player;
            }
        }
        return null;
    }

    /**
     * this method creates the board and everything it contains
     */
    public void initializeMatch() {

      //TODO implement

    }

    /**
     * this method basically do the final update of the score that will be showned from the view to the client
     */
    public void endMatch() {

      for(Player player: players){

          if(player.getPlayerBoard().isFlipped()){

              player.getPlayerBoard().updateFrenzyScore(score);
          }

          else {

              player.getPlayerBoard().updateScore(score);
          }
      }

      board.getKillTrack().updateScore(score);

      setEnded(true);
    }

    /**
     * this method updates the current player
     */
    public void setNextPlayer(){

        int i = players.indexOf(currentPlayer);

        if (isEnded){

            currentPlayer = null;
        }

        else if ( i < numberOfPlayers - 1){

            currentPlayer = players.get(i+1);
        }

        else {

            currentPlayer = players.get(0);
        }
    }

    public void endTurn() {

        PlayerBoard playerBoard;
        KillTokens killTokens;

        killTokens = board.getKillTrack();

        /*
         * verifies if players are dead or not (if true resets the damages)
         */
        for(int i = 0; i < players.size(); i++) {

             if(this.players.get(i).isDead()) {

                 playerBoard = this.players.get(i).getPlayerBoard();

                 if (playerBoard.isFlipped()) {

                     playerBoard.updateFrenzyScore(this.score);

                 }

                 else {

                     playerBoard.updateScore(this.score);

                 }

                 board.updateKillTrack(players.get(i));

                 playerBoard.getDamage().reset();

                 /* if a player dies during frenzy his board is flipped */

                 if (killTokens.getTotalKills() >= 8 && iWantFrenzyMode) {

                     playerBoard.setFlipped(true);

                 }

                 respawn(this.players.get(i));
             }
         }

        /*
         * calls endMatch at the end
         */
         if(frenzyMode && currentPlayer == lastPlayer) {

              endMatch();
         }


        /*
         * initializes frenzy mode
         */
        else if(killTokens.getTotalKills() >= 8 && this.lastPlayer == null && iWantFrenzyMode){

                 frenzyMode = true;
                 this.lastPlayer = this.currentPlayer;

                 for (int i = 0; i < players.size(); i++){

                     while(players.get(i) != currentPlayer){

                         players.get(i).setState(State.FRENZYAFTERFIRST);
                         i++;
                     }

                     players.get(i).setState(State.FRENZYBEFOREFIRST);
                 }

        }

        else if (killTokens.getTotalKills() >= 8 && !iWantFrenzyMode){

            endMatch();
         }


         setNextPlayer();
    }


    public void respawn(Player deadPlayer){

        //TODO implement
    }

}

