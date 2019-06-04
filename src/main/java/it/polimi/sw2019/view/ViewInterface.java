package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.messages.*;

import java.util.List;

public interface ViewInterface {

    /* Methods */

    /**
     * first window the user see when he starts the application
     */
    public abstract void displayLoginWindow();

    /**
     * when the turn timer elapses a reconnection window appears to ask to the player if he wants to reconnect
     */
    public abstract void displayReconnectionWindow();

    /**
     * shows an alert to tell that a player is disconnected
     * @param indexOfTheDisconnected index
     */
    public abstract void displayPlayerDisconnectedWindow(int indexOfTheDisconnected);

    /**
     * shows a window that tells if the username is not available
     */
    public abstract void displayUsernameNotAvailable();

    /**
     * shows the window after the login the one with match setups
     */
    public abstract void displayLoginSuccessful(LoginReport loginReport);

    /**
     * displays for the first time the board with all setting chosen
     * @param matchStart info about the match setting
     */
    public abstract void displayMatchStart(MatchStart matchStart);

    /**
     * show the window with the possible actions
     * @param answer if answer is false player can't click on shoot action
     */
    public abstract void displayCanIShoot(boolean answer);

    /**
     * show the cells the player can click (player must click one)
     * @param cells List of coords of the cell that can be choosed
     */
    public abstract void displayAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction);

    /**
     * shows the cards the player can choose
     * @param cards contains options
     */
    public abstract void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction);

    /**
     * shows the cards the player can choose with the "no" button
     * @param cards contains options
     */
    public abstract void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction);

    /**
     * shows the characters the player can choose
     * @param players contains options
     */
    public abstract void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction);

    /**
     * shows the characters the player can choose with the "no" button
     * @param players contains options
     */
    public abstract void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction);

    /**
     * shows the effects the player can choose
     * @param effects contains options
     */
    public abstract void displayAvailableEffects(List<IndexMessage> effects);

    /**
     * shows the effects the player can choose with the "no" button
     * @param effects contains options
     */
    public abstract void displayAvailableEffectsWithNoOption(List<IndexMessage> effects);

    /**
     * show the payment message with the powerups that can be used to pay
     * @param paymentInfo if must is true the player has to choose one, otherwise he can click "no" button
     */
    public abstract void displayPayment(PaymentMessage paymentInfo);

    /**
     * like the method before, but this method, if the player select "pay with ammo" button shows a window where
     * the player has to select a color (between the available ones) to pay an ammo cube
     * @param paymentMessage payment info, if must is true then no answer is not accepted
     */
    public abstract void displayPaymentForPowerupsCost(PaymentMessage paymentMessage);

    /**
     * updates all the info showed in cli and gui about the status of the match
     * @param matchState updates all over the board and the cards
     */
    public abstract void updateMatchState(MatchState matchState);

    /**
     * updates all the info about the end of the player
     * @param privateHand the hand of the player who is playing
     */
    public abstract void updatePrivateHand(PrivateHand privateHand);

    /**
     * shows the end of the match with the winner
     * @param leaderBoard contains the leader board with points and characters
     */
    public abstract void displayEndMatchLeaderBoard(LeaderBoard leaderBoard);

}