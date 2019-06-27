package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.logging.Level;

/**
 * this class represents the clients in server side
 */
public class Client {

    /**
     * Constructor
     */
    public Client(String username, ClientInterface clientInterface) {

        setUsername(username);
        setClientInterface(clientInterface);
        setConnected(true);
    }

    /* Attributes */

    private String username;

    private ClientInterface clientInterface;

    private Boolean connected;

    /* Methods */

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public void notify(Message message, Server server, String user) throws ConnectException {

        try {

            clientInterface.notify(message);
        } catch (RemoteException e) {

            server.removeWaitingPlayer(user);
            server.getLOGGER().log(Level.WARNING, e.getMessage());
        }
    }
}