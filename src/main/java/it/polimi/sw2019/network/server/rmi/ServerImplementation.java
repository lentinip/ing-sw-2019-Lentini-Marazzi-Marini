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
     * Constructor
     */

    public ServerImplementation(Server server) throws RemoteException {

        setServer(server);
    }

    /* Attributes */

    Server server = new Server();

    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    @Override
    public void register(String username, ClientInterface clientInterface) {

        server.addPlayer(username, clientInterface);
    }


    @Override
    public void messageHandler(Message message) {

        server.receiveMessage(message);
    }

    @Override
    public void reconnect(String username, ClientInterface clientInterface) {

        server.reconnectPlayer(username, clientInterface);
    }

}