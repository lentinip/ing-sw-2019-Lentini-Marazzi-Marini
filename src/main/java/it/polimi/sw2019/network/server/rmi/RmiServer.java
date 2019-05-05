package it.polimi.sw2019.network.server.rmi;

import it.polimi.sw2019.network.server.Server;

public class RmiServer implements RmiInterface{

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

    public void startServer(int port) {

        //TODO implement
    }
}
