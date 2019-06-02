package it.polimi.sw2019.network.server.rmi;

import it.polimi.sw2019.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMIserver runned by the server
 */
public class RmiServer implements Remote {

    /**
     * Default  constructor
     */
    public RmiServer() {

    }

    public RmiServer(Server server) {
        setServer(server);
    }

    /* Attributes */

    private Server server;

    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    public void startServer(int port) throws RemoteException {

        Registry registry = LocateRegistry.createRegistry(port);
        ServerImplementation serverImplementation = new ServerImplementation();
        try {
            registry.rebind("Server", serverImplementation);
        } catch (RemoteException e) {

            //TODO implement exception
        }
    }
}
