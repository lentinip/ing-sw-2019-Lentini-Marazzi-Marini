package it.polimi.sw2019.network.server.rmi;

import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{

    /**
     * Default constructor
     */

    public ServerImplementation() throws RemoteException {

    }

    /* Attributes */

    Server server = new Server();

    /* Methods */

    @Override
    public void register(String username, ClientInterface clientInterface) {

        server.addPlayer(username, clientInterface);
    }


    @Override
    public void messageHandler(Message message) {

        //TODO implement on the server the function that gets the message and use them
    }

}