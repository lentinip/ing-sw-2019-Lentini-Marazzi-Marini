package java.it.polimi.sw2019.model;

public class Match {

    /** Default constructor */
    public Match() {

    }

    /** Attributes */

    private int idPartita;

    private int boardType;      /** tells in what kind of board players are going to play */

    private Board board;

    private Player[] players;     /** max 5 players */

    private int numberOfPlayers;

    private Player currentPlayer;

    private Score score;

    private boolean iWantFrenzyMode;  /** set at the start of the game when you choose if you want to play frenzy*/

    private boolean frenzyMode; /** set 'true' when last player dies only if iWantFrenzyMode is 'true' */

    /** Methods */

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

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
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

    public void initializeMatch() {

      //TODO implement

    }

      public void endMatch() {

      //TODO implement

     }

}
