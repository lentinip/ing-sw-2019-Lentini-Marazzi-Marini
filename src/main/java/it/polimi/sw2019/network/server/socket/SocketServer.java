package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread {

    /**
     * Socket server runned by the server
     */

    /**
     * Default constructor
     */

    public SocketServer() {

    }

    public SocketServer(Server server) {
        setServer(server);
    }



    /* Attributes */

    private ServerSocket serverSocket;

    private Server server;

    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    public void startServer(int port) {

        try {
            serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            //TODO manage exception
        }

    }

    @Override
    public void run() {

        while(true) {

            try {
                Socket connection = serverSocket.accept();
            } catch (IOException e) {
                //TODO manage exception
            }
        }
    }

}
