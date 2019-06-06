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

        waitingRoom = new VirtualView();
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
        loginMessage = new Message();
        loginMessage.setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
    }

    /* Attributes */
    private int rmiPort;

    private int socketPort;

    private VirtualView waitingRoom;

    private SocketServer socketServer;

    private RmiServer rmiServer;

    private Message loginMessage;

    private Map<String, VirtualView> playersInGame;

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

    public VirtualView getWaitingRoom() {
        return waitingRoom;
    }

    public void startMatch() {

        Message loginReport = new Message(waitingRoom.getUserNames().get(0));
        loginReport.createLoginReport(waitingRoom.getUserNames().size());
        sendMessage(loginReport);
    }

    /**
     * this method is used both to manage first login and reconnection of the client
     * @param username to add
     * @param clientInterface connection methods
     */
    public void addPlayer(String username, ClientInterface clientInterface) {

        if(waitingRoom.getWaitingPlayers().containsKey(username) && waitingRoom.getWaitingPlayers().get(username).getConnected()) {

            try {
                loginMessage.createLoginReport(false);
                clientInterface.notify(loginMessage);
                LOGGER.log(Level.INFO, "You are connected yet");
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
        else if (waitingRoom.getWaitingPlayers().containsKey(username) && !waitingRoom.getWaitingPlayers().get(username).getConnected()) {

            Client client = waitingRoom.getWaitingPlayers().get(username);
            client.setConnected(true);
            try {

                loginMessage.createLoginReport(true);
                clientInterface.notify(loginMessage);
            } catch (RemoteException e) {
                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
        else {
            try {
                Client client = new Client(username, clientInterface);
                if (waitingRoom.getWaitingPlayers().containsKey(username) || username == "all")
                    throw new InvalidParameterException();

                waitingRoom.addWaitingPlayer(username, client);
                loginMessage.createLoginReport(true);
                clientInterface.notify(loginMessage);
                waitingRoom.addWaitingPlayer(username, client);
            } catch (InvalidParameterException e) { //this exception is called when the username chosen by the user isn't correct

                try {
                    loginMessage.createLoginReport(false);
                    clientInterface.notify(loginMessage);
                } catch (RemoteException ex) { //this exception is called when I can't notify the message of failed login

                    LOGGER.log(Level.WARNING, ex.getMessage());
                }
            } catch (RemoteException e) { //this exception is called when I can't notify the message of login successful

                LOGGER.log(Level.WARNING, e.getMessage());
            }

            if(waitingRoom.getNumOfWaitingPlayers() == 3){

                waitingRoom.startTimer();
            }

            else if (waitingRoom.getNumOfWaitingPlayers() == 5){

                waitingRoom.getTimer().cancel();
                startMatch();
            }
        }
    }

    /**
     * if the message is a login message, the player will be registered, else will be handled by controller
     * @param message
     */
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
        //TODO implement
    }
    /**
     * send a message to the specific client in the username field of the message
     * @param message to be sent
     */
    public void sendMessage(Message message){

    }

    /**
     * send a message to all clients (except for disconnected ones)
     * @param message to be sent
     */
    public void sendAll(Message message){

        //TODO implement

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

            if (counter < waitingRoom.getNumOfWaitingPlayers()) {           /* if there are 4 players adding violet */
                charactersInGame.add(Character.VIOLET);
                counter++;
            }
            if (counter < waitingRoom.getNumOfWaitingPlayers()) {          /* if there are 5 players adding also sprog */
                charactersInGame.add(Character.SPROG);
            }
            MatchStart matchStartClass = new MatchStart(message, waitingRoom.getUsernames(), charactersInGame);
            matchStart.createMessageMatchStart(matchStartClass);
            sendAll(matchStart);
        }

        waitingRoom.notify(message);
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
