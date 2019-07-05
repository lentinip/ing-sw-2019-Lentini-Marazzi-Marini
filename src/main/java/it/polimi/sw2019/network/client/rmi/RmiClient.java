package it.polimi.sw2019.network.client.rmi;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import it.polimi.sw2019.network.server.rmi.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mi97ch
 * this class represents the logic of rmi connection on client side
 */
public class RmiClient implements ClientActions {

    public RmiClient(Client client) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(client.getIpAddress(), 1099);
        serverInterface = (ServerInterface) registry.lookup("Server");
        ClientImplementation clientImplementation = new ClientImplementation(client);
        clientInterface = (ClientInterface) UnicastRemoteObject.exportObject(clientImplementation, 0); //Port 0 because I use a random available port
        this.client = client;
    }

    /* Attributes */

    private ServerInterface serverInterface;

    private ClientInterface clientInterface;

    private Client client;

    private static Logger LOGGER = Logger.getLogger("RmiClient");

    /* Methods */

    /**
     * if the username is avaliable, adds the player in the game
     * @param username nickname chose by the player
     */
    @Override
    public void register(String username) throws RemoteException{

            serverInterface.register(username, clientInterface);
    }

    /**
     * calls the messageHandler method in server
     * @param message parameter to use in messageHandler
     */
    @Override
    public void doSomething(Message message) {

        if(message.getTypeOfMessage() == TypeOfMessage.RECONNECTION) {

            try{
                serverInterface.reconnect(message.getUsername(), clientInterface);
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, "connection failure");
                client.getView().displayConnectionFailure();
            }
        }
        else {
            try {
                serverInterface.messageHandler(message);
            } catch (RemoteException e) {

                LOGGER.log(Level.WARNING, "connection failure");
                client.getView().displayConnectionFailure();
            }
        }
    }
}
