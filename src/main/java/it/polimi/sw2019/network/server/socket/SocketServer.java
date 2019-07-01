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

    private SocketServerClientHandler socketServerClientHandler;

    /* Methods */

    public SocketServerClientHandler getSocketServerClientHandler() {
        return socketServerClientHandler;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    /**
     * creates the serverSocket and binds the port parameter to it
     * @param port
     */
    public void startServer(int port) {

        try {

            serverSocket = new ServerSocket(port);
            LOGGER.log(Level.INFO, "SocketServer is online");
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

            Socket connection;
            try {

                LOGGER.log(Level.INFO, "SocketServer is ready to accept a connection");
                connection = serverSocket.accept();
                (socketServerClientHandler = new SocketServerClientHandler(connection, this)).start();
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

        server.receiveMessage(message);
    }

    /**
     * disconnect the client due to an error in connection
     * @param username to be disconnected
     */
    public void disconnect(String username) {

        /*if (server.getCurrentWaitingRoom().getUsernames().contains(username)){
            server.removeWaitingPlayer(username);
        }*/
        //else {
        if (server.getVirtualViewMap().containsKey(username)) {
            server.getVirtualViewMap().get(username).addDisconnectedPlayer(username);
        }
        //}
    }

}
