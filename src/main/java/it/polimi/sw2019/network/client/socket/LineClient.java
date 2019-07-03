package it.polimi.sw2019.network.client.socket;


import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.socket.ServerInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * creates the connection between client and server and uses ServerInterface methods to communicate with the server
 */
public class LineClient extends Thread implements ServerInterface {

    /**
     * Constructor
     */
    public LineClient(int port, String host, ClientInterface clientInterface, Client client) {
        this.port = port;
        this.host = host;
        clientClass = client;

        startLine(clientInterface);
    }



    /* Attributes */

    private Socket socketClient;

    private int port;

    private String host;

    private ClientInterface client;

    private ObjectInputStream ObjectIn;

    private ObjectOutputStream ObjectOut;

    private Boolean connected;

    private static Logger LOGGER = Logger.getLogger("Socket connection");

    private Client clientClass;

    /* Methods */

    private void startLine(ClientInterface clientInterface) {

        try {
            this.client = clientInterface;
            socketClient = new Socket(host, port);
            ObjectOut = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
            ObjectOut.flush();
            ObjectIn = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
            connected = true;
        } catch (UnknownHostException e) {
            connected = false;
        } catch (IOException e) {
            connected = false;
        }


    }

    /**
     * sends message the server
     * @param message to doSomething
     */
    @Override
    public void send(Message message) throws IOException,NullPointerException {

            ObjectOut.reset();
            ObjectOut.writeObject(message);
            ObjectOut.flush();

    }

    /**
     * start listening for messages
     */
    @Override
    public void run() {

        boolean go = true;
        while (socketClient != null && go && !socketClient.isClosed() && connected) {

            try{
                Message message = (Message) ObjectIn.readObject();
                if(message == null) {
                    go = false;
                } else {
                    client.notify(message);
                }
            } catch (IOException|ClassNotFoundException e) {

                go = false;
                LOGGER.log(Level.WARNING, "failure: error occurred during connection to server");
                clientClass.getView().displayConnectionFailure();
            }
        }
    }



}
