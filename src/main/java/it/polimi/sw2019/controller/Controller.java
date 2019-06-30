package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.VirtualView;
import static it.polimi.sw2019.network.messages.TypeOfMessage.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Observer {

    /**
     * Customized constructor
     */
    public Controller(VirtualView view) {

        this.view = view;
    }
    /* Attributes */

    private Match match;

    private VirtualView view;

    private TurnManager turnManager;

    private static final Logger LOGGER = Logger.getLogger("controller");

    /* Methods */

    public void setMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    /**
     * this method creates the match and all the data structures useful in the game
     * @param message with the setup info
     */
    public void initializeMatch(Message message){

        MatchSetup setupInfo = message.deserializeMatchSetup();

        match = new Match(setupInfo.isFrenzy(), setupInfo.isEightSkulls(), view.getUsernames(), setupInfo.getBoardJsonName(), view);
        turnManager = new TurnManager(match, view);

        //sending the spawn message to the first player
        view.display(turnManager.spawningHandler());
    }

    /**
     * Receives Message from the virtual view and calls different methods checking Message's attributes
     * @param virtualView class that does the notify
     * @param mes message received
     */
    @Override
    public void update(Observable virtualView, Object mes){
        Message message = (Message) mes;

        TypeOfMessage typeOfMessage = message.getTypeOfMessage();

        if (typeOfMessage==MATCH_SETUP) {


            initializeMatch(message);

        }

        else if (typeOfMessage==SINGLE_ACTION){
            turnManager.getSingleActionManager().singleActionHandler(message);
        }

        else if (typeOfMessage==ASK){
            askManager(message);
        }

        else if (typeOfMessage==SELECTED_CARD || typeOfMessage == SELECTED_PLAYER || typeOfMessage == SELECTED_CELL || typeOfMessage == SELECTED_EFFECT || typeOfMessage == SELECTED_COLOR) {
            turnManager.getSingleActionManager().getChoices().selectionHandler(message);
        }

        else if (typeOfMessage==PAYMENT){
            turnManager.getSingleActionManager().getPayment().paymentHandler(message);
        }

        //case match is ended for disconnected players
        else if (typeOfMessage == END_MATCH){

            //calling the end match method of the model
            match.endMatch();
            turnManager.endTurn();
        }

        else if (typeOfMessage == null &&  message.getTypeOfAction() == null){
            match.notifyPrivateHand(match.getPlayerByUsername(message.getUsername()));
            Message actionReport = new Message("All");
            actionReport.createActionReports("  RECONNECTED to the game", match.getPlayerByUsername(message.getUsername()).getCharacter(), null);
            view.display(actionReport);
        }

        else {
            LOGGER.log(Level.SEVERE, "switch error");
        }

    }

    public void askManager(Message message){
        Message answer = new Message(message.getUsername());
        Player currentPlayer = match.getCurrentPlayer();

        switch (message.getTypeOfAction()){
            case MOVE:
                List<Cell> availableCells = currentPlayer.allowedCellsMove();
                List<BoardCoord> convertedCells = new ArrayList<>();
                for (Cell cell : availableCells){
                    convertedCells.add(cell.getCoord());
                }

                answer.createAvailableCellsMessage(TypeOfAction.MOVE, convertedCells);
                break;
            case GRAB:
                List<Cell> allowedCellsGrab = currentPlayer.allowedCellsGrab();
                List<BoardCoord> grabCoord = new ArrayList<>();
                for (Cell cell : allowedCellsGrab){
                    grabCoord.add(cell.getCoord());
                }

                answer.createAvailableCellsMessage(TypeOfAction.GRAB, grabCoord);
                break;
            case SHOOT:
                if (currentPlayer.getState()==State.NORMAL || currentPlayer.getState()==State.ADRENALINIC1){
                    List<IndexMessage> indexMessageList = turnManager.getSingleActionManager().createShootMessage(currentPlayer);
                    answer.createAvailableCardsMessage(TypeOfAction.SHOOT, indexMessageList, true);
                }

                //Otherwise calculates the available cells
                else {
                    List<Cell> shootingCells = currentPlayer.allowedCellsShoot();
                    List<BoardCoord> shootCoord = new ArrayList<>();
                    for (Cell cell : shootingCells){
                        shootCoord.add(cell.getCoord());
                    }

                    answer.createAvailableCellsMessage(TypeOfAction.MOVEBEFORESHOOT, shootCoord);
                }
                break;
            case USEPOWERUP:
                List<Powerup> powerups = currentPlayer.usablePowerups();

                if (turnManager.isFirstRound()){
                    List<Player> playerList = new ArrayList<>(match.getPlayers());
                    playerList.remove(currentPlayer);

                    List<Player> playerListTemp = new ArrayList<>(playerList);

                    for (Player player : playerListTemp){
                        if (player.getPosition()==null){
                            playerList.remove(player);
                        }
                    }

                    if (playerList.isEmpty()){

                        List<Powerup> powerupList = new ArrayList<>(powerups);

                        for (Powerup powerup : powerupList){
                            if (powerup.getMove()!=null){
                                powerups.remove(powerup);
                            }
                        }
                    }
                }

                List<IndexMessage> powerupsIndex = new ArrayList<>();

                for (Powerup powerup : powerups){
                    powerupsIndex.add(new IndexMessage(currentPlayer.getPowerupIndex(powerup)));
                }

                answer.createAvailableCardsMessage(TypeOfAction.USEPOWERUP, powerupsIndex, false);
                break;
            case RELOAD:
                //If the player asks for a reload, can't do other actions
                match.setCurrentPlayerLeftActions(0);

                List<IndexMessage> indexMessageList = turnManager.getSingleActionManager().createReloadMessage(currentPlayer);
                //The view checks if the indexMessageList is empty
                answer.createAvailableCardsMessage(TypeOfAction.RELOAD, indexMessageList, true);
                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;
        }

        view.display(answer);
    }
}
