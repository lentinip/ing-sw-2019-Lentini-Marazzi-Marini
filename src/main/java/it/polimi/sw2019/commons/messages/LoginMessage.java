package it.polimi.sw2019.commons.messages;

/**
 * @author poligenius
 * this class contains infos about the login of a player
 */
public class LoginMessage {

    public LoginMessage(String username, boolean rmi){

        setRmi(rmi);
        setUsername(username);
    }

    /* Attributes */

    private String username;

    private boolean rmi;

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
}

