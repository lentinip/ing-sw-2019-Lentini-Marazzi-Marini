package it.polimi.sw2019.network.client;

import it.polimi.sw2019.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Mi97ch
 * implemented by SocketClientConnection, SocketServerClientHandler
 */
public interface ClientInterface extends Remote {

    /**
     * notify a message to the client
     * @param message to be notified
     * @throws RemoteException connection fail
     */
     void notify(Message message) throws RemoteException;

}
