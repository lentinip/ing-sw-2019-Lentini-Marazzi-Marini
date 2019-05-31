package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class VirtualView {

    /**
     * Default constructor
     */

    public VirtualView() {

    }

    /* Attributes */

    private Map<String, Client> waitingPlayers;

    private List<String> userNames = new ArrayList<>();

    private int NumOfWaitingPlayers = 0;

    private Timer timer = new Timer();

    /* Methods */

    public Timer getTimer() {
        return timer;
    }

    public void setNumOfWaitingPlayers(int numOfWaitingPlayers) {
        NumOfWaitingPlayers = numOfWaitingPlayers;
    }

    public int getNumOfWaitingPlayers() {
        return NumOfWaitingPlayers;
    }

    public Map<String, Client> getWaitingPlayers() {
        return waitingPlayers;
    }

    public void setWaitingPlayers(Map<String, Client> waitingPlayers) {
        this.waitingPlayers = waitingPlayers;
    }

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
        NumOfWaitingPlayers++;
    }

    public void removeWaitingPlayer(String username) {

        waitingPlayers.remove(username);
        NumOfWaitingPlayers--;
    }

    public void display(Message message){
        //TODO implement
    }

}

