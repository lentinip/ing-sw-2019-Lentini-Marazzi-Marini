package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.TypeOfAction;

import java.io.Serializable;

/**
 * generic class used from view and controller to send information
 */
public class Message implements Serializable {

    /**
     * Default Constructor
     */
    public Message(){}

    /**
     * customized constructor
     */
    public Message(String username, TypeOfMessage typeOfMessage, TypeOfAction typeOfAction, String jsonFile){

        setJsonFile(jsonFile);
        setTypeOfAction(typeOfAction);
        setTypeOfMessage(typeOfMessage);
        setUsername(username);

    }

    /* Attributes */

    private String username;

    private TypeOfMessage typeOfMessage;

    private TypeOfAction typeOfAction;

    private String jsonFile; /* the information used by the controller and the view are serialized in this string */

    /* Methods */

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(TypeOfAction typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getUsername() {
        return username;
    }

    public void setTypeOfMessage(TypeOfMessage typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }

    public TypeOfMessage getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
