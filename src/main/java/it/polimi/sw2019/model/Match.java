package it.polimi.sw2019.model;

import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author poligenius
 * this class is the facade of the model, contains reference to all the data structures
 * and methods to initialize the match and end it
 */
public class Match extends Observable {

    /**
     * Default constructor
     */
    public Match(){}

    /**
     * customized constructor
     * @param iWantFrenzyMode if the player wants the frenzy
     * @param easyMode if the player wants 5 skulls or 8
     * @param usernames usernames of the players
     * @param boardJsonName board chosen
     * @param view reference to the view
     */
    public Match(boolean iWantFrenzyMode, boolean easyMode, List<String> usernames, String boardJsonName, VirtualView view){

        addObserver(view);
        List<Character> charactersInGame = new ArrayList<>();
        int counter = 3; /* set to 3 because the smaller number of player allowed is 3 */

        charactersInGame.add(Character.DISTRUCTOR);
        charactersInGame.add(Character.BANSHEE);
        charactersInGame.add(Character.DOZER);

        if (counter < usernames.size()) {           /* if there are 4 players adding violet */
            charactersInGame.add(Character.VIOLET);
            counter++;
        }
        if (counter < usernames.size()) {          /* if there are 5 players adding also sprog */
            charactersInGame.add(Character.SPROG);
        }

        for (counter = 0; counter < usernames.size(); counter++){

            players.add(new Player(usernames.get(counter), charactersInGame.get(counter), charactersInGame));
        }

        deadPlayers = new ArrayList<>(players);
        initializeMatch(boardJsonName);
        score = new Score(charactersInGame, board.getKillTrack());
        setIWantFrenzyMode(iWantFrenzyMode);
        setEasyMode(easyMode);
        setNumberOfPlayers(usernames.size());
        currentPlayer = players.get(0);
        notifyMatchState();
    }

    /* Attributes */

    private Board board;

    private List<Player> players = new ArrayList<>(); /* max five players */

    private Player currentPlayer;

    private int currentPlayerLeftActions = 2;

    private List<Player> deadPlayers;

    private Score score;

    private int numberOfPlayers;

    private Player lastPlayer = null; /* the player that takes the last turn and after it, the match is ended */

    private Factory factory = new Factory();

    private boolean iWantFrenzyMode; /* set at the start of the game when you choose if you want to play frenzy */

    private boolean easyMode; /* 5 skulls */

    private boolean frenzyMode = false; /* set 'true' when last player dies only if iWantFrenzyMode is 'true' */

    private boolean isEnded = false; /* true when the game is ended */


    /* Methods */

    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public void setFrenzyMode(boolean frenzyMode) {
        this.frenzyMode = frenzyMode;
    }

