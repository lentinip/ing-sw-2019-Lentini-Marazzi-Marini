package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VirtualView {

    /**
     * Default constructor
     */

    public VirtualView() {

    }

    /* Attributes */

    private Map<String, Client> waitingPlayers;
    private List<String> userNames = new ArrayList<>();

    /* Methods */

    public void setUsernames(List<String> usernames) {
        this.userNames = usernames;
    }

    public List<String> getUsernames() {
        return userNames;
    }

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

    public void display(Message message){
        //TODO implement
    }

}

