package it.polimi.sw2019.network.client;

import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import it.polimi.sw2019.view.ViewInterface;

public class Client {

    /**
     *  Default constructor
     */
    public Client(ViewInterface view){

        setView(view);
    }

    /* Attributes */

    private ViewInterface view;  // abstract class useful to show objects both on the gui and on the cli

    private Message lastMessage;  // here I save the last message received

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

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ViewInterface getView() {
        return view;
    }

    public void setView(ViewInterface view) {
        this.view = view;
    }

    /**
     * this method analyzes the message received and calls the correct method of the view interface to display
     * the correct information on the cli/gui
     * @param message received from the server
     */
    public void handleMessage(Message message){


        switch (message.getTypeOfMessage()){

            case MATCH_START:
                //TODO implement
                break;
            case CAN_I_SHOOT:
                view.displayCanIShoot(message.deserializeBooleanMessage().isAnswer());
                break;
            case AVAILABLE_CELLS:
                view.displayAvailableCells(message.deserializeAvailableCells().getAvailableCells(), message.getTypeOfAction());
                break;
            case AVAILABLE_CARDS:
                availableCardsHandler(message);
                break;
            case AVAILABLE_PLAYERS:
                availablePlayersHandler(message);
                break;
            case AVAILABLE_EFFECTS:
                availableEffectsHandler(message);
                break;
            case PAYMENT:
                view.displayPayment(message.deserializePaymentMessage());
                break;
            case ASK:
                view.displayPaymentForPowerupsCost(message.deserializePaymentMessage());
                break;
            case MATCH_STATE:
                view.updateMatchState(message.deserializeMatchState());
                break;
            case PRIVATE_HAND:
                view.updatePrivateHand(message.deserializePrivateHand());
                break;
            case END_MATCH:
                view.displayEndMatchLeaderBoard(message.deserializeLeaderBoard());
                break;
            default:
                System.console().printf("TYPE OF MESSAGE UNKNOWN");
                break;

        }

        if (message.getTypeOfMessage() != TypeOfMessage.MATCH_STATE || message.getTypeOfMessage() != TypeOfMessage.PRIVATE_HAND) {
            lastMessage = message;
        }

    }

    /**
     * shows the correct info on the gui, cli, by analyzing the last message received
     * @param message contains the info to send to gui/cli
     */
    public void availableCardsHandler(Message message){

        TypeOfAction action = message.getTypeOfAction();

        //possible no answer (index selected < 0 )
        if (action == TypeOfAction.RELOAD ||  action == TypeOfAction.USEPOWERUP ){

            view.displayAvailableCardsWithNoOption(message.deserializeAvailableCards(), message.getTypeOfAction());
        }

        else {

            view.displayAvailableCards(message.deserializeAvailableCards(), message.getTypeOfAction());
        }
    }

    /**
     * shows the correct info on the gui, cli, by analyzing the last message received
     * @param message contains the info to send to gui/cli
     */
    public void availablePlayersHandler(Message message){

        TypeOfAction action = message.getTypeOfAction();

        // I can answer no (see the tree of messages to understand why)
        if (action == TypeOfAction.MOVEBEFORESHOOT && lastMessage.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CELLS){

            view.displayAvailablePlayersWithNoOption(message.deserializePlayersMessage().getCharacters(), action);
        }

        // I can answer no (see the tree of messages to understand why)
        else if (action == TypeOfAction.SHOOT && lastMessage.getTypeOfMessage() == TypeOfMessage.AVAILABLE_PLAYERS){

            view.displayAvailablePlayersWithNoOption(message.deserializePlayersMessage().getCharacters(), action);
        }

        else {

            view.displayAvailablePlayers(message.deserializePlayersMessage().getCharacters(), action);
        }
    }

    /**
     * shows the correct info on the gui, cli, by analyzing the last message received
     * @param message contains the info to send to gui/cli
     */
    public void availableEffectsHandler(Message message){

        // must choose one
        if (lastMessage.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CARDS){

            view.displayAvailableEffects(message.deserializeAvailableEffects().getIndexes());
        }

        // I can answer no
        else {

            view.displayAvailableEffectsWithNoOption(message.deserializeAvailableEffects().getIndexes());
        }
    }



}

