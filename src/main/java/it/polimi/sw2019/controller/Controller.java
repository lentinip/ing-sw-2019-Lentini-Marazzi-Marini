package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.VirtualView;

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

        if (typeOfMessage==TypeOfMessage.MATCH_SETUP){
            initializeMatch();
        }

        else if (typeOfMessage==TypeOfMessage.SINGLE_ACTION){
            turnManager.getSingleActionManager().singleActionHandler(message);
        }

        else if (typeOfMessage==TypeOfMessage.ASK){
            askManager(message);
        }

        else if (typeOfMessage==TypeOfMessage.SELECTED_CARD || typeOfMessage == TypeOfMessage.SELECTED_PLAYER || typeOfMessage == TypeOfMessage.SELECTED_CELL || typeOfMessage == TypeOfMessage.SELECTED_EFFECT) {
            turnManager.getSingleActionManager().getShootingChoices().selectionHandler(message);
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
                    List<Weapon> usableWeapon = currentPlayer.availableWeapons();
                    List<IndexMessage> indexMessageList = new ArrayList<>();

                    for (Weapon weapon : usableWeapon){
                        indexMessageList.add(new IndexMessage(currentPlayer.getWeaponIndex(weapon)));
                    }
                    answer.createAvailableCardsMessage(TypeOfAction.SHOOT, indexMessageList, true);
                }

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
                //TODO implementation
            default:
                //TODO exception
        }

        view.display(answer);
    }


}
