package it.polimi.sw2019.network.messages;

public class LoginReport {

    /**
     * Default constructor
     */
    public LoginReport() {

    }

    public LoginReport(Boolean loginSuccessful) {

        setLoginSuccessful(loginSuccessful);
    }

    /* Attributes */

    private Boolean loginSuccessful;

    /* Methods */

    public void setLoginSuccessful(Boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public Boolean getLoginSuccessful() {
        return loginSuccessful;
    }
}
