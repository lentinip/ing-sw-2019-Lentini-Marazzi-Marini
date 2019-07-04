package it.polimi.sw2019.network.server;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.logging.Level;

/**
 * @author Mi97ch
 * this class represents the clients in server side
 */
public class Client {

    /**
     * Constructor
     * @param clientInterface reference to the client Interface
     * @param username username of the player who is connecting
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

    /**
     * method to send a message to the client
     * @param message mes to send
     * @param server reference to the server in case of exception (to remove disconnected player)
     * @param user player that is going to receive the message
     * @exception RemoteException exception for connection
     */
    public void notify(Message message, Server server, String user) throws RemoteException {

        try {

            clientInterface.notify(message);
        } catch (RemoteException e) {

            server.removeWaitingPlayer(user);
            server.getLOGGER().log(Level.WARNING, e.getMessage());
        }
    }
}