package it.polimi.sw2019.network.client.rmi;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

public class ClientImplementation implements ClientInterface {

    /**
     * Constructor
     */
    public ClientImplementation(Client client) {

        setClient(client);
    }

    /* Attributes */

    Client client = new Client();

    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void notify(Message message) {

        //TODO implement what to do with the message in client
    }

}
