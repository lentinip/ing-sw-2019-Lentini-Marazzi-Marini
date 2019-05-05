package it.polimi.sw2019.network.client;

import it.polimi.sw2019.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientActions extends Remote {

    public void notify(Message message) throws RemoteException;
}
