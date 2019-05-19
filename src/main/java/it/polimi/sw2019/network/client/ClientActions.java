package it.polimi.sw2019.network.client;

import it.polimi.sw2019.network.messages.Message;

import java.rmi.RemoteException;

/**
 * implemented by SocketClientConnection
 */
public interface ClientActions {

    /**
     * send message to server
     * @param message to be sent
     * @throws RemoteException connection fail
     */
    public void send(Message message) throws RemoteException;
}
