package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;

public class Controller {

    /**
     * Default constructor
     */
    public Controller() {

    }

    /* Attributes */

    private Match match;

    private Player[] waitingPlayers;

    private TurnManager turnManager;

    /* Methods */

    public void setMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    public Player[] getWaitingPlayers() {
        return waitingPlayers[];
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

    public void turnHandler() {

        //TODO implement
        return;
    }

    public void addWaitingPlayer() {

        //TODO implement
        return;
    }

    public void removeWaitingPlayer() {

        //TODO implement
        return;
    }

    public void matchingTimer() {

        //TODO implement
        return;
    }

    public void initializeCharachters() {

        //TODO implement
        return;
    }

}
