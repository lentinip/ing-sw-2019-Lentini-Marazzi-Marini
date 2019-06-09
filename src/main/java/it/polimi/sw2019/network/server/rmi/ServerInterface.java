package it.polimi.sw2019.network.server.rmi;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    /**
     * Used by RMI connection
     * use the message from the client to get the parameters of the method called
     * @param message
     */
    public void messageHandler(Message message) throws RemoteException;

    /**
     * used during the login phase of the player
     * @param username nickname chose by the player
     * @param clientInterface used by server to create the new Client class
     */
    public void register(String username, ClientInterface clientInterface) throws RemoteException;

    /**
     * used to reconnect a client
     * @param username client to reconnect
     * @param clientInterface new type of connection
     * @throws RemoteException
     */
    public void reconnect(String username, ClientInterface clientInterface) throws RemoteException;
}
