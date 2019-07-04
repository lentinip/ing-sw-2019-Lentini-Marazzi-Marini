package it.polimi.sw2019.network.client.socket;

import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import it.polimi.sw2019.network.server.socket.ServerInterface;
import it.polimi.sw2019.network.client.Client;

import java.io.IOException;

/**
 * @author Mi97ch
 * socket connection logic
 */
public class SocketClientConnection implements ClientInterface, ClientActions {

    /**
     * Constructor
     * @param client client reference
     */
    public SocketClientConnection(Client client) {

        this.client = client;
        LineClient lineClient = new LineClient(port, client.getIpAddress(), this, client);
        lineClient.start();
        this.serverInterface = lineClient;
        loginMessage = new Message();
        loginMessage.setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
    }




    /* Attributes */

    private static final int port = 1111;

    private Client client;

    private ServerInterface serverInterface;

    private Message loginMessage;

    /* Methods */

    /**
     * notify message to client
     * @param message to be notified
     */
    @Override
    public  void notify(Message message) {

        client.handleMessage(message);
    }

    /**
     * doSomething message to server
     * @param message to be sent
     */
    @Override
    public void doSomething(Message message) throws IOException{

        System.out.print("\n");
        System.out.print("Do something - Username: ");
        System.out.print(message.getUsername());
        System.out.print("\n");

        serverInterface.send(message);
    }

    /**
     * register the client by Socket connection
     * @param username nickname chose by the player
     */
    @Override
    public void register(String username) throws IOException{

        System.out.print("\n");
        System.out.print("Register - Username: ");
        System.out.print(username);
        System.out.print("\n");

        loginMessage.setUsername(username);
        loginMessage.createLoginMessage(username, false);
        doSomething(loginMessage);
    }



}
