package it.polimi.sw2019.network.server;

import com.google.gson.Gson;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.*;

public class VirtualView extends Observable implements Observer {

    /**
     * Default constructor
     */

    public VirtualView(){

        TimeConfigurations timeConfigurations = new TimeConfigurations();
        Gson gson = new Gson();
        File jsonFile = Paths.get("/src/main/resources/configurations.json").toFile();
        try {
            timeConfigurations = gson.fromJson(new FileReader(jsonFile), TimeConfigurations.class);
        }
        catch (FileNotFoundException e){
            System.console().printf("File not found");
        }
        //reading the timer from the file and converting it in milliseconds
        setTurnTimer(timeConfigurations.getTurnTimer()*1000);
        setMatchCreationTimer(timeConfigurations.getMatchCreationTime()*1000);
        setCounterAttackPowerupTimer(timeConfigurations.getCounterAttackPowerupTimer()*1000);
    }

    /* Attributes */

    private Server server;

    private Map<String, Client> waitingPlayers;

    private List<String> userNames = new ArrayList<>();

    private List<String> disconnectedPlayers = new ArrayList<>();

    private String messageSender; //who has sent me last message, used to manage timer elapsed cases

    private String currentPlayer; //the player that is taking the turn

    private static long matchCreationTimer;

    private static long turnTimer;  //used for the duration of the turn

    private static long counterAttackPowerupTimer; //used for the time to choose if you want to use the tagback grenade

    private Timer timer = new Timer(); //used to manage the creation of the match

    private Timer turn = new Timer(); //used to manage the duration of a turn

    private Timer responseTimer = new Timer(); //used to manage the time of response for tagback


    /* Methods */

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Timer getTimer() {
        return timer;
    }

    public int getNumOfWaitingPlayers() {
        return userNames.size();
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

    public static long getCounterAttackPowerupTimer() {
        return counterAttackPowerupTimer;
    }

    public static void setCounterAttackPowerupTimer(long counterAttackPowerupTimer) {
        VirtualView.counterAttackPowerupTimer = counterAttackPowerupTimer;
    }

    public static long getTurnTimer() {
        return turnTimer;
    }

    public static void setTurnTimer(long turnTimer) {
        VirtualView.turnTimer = turnTimer;
    }

    public List<String> getUsernames() {
        return userNames;
    }

    public List<String> getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    public void setDisconnectedPlayers(List<String> disconnectedPlayers) {
        this.disconnectedPlayers = disconnectedPlayers;
    }

    public String getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public static long getMatchCreationTimer() {
        return matchCreationTimer;
    }

    public static void setMatchCreationTimer(long matchCreationTimer) {
        VirtualView.matchCreationTimer = matchCreationTimer;
    }

    public Timer getResponseTimer() {
        return responseTimer;
    }

    public Timer getTurn() {
        return turn;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void addWaitingPlayer(String username, Client client) {

        waitingPlayers.put(username, client);
        userNames.add(username);
    }

    public void removeWaitingPlayer(String username) {

        waitingPlayers.remove(username);
        userNames.remove(username);
    }

    public void addDisconnectedPlayer(String username){

        disconnectedPlayers.add(username);

        //sending a message to tell everybody the player is disconnected
        Message disconnectionMes = new Message("All");
        disconnectionMes.createDisconnectionMessage(userNames.indexOf(username));
        display(disconnectionMes);

        if (getNumOfWaitingPlayers() - disconnectedPlayers.size() < 3){

            sendEndMatchMessage();
        }
    }

    /**
     * sending a message to tell the controller to end the match
     */
    public void sendEndMatchMessage(){

        Message message = new Message(null);
        message.setTypeOfMessage(TypeOfMessage.END_MATCH);
        notify(message);
    }

    public void removeDisconnectedPlayer(String username){

        disconnectedPlayers.remove(username);
    }

    public void startTimer(){

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                server.startMatch();
            }
        }, matchCreationTimer);
    }

    /**
     * this method start the timer when a player starts his turn
     * @param currentPlayer the user of the player that is playing
     */
    public void startTurnTimer(String currentPlayer){

        turn.schedule(new TimerTask() {
            @Override
            public void run() {

                addDisconnectedPlayer(currentPlayer);
                sendReconnectionRequest(currentPlayer);
                sendEndTurnMessage();
            }
        }, turnTimer);
    }

    /**
     * if the timer elapses before the player click on end turn an automatic end
     * turn message is sent and the player is marked as disconnected
     */
    public void sendEndTurnMessage(){

        Message endTurnMessage = new Message(messageSender);
        endTurnMessage.createEndTurnMessage();
        notify(endTurnMessage);
    }

    public void sendReconnectionRequest(String currentPlayer){

       Message reconnectionRequest = new Message(currentPlayer);
       reconnectionRequest.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
       display(reconnectionRequest);
    }

    public void startResponseMessage(){

        responseTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                sendAutomaticResponse();
            }
        }, counterAttackPowerupTimer);
    }

    /**
     * if the timer elapses before the player click on yes/no an automatic no
     * message is sent
     */
    public void sendAutomaticResponse(){

        Message noMessage = new Message(messageSender);
        // negative index used to say 'no'
        noMessage.createSelectionForUsePowerup(-1);
        notify(noMessage);
    }

    /**
     * send the message to the client, if username is "All", send the message to all the clients,
     * otherwise send it to the specific client
     * consider if the client is disconnected and send an automatic response to the controller
     * @param message to be sent
     */
    public void display(Message message){

        if (message.getUsername().equals("All")){

            server.sendAll(message);
        }

        else {

            // the player is disconnected
            if (disconnectedPlayers.contains(message.getUsername())){

                //if he has received an ask to use a tagback I answer no to the controller instantly
                if (message.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CARDS && !message.getUsername().equals(currentPlayer)){

                    sendAutomaticResponse();
                }

                //otherwise I send a message to the controller to go to the next player because this one is disconnected
                else {

                    sendEndTurnMessage();
                }
            }
            //sending the message to the client
            else {

                server.sendMessage(message);
            }
        }
    }

    public void notify(Message message) {

        setChanged();
        notifyObservers(message);
    }

    @Override
    public void update(Observable match, Object mes){

        Message message = (Message) mes;
        display(message);
    }
}

