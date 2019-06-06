package it.polimi.sw2019.network.client;

import it.polimi.sw2019.network.messages.Message;

import java.rmi.RemoteException;

/**
 * implemented by SocketClientConnection
 */
public interface ClientActions {

    /**
     * in case of rmi connection it calls the method handleMessage from server (remotely) with message as parameter
     * in case of socket connection it sends the message to the server that will elaborate it
     * @param message to be sent
     * @throws RemoteException connection fail
     */
    public void doSomething(Message message) throws RemoteException;

    /**
     * Used by RMI connection
     * add the client to the waitingPlayers list
     * @param username nickname chose by the player
     */
    public void register(String username);
}