    public void setiWantFrenzyMode(boolean iWantFrenzyMode) {
        this.iWantFrenzyMode = iWantFrenzyMode;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public boolean isiWantFrenzyMode() {
        return iWantFrenzyMode;
    }

    public void setDeadPlayers(List<Player> deadPlayers) {
        this.deadPlayers = deadPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentPlayerLeftActions() {
        return currentPlayerLeftActions;
    }

    public void setCurrentPlayerLeftActions(int currentPlayerLeftActions) {
        this.currentPlayerLeftActions = currentPlayerLeftActions;
        notifyMatchState();
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setIWantFrenzyMode(boolean iWantFrenzyMode) {
        this.iWantFrenzyMode = iWantFrenzyMode;
    }

    public boolean getFrenzyMode() {
        return frenzyMode;
    }

    public List<Player> getDeadPlayers() {
        return deadPlayers;
    }

    public void setEasyMode(boolean easyMode) {
        this.easyMode = easyMode;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Factory getFactory() {
        return factory;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Get Player by username (if there is no player with that username returns null)
     * @param username user of the player
     * @return player
     */
    public Player getPlayerByUsername(String username){
        for (Player player : players){
            if (player.getName().equals(username)){
                return player;
            }
        }
        return null;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }


    /**
     * this method creates the board and everything it contains
     * @param boardFileName board chosen
     */
    public void initializeMatch(String boardFileName){

      Visibility visibilityClass = new Visibility();

      factory.setVisibilityClass(visibilityClass);

      this.board = factory.createBoard(boardFileName, players);

      visibilityClass.setBoard(board);

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

            //System.out.print("\n ended ");
            currentPlayer = null;
            return;
        }

        else if ( i < numberOfPlayers - 1){

            //System.out.print("\n"+i);
            currentPlayer = players.get(i+1);
        }

        else {
            //System.out.print("\n"+i);
            currentPlayer = players.get(0);
        }

        //Resets the player's numberOfActions
        if (currentPlayer != null && currentPlayer.getState() == State.FRENZYAFTERFIRST){
            setCurrentPlayerLeftActions(1);
        }
        else {
            setCurrentPlayerLeftActions(2);
        }
    }

    /**
     * this method updates board and points at the end of the turn
     * and checks if we entered frenzy mode, if the match is ended, etc.
     */
    @SuppressWarnings("squid:S3776")
    public void endTurn() {

        PlayerBoard playerBoard;
        KillTokens killTokens;

        killTokens = board.getKillTrack();

        /*
         * verifies if players are dead or not (if true resets the damages)
         */
        for(int i = 0; i < players.size(); i++) {

             if(players.get(i).getPosition() != null && players.get(i).isDead()) {

                 System.out.print("\nThe player is dead: " + players.get(i).isDead()+"\n");
                 System.out.print("\nThe player position is : " + players.get(i).getPosition().getCoord().getCellNumber()+"\n");
                 System.out.println("endTurn model- position not null and player = " + players.get(i).getName());

                 playerBoard = players.get(i).getPlayerBoard();

                 if (playerBoard.isFlipped()) {

                     playerBoard.updateFrenzyScore(score);

                 }

                 else {

                     playerBoard.updateScore(score);

                 }

                 board.updateKillTrack(players.get(i));

                 notifyMatchState();

                 playerBoard.getDamage().reset();

                 notifyMatchState();

                 /* if a player dies during frenzy his board is flipped */

                 if ((killTokens.getTotalKills() >= 8 && iWantFrenzyMode && !easyMode) || (easyMode && killTokens.getTotalKills() >= 5 && iWantFrenzyMode )) {

                     playerBoard.setFlipped(true);
                     notifyMatchState();
                 }

                 deadPlayers.add(players.get(i));
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
        else if((killTokens.getTotalKills() >= 8 && this.lastPlayer == null && iWantFrenzyMode && !easyMode) || (killTokens.getTotalKills() >= 5 && this.lastPlayer == null && iWantFrenzyMode && easyMode)){

                 frenzyMode = true;
                 this.lastPlayer = this.currentPlayer;

                 for (int i = 0; i < players.size(); i++){

                     /* the player with index 0 is the first player, the players between the first player and who triggered
                        the frenzy mode are set as FRENZYAFTERFIRST, the others as FRENZYBEFOREFIRST */
                     if (i <= players.indexOf(currentPlayer)){

                         players.get(i).setState(State.FRENZYAFTERFIRST);
                     }

                     else {

                         players.get(i).setState(State.FRENZYBEFOREFIRST);
                     }
                 }

        }

        int trulyDead = 0;
        for (Player player : deadPlayers) {
            if (player.getPosition() != null) {
                trulyDead++;
            }
        }

        // if the player has done a multiple kill he gets one extra point
        if (trulyDead > 1){

            score.addPoints(1, currentPlayer.getCharacter());
        }

        else if ((killTokens.getTotalKills() >= 8 && !iWantFrenzyMode && !easyMode) || (killTokens.getTotalKills() >= 5 && !iWantFrenzyMode && easyMode) ){

            endMatch();
         }


         setNextPlayer();
    }


    /**
     * method used to send infos about the match to the view
     */
    public void notifyMatchState(){
        Message message = new Message("All");
        message.createMessageMatchState(createMatchState());
        setChanged();
        notifyObservers(message);
    }

    /**
     * method used to send info about the hand of a player to the view
     * @param player specific player
     */
    public void notifyPrivateHand(Player player){
        Message message = player.notifyPrivateHand();
        setChanged();
        notifyObservers(message);
        notifyMatchState();
    }

    /**
     * method used to create the match state
     * @return match state with all the infos about the status of the match
     */
    public MatchState createMatchState(){

        MatchState currentMatchState = new MatchState();
        List<Cell> allCells = board.getField();
        List<MessageCell> messageCells = new ArrayList<>();

        for (Cell cell: allCells){

            messageCells.add(cell.createMessageCell());
        }

        currentMatchState.setCells(messageCells);

        List<PlayerBoardMessage> playerBoardMessages = new ArrayList<>();

        for (Player player: players){

            playerBoardMessages.add(player.getPlayerBoard().createPlayerBoard());
        }

        currentMatchState.setPlayerBoardMessages(playerBoardMessages);
        currentMatchState.setCurrentPlayerLeftActions(currentPlayerLeftActions);
        List<PlayerHand> playerHands = new ArrayList<>();

        for (Player player: players){

            playerHands.add(player.createPlayerHand());
        }
        currentMatchState.setPlayerHands(playerHands);
        currentMatchState.setKillSequence(board.getKillTrack().getKillSequence());
        currentMatchState.setOverkillSequence(board.getKillTrack().getOverkillSequence());
        currentMatchState.setWeaponsDeckSize(board.getWeaponsDeck().size());
        currentMatchState.setPowerupsDeckSize(board.getPowerupsDeck().size());
        if(currentPlayer == null) {
            currentPlayer = players.get(0);
        }
        currentMatchState.setCurrentPlayer(currentPlayer.getCharacter());
        return currentMatchState;
    }

}

