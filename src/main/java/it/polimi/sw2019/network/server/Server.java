package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import it.polimi.sw2019.network.server.rmi.RmiServer;
import it.polimi.sw2019.network.server.socket.SocketServer;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
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

        // a message with the match set up has to be sent to the first player of the waitingPLayers
        //TODO implement

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

            if (waitingRoom.getNumOfWaitingPlayers() == 5){

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

        if(waitingRoom.getWaitingPlayers().containsKey(message.getUsername()) && !waitingRoom.getWaitingPlayers().get(message.getUsername()).getConnected()) {

            if(message.getTypeOfMessage() == TypeOfMessage.LOGIN_REPORT) {

                addPlayer(message.deserializeLoginMessage().getUsername(), message.deserializeLoginMessage().getClientInterface());
            } else
            {
                //do nothing
            }
        }
        else{

            waitingRoom.notify(message);
        }
        //TODO implement

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
