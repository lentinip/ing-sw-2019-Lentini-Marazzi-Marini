package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.server.rmi.RmiServer;
import it.polimi.sw2019.network.server.socket.SocketServer;

public class Server {

    /**
     * Default constructor
     */
    public Server() {

        waitingRoom = new VirtualView();
        socketServer = new SocketServer(this);
        rmiServer = new RmiServer(this);
    }

    /* Attributes */
    private int rmiPort;
    private int socketPort;
    private VirtualView waitingRoom;
    private SocketServer socketServer;
    private RmiServer rmiServer;

    /* Methods */

    public static void main(String[] args) {

            Server server = new Server();
            server.start(1111, 1099 );

    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public void startMatch() {

        //TODO implement
        return;
    }

    public void addWaitingPlayer(String username, Client client) {

        waitingRoom.addWaitingPlayer(username, client);
    }

    public void removeWaitingPlayer(String username) {

        waitingRoom.removeWaitingPlayer(username);
    }

    private void start(int socketPort, int rmiPort) {

        socketServer.startServer(socketPort);
        socketServer.start();
        rmiServer.startServer(rmiPort);
    }


}
