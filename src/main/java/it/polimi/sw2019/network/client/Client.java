package it.polimi.sw2019.network.client;

import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.network.client.rmi.RmiClient;
import it.polimi.sw2019.network.client.socket.SocketClientConnection;
import it.polimi.sw2019.commons.messages.LoginReport;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import it.polimi.sw2019.view.CLI;
import it.polimi.sw2019.view.ViewInterface;
import it.polimi.sw2019.view.gui.GUI;
import javafx.application.Application;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author poligenius, Mi97ch
 * this class represents the client and shows the message received on the gui or cli
 */
public class Client {

    /**
     * Default constructor
     */
    public Client(){}

    /* Attributes */

    private static ViewInterface view;  // abstract class useful to show objects both on the gui and on the cli

    private Message lastMessage;  // here I save the last message received

    private Message lastMessageForReconnection;

    private String ipAddress;

    private String username;

    private boolean rmi; //tells what kind of connection he is using

    private ClientActions clientActions;

    private static Logger LOGGER = Logger.getLogger("Client");

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

    public boolean isRmi() {
        return rmi;
    }

    public void setRmi(boolean rmi) {
        this.rmi = rmi;
    }

    public Message getLastMessageForReconnection() {
        return lastMessageForReconnection;
    }

    /**
     * starts the client based on the console input
     * @param args console input (cli/gui and ip address)
     */
    public static void main(String[] args){

        Client client = new Client();
        boolean cli = false;

        if (args.length > 0) {

            switch (args[0]) {

                case "cli":
                    cli = true;
                    break;
                case "gui":
                    break;
                default:
                    LOGGER.log(Level.WARNING, "After the name of the program write 'cli' if you want to use the console, 'gui' if you want to use the gui interface");
                    System.exit(0);
                    break;
            }
        }
        if(args.length > 1){
            client.setIpAddress(args[1]);
        }

        else {
            client.setIpAddress("localhost");
        }

        if (cli){

            CLI view = new CLI(client);
            client.setView(view);
            view.displayLoginWindow();
        }

        else {

            Application.launch(GUI.class, args);
        }
    }

    /**
     * creates the connection and tries to register the client
     * @param loginMessage login message to send
     */
    public void connect(Message loginMessage) {

        rmi = loginMessage.deserializeLoginMessage().isRmi();
        username = loginMessage.getUsername();

        try {

            if (rmi) {

                if (clientActions != null){
                    clientActions = null;
                }
                clientActions = new RmiClient(this);
                clientActions.register(username);
            }
        }
        catch (NotBoundException|IOException e){

            LOGGER.log(Level.WARNING, "connection failure");
            view.displayConnectionErrorClient(loginMessage);
        }

        try {
            if (!rmi) {

                clientActions = new SocketClientConnection(this);
                clientActions.register(username);
            }
        }
        catch (IOException|NullPointerException e){

            view.displayConnectionErrorClient(loginMessage);
        }
    }

    /**
     * this method analyzes the message received and calls the correct method of the view interface to display
     * the correct information on the cli/gui
     * @param message received from the server
     */
    public void handleMessage(Message message){

        //messages to verify that the client is connected
        if(message.getTypeOfMessage() == null) {

            //do nothing
            return;
        }

        switch (message.getTypeOfMessage()){

            case LOGIN_REPORT:
                if (message.deserializeLoginReport().getLoginSuccessful()){
                    LoginReport loginReport = message.deserializeLoginReport();
                    if(loginReport.getNumberOfPlayers() != 0){
                        view.displayLoginSuccessful(loginReport);
                    }
                }
                else {
                    view.displayUsernameNotAvailable();
                }
                break;
            case MATCH_START:
                view.displayMatchStart(message.deserializeMatchStart());
                break;
            case ACTION_REPORT:
                view.displayActionReport(message.deserializeActionReports());
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
            case DISCONNECTED:
                view.displayPlayerDisconnectedWindow(message.deserializeIndexMessage().getSelectionIndex());
                break;
            case RECONNECTION_REQUEST:
                view.displayReconnectionWindow();
                break;
            case PLAYER_ALREADY_LOGGED:
                view.displayAlreadyConnectedWindow();
                break;
            case DISCONNECTION_SETUP:
                view.displayDisconnectionDuringSetup();
                break;
            case EXIT_GAME:
                //view.displayExitMessage();
                break;
            default:
                LOGGER.log(Level.WARNING,"TYPE OF MESSAGE UNKNOWN");
                break;

        }

        if (message.getTypeOfMessage() != TypeOfMessage.MATCH_STATE && message.getTypeOfMessage() != TypeOfMessage.PRIVATE_HAND && message.getTypeOfMessage() != TypeOfMessage.ACTION_REPORT && message.getTypeOfMessage() != TypeOfMessage.DISCONNECTED && message.getTypeOfMessage() != null) {
            lastMessage = message;
        }

    }

    /**
     * shows the correct info on the gui, cli, by analyzing the last message received
     * @param message contains the info to doSomething to gui/cli
     */
    public void availableCardsHandler(Message message){

        TypeOfAction action = message.getTypeOfAction();

        //possible no answer (index selected < 0 )
        if (action == TypeOfAction.RELOAD ||  action == TypeOfAction.USEPOWERUP || (action == TypeOfAction.GRAB && message.deserializeAvailableCards().getAvailableCards().isEmpty())){

            view.displayAvailableCardsWithNoOption(message.deserializeAvailableCards(), message.getTypeOfAction());
        }

        else {

            view.displayAvailableCards(message.deserializeAvailableCards(), message.getTypeOfAction());
        }
    }

    /**
     * shows the correct info on the gui, cli, by analyzing the last message received
     * @param message contains the info to doSomething to gui/cli
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
     * @param message contains the info to doSomething to gui/cli
     */
    public void availableEffectsHandler(Message message){

        // must choose one
        if (lastMessage.getTypeOfMessage() == TypeOfMessage.AVAILABLE_CARDS){

            view.displayAvailableEffects(message.deserializeAvailableEffects());
        }

        // I can answer no
        else {

            view.displayAvailableEffectsWithNoOption(message.deserializeAvailableEffects());
        }
    }

    /**
     * represents client actions
     * @param messageToSend parameter or message to send to server
     */
    public void send(Message messageToSend){

        lastMessageForReconnection = messageToSend;

        try {
            clientActions.doSomething(messageToSend);
        } catch (IOException e) {

            LOGGER.log(Level.WARNING, "failure: connection error");
        }
    }


}

