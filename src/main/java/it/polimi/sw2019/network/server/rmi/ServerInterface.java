package it.polimi.sw2019.network.server.rmi;


import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.commons.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Mi97ch
 * interface for RMI server side
 */
public interface ServerInterface extends Remote {

    /**
     * Used by RMI connection
     * use the message from the client to get the parameters of the method called
     * @param message message received
     * @exception RemoteException error in connection
     */
     void messageHandler(Message message) throws RemoteException;

    /**
     * used during the login phase of the player
     * @param username nickname chose by the player
     * @param clientInterface used by server to create the new Client class
     * @exception RemoteException error in connection
     */
     void register(String username, ClientInterface clientInterface) throws RemoteException;

    /**
     * used to reconnect a client
     * @param username client to reconnect
     * @param clientInterface new type of connection
     * @throws RemoteException in case of errore in connection
     */
     void reconnect(String username, ClientInterface clientInterface) throws RemoteException;
}
