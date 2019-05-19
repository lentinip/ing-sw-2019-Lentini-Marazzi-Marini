package it.polimi.sw2019.network.client;

public class Client {

    /**
     * Default constructor
     */
    public Client(){

    }

    /* Attributes */

    private String ipAddress;

    private String username;

    /* Methods */

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
