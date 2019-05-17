package it.polimi.sw2019.network.messages;

import com.google.gson.Gson;
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

    /* Attributes */

    private String username;  /* if null the message is for all players */

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

    /* The following methods are written to serialize every message class and create the attribute jsonFile */

    public String serializeMatchStart(MatchStart matchStart){

        Gson gson = new Gson();
        return gson.toJson(matchStart);
    }

    public String serializeMatchSetup(MatchSetup matchSetup){

        Gson gson = new Gson();
        return gson.toJson(matchSetup);
    }

    public String serializeAvailableEffects(AvailableEffects availableEffects){

        Gson gson = new Gson();
        return gson.toJson(availableEffects);
    }

    public String serializeGrabWeapon(GrabWeapon grabWeapon){

        Gson gson = new Gson();
        return gson.toJson(grabWeapon);
    }

    public String serializeIndexMessage(IndexMessage indexMessage){

        Gson gson = new Gson();
        return gson.toJson(indexMessage);
    }

    public String serializePlayers(Players players){

        Gson gson = new Gson();
        return gson.toJson(players);
    }

    public String serializeMatchState(MatchState matchState){

        Gson gson = new Gson();
        return gson.toJson(matchState);
    }

    public String serializePrivateHand(PrivateHand privateHand){

        Gson gson = new Gson();
        return gson.toJson(privateHand);
    }

    public String serializeBooleanMessage(BooleanMessage booleanMessage){

        Gson gson = new Gson();
        return gson.toJson(booleanMessage);
    }

    public String serializeAvailableCards(AvailableCards availableCards){

        Gson gson = new Gson();
        return gson.toJson(availableCards);
    }

    public String serializeAvailableCells(AvailableCells availableCells){

        Gson gson = new Gson();
        return gson.toJson(availableCells);
    }

    public String serializeBoardCoord(BoardCoord boardCoord){

        Gson gson = new Gson();
        return gson.toJson(boardCoord);
    }

    /* the following methods are called to create different types of Message Class */

    public void createMessageMatchState(MatchState matchState){

        setUsername(null); /* sent to all */
        setTypeOfMessage(TypeOfMessage.MATCH_STATE);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchState(matchState));
    }

    public void createMessagePrivateHand(PrivateHand privateHand, String username){

        setUsername(username);
        setTypeOfMessage(TypeOfMessage.PRIVATE_HAND);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializePrivateHand(privateHand));
    }

    public void createMessageMatchSetup(MatchSetup matchSetup, String username){

        setUsername(username);
        setTypeOfMessage(TypeOfMessage.MATCH_SETUP);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchSetup(matchSetup));
    }

    public void createMessageMatchStart(MatchStart matchStart){

        setUsername(null); /* this message is sent to all clients */
        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.MATCH_START);
        setJsonFile(serializeMatchStart(matchStart));
    }

    public void createMessageCanIShoot(BooleanMessage booleanMessage, String username){

        setUsername(username);
        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.CAN_I_SHOOT);
        setJsonFile(serializeBooleanMessage(booleanMessage));
    }

    public void createAskMessage(TypeOfAction typeOfAction, String username){

        setUsername(username);
        setTypeOfAction(typeOfAction);
        setTypeOfMessage(TypeOfMessage.ASK);
        setJsonFile(null);                        /* no content */
    }

    public void createAvailableCellsMessage(TypeOfAction typeOfAction, AvailableCells availableCells, String username){

        setUsername(username);
        setTypeOfMessage(TypeOfMessage.AVAILABLE_CELLS);
        setTypeOfAction(typeOfAction);
        setJsonFile(serializeAvailableCells(availableCells));
    }




}
