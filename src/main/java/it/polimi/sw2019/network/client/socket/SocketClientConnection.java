package it.polimi.sw2019.network.client.socket;

import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.socket.ServerInterface;
import it.polimi.sw2019.network.client.Client;

/**
 * socket connection logic
 */
public class SocketClientConnection implements ClientInterface, ClientActions {

    /**
     * Default constructor
     */
    public SocketClientConnection() {

    }

    public SocketClientConnection(Client client) {

        this.client = client;
        LineClient lineClient = new LineClient(port, client.getIpAddress(), this);
        lineClient.start();
        this.serverInterface = lineClient;
    }




    /* Attributes */

    private static final int port = 1111;

    private Client client;

    private ServerInterface serverInterface;


    /* Methods */

    @Override
    public  void notify(Message message) {

        client.handleMessage(message);
    }

    @Override
    public void send(Message message) {

        serverInterface.send(message);
    }

    @Override
    public void register(String username) {
        /* Does nothing here */
    }

}
