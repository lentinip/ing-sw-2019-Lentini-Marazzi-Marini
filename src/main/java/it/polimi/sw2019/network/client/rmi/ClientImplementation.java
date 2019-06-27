package it.polimi.sw2019.network.client.rmi;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

import java.net.ConnectException;

/**
 * this class is used to notify the client by rmi connection
 */
public class ClientImplementation implements ClientInterface {

    /**
     * Constructor
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

    @Override
    public void notify(Message message) {

        client.handleMessage(message);
    }

}
