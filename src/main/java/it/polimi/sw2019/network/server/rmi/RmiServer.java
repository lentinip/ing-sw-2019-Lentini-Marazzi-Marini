package it.polimi.sw2019.network.server.rmi;

import it.polimi.sw2019.network.server.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RMIserver runned by the main server
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

    private static Logger LOGGER = Logger.getLogger("RmiServer");

    /* Methods */

    public void setServer(Server server) {
        this.server = server;
    }

    public void startServer(int port) throws RemoteException {

        Registry registry = LocateRegistry.createRegistry(port);
        ServerImplementation serverImplementation = new ServerImplementation(server);
        try {
            registry.rebind("Server", serverImplementation);
            LOGGER.log(Level.INFO, "RmiServer is online");
        } catch (RemoteException e) {

            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }
}
