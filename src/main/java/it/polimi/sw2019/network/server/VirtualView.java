package it.polimi.sw2019.network.server;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;


import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualView extends Observable implements Observer {

    /**
     * Default constructor
     */

    public VirtualView(Server server){
        setServer(server);

        TimeConfigurations timeConfigurations = new TimeConfigurations();
        Gson json = new Gson();
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/configurations.json")));
            timeConfigurations = json.fromJson(jsonReader, TimeConfigurations.class);
        }
        catch (Exception e){
            LOGGER.log(Level.SEVERE, "file not found");
        }
        //reading the timer from the file and converting it in milliseconds
        setTurnTimer(timeConfigurations.getTurnTimer()*1000);
        setMatchCreationTimer(timeConfigurations.getMatchCreationTime()*1000);
        setQuickResponseTimer(timeConfigurations.getCounterAttackPowerupTimer()*1000);
        setMatchSetupTimer(timeConfigurations.getMatchSetupTimer()*1000);
        addObserver(new Controller(this));

    }

    /* Attributes */

    private Server server;

    private Map<String, Client> waitingPlayers = new HashMap<>();

    private List<String> userNames = new ArrayList<>();

    private List<String> disconnectedPlayers = new ArrayList<>();

    private String messageSender; //who has sent me last message, used to manage timer elapsed cases

    private String currentPlayer; //the player that is taking the turn

    private static long matchCreationTimer;

    private static long turnTimer;  //used for the duration of the turn

    private static long quickResponseTimer; //used for the time to choose if you want to use the tagback grenade, and to select powerup for the spawn

    private static long matchSetupTimer; //used for time to choose match setup settings

    private Timer timer = new Timer(); //used to manage the creation of the match

    private Timer turn = new Timer(); //used to manage the duration of a turn

    private Timer responseTimer = new Timer(); //used to manage the time of response for tagback

    private Timer spawningChoiceTimer = new Timer(); //used to manage the time of response for spawn

    private Timer matchSetupChoiceTimer = new Timer(); //used to manage the choice of setups

    private Timer clientTimer = new Timer(); //used to track timeLeft for the client

    private static final Logger LOGGER = Logger.getLogger("virtual view");

    private Message matchSetupMessage;

    private Message lastMessage;

    private long timeLeft;

    private boolean ended = false;

    /* Methods */

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setMatchSetupMessage(Message matchSetupMessage) {
        this.matchSetupMessage = matchSetupMessage;
    }

    public Message getMatchSetupMessage() {
        return matchSetupMessage;
    }

    public static void setMatchSetupTimer(long matchSetupTimer) {
        VirtualView.matchSetupTimer = matchSetupTimer;
    }

    public static long getMatchSetupTimer() {
        return matchSetupTimer;
    }

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

    public static long getQuickResponseTimer() {
        return quickResponseTimer;
    }

    public static void setQuickResponseTimer(long quickResponseTimer) {
        VirtualView.quickResponseTimer = quickResponseTimer;
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

    public Timer getSpawningChoiceTimer() {
        return spawningChoiceTimer;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Timer getMatchSetupChoiceTimer() {
        return matchSetupChoiceTimer;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void addWaitingPlayer(String username, Client client) {

        waitingPlayers.put(username, client);
    }

    public void removeWaitingPlayer(String username) {

        waitingPlayers.remove(username);
        userNames.remove(username);
    }

    public void addDisconnectedPlayer(String username){

        Message message = new Message(username);
        message.setTypeOfMessage(TypeOfMessage.EXIT_GAME);

        waitingPlayers.get(username).setConnected(false);
        if (!disconnectedPlayers.contains(username)) {
            disconnectedPlayers.add(username);
        }
        System.out.print("\n Offline players:" + disconnectedPlayers);

        //sending a message to tell everybody the player is disconnected
        Message disconnectionMes = new Message("All");
        disconnectionMes.createDisconnectionMessage(userNames.indexOf(username));
        display(disconnectionMes);

        int counter = 0;

        for(String user : waitingPlayers.keySet()) {

            if(!waitingPlayers.get(user).getConnected()) {

                counter++;
            }
        }

        if(tooManyDisconnected(3)) {

            sendEndMatchMessage();
        }
    }

    private boolean tooManyDisconnected(int quantity) {

        int counter = 0;

        List<String> players = new ArrayList<>(waitingPlayers.keySet());

        for (String user : players) {

            if (!waitingPlayers.get(user).getConnected()) {

                counter++;
            }
        }

        return (getNumOfWaitingPlayers() - counter < quantity);
    }

    /**
     * sending a message to tell the controller to end the match
     */
    public void sendEndMatchMessage(){

        Message message = new Message(null);
        message.setTypeOfMessage(TypeOfMessage.END_MATCH);
        notify(message);
        ended = true;
    }

    public void removeDisconnectedPlayer(String username){

        disconnectedPlayers.remove(username);
    }

    public void startSetupTimer(){
        matchSetupChoiceTimer = new Timer();

        matchSetupChoiceTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                Message fail = new Message("All");
                fail.setTypeOfMessage(TypeOfMessage.DISCONNECTION_SETUP);
                display(fail);
                removeWaitingRoom();

            }
        }, matchSetupTimer);
    }

    public void removeWaitingRoom(){
        server.endMatch(this);
    }

    public void startTimer(){

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                System.out.print("\n sono prima della verifyOnline \n");
                server.verifyOnline(new Message("all"), server.getCurrentWaitingRoom());
                System.out.print("\n sono prima dell'if \n");
                if(server.getCurrentWaitingRoom().getNumOfWaitingPlayers() > 2) {

                    System.out.print("\n sono nell'if \n");
                    server.startMatch();
                }
            }
        }, matchCreationTimer);
    }

    /**
     * this method start the timer when a player starts his turn
     * @param currentPlayer the user of the player that is playing
     */
    public void startTurnTimer(String currentPlayer){

        turn = new Timer();

        turn.schedule(new TimerTask() {
            @Override
            public void run() {

                if (waitingPlayers.get(currentPlayer).getConnected()) {
                    sendReconnectionRequest(currentPlayer);
                    waitingPlayers.get(currentPlayer).setConnected(false);
                }
                sendEndTurnMessage();
            }
        }, turnTimer);

        if (clientTimer!=null){
            clientTimer.cancel();
        }
        timeLeft = turnTimer;
        clientTimer = new Timer();
        clientTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeLeft = timeLeft - 1000;
            }
        }, 0, 1000);

    }

    /**
     * if the timer elapses before the player click on end turn an automatic end
     * turn message is sent and the player is marked as disconnected
     */
    public void sendEndTurnMessage(){

        if (tooManyDisconnected(3) && getNumOfWaitingPlayers() > 3){
            sendEndMatchMessage();
        }
        else if (tooManyDisconnected(2)){
            sendEndMatchMessage();
        }
        else {
            Message endTurnMessage = new Message(currentPlayer);
            endTurnMessage.createEndTurnMessage();
            notify(endTurnMessage);
        }
    }

    public void sendReconnectionRequest(String currentPlayer){

       Message reconnectionRequest = new Message(currentPlayer);
       reconnectionRequest.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
       display(reconnectionRequest);
    }

    /**
     * if the timer elapses before the player click on yes/no an automatic
     * response message is sent
     */
    public void startResponseMessage(){

        responseTimer = new Timer();

        responseTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (waitingPlayers.get(messageSender).getConnected()) {
                    sendReconnectionRequest(messageSender);
                    waitingPlayers.get(messageSender).setConnected(false);
                }
                sendAutomaticResponse();
            }
        }, quickResponseTimer);
    }

    /**
     * if the timer elapses before the player chooses a spawn location we choose it for him
     */
    public void startSpawnMessage(){

        spawningChoiceTimer = new Timer();

        spawningChoiceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.log(Level.INFO, "TIMER FOR SPAWN FINISHED");
                System.out.print("\n"+ messageSender +"\n");
                if (waitingPlayers.get(messageSender).getConnected()) {
                    sendReconnectionRequest(messageSender);
                    waitingPlayers.get(messageSender).setConnected(false);
                }
                sendAutomaticSpawn();
            }
        }, quickResponseTimer);
    }

    /**
     * automatic response for spawn choice
     */
    public void sendAutomaticSpawn(){

       Message automaticSpawn = new Message(messageSender);
       System.out.println("automatic spawn : spawning player = " + messageSender);
       automaticSpawn.createSpawnSelection(0);
       notify(automaticSpawn);

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
    public void display(Message message) {

        if (message.getUsername().equals("All")) {

            server.sendAll(message, this);
            if(message.getTypeOfMessage() == TypeOfMessage.END_MATCH) {

                server.endMatch(this);
            }
        }
        else {
            // the player is disconnected
            if (!waitingPlayers.get(message.getUsername()).getConnected() && message.getTypeOfMessage() != TypeOfMessage.PRIVATE_HAND) {

                if (message.getTypeOfAction() == TypeOfAction.SPAWN && message.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CARDS){

                    System.out.println("display - message receiver disconnected = " + message.getUsername());
                    sendAutomaticSpawn();
                }

                //if he has received an ask to use a tagback I answer no to the controller instantly
                else if (message.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CARDS && !message.getUsername().equals(currentPlayer)) {

                    sendAutomaticResponse();
                }

                //otherwise I send a message to the controller to go to the next player because this one is disconnected
                else {
                    sendEndTurnMessage();
                }
            }
            //sending the message to the client
            else {

                if (message.getUsername().equals(currentPlayer)){
                    saveLastMessage(message);
                }
                server.sendMessage(message);
            }
        }
    }

    public void saveLastMessage(Message message){

        TypeOfMessage type = message.getTypeOfMessage();
        if (type != TypeOfMessage.PRIVATE_HAND && type != TypeOfMessage.LOGIN_REPORT && type != TypeOfMessage.DISCONNECTED && type != TypeOfMessage.RECONNECTION_REQUEST && type != TypeOfMessage.RECONNECTION && type != TypeOfMessage.PLAYER_ALREADY_LOGGED){

            lastMessage = message;
        }
    }

    public void notify(Message message) {

        if (!ended) {
            System.out.println("\n Sto gestendo il messaggio ricevuto");
            System.out.println(" type of message : " + message.getTypeOfMessage() + "  type of action: " + message.getTypeOfAction());
            System.out.println(" sender : " + message.getUsername());
            setChanged();
            notifyObservers(message);
        }
    }

    @Override
    public void update(Observable match, Object mes){

        Message message = (Message) mes;
        display(message);
    }
}

