package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.view.ViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends Application implements ViewInterface {

    /* Attributes */

    private Stage primaryStage;

    private Client client;

    //Message with all the settings
    private MatchStart matchStart;

    private BoardController boardController;

    private StartScreenController startScreenController;

    private ActionReportController actionReportController;

    private static Logger logger = Logger.getLogger("gui");

    private String errorString = "ERROR";

    private double xOffset = 0;

    private double yOffset = 0;

    private Stage weaponsManualStage;

    private Stage selectCardControllerStage;

    private Stage paymentStage;

    private Stage selectEffectStage;

    private Stage reconnectStage;

    private static Media sound;

    /* Methods */

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
        createReconnect();
    }

    public void displayPlayerDisconnectedWindow(int indexOfTheDisconnected){
        if (matchStart!=null){
            String username = matchStart.getUsernames().get(indexOfTheDisconnected);
            alertPlayerLeft(username);
        }
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
        Platform.runLater(()->{
            boardController.canIShoot(answer);
        });
    }

    public void displayAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction){
        Platform.runLater(()->{
            boardController.showAvailableCells(cells, typeOfAction);
        });
    }

    public void displayAvailableCards(AvailableCards cards, TypeOfAction typeOfAction){
        Platform.runLater(()->{
            boardController.disableAvailableCells();

            List<Image> cardsImages = boardController.getImageCards(typeOfAction);

            if (typeOfAction == TypeOfAction.SHOOT){
                boardController.disableActions();
            }

            showCardSelection(cards, typeOfAction, cardsImages, false);
        });
    }

    public void displayAvailableCardsWithNoOption(AvailableCards cards, TypeOfAction typeOfAction){
        Platform.runLater(()->{
            boardController.disableAvailableCells();

            List<Image> cardsImages = boardController.getImageCards(typeOfAction);

            showCardSelection(cards, typeOfAction, cardsImages, true);
        });
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
        Platform.runLater(()->{
            boardController.showSelectablePlayers(players, typeOfAction, false);
        });
    }

    public void displayAvailablePlayersWithNoOption(List<Character> players, TypeOfAction typeOfAction){
        Platform.runLater(()->{
            boardController.showSelectablePlayers(players, typeOfAction, true);
        });
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
        Platform.runLater(()->{
            boardController.updateMatch(matchState);
        });
    }

    public void updatePrivateHand(PrivateHand privateHand){
        Platform.runLater(()->{
            boardController.updatePrivateHand(privateHand);
        });
    }

    public void displayAlreadyConnectedWindow(){
        Platform.runLater(() -> {
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
        });
    }

    public void displayEndMatchLeaderBoard(LeaderBoard leaderBoard){
        createLeaderboard(leaderBoard);
    }

    public void displayActionReport(ActionReports actionReports){
        if (boardController!=null){
            boardController.setActionReports(actionReports);
        }
        if (actionReportController!=null){
            actionReportController.manageActionReport(actionReports);
        }
    }

    public void showCardSelection(AvailableCards cards, TypeOfAction typeOfAction, List<Image> images, boolean noOption){
        FXMLLoader fxmlLoader = new FXMLLoader();
        if (typeOfAction == TypeOfAction.SPAWN){
            fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectCardScreenForSpawn.fxml"));
        }
        else {
            fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectCardScreen.fxml"));
        }

        Parent root;
        Scene scene;
        Stage newWindow;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "SelectCardScreen.fxml not found");
            scene = new Scene(new Label(errorString));
            root = null;
        }

        //Configures the controller
        SelectCardController selectCardController = fxmlLoader.getController();


        PrivateHand privateHand = boardController.getPrivateHand();
        int numberOfWeapons = privateHand.getAllWeapons().size();
        BoardCoord lastSelectedCell = boardController.getLastSelectedCell();

        //If is a grab and the player has already 3 weapons
        if (numberOfWeapons == 3 && typeOfAction==TypeOfAction.GRAB){

            List<ImageView> myWeapons = boardController.getMyWeapons();
            selectCardController.configure(client, boardController, cards, images, lastSelectedCell, myWeapons);
        }
        else {
            selectCardController.configure(client, boardController, cards, typeOfAction, images, lastSelectedCell, noOption);
        }
        //The next line sets that this new window will lock the parent window.
        //Is impossible to interact with the parent window until this window is closed.
        //The window doesn't have the close button
        //Creates a new window for the match setting
        newWindow = new Stage();
        if (cards.areWeapons()){
            newWindow.setTitle("Select Weapon");
        }
        else {
            newWindow.setTitle("Select Powerup");
        }
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.initOwner(primaryStage);

        try {
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newWindow.setX(event.getScreenX() - xOffset);
                    newWindow.setY(event.getScreenY() - yOffset);
                }
            });
        }
        catch (NullPointerException e){
            logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
        }

        selectCardController.setWeaponsManualStage(weaponsManualStage);
        selectCardControllerStage = newWindow;

        newWindow.setScene(scene);
        newWindow.show();
    }

    public void showEffectSelection(AvailableEffects availableEffects, Image card, int type, boolean noOption){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectEffectScreen.fxml"));

        Parent root;

        try {
            root = fxmlLoader.load();
            Platform.runLater(() -> {
                Scene scene = new Scene(root);

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

                try {
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            newWindow.setX(event.getScreenX() - xOffset);
                            newWindow.setY(event.getScreenY() - yOffset);
                        }
                    });
                }
                catch (NullPointerException e){
                    logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
                }

                selectEffectController.setWeaponsManualStage(weaponsManualStage);
                selectEffectStage = newWindow;

                newWindow.setScene(scene);
                newWindow.show();
            });
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "SelectEffectScreen.fxml not found");
        }
    }

    public void startPaymentSession(PaymentMessage paymentMessage, List<Image> images, PlayerBoardMessage playerBoardMessage, Group ammoGroup, boolean anyColor){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/PaymentScreen.fxml"));

        Parent root;

        try {
            root = fxmlLoader.load();

            Platform.runLater(() -> {

                Scene scene = new Scene(root);

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

                try {
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            newWindow.setX(event.getScreenX() - xOffset);
                            newWindow.setY(event.getScreenY() - yOffset);
                        }
                    });
                }
                catch (NullPointerException e){
                    logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
                }

                paymentStage = newWindow;

                newWindow.setScene(scene);
                newWindow.show();
            });
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "PaymentScreen.fxml not found");
        }
    }

    public void createLeaderboard(LeaderBoard leaderBoard){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/LeaderBoardScreen.fxml"));

        Parent root;

        try {
            root = fxmlLoader.load();
            Platform.runLater(()-> {
                Scene scene = new Scene(root);


                LeaderBoardController leaderBoardController = fxmlLoader.getController();
                leaderBoardController.configure(this, leaderBoard, matchStart.getUsernames(), matchStart.getCharacters());

                //Creates a new window for the match setting
                Stage newWindow = new Stage();
                newWindow.setTitle("Leaderboard");


                //The next line sets that this new window will lock the parent window.
                //Is impossible to interact with the parent window until this window is closed.
                //The window doesn't have the close button
                newWindow.initModality(Modality.WINDOW_MODAL);
                newWindow.initStyle(StageStyle.UNDECORATED);
                newWindow.initOwner(primaryStage);

                try {
                    root.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            newWindow.setX(event.getScreenX() - xOffset);
                            newWindow.setY(event.getScreenY() - yOffset);
                        }
                    });
                }
                catch (NullPointerException e){
                    logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
                }

                //Stops the timer in the boardcontroller
                boardController.stopTimer();

                //Closes the reconnect window if the player has one
                if (reconnectStage!=null && reconnectStage.isShowing()){
                    reconnectStage.close();
                }

                newWindow.setScene(scene);
                newWindow.show();
            });

        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "LeaderBoardScreen.fxml not found");
        }
    }

    public void setBoard(){
        Platform.runLater(()-> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/BoardScreen.fxml"));

            Parent board;
            final Scene scene;

            try {
                board = fxmlLoader.load();
            }

            catch (Exception e){
                logger.log(Level.SEVERE, "BoardScreen.fxml not found");
                logger.log(Level.SEVERE, e.getMessage());
                logger.log(Level.SEVERE, e.getLocalizedMessage());
                board = null;
            }
            scene = new Scene(board);

            primaryStage.close();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Adrenalina");
            primaryStage.setResizable(true);

            boardController = fxmlLoader.getController();


            //Set the board parameters in the controller
            boardController.configureBoard(client, matchStart);

            try {
                configureInstructionManualScreen();
                configureWeaponsManualScreen();
            }
            catch (IOException e){
                logger.log(Level.SEVERE, "Problems with InstructionManualScreen or WeaponsManualScreen");
                logger.log(Level.SEVERE, e.getMessage());
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

            try {
                createActionReportScreen();
            }
            catch (IOException e){
                logger.log(Level.SEVERE, "Problems with ActionReportScreen");
                logger.log(Level.SEVERE, e.getMessage());
                logger.log(Level.SEVERE, e.getLocalizedMessage());
            }

            //startMusic();

            primaryStage.show();
        });
    }

    public void askMatchSetting(Integer numberOfPlayers){
        Platform.runLater(() -> {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/MatchSettingScreen.fxml"));

                Parent board;
                final Scene scene;

                try {
                    board = fxmlLoader.load();

                }
                catch (IOException e) {
                    logger.log(Level.SEVERE, "MatchSettingScreen.fxml not found");
                    board = null;
                }
                scene = new Scene(board);

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
                matchSettingController.setClient(client);

                try {
                    board.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = event.getSceneX();
                            yOffset = event.getSceneY();
                        }
                    });
                    board.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            newWindow.setX(event.getScreenX() - xOffset);
                            newWindow.setY(event.getScreenY() - yOffset);
                        }
                    });
                }
                catch (NullPointerException e){
                    logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
                }

                newWindow.setScene(scene);
                newWindow.show();
        });
    }

    public void alertPlayerLeft(String username){
        String whatToShow = String.format("%s left the game.", username);
        createAlertInfo(whatToShow);
    }

    public void createAlertWarning(String whatToShow){
        Platform.runLater(() ->{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(whatToShow);
            a.show();
        });
    }

    public void createAlertInfo(String whatToShow){
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(whatToShow);
            a.show();
        });
    }

    public void createAlertInfo(String header, String whatToShow){
        Platform.runLater(() ->{
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(header);
            a.setContentText(whatToShow);
            a.show();
        });
    }

    public void createReconnect(){

        Platform.runLater(() -> {
            if (selectCardControllerStage != null && selectCardControllerStage.isShowing()) {
                selectCardControllerStage.close();
                selectCardControllerStage = null;
            }

            if (selectEffectStage != null && selectEffectStage.isShowing()) {
                selectEffectStage.close();
                selectCardControllerStage = null;
            }

            if (paymentStage != null && paymentStage.isShowing()) {
                paymentStage.close();
                paymentStage = null;
            }

            if (boardController.getActionReportsStage() != null) {
                if (boardController.getActionReportsStage().isShowing()) {
                    boardController.getActionReportsStage().close();
                }
                if (actionReportController != null) {
                    actionReportController.clear();
                }
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/ReconnectScreen.fxml"));

            Parent root;
            Scene scene;
            Stage newWindow;

            try {
                root = fxmlLoader.load();
                scene = new Scene(root);
            }
            catch (IOException e){
                logger.log(Level.SEVERE, "ReconnectScreen.fxml not found");
                scene = new Scene(new Label(errorString));
                root = null;
            }

            //Configures the controller
            ReconnectController reconnectController = fxmlLoader.getController();
            reconnectController.setClient(client);

            //The next line sets that this new window will lock the parent window.
            //Is impossible to interact with the parent window until this window is closed.
            //The window doesn't have the close button
            //Creates a new window for the match setting
            newWindow = new Stage();
            newWindow.setTitle("Reconnect Screen");

            newWindow.initModality(Modality.WINDOW_MODAL);
            newWindow.initStyle(StageStyle.UNDECORATED);
            newWindow.initOwner(primaryStage);

            try {
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });
                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        newWindow.setX(event.getScreenX() - xOffset);
                        newWindow.setY(event.getScreenY() - yOffset);
                    }
                });
            }
            catch (NullPointerException e){
                logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
            }

            reconnectStage = newWindow;

            newWindow.setScene(scene);
            newWindow.show();
        });
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
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });

        List<String> args = getParameters().getRaw();

        client = new Client();
        client.setView(this);

        if (args.size() > 1) {
            client.setIpAddress(args.get(1));
        } else {
            client.setIpAddress("localhost");
        }

        displayLoginWindow();
        startMusic();
    }

    /**
     * shows the return to login window because a player has been disconnected
     */
    public void displayDisconnectionDuringSetup(){
        String header = "The player that was choosing the configuration of the match left the game.";
        String content = "Please press the start game button again to join a new match.";
        createAlertInfo(header, content);
        startScreenController.hidePleaseWait();

    }


    public void configureInstructionManualScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/InstructionManualWindow.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Instruction manual");
        stage.setResizable(true);
        stage.initOwner(primaryStage);

        InstructionManualController instructionManualController = fxmlLoader.getController();
        instructionManualController.configure();
        boardController.setInstructionManualStage(stage);
    }

    public void configureWeaponsManualScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/WeaponsManualWindow.fxml"));

        Parent root;

        root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Weapons manual");
        stage.setResizable(true);
        stage.initOwner(primaryStage);

        WeaponsManualController weaponsManualController = fxmlLoader.getController();
        weaponsManualController.configure();
        boardController.setWeaponsManualStage(stage);
        weaponsManualStage = stage;
    }

    public void createActionReportScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/ActionReportScreen.fxml"));

        Parent root;

        root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("Action Reports");
        stage.setResizable(false);
        stage.initOwner(primaryStage);

        actionReportController = fxmlLoader.getController();
        boardController.setActionReportsStage(stage);
    }

    public void startMusic(){
        Platform.runLater(() -> {

            try{
                sound = new Media(getClass().getResource("/Music/adrenalinaMusic.mp3").toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();

            }
            catch(Exception e){
                logger.log(Level.WARNING, "exception in music line 759 GUI " + e.getMessage());
            }

        });
    }

    public void displayConnectionErrorClient(Message messageToResend){}

    public void displayConnectionFailure(){}

}