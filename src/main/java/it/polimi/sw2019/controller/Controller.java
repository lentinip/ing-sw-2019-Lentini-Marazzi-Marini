package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.VirtualView;
import static it.polimi.sw2019.network.messages.TypeOfMessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    /**
     * Default constructor
     */
    public Controller(Match match, VirtualView view) {
        this.match = match;
        this.view = view;
        turnManager = new TurnManager(match, view);
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private TurnManager turnManager;

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

    public void initializeMatch() {

        //TODO implement
        return;
    }

    /**
     * Receives Message from the virtual view and calls different methods checking Message's attributes
     * @param virtualView
     * @param mes
     */
    @Override
    public void update(Observable virtualView, Object mes){
        Message message = (Message) mes;

        TypeOfMessage typeOfMessage = message.getTypeOfMessage();

        if (typeOfMessage==MATCH_SETUP){
            initializeMatch();
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

        else {

            //TODO implement exception for tests
            //If you arrive here there's a problem
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

                //Otherwise send the available cells
                else {
                    List<Cell> shootingCells = currentPlayer.allowedCellsShoot();
                    List<BoardCoord> shootCoord = new ArrayList<>();
                    for (Cell cell : shootingCells){
                        shootCoord.add(cell.getCoord());
                    }

                    answer.createAvailableCellsMessage(TypeOfAction.SHOOT, shootCoord);
                }
                break;
            case USEPOWERUP:
                List<Powerup> powerups = currentPlayer.usablePowerups();
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
                //TODO exception
        }

        view.display(answer);
    }


}
