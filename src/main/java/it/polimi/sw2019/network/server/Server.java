package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import it.polimi.sw2019.network.server.rmi.RmiServer;
import it.polimi.sw2019.network.server.socket.SocketServer;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;
import java.util.TimerTask;
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

    private static Logger LOGGER = Logger.getLogger("server");

    /* Methods */

    public static void main(String[] args) {

            Server server = new Server();
            try {
                server.start(1111, 1099 );
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
            server.getWaitingRoom().getTimer().schedule(new TimerTask() {
                @Override
                public void run() {

                    server.startMatch();
                }
            }, 2*60*1000);  //time is in milliseconds
            //after 2 minutes the main server tries to start the match, if the number of players is 2 or less, the timer will be restarted

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

        //if the players aren't enough to start the match, the timer will be reset and the method ends
        if(waitingRoom.getNumOfWaitingPlayers() < 3) {

            waitingRoom.getTimer().schedule(new TimerTask() {
                @Override
                public void run() {

                    startMatch();
                }
            }, 2*60*1000);
        }
        else {

            //TODO implement

        }
    }

    public void addPlayer(String username, ClientInterface clientInterface) {

        if(waitingRoom.getWaitingPlayers().containsKey(username) && waitingRoom.getWaitingPlayers().get(username).getConnected()) {

            try {
                loginMessage.createLoginReport(false);
                clientInterface.notify(loginMessage);
                //TODO implement a text message that tell the user he's connected yet
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

            if(waitingRoom.getNumOfWaitingPlayers() == 5) {

                waitingRoom.getTimer().cancel();
                startMatch();
            }
        }
    }

    public void handleMessage() {

        
    }

    public void removeWaitingPlayer(String username) {

        waitingRoom.removeWaitingPlayer(username);
    }

    private void start(int socketPort, int rmiPort) throws RemoteException {

        socketServer.startServer(socketPort);
        socketServer.start();
        rmiServer.startServer(rmiPort);
    }

}
