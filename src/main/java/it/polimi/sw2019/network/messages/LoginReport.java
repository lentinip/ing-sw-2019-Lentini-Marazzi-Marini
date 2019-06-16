package it.polimi.sw2019.network.messages;

public class LoginReport {


    public LoginReport(Boolean loginSuccessful) {

        setLoginSuccessful(loginSuccessful);
        numberOfPlayers=0;
    }

    public LoginReport(int numberOfPlayers){

        setNumberOfPlayers(numberOfPlayers);
        setLoginSuccessful(true);
    }

    /* Attributes */

    private Boolean loginSuccessful;

    private int numberOfPlayers;

    /* Methods */

    public void setLoginSuccessful(Boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public Boolean getLoginSuccessful() {
        return loginSuccessful;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
