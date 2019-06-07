package it.polimi.sw2019.network.server;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.MatchSetup;
import it.polimi.sw2019.network.messages.MatchStart;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import it.polimi.sw2019.network.server.rmi.RmiServer;
import it.polimi.sw2019.network.server.socket.SocketServer;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    /**
     * Default constructor
     */
    public Server() {

        currentWaitingRoom = new VirtualView();
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

    private Map<String, VirtualView> virtualViewMap;

    private static Logger LOGGER = Logger.getLogger("server");


    /* Methods */

    public static void main(String[] args) {

            Server server = new Server();
            try {
                server.start(1111, 1099 );
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
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

    public void startMatch() {

        Message loginReport = new Message(currentWaitingRoom.getUserNames().get(0));
        loginReport.createLoginReport(currentWaitingRoom.getUserNames().size());
        sendMessage(loginReport);
        currentWaitingRoom = new VirtualView();
    }

    /**
     * this method is used both to manage first login and reconnection of the client
     * @param username to add
     * @param clientInterface connection methods
     */
    public void addPlayer(String username, ClientInterface clientInterface) {

        //If there is a player connected with the same username, tell the client he has to choose another username
        if(virtualViewMap.containsKey(username) && !virtualViewMap.get(username).getDisconnectedPlayers().contains(username)) {

            try {
                loginMessage.createLoginReport(false);
                //TODO send message to server that the username is used yet
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }

        //If there is a player disconnected with the same username, tell the client if he wants to rejoin that match or if he wants to begin another match (In this case he has to choose another username)
        else if (virtualViewMap.containsKey(username) && !virtualViewMap.get(username).getWaitingPlayers().get(username).getConnected() && virtualViewMap.get(username).getDisconnectedPlayers().contains(username)) {

                reconnectionMessage.createReconnectionMessage();
            try {

                clientInterface.notify(loginMessage);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }

        //If the username is usable
        else {
            try{
                Client client = new Client(username, clientInterface);

                currentWaitingRoom.addWaitingPlayer(username, client);
                virtualViewMap.put(username, currentWaitingRoom);
                loginMessage.createLoginReport(true);
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) { //this exception is called when I can't notify the message of login successful

                LOGGER.log(Level.WARNING, e.getMessage());
            }

            if(currentWaitingRoom.getNumOfWaitingPlayers() == 3){

                currentWaitingRoom.startTimer();
            }

            else if (currentWaitingRoom.getNumOfWaitingPlayers() == 5){

                currentWaitingRoom.getTimer().cancel();
                startMatch();
            }
        }
    }

 /*   /**
     * if the message is a login message, the player will be registered, else will be handled by controller
     * @param message

    public void handleMessage(Message message) {

        if (waitingRoom.getWaitingPlayers().containsKey(message.getUsername()) && !waitingRoom.getWaitingPlayers().get(message.getUsername()).getConnected()) {
            if (message.getTypeOfMessage() == TypeOfMessage.LOGIN_REPORT) {

                //addPlayer(message.deserializeLoginMessage().getUsername(), message.deserializeLoginMessage().getClientInterface());
            } else {
                //do nothing
            }
        } else {

            waitingRoom.notify(message);
        }
    } */

    /**
     * send a message to the specific client in the username field of the message
     * @param message to be sent
     */
    public void sendMessage(Message message){

        try {

            virtualViewMap.get(message.getUsername()).getWaitingPlayers().get(message.getUsername()).getClientInterface().notify(message);
        } catch (RemoteException e) {

            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    /**
     * send a message to all clients (except for disconnected ones)
     * @param message to be sent
     */
    public void sendAll(Message message, VirtualView virtualView){

        for(String user : virtualViewMap.keySet()) {

            if(virtualViewMap.get(user) == virtualView) {

                try {

                    virtualViewMap.get(user).getWaitingPlayers().get(user).getClientInterface().notify(message);
                } catch (RemoteException e) {

                    LOGGER.log(Level.WARNING, e.getMessage());
                }
            }
        }

    }

    /**
     * this method receive messages from clients and send them to the VirtualView, also check the message received
     * and do stuff based on the type of message
     * @param message
     */
    public void receiveMessage(Message message){



        //when the first player chooses the setup options we send to everyOne a message with the choices that the player has taken
        if (message.getTypeOfMessage() == TypeOfMessage.MATCH_SETUP){

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
            MatchStart matchStartClass = new MatchStart(message, virtualViewMap.get(message.getUsername()).getUsernames(), charactersInGame);
            matchStart.createMessageMatchStart(matchStartClass);
            sendAll(matchStart, virtualViewMap.get(message.getUsername()));
        }

        virtualViewMap.get(message.getUsername()).notify(message);
    }

    /**
     * removes the waiting player (without a disconnection) when he disconnects during the game creation phase
     * @param username
     */
    public void removeWaitingPlayer(String username) {

        //I have to reset the timer if I don't have 3 players anymore
        if (waitingRoom.getNumOfWaitingPlayers() == 3){

            waitingRoom.getTimer().cancel();
        }

        waitingRoom.removeWaitingPlayer(username);
    }

    private void start(int socketPort, int rmiPort) throws RemoteException {

        socketServer.startServer(socketPort);
        socketServer.start();
        rmiServer.startServer(rmiPort);
    }

}
