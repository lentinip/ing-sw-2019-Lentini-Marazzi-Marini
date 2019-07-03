package it.polimi.sw2019.network.client;

import it.polimi.sw2019.network.messages.Message;

import java.io.IOException;
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
    void doSomething(Message message) throws IOException;

    /**
     * Used by RMI connection
     * add the client to the waitingPlayers list
     * @param username nickname chose by the player
     */
    void register(String username) throws IOException;
}
