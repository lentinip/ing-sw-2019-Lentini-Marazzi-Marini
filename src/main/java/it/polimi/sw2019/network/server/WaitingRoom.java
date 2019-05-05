package it.polimi.sw2019.network.server;

import java.util.Map;

public class WaitingRoom {

    /**
     * Default constructor
     */

    public WaitingRoom() {

    }

    /* Attributes */

    private Map<String, Client> waitingPlayers;

    /* Methods */

    public void timer() {

        //TODO implement
        return;
    }

    public void addWaitingPlayer(String username, Client client) {

        waitingPlayers.put(username, client);
    }

    public void removeWaitingPlayer(String username) {

        waitingPlayers.remove(username);
    }

}

