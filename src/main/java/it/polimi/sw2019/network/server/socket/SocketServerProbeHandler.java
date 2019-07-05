package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mi97ch, lentinip
 * Socket server runned by the main server for probe messages
 */
public class SocketServerProbeHandler extends Thread {

    /**
     * Constructor
     * @param server server
     */
    public SocketServerProbeHandler(Server server) {
        setServer(server);
    }

    /* Attributes */

    private ServerSocket serverSocket;

    private Server server;

    private static Logger LOGGER = Logger.getLogger("SocketServerProbeHandler");


    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    /**
     * creates the serverSocket and binds the port parameter to it
     * @param port port to connect
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
                serverSocket.accept();
            } catch (IOException e) {

                LOGGER.log(Level.WARNING, "Problems in the SocketServerProbeHandler");
            }
        }
    }

}
