package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Socket server runned by the main server
 */
public class SocketServer extends Thread {

    /**
     * Constructor
     */
    public SocketServer(Server server) {
        setServer(server);
    }

    /* Attributes */

    private ServerSocket serverSocket;

    private Server server;

    private static Logger LOGGER = Logger.getLogger("SocketServer");

    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * creates the serverSocket and binds the port parameter to it
     * @param port
     */
    public void startServer(int port) {

        try {

            serverSocket = new ServerSocket(port);
        } catch (IOException e) {

            LOGGER.log(Level.WARNING, e.getMessage());
        }

    }

    /**
     * with this method the server is ready to accept a connection
     */
    @Override
    public void run() {

        while(true) {

            try {

                Socket connection = serverSocket.accept();
                (new SocketServerClientHandler(connection, this)).start();
            } catch (IOException e) {

                LOGGER.log(Level.WARNING, e.getMessage());
            }
        }
    }

    /**
     * forward message to server
     * @param message to be forwarded
     */
    public void receive(Message message) {

        server.handleMessage(message);
    }

    /**
     * disconnect the client due to an error in connection
     * @param username to be disconnected
     */
    public void disconnect(String username) {

        server.getWaitingRoom().getWaitingPlayers().get(username).setConnected(false);
        server.getWaitingRoom().addDisconnectedPlayer(username);
        server.getWaitingRoom().removeWaitingPlayer(username);
    }

}
