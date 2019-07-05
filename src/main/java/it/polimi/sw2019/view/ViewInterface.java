package it.polimi.sw2019.view;

import it.polimi.sw2019.commons.Character;
import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.commons.messages.*;

import java.util.List;

/**
 * @author poligenius
 * this interface is implemented by cli and gui
 * to show windows and stuff on console/gui
 */
public interface ViewInterface {

    /* Methods */

    /**
     * first window the user see when he starts the application
     */
    void displayLoginWindow();

    /**
     * when the turn timer elapses a reconnection window appears to ask to the player if he wants to reconnect
     */
    void displayReconnectionWindow();

    /**
     * shows an alert to tell that a player is disconnected
     * @param indexOfTheDisconnected index
     */
    void displayPlayerDisconnectedWindow(int indexOfTheDisconnected);

    /**
     * shows a window that tells if the username is not available
     */
    void displayUsernameNotAvailable();

    /**
     * shows the window after the login the one with match setups
     * @param loginReport message received
     */
    void displayLoginSuccessful(LoginReport loginReport);

    /**
     * displays for the first time the board with all setting chosen
     * @param matchStart info about the match setting
     */
    void displayMatchStart(MatchStart matchStart);

    /**
     * show the window with the possible actions
     * @param answer if answer is false player can't click on shoot action
     */
    void displayCanIShoot(boolean answer);

    /**
     * show the cells the player can click (player must click one)
     * @param cells List of coords of the cell that can be choosed
     * @param typeOfAction type of action chosen
     */
    void displayAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction);

    /**
     * shows the cards the player can choose
     * @param cards contains options
     * @param typeOfAction type of action chosen
     */
    void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction);

    /**
     * shows the cards the player can choose with the "no" button
     * @param cards contains options
     * @param typeOfAction type of action chosen
     */
    void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction);

    /**
     * shows the characters the player can choose
     * @param players contains options
     * @param typeOfAction type of action chosen
     */
    void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction);

    /**
     * shows the characters the player can choose with the "no" button
     * @param players contains options
     * @param typeOfAction type of action chosen
     */
    void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction);

    /**
     * shows the effects the player can choose
     * @param availableEffects contains options
     */
    void displayAvailableEffects(AvailableEffects availableEffects);

    /**
     * shows the effects the player can choose with the "no" button
     * @param availableEffects contains options
     */
    void displayAvailableEffectsWithNoOption(AvailableEffects availableEffects);

    /**
     * show the payment message with the powerups that can be used to pay
     * @param paymentInfo if must is true the player has to choose one, otherwise he can click "no" button
     */
    void displayPayment(PaymentMessage paymentInfo);

    /**
     * like the method before, but this method, if the player select "pay with ammo" button shows a window where
     * the player has to select a color (between the available ones) to pay an ammo cube
     * @param paymentMessage payment info, if must is true then no answer is not accepted
     */
    void displayPaymentForPowerupsCost(PaymentMessage paymentMessage);

    /**
     * updates all the info showed in cli and gui about the status of the match
     * @param matchState updates all over the board and the cards
     */
    void updateMatchState(MatchState matchState);

    /**
     * updates all the info about the end of the player
     * @param privateHand the hand of the player who is playing
     */
    void updatePrivateHand(PrivateHand privateHand);

    /**
     * shows the end of the match with the winner
     * @param leaderBoard contains the leader board with points and characters
     */
    void displayEndMatchLeaderBoard(LeaderBoard leaderBoard);

    /**
     * shows a textual message with the action a player has done (used onli in cli)
     * @param actionReports message
     */
    void displayActionReport(ActionReports actionReports);

    /**
     * shows the possibility to reconnect to a match started yet, or the possibility to start a new match (choosing another username)
     */
    void displayAlreadyConnectedWindow();

    /**
     * shows the return to login window because a player has been disconnected
     */
    void displayDisconnectionDuringSetup();

    /**
     * if the client can't reach the server we try again until it works
     * @param messageToResend message to send again
     */
    void displayConnectionErrorClient(Message messageToResend);

    /**
     * this method displays a connection error
     */
    void displayConnectionFailure();

}