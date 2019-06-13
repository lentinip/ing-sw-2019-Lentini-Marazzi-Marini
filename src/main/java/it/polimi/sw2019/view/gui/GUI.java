package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.view.ViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends Application implements ViewInterface {

    public GUI(Client client){
        this.client = client;
    }

    /* Attributes */

    private Stage primaryStage;

    private Client client;

    //Message with all the settings
    private MatchStart matchStart;

    private BoardController boardController;

    private StartScreenController startScreenController;

    private static Logger logger = Logger.getLogger("gui");

    private String errorString = "ERROR";

    /* Methods */

    public static void main(String[] args) {
        launch(args);
    }

    public void displayLoginWindow(){

        //This resets the class if there's a new game (for safety)
        resetClass();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/StartScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);

        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "StartScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        primaryStage.setScene(scene);

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);

        primaryStage.show();

        startScreenController = fxmlLoader.getController();
        startScreenController.setClient(client);
    }

    public void displayReconnectionWindow(){
        createAlertReconnect();
    }

    public void displayPlayerDisconnectedWindow(int indexOfTheDisconnected){
        String username = matchStart.getUsernames().get(indexOfTheDisconnected);
        alertPlayerLeft(username);
    }

    public void displayUsernameNotAvailable(){
        createAlertWarning("The chosen username is not available.");
        startScreenController.hidePleaseWait();
    }

    public void displayLoginSuccessful(LoginReport loginReport){
        askMatchSetting(loginReport.getNumberOfPlayers());
    }

    public void displayMatchStart(MatchStart matchStart){
        this.matchStart = matchStart;
        setBoard();
    }

    public void displayCanIShoot(boolean answer){
        boardController.canIShoot(answer);
    }

    public void displayAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction){
        boardController.showAvailableCells(cells, typeOfAction);
    }

    public void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction){
        List<Image> cardsImages = boardController.getImageCards(typeOfAction);

        showCardSelection(cards, typeOfAction, cardsImages, false);
    }

    public void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction){
        List<Image> cardsImages = boardController.getImageCards(typeOfAction);

        showCardSelection(cards, typeOfAction, cardsImages, true);
    }

    public void displayAvailableEffects(AvailableEffects availableEffects){
        Image card = boardController.getSelectedWeaponImage();
        int type = boardController.getSelectedWeaponType();

        showEffectSelection(availableEffects, card, type, false);
    }

    public void displayAvailableEffectsWithNoOption(AvailableEffects availableEffects){
        Image card = boardController.getSelectedWeaponImage();
        int type = boardController.getSelectedWeaponType();

        showEffectSelection(availableEffects, card, type, true);
    }

    public void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction){
        boardController.showSelectablePlayers(players, typeOfAction, false);
    }

    public void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction){
        boardController.showSelectablePlayers(players, typeOfAction, true);
    }

    public void displayPayment(PaymentMessage paymentInfo){
        List<Image> powerups = boardController.getImageCards(TypeOfAction.USEPOWERUP);

        startPaymentSession(paymentInfo, powerups, null, null, false);
    }

    public void displayPaymentForPowerupsCost(PaymentMessage paymentMessage){
        List<Image> powerups = boardController.getImageCards(TypeOfAction.USEPOWERUP);
        PlayerBoardMessage playerBoardMessage = boardController.getMyCurrentPlayerBoardMessage();
        Group ammoGroup = boardController.getMyAmmoGroup();

        startPaymentSession(paymentMessage, powerups, playerBoardMessage, ammoGroup, true);
    }

    public void updateMatchState(MatchState matchState){
        boardController.updateMatch(matchState);
    }

    public void updatePrivateHand(PrivateHand privateHand){
        boardController.updatePrivateHand(privateHand);
    }

    public void displayAlreadyConnectedWindow(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Username already in the server");
        alert.setHeaderText("There's a match started with the username you entered.");
        alert.setContentText("Do you want to reconnect or start a new game with a new username?");

        ButtonType buttonTypeJoin = new ButtonType("JOIN");
        ButtonType buttonTypeNewGame = new ButtonType("NEW GAME");

        alert.getButtonTypes().setAll(buttonTypeJoin, buttonTypeNewGame);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()){
            if (result.get() == buttonTypeJoin){
                Message message = new Message(client.getUsername());
                message.setTypeOfMessage(TypeOfMessage.RECONNECTION);
                client.send(message);
            }
            else {
                startScreenController.hidePleaseWait();
            }
        }
    }

    public void displayEndMatchLeaderBoard(LeaderBoard leaderBoard){
        createLeaderboard(leaderBoard);
    }

    public void displayActionReport(ActionReports actionReports){
        //Do nothing: is for the CLI
    }

    public void showCardSelection(AvailableCards cards, TypeOfAction typeOfAction, List<Image> images, boolean noOption){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectCardScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "SelectCardScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        //Configures the controller
        SelectCardController selectCardController = fxmlLoader.getController();


        PrivateHand privateHand = boardController.getPrivateHand();
        int numberOfWeapons = privateHand.getAllWeapons().size();
        BoardCoord lastSelectedCell = boardController.getLastSelectedCell();

        //If is a grab and the player has already 3 weapons
        if (numberOfWeapons == 3){

            List<ImageView> myWeapons = boardController.getMyWeapons();
            selectCardController.configure(client, cards, images, lastSelectedCell, myWeapons);
        }
        else {
            selectCardController.configure(client, cards, typeOfAction, images, lastSelectedCell, noOption);
        }

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        if (cards.areWeapons()){
            newWindow.setTitle("Select Weapon");
        }
        else {
            newWindow.setTitle("Select Powerup");
        }

        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The window doesn't have the close button
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void showEffectSelection(AvailableEffects availableEffects, Image card, int type, boolean noOption){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectEffectScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "SelectEffectScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        //Configures the controller
        SelectEffectController selectEffectController = fxmlLoader.getController();
        selectEffectController.configure(client, availableEffects, card, type, noOption);

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Select Effect");


        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The window doesn't have the close button
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void startPaymentSession(PaymentMessage paymentMessage, List<Image> images, PlayerBoardMessage playerBoardMessage, Group ammoGroup, boolean anyColor){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/PaymentScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "PaymentScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        //Configures the controller
        PaymentController paymentController = fxmlLoader.getController();

        if (anyColor) {
          paymentController.configure(client, paymentMessage, images, playerBoardMessage, ammoGroup, true);
        }
        else {
            paymentController.configure(client, paymentMessage, images);
        }

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Payment session");


        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The window doesn't have the close button
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void createLeaderboard(LeaderBoard leaderBoard){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/LeaderBoardScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);

        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "LeaderBoardScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        LeaderBoardController leaderBoardController = fxmlLoader.getController();
        leaderBoardController.configure(this, leaderBoard, matchStart.getUsernames(), matchStart.getCharacters());

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Payment session");


        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The window doesn't have the close button
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void setBoard(){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/BoardScreen.fxml"));

        Parent board;
        Scene scene;

        try {
            board = fxmlLoader.load();
            scene = new Scene(board);
            primaryStage.setScene(scene);
        }

        catch (IOException e){
            logger.log(Level.SEVERE, "BoardScreen.fxml not found");
        }
        boardController = fxmlLoader.getController();


        //Set the board parameters in the controller
        boardController.configureBoard(client, matchStart);

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void askMatchSetting(Integer numberOfPlayers){

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/MatchSettingScreen.fxml"));

        Parent board;
        Scene scene;

        try {
            board = fxmlLoader.load();
            scene = new Scene(board);

        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "MatchSettingScreen.fxml not found");
            scene = new Scene(new Label(errorString));
        }

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Match Settings");

        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The new window doesn't have a close button
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        MatchSettingController matchSettingController = fxmlLoader.getController();
        matchSettingController.setNumberOfPlayers(numberOfPlayers);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void alertPlayerLeft(String username){
        String whatToShow = String.format("%s left the game.", username);
        createAlertInfo(whatToShow);
    }

    public void createAlertWarning(String whatToShow){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(whatToShow);
        a.show();
    }

    public void createAlertInfo(String whatToShow){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(whatToShow);
        a.show();
    }

    public void createAlertReconnect(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Reconnection window");
        a.setHeaderText("You let the timer finish and now you are going to appear offline. Do you want to reconnect?");
        a.setContentText("Click ok to reconnect. Click cancel or close to exit the game.");

        Optional<ButtonType> option = a.showAndWait();

        if (option.isPresent()){
            if (option.get() == ButtonType.OK){

                Message reconnectionMessage = new Message(client.getUsername());
                reconnectionMessage.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
                client.send(reconnectionMessage);
            }
            else {
                Platform.exit();
                System.exit(0);
            }
        }
    }

    public void resetClass(){
        matchStart = null;
        boardController = null;
        startScreenController = null;
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        displayLoginWindow();
    }

    /**
     * shows the return to login window because a player has been disconnected
     */
    public void displayDisconnectionDuringSetup(){

    }
}