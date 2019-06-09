package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.view.ViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    /* Methods */

    public static void main(String[] args) {
        launch(args);
    }

    public void displayLoginWindow(){
        //TODO Implements
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
    }

    public void displayLoginSuccessful(LoginReport loginReport){
        try{
            askMatchSetting(loginReport.getNumberOfPlayers());
        } catch (IOException e){
            //Do nothing
        }
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

        if (cards.areWeapons()){
            showWeaponSelection(cards, typeOfAction, cardsImages);
        }
        else {
            //TODO implement for powerups
        }
    }

    public void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction){

    }

    public void displayAvailableEffects(AvailableEffects availableEffects){

    }

    public void displayAvailableEffectsWithNoOption(AvailableEffects availableEffects){

    }

    public void displayAvailablePlayers(List<Character> players, TypeOfAction typeOfAction){

    }

    public void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction){

    }

    public void displayAvailableEffects(List<IndexMessage> effects){

    }

    public void displayAvailableEffectsWithNoOption(List<IndexMessage> effects){

    }

    public void displayPayment(PaymentMessage paymentInfo){

    }

    public void displayPaymentForPowerupsCost(PaymentMessage paymentMessage){

    }

    public void updateMatchState(MatchState matchState){

    }

    public void updatePrivateHand(PrivateHand privateHand){
        boardController.updatePrivateHand(privateHand);
    }

    public void displayAlreadyConnectedWindow(){

    }

    public void displayEndMatchLeaderBoard(LeaderBoard leaderBoard){

    }

    public void displayActionReport(ActionReports actionReports){

    }

    public void showWeaponSelection(AvailableCards cards, TypeOfAction typeOfAction, List<Image> images){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/BoardScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        }
        catch (IOException e){
            //TODO implement logger
            scene = new Scene(new Label("ERROR"));
        }

        //Configures the controller
        SelectWeaponController selectWeaponController = fxmlLoader.getController();
        selectWeaponController.configure(client, cards, typeOfAction, images);

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Select Weapon");

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
            //TODO implement logger
        }
        boardController = fxmlLoader.getController();


        //Set the board parameters in the controller
        boardController.configureBoard(client, matchStart);

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void askMatchSetting(Integer numberOfPlayers) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/MatchSettingScreen.fxml"));

        Parent board = fxmlLoader.load();

        Scene scene = new Scene(board);

        //Creates a new window for the match setting
        Stage newWindow = new Stage();
        newWindow.setTitle("Match Settings");

        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(primaryStage);

        MatchSettingController matchSettingController = fxmlLoader.getController();
        matchSettingController.setNumberOfPlayers(numberOfPlayers);

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void alertAlreadyUsedUsername() {
        createAlertWarning("The username you chose is already in use. Please choose another one.");
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
                //TODO implement reconnecion
            }
            else {
                Platform.exit();
                System.exit(0);
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/StartScreen.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Adrenalina");
        primaryStage.setResizable(false);

        primaryStage.show();

        this.primaryStage = primaryStage;
        StartScreenController startScreenController = fxmlLoader.getController();
        startScreenController.setClient(client);
    }
}

