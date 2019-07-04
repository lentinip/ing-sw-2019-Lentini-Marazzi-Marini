package it.polimi.sw2019.network.client.rmi;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

import java.net.ConnectException;


/**
 * @author Mi97ch
 * this class is used to notify the client by rmi connection
 */
public class ClientImplementation implements ClientInterface {

    /**
     * Constructor
     * @param client reference to the client
     */
    public ClientImplementation(Client client) {

        setClient(client);
    }

    /* Attributes */

    private Client client;


    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    /**
     * method to send the message to the client
     * @param message to be notified
     */
    @Override
    public void notify(Message message) {

        client.handleMessage(message);
    }

}
