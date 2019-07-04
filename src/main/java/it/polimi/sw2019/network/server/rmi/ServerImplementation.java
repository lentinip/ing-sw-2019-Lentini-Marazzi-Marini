package it.polimi.sw2019.network.server.rmi;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Mi97ch
 * class to manage connection for rmi
 */
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{

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

    /**
     * method to connect to the server
     * @param username nickname chose by the player
     * @param clientInterface used by server to create the new Client class
     */
    @Override
    public void register(String username, ClientInterface clientInterface) {

        server.addPlayer(username, clientInterface);
    }


    /**
     * send message received to the handler of the server
     * @param message message received
     */
    @Override
    public void messageHandler(Message message) {

        server.receiveMessage(message);
    }

    /**
     * method used to reconnect a player disconnected
     * @param username client to reconnect
     * @param clientInterface new type of connection
     */
    @Override
    public void reconnect(String username, ClientInterface clientInterface) {

        server.reconnectPlayer(username, clientInterface);
    }

}