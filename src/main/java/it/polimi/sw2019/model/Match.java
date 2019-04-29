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

    public void initializeMatch() {

      //TODO implement

    }

    public void endMatch() {

      //TODO implement

    }

    public void setNextPlayer(){

        //TODO implement
    }

    public void endTurn() {

        PlayerBoard playerBoard;
        KillTokens killTokens;

        killTokens = board.getKillTrack();

        /*
         * verifies if players are dead or not (if true resets the damages)
         */
        for(int i = 0; i<players.size(); i++) {

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

                 if (killTokens.getTotalKills() >= 8) {

                     playerBoard.setFlipped(true);

                 }

                 respawn(this.players.get(i));
             }
         }
        //TODO implement exceptions

        /*
         * calls endMatch at the end
         */
         if(frenzyMode && currentPlayer == lastPlayer) {

              endMatch();
         }

        /*
         * initializes frenzy mode
         */

        if(killTokens.getTotalKills() >= 8 && this.lastPlayer == null){

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

        //TODO implement exceptions

         setNextPlayer();

    }


    public void respawn(Player deadPlayer){

        //TODO implement
    }

}

