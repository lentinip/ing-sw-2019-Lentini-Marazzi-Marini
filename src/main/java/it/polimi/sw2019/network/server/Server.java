package it.polimi.sw2019.network.server;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.rmi.RmiServer;
import it.polimi.sw2019.network.server.socket.SocketServer;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Mi97ch
 * class used to manage login by new players and creation of new matches
 * every message passes from this class
 */
public class Server {

    /**
     * Default constructor
     */
    public Server() {

        currentWaitingRoom = new VirtualView(this);
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
        loginMessage = new Message();
        loginMessage.setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
        reconnectionMessage = new Message();
        reconnectionMessage.setTypeOfMessage(TypeOfMessage.PLAYER_ALREADY_LOGGED);
    }

    /* Attributes */

    private int rmiPort;

    private int socketPort;

    private VirtualView currentWaitingRoom;

    private SocketServer socketServer;

    private RmiServer rmiServer;

    private Message loginMessage;

    private Message reconnectionMessage;

    private Map<String, VirtualView> virtualViewMap = new HashMap<>();

    private static Logger LOGGER = Logger.getLogger("server");


    /* Methods */

    /**
     * main to start the server
     * @param args usually it takes no args
     */
    @SuppressWarnings("squid:S106")
    public static void main(String[] args) {

            Server server = new Server();
            System.out.println("SERVER STARTED!");

            try {
                server.start(1111, 1099 );
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
    }

    public Map<String, VirtualView> getVirtualViewMap() {
        return virtualViewMap;
    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public VirtualView getWaitingRoom(String username) {
        return virtualViewMap.get(username);
    }

    public VirtualView getCurrentWaitingRoom() {
        return currentWaitingRoom;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * method to start the match
     */
    public void startMatch() {

        Message loginReport = new Message(currentWaitingRoom.getUsernames().get(0));
        loginReport.createLoginReport(currentWaitingRoom.getUsernames().size());
        sendMessage(loginReport);
        currentWaitingRoom.startSetupTimer();
        currentWaitingRoom = new VirtualView(this);
        System.out.print(virtualViewMap.keySet());

    }

    /**
     * this method is used both to manage first login and reconnection of the client
     * @param username to add
     * @param clientInterface connection methods
     */
    public  void addPlayer(String username, ClientInterface clientInterface){

        verifyConnected(username);

        verifyOnline(new Message("All"), currentWaitingRoom);

        System.out.print("\n");
        System.out.print("\nUsername: ");
        System.out.print(username);
        System.out.print("\n");


        //If there is a player connected with the same username, tell the client he has to choose another username
        if(virtualViewMap.containsKey(username) && !virtualViewMap.get(username).getDisconnectedPlayers().contains(username)) {

            System.out.print("\nFirst if\n");

            try {
                loginMessage.createLoginReport(false);
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }

        //If there is a player disconnected with the same username, tell the client if he wants to rejoin that match or if he wants to begin another match (In this case he has to choose another username)
        else if (virtualViewMap.containsKey(username) && !virtualViewMap.get(username).getWaitingPlayers().get(username).getConnected() && virtualViewMap.get(username).getDisconnectedPlayers().contains(username)) {

            System.out.print("\nSecond if\n");

            reconnectionMessage.createReconnectionMessage();
            try {

                clientInterface.notify(reconnectionMessage);
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }

        //If the username is usable
        else {
            try{
                System.out.print("\nThird if");

                Client client = new Client(username, clientInterface);

                currentWaitingRoom.addWaitingPlayer(username, client);
                currentWaitingRoom.getUsernames().add(username);
                virtualViewMap.put(username, currentWaitingRoom);
                loginMessage.createLoginReport(true);
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) { //this exception is called when I can't notify the message of login successful

                removeWaitingPlayer(username);
                LOGGER.log(Level.WARNING, e.getMessage());
            }

            verifyOnline(new Message("all"), currentWaitingRoom);

            if(currentWaitingRoom.getNumOfWaitingPlayers() == 3){

                currentWaitingRoom.startTimer();
            }

            else if (currentWaitingRoom.getNumOfWaitingPlayers() == 5){

                currentWaitingRoom.getTimer().cancel();
                startMatch();
            }
            System.out.print("\nNumber of waiting players: ");
            System.out.print(currentWaitingRoom.getNumOfWaitingPlayers());
            System.out.print("\n\n");
        }
    }

    /**
     * send a message to the specific client in the username field of the message
     * @param message to be sent
     */
    public void sendMessage(Message message){

        try {

            if (virtualViewMap.get(message.getUsername()) != null) {
                virtualViewMap.get(message.getUsername()).getWaitingPlayers().get(message.getUsername()).getClientInterface().notify(message);
            }
        } catch (RemoteException e) {

            virtualViewMap.get(message.getUsername()).addDisconnectedPlayer(message.getUsername());
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * send a message to all clients (except for disconnected ones)
     * @param message to be sent
     * @param virtualView reference to the virtual view
     */
    public void sendAll(Message message, VirtualView virtualView){

        List<String> usernames = new ArrayList<>();

        for(int i = 0; i < virtualView.getNumOfWaitingPlayers(); i++) {

            if(!virtualView.getDisconnectedPlayers().contains(virtualView.getUsernames().get(i))) {

                usernames.add(virtualView.getUsernames().get(i));
            }
        }

        for(String user : usernames) {

            try {

                virtualView.getWaitingPlayers().get(user).getClientInterface().notify(message);
            } catch (RemoteException e) {

                if (virtualViewMap.get(user) != null) {
                    virtualViewMap.get(user).addDisconnectedPlayer(user);
                    LOGGER.log(Level.WARNING, e.getMessage());
                    System.out.print("non sono riuscito a inviare un messaggio al client per verificare che sia connesso");
                }
            }
        }
    }

    /**
     * this method verify if the players in queue are online during matching phase (else it removes them)
     * @param message is a message with null parameter used only to verify connection (does nothing in the client)
     * @param virtualView virtual view
     */
    public void verifyOnline(Message message, VirtualView virtualView) {

        System.out.print("\n sono nella verifyOnline");

        List<String> usernames = new ArrayList<>();
        usernames.addAll(virtualView.getUsernames());

        for(String user : usernames) {

            try {

                System.out.print("\n" + user);
                virtualView.getWaitingPlayers().get(user).notify(message, this, user);
            } catch (RemoteException e1) {

                //removeWaitingPlayer(user);
                LOGGER.log(Level.WARNING, e1.getMessage());
                System.out.print("Ho rimosso il player con username:" + user);
            }
        }
    }



    /**
     * this method receive messages from clients and send them to the VirtualView, also check the message received
     * and do stuff based on the type of message
     * @param message message received
     */
    public void receiveMessage(Message message){

        System.out.print("\n I received message from " + message.getUsername());

        if(message.getTypeOfMessage() == TypeOfMessage.RECONNECTION_REQUEST ) {

            if (virtualViewMap.get(message.getUsername()) != null){

                virtualViewMap.get(message.getUsername()).getWaitingPlayers().get(message.getUsername()).setConnected(true);
                virtualViewMap.get(message.getUsername()).notify(new Message(message.getUsername()));
            }

        }

        else if(message.getTypeOfMessage() == TypeOfMessage.LOGIN_REPORT) {

            addPlayer(message.getUsername(), socketServer.getSocketServerClientHandler());
        }

        else if(message.getTypeOfMessage() == TypeOfMessage.RECONNECTION) {

            reconnectPlayer(message.getUsername(), socketServer.getSocketServerClientHandler());
        }

        else if( virtualViewMap.get(message.getUsername()) != null && virtualViewMap.get(message.getUsername()).getWaitingPlayers().get(message.getUsername()).getConnected()) {

            System.out.print("\n The message as been sent by an authorized player");

            //when the first player chooses the setup options we send to everyOne a message with the choices that the player has taken
            if (message.getTypeOfMessage() == TypeOfMessage.MATCH_SETUP) {

                virtualViewMap.get(message.getUsername()).setMatchSetupMessage(message);

                //stopping the timer
                virtualViewMap.get(message.getUsername()).getMatchSetupChoiceTimer().cancel();
                Message matchStart = new Message("All");

                List<Character> charactersInGame = new ArrayList<>();
                int counter = 3; /* set to 3 because the smaller number of player allowed is 3 */

                charactersInGame.add(Character.DISTRUCTOR);
                charactersInGame.add(Character.BANSHEE);
                charactersInGame.add(Character.DOZER);

                if (counter < virtualViewMap.get(message.getUsername()).getNumOfWaitingPlayers()) {  /* if there are 4 players adding violet */
                    charactersInGame.add(Character.VIOLET);
                    counter++;
                }
                if (counter < virtualViewMap.get(message.getUsername()).getNumOfWaitingPlayers()) {  /* if there are 5 players adding also sprog */
                    charactersInGame.add(Character.SPROG);
                }
                MatchStart matchStartClass = new MatchStart(message, virtualViewMap.get(message.getUsername()).getUsernames(), charactersInGame, VirtualView.getTurnTimer());
                matchStart.createMessageMatchStart(matchStartClass);
                sendAll(matchStart, virtualViewMap.get(message.getUsername()));
            }

            virtualViewMap.get(message.getUsername()).notify(message);
        }
    }

    /**
     * tries to reconnect the player in the match he started before
     * @param username to reconnect
     * @param clientInterface connection
     */
    public void reconnectPlayer(String username, ClientInterface clientInterface) {

        //If there is a player connected with the same username, tell the client he has to choose another username
        if(virtualViewMap.containsKey(username) && !virtualViewMap.get(username).getDisconnectedPlayers().contains(username)) {

            try {
                loginMessage.createLoginReport(false);
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) {

                virtualViewMap.get(username).addDisconnectedPlayer(username);
                virtualViewMap.get(username).getDisconnectedPlayers().add(username);
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
        else {

            virtualViewMap.get(username).getWaitingPlayers().get(username).setClientInterface(clientInterface);
            virtualViewMap.get(username).getDisconnectedPlayers().remove(username);
            virtualViewMap.get(username).getWaitingPlayers().get(username).setConnected(true);

            Message matchStart = new Message(username);

            List<Character> charactersInGame = new ArrayList<>();
            int counter = 3; /* set to 3 because the smaller number of player allowed is 3 */

            charactersInGame.add(Character.DISTRUCTOR);
            charactersInGame.add(Character.BANSHEE);
            charactersInGame.add(Character.DOZER);

            if (counter < virtualViewMap.get(username).getNumOfWaitingPlayers()) {  /* if there are 4 players adding violet */
                charactersInGame.add(Character.VIOLET);
                counter++;
            }
            if (counter < virtualViewMap.get(username).getNumOfWaitingPlayers()) {  /* if there are 5 players adding also sprog */
                charactersInGame.add(Character.SPROG);
            }
            MatchStart matchStartClass = new MatchStart(virtualViewMap.get(username).getMatchSetupMessage(), virtualViewMap.get(username).getUsernames(), charactersInGame, VirtualView.getTurnTimer());
            matchStartClass.setTimeLeft(virtualViewMap.get(username).getTimeLeft());
            matchStart.createMessageMatchStart(matchStartClass);
            sendMessage(matchStart);

            Message updateMatchState = new Message(username);
            virtualViewMap.get(username).notify(updateMatchState);
            if (username.equals(virtualViewMap.get(username).getCurrentPlayer())){
                System.out.print("\nCurrent player reconnected\n");
                sendMessage(virtualViewMap.get(username).getLastMessage());
            }
            //System.out.print("\nLast message :"+ virtualViewMap.get(username).getLastMessage().getTypeOfMessage() + " receiver: " +  virtualViewMap.get(username).getLastMessage().getUsername());
        }
    }

    /**
     * removes the waiting player (without a disconnection) when he disconnects during the game creation phase
     * @param username user disconnected
     */
    public void removeWaitingPlayer(String username) {

        System.out.print("\n");
        System.out.print("\nI'm removing this username: ");
        System.out.print(username);
        System.out.print("\n");



        //I have to reset the timer if I don't have 3 players anymore
        if (currentWaitingRoom.getNumOfWaitingPlayers() < 3){

            currentWaitingRoom.getTimer().cancel();
        }

        currentWaitingRoom.removeWaitingPlayer(username);
        currentWaitingRoom.getUsernames().remove(username);
        virtualViewMap.remove(username);

    }

    /**
     * removes the player
     * @param virtualView virtual view
     */
    public void endMatch(VirtualView virtualView) {

        for(String user : virtualView.getWaitingPlayers().keySet()) {

            virtualViewMap.remove(user);
        }
    }

    /**
     * method to start rmi and socket server
     * @param socketPort socket port
     * @param rmiPort rmi port
     * @throws RemoteException error of connection
     */
    private void start(int socketPort, int rmiPort) throws RemoteException {

        socketServer.startServer(socketPort);
        socketServer.start();
        rmiServer.startServer(rmiPort);
    }

    /**
     * method to verify if the user is still connected
     * @param user user to verify
     */
    public void verifyConnected(String user) {


        if (!currentWaitingRoom.getWaitingPlayers().containsKey(user) && virtualViewMap.keySet().contains(user) && virtualViewMap.get(user).getWaitingPlayers().get(user).getConnected()) {

            Message message = new Message(user);

            try {

                System.out.print("\n" + user);
                virtualViewMap.get(user).getWaitingPlayers().get(user).getClientInterface().notify(message);
            } catch (RemoteException e) {

                virtualViewMap.get(message.getUsername()).addDisconnectedPlayer(message.getUsername());
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
    }
}
