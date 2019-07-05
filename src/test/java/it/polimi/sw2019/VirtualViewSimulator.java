package it.polimi.sw2019;

import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.controller.Controller;

import java.util.List;

/**
 * class made to test the controller
 */
public class VirtualViewSimulator extends it.polimi.sw2019.network.server.VirtualView {


    public VirtualViewSimulator(List<String> usernames){

        setUsernames(usernames);
        controller = new Controller(this);
    }

    /* Attributes */

    Controller controller;

    Message messageReceived;


    /* Methods */

    public Controller getController() {
        return controller;
    }

    public Message getMessageReceived() {
        return messageReceived;
    }

    @Override
    public void setMessageSender(String username){}

    @Override
    public void setCurrentPlayer(String username){}

    @Override
    public void display(Message message){
        messageReceived = message;
    }

    @Override
    public void notify(Message message){}

    @Override
    public void startSetupTimer(){}

    @Override
    public void startTimer(){}

    @Override
    public void startTurnTimer(String currentPlayer){}

    @Override
    public void startSpawnMessage(){}

    @Override
    public void startResponseMessage(){}





}
