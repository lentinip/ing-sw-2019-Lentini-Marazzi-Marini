package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.network.client.ClientInterface;

public class LoginMessage {

    public LoginMessage(String username, boolean rmi, ClientInterface clientInterface){

        setRmi(rmi);
        setUsername(username);
        setClientInterface(clientInterface);
    }

    /* Attributes */

    private String username;

    private boolean rmi;

    private ClientInterface clientInterface;

    /* Methods */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRmi() {
        return rmi;
    }

    public void setRmi(boolean rmi) {
        this.rmi = rmi;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }
}

