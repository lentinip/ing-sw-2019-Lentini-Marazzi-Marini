package it.polimi.sw2019.view.gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.sw2019.commons.Character;
import it.polimi.sw2019.commons.Colors;
import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.commons.messages.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the BoardScreen
 *
 * @author lentinip
 */
public class BoardController {

    /* Attributes */

    private Client client;

    private final Semaphore semaphore = new Semaphore(1);

    private TypeOfAction currentTypeOfAction;

    private CardController cardController = new CardController();

    private MatchStart configurationMessage;

    private MatchState oldMatchState;

    private ActionReports actionReports;

    private static Logger logger = Logger.getLogger("BoardController");

    //The index of my user in the message MatchState
    private int myIndex;

    private PrivateHand oldPrivateHand;

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView boardImage;

    @FXML
    private ProgressBar timer;

    //Timer in milliseconds
    private Integer timerDuration;

    private Double timeLeft;

    private Timeline timeline;

    private boolean reconnected=false;

    @FXML
    private ImageView weaponsDeck;

    @FXML
    private ImageView powerupsDeck;

    @FXML
    private ImageView skull0;

    @FXML
    private ImageView skull1;

    @FXML
    private ImageView skull2;

    @FXML
    private ImageView skull3;

    @FXML
    private ImageView skull4;

    @FXML
    private ImageView skull5;

    @FXML
    private ImageView skull6;

    @FXML
    private ImageView skull7;

    private List<ImageView> killTrackSkulls = new ArrayList<>();

    @FXML
    private ImageView killToken0;

    @FXML
    private ImageView killToken1;

    @FXML
    private ImageView killToken2;

    @FXML
    private ImageView killToken3;

    @FXML
    private ImageView killToken4;

    @FXML
    private ImageView killToken5;

    @FXML
    private ImageView killToken6;

    @FXML
    private ImageView killToken7;

    @FXML
    private ImageView extraKillToken;

    private KillTrackController killTrackController;

    private Stage killTrackStage;

    private List<ImageView> killTrackKillTokens = new ArrayList<>();

    @FXML
    private ImageView overKillToken0;

    @FXML
    private ImageView overKillToken1;

    @FXML
    private ImageView overKillToken2;

    @FXML
    private ImageView overKillToken3;

    @FXML
    private ImageView overKillToken4;

    @FXML
    private ImageView overKillToken5;

    @FXML
    private ImageView overKillToken6;

    @FXML
    private ImageView overKillToken7;

    private List<ImageView> killTrackOverKillTokens = new ArrayList<>();

    @FXML
    private ImageView blueSpawnCellWeapon0;

    @FXML
    private ImageView blueSpawnCellWeapon1;

    @FXML
    private ImageView blueSpawnCellWeapon2;

    private List<ImageView> blueSpawnCellWeapons = new ArrayList<>();

    @FXML
    private ImageView yellowSpawnCellWeapon0;

    @FXML
    private ImageView yellowSpawnCellWeapon1;

    @FXML
    private ImageView yellowSpawnCellWeapon2;

    private List<ImageView> yellowSpawnCellWeapons = new ArrayList<>();

    @FXML
    private ImageView redSpawnCellWeapon2;

    @FXML
    private ImageView redSpawnCellWeapon1;

    @FXML
    private ImageView redSpawnCellWeapon0;

    private List<ImageView> redSpawnCellWeapons = new ArrayList<>();

    private List<ImageView> selectedSpawnCell;

    @FXML
    private ImageView board1AmmoTile0;

    @FXML
    private ImageView board1AmmoTile1;

    @FXML
    private ImageView board1AmmoTile3;

    @FXML
    private ImageView board1AmmoTile5;

    @FXML
    private ImageView board1AmmoTile6;

    @FXML
    private ImageView board1AmmoTile7;

    @FXML
    private ImageView board1AmmoTile9;

    @FXML
    private ImageView board1AmmoTile10;

    private List<ImageView> board1AmmoTiles = new ArrayList<>();

    @FXML
    private ImageView board2AmmoTile0;

    @FXML
    private ImageView board2AmmoTile1;

    @FXML
    private ImageView board2AmmoTile3;

    @FXML
    private ImageView board2AmmoTile5;

    @FXML
    private ImageView board2AmmoTile6;

    @FXML
    private ImageView board2AmmoTile7;

    @FXML
    private ImageView board2AmmoTile8;

    @FXML
    private ImageView board2AmmoTile9;

    @FXML
    private ImageView board2AmmoTile10;

    private List<ImageView> board2AmmoTiles = new ArrayList<>();

    @FXML
    private ImageView board3AmmoTile0;

    @FXML
    private ImageView board3AmmoTile1;

    @FXML
    private ImageView board3AmmoTile5;

    @FXML
    private ImageView board3AmmoTile6;

    @FXML
    private ImageView board3AmmoTile7;

    @FXML
    private ImageView board3AmmoTile8;

    @FXML
    private ImageView board3AmmoTile9;

    @FXML
    private ImageView board3AmmoTile10;

    private List<ImageView> board3AmmoTiles = new ArrayList<>();

    @FXML
    private ImageView board4AmmoTile0;

    @FXML
    private ImageView board4AmmoTile1;

    @FXML
    private ImageView board4AmmoTile5;

    @FXML
    private ImageView board4AmmoTile6;

    @FXML
    private ImageView board4AmmoTile7;

    @FXML
    private ImageView board4AmmoTile9;

    @FXML
    private ImageView board4AmmoTile10;

    private List<ImageView> board4AmmoTiles = new ArrayList<>();

    private List<ImageView> boardAmmoTiles;

    @FXML
    private Pane cell0;

    @FXML
    private Pane cell1;

    @FXML
    private Pane cell2;

    @FXML
    private Pane cell3;

    @FXML
    private Pane cell4;

    @FXML
    private Pane cell5;

    @FXML
    private Pane cell6;

    @FXML
    private Pane cell7;

    @FXML
    private Pane cell8;

    @FXML
    private Pane cell9;

    @FXML
    private Pane cell10;

    @FXML
    private Pane cell11;

    private List<Pane> selectableCells = new ArrayList<>();

    private BoardCoord lastSelectedCell;

    @FXML
    private Circle yellowCharacter0;

    @FXML
    private Circle blueCharacter0;

    @FXML
    private Circle greenCharacter0;

    @FXML
    private Circle greyCharacter0;

    @FXML
    private Circle violetCharacter0;

    @FXML
    private Circle yellowCharacter1;

    @FXML
    private Circle blueCharacter1;

    @FXML
    private Circle greenCharacter1;

    @FXML
    private Circle greyCharacter1;

    @FXML
    private Circle violetCharacter1;

    @FXML
    private Circle yellowCharacter2;

    @FXML
    private Circle blueCharacter2;

    @FXML
    private Circle greenCharacter2;

    @FXML
    private Circle greyCharacter2;

    @FXML
    private Circle violetCharacter2;

    @FXML
    private Circle yellowCharacter3;

    @FXML
    private Circle blueCharacter3;

    @FXML
    private Circle greenCharacter3;

    @FXML
    private Circle greyCharacter3;

    @FXML
    private Circle violetCharacter3;

    @FXML
    private Circle yellowCharacter4;

    @FXML
    private Circle blueCharacter4;

    @FXML
    private Circle greenCharacter4;

    @FXML
    private Circle greyCharacter4;

    @FXML
    private Circle violetCharacter4;

    @FXML
    private Circle yellowCharacter5;

    @FXML
    private Circle blueCharacter5;

    @FXML
    private Circle greenCharacter5;

    @FXML
    private Circle greyCharacter5;

    @FXML
    private Circle violetCharacter5;

    @FXML
    private Circle yellowCharacter6;

    @FXML
    private Circle blueCharacter6;

    @FXML
    private Circle greenCharacter6;

    @FXML
    private Circle greyCharacter6;

    @FXML
    private Circle violetCharacter6;

    @FXML
    private Circle yellowCharacter7;

    @FXML
    private Circle blueCharacter7;

    @FXML
    private Circle greenCharacter7;

    @FXML
    private Circle greyCharacter7;

    @FXML
    private Circle violetCharacter7;

    @FXML
    private Circle yellowCharacter8;

    @FXML
    private Circle blueCharacter8;

    @FXML
    private Circle greenCharacter8;

    @FXML
    private Circle greyCharacter8;

    @FXML
    private Circle violetCharacter8;

    @FXML
    private Circle yellowCharacter9;

    @FXML
    private Circle blueCharacter9;

    @FXML
    private Circle greenCharacter9;

    @FXML
    private Circle greyCharacter9;

    @FXML
    private Circle violetCharacter9;

    @FXML
    private Circle yellowCharacter10;

    @FXML
    private Circle blueCharacter10;

    @FXML
    private Circle greenCharacter10;

    @FXML
    private Circle greyCharacter10;

    @FXML
    private Circle violetCharacter10;

    @FXML
    private Circle yellowCharacter11;

    @FXML
    private Circle blueCharacter11;

    @FXML
    private Circle greenCharacter11;

    @FXML
    private Circle greyCharacter11;

    @FXML
    private Circle violetCharacter11;

    private List<Circle> bansheePositions = new ArrayList<>();
    private List<Circle> distructorPositions = new ArrayList<>();
    private List<Circle> dozerPositions = new ArrayList<>();
    private List<Circle> sprogPositions = new ArrayList<>();
    private List<Circle> violetPositions = new ArrayList<>();

    private List<Circle> ablePositions = new ArrayList<>();

    @FXML
    private ImageView myWeaponCard0;

    @FXML
    private ImageView myWeaponCard1;

    @FXML
    private ImageView myWeaponCard2;

    private List<ImageView> myWeapons = new ArrayList<>();

    @FXML
    private ImageView myPowerupCard0;

    @FXML
    private ImageView myPowerupCard1;

    @FXML
    private ImageView myPowerupCard2;

    private List<ImageView> myPowerups = new ArrayList<>();

    private PlayerBoardController myPlayerBoard;

    private List<PlayerBoardController> otherPlayerBoards = new ArrayList<>();

    private List<PlayerBoardController> allPlayerBoards = new ArrayList<>();

    @FXML
    private Label weaponsDeckLabel;

    @FXML
    private Label powerupsDeckLabel;

    @FXML
    private Label frenzyModeLabel;

    private boolean frenzyStarted = false;

    @FXML
    private Label leftActionsLabel;

    @FXML
    private Button endTurnButton;

    @FXML
    private Button usePowerupButton;

    @FXML
    private Group selectAPlayerGroup;

    @FXML
    private Button cancelSelectAplayerButton;

    @FXML
    private Rectangle frenzyModeRectangle;

    @FXML
    private Pane pane;

    private Stage instructionManualStage;

    private Stage weaponsManualStage;

    private Stage actionReportsStage;


    /* Methods */

    //Initialization methods

    /**
     * This method initialize the class for all different configurations
     */
    public void initialize(){

        //Initialize the cells (for selection) and the spots for the weapons in the spawn cells
        initializeCells();
        initializeSpawnCellWeapons();

        //Initialize the killtrack
        initializeKilltrack();
        initializeKillTrackSummary();

        //Initialize the spot for my cards
        initializeMyCards();

        //Initialize the players positions
        initializePositions();

        //Initialize labels
        initializeLabels();
    }

    /**
     * This method configure the board using the message received from the main Controller.
     * This method needs to be called after the controller is instantiated.
     * @param client reference of the Client instance
     * @param configuration configuration message
     */
    public void configureBoard(Client client, MatchStart configuration){


        try {
            //First acquires a semaphore for safety
            semaphore.acquire();

            //The next lines bind the content of the BoardScreen to a specific scale.
            //The result is that the window is resizable, the content fits the window using a specific scale and the background fills the empty spaces.
            //The stackPane specified in the fxml file keeps the content in the middle of the window.
            Scene scene = pane.getScene();

            double origW = 1261;
            double origH = 901;

            pane.setMinSize(origW, origH);
            pane.setMaxSize(origW, origH);

            NumberBinding maxScale = Bindings.min(scene.widthProperty().divide(origW),scene.heightProperty().divide(origH));
            pane.scaleXProperty().bind(maxScale);
            pane.scaleYProperty().bind(maxScale);

            //Sets the client and saves the message in the configurationMessage attribute
            this.client = client;
            configurationMessage = configuration;

            //First sets the image of the board and the tiles
            String boardPath = getBoardPath(configuration.getBoardType());

            boardImage.setImage(new Image(boardPath));
            initializeAmmoTiles(configuration.getBoardType());

            //Initializes the possible easyMode with 5 skulls
            if(configuration.isEightSkulls()){
                setEasyMode();
            }

            //Initializes my playerBoard

            myIndex = configuration.getUsernames().indexOf(client.getUsername());
            Character myCharacter = configuration.getCharacters().get(myIndex);
            boolean iAmTheFirst=false;

            if (myIndex==0){
                iAmTheFirst=true;

            }

            initializeMyPlayerBoard(myCharacter, iAmTheFirst);

            //Initializes the other playerBoards

            List<String> usernames = new ArrayList<>(configuration.getUsernames());
            List<Character> characters = new ArrayList<>(configuration.getCharacters());

            usernames.remove(myIndex);
            characters.remove(myIndex);

            for (int i=0; i < usernames.size(); i++){

                if (!iAmTheFirst && i==0){
                    initializeOtherPlayerBoards(usernames.get(0),characters.get(0),true, 0);
                }
                else {
                    initializeOtherPlayerBoards(usernames.get(i), characters.get(i), false, i);
                }
            }

            //Orders the playerBoardControllers in an arrayList by the Character order
            orderAllPlayerBoards();

            //Sets if there's the frenzy mode
            initializeFrenzyLabel(configuration.isFrenzy());

            //Gets the timer value
            Long longValue = configuration.getTurnDuration();
            timerDuration = longValue.intValue();

            //Initializes the timer timer
            initializeTimer();

            //If the timer already started
            if(configuration.getTimeLeft()>=0){
                //Gets the time left
                Long timerLeftLong = configuration.getTimeLeft();
                Double time = timerLeftLong.doubleValue();
                //Sets in the attribute timeLeft the amount of time left
                timeLeft = timerDuration.doubleValue() - time;
                //With this boolean the timer know that has to start from a different value
                reconnected=true;
            }

            //Releases the semaphore
            semaphore.release();

        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * This method gets the path of the image of the board from the BoardsDictionary.json
     * @param jsonName name of the json in the server
     * @return the path of the image
     */
    public String getBoardPath(String jsonName){
        Gson json = new Gson();
        Map <String, String> boards = new HashMap<>();
        try {
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/BoardsDictionary.json")));
            boards = json.fromJson(jsonReader, Map.class);
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "BoardDictionary.json not found");
        }
        return boards.get(jsonName);
    }

    /**
     * Initializes the timer that controls the progress bar
     */
    public void initializeTimer(){
        IntegerProperty timeSeconds = new SimpleIntegerProperty(timerDuration);

        //Sets the progress property of the progress bar
        timer.progressProperty().bind(timeSeconds.divide(timerDuration.doubleValue()));

        timeSeconds.set(timerDuration);

        //Creates a Timeline (JavaFX) that changes the timeSeconds property
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(timerDuration/1000),
                        new KeyValue(timeSeconds, 0)));
    }

    /**
     * Starts the timer
     */
    public void startTimer() {
        //If the player just reconnected the timer starts from timeLeft
        if (reconnected){
            timeline.playFrom(new Duration(timeLeft));
            reconnected=false;
        }
        else {
            initializeTimer();
            timeline.playFromStart();
        }
    }

    /**
     * Stops the timer
     */
    public void stopTimer(){
        timeline.stop();
    }

    /**
     * Initializes the listeners of the labels over the decks
     */
    public void initializeLabels(){
        powerupsDeck.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showLabel(powerupsDeckLabel);
            }
        });

        powerupsDeckLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideLabel(powerupsDeckLabel);
            }
        });


        weaponsDeck.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showLabel(weaponsDeckLabel);
            }
        });

        weaponsDeckLabel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hideLabel(weaponsDeckLabel);
            }
        });
    }

    /**
     * Initializes the ammo tiles of a specific board
     * @param url name of the json of the selected board
     */
    public void initializeAmmoTiles(String url){

        board1AmmoTiles.add(board1AmmoTile0);
        board1AmmoTiles.add(board1AmmoTile1);
        board1AmmoTiles.add(board1AmmoTile3);
        board1AmmoTiles.add(board1AmmoTile5);
        board1AmmoTiles.add(board1AmmoTile6);
        board1AmmoTiles.add(board1AmmoTile7);
        board1AmmoTiles.add(board1AmmoTile9);
        board1AmmoTiles.add(board1AmmoTile10);

        //If the board is not the one with this ammoTiles hides them
        if (!url.contentEquals("Board1.json")){
            for (ImageView ammoTile : board1AmmoTiles){
                ammoTile.setVisible(false);
            }
        }
        else {
            //Sets this ammoTiles as the one of the board using
            boardAmmoTiles = board1AmmoTiles;
        }


        board2AmmoTiles.add(board2AmmoTile0);
        board2AmmoTiles.add(board2AmmoTile1);
        board2AmmoTiles.add(board2AmmoTile3);
        board2AmmoTiles.add(board2AmmoTile5);
        board2AmmoTiles.add(board2AmmoTile6);
        board2AmmoTiles.add(board2AmmoTile7);
        board2AmmoTiles.add(board2AmmoTile8);
        board2AmmoTiles.add(board2AmmoTile9);
        board2AmmoTiles.add(board2AmmoTile10);

        if (!url.contentEquals("Board2.json")){
            for (ImageView ammoTile : board2AmmoTiles){
                ammoTile.setVisible(false);
            }
        }
        else {
            boardAmmoTiles = board2AmmoTiles;
        }

        board3AmmoTiles.add(board3AmmoTile0);
        board3AmmoTiles.add(board3AmmoTile1);
        board3AmmoTiles.add(board3AmmoTile5);
        board3AmmoTiles.add(board3AmmoTile6);
        board3AmmoTiles.add(board3AmmoTile7);
        board3AmmoTiles.add(board3AmmoTile8);
        board3AmmoTiles.add(board3AmmoTile9);
        board3AmmoTiles.add(board3AmmoTile10);

        if (!url.contentEquals("Board3.json")){
            for (ImageView ammoTile : board3AmmoTiles){
                ammoTile.setVisible(false);
            }
        }
        else {
            boardAmmoTiles = board3AmmoTiles;
        }

        board4AmmoTiles.add(board4AmmoTile0);
        board4AmmoTiles.add(board4AmmoTile1);
        board4AmmoTiles.add(board4AmmoTile5);
        board4AmmoTiles.add(board4AmmoTile6);
        board4AmmoTiles.add(board4AmmoTile7);
        board4AmmoTiles.add(board4AmmoTile9);
        board4AmmoTiles.add(board4AmmoTile10);

        if (!url.contentEquals("Board4.json")){
            for (ImageView ammoTile : board4AmmoTiles){
                ammoTile.setVisible(false);
            }
        }
        else {
            boardAmmoTiles = board4AmmoTiles;
        }

        if (boardAmmoTiles==null){
            logger.log(Level.SEVERE, "AmmoTiles not successfully loaded, probably wrong Boardname");
        }
    }

    /**
     * Initializes the graphic cells used for the selection
     */
    public void initializeCells(){

        selectableCells.add(cell0);
        selectableCells.add(cell1);
        selectableCells.add(cell2);
        selectableCells.add(cell3);
        selectableCells.add(cell4);
        selectableCells.add(cell5);
        selectableCells.add(cell6);
        selectableCells.add(cell7);
        selectableCells.add(cell8);
        selectableCells.add(cell9);
        selectableCells.add(cell10);
        selectableCells.add(cell11);

        disableAvailableCells();

        cell0.setUserData(new BoardCoord(0, 0));
        cell1.setUserData(new BoardCoord(0, 1));
        cell2.setUserData(new BoardCoord(0, 2));
        cell3.setUserData(new BoardCoord(0, 3));
        cell4.setUserData(new BoardCoord(1, 0));
        cell5.setUserData(new BoardCoord(1, 1));
        cell6.setUserData(new BoardCoord(1, 2));
        cell7.setUserData(new BoardCoord(1, 3));
        cell8.setUserData(new BoardCoord(2, 0));
        cell9.setUserData(new BoardCoord(2, 1));
        cell10.setUserData(new BoardCoord(2, 2));
        cell11.setUserData(new BoardCoord(2, 3));

        for (Pane cell : selectableCells){
            cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Pane cell = (Pane) event.getSource();
                    cellSelectionHandler(cell);
                }
            });
        }
    }

    /**
     * Initializes the positions of the players
     */
    public void initializePositions(){
        bansheePositions.add(blueCharacter0);
        bansheePositions.add(blueCharacter1);
        bansheePositions.add(blueCharacter2);
        bansheePositions.add(blueCharacter3);
        bansheePositions.add(blueCharacter4);
        bansheePositions.add(blueCharacter5);
        bansheePositions.add(blueCharacter6);
        bansheePositions.add(blueCharacter7);
        bansheePositions.add(blueCharacter8);
        bansheePositions.add(blueCharacter9);
        bansheePositions.add(blueCharacter10);
        bansheePositions.add(blueCharacter11);

        for (Circle position : bansheePositions){
            position.setUserData(Character.BANSHEE);
        }

        distructorPositions.add(yellowCharacter0);
        distructorPositions.add(yellowCharacter1);
        distructorPositions.add(yellowCharacter2);
        distructorPositions.add(yellowCharacter3);
        distructorPositions.add(yellowCharacter4);
        distructorPositions.add(yellowCharacter5);
        distructorPositions.add(yellowCharacter6);
        distructorPositions.add(yellowCharacter7);
        distructorPositions.add(yellowCharacter8);
        distructorPositions.add(yellowCharacter9);
        distructorPositions.add(yellowCharacter10);
        distructorPositions.add(yellowCharacter11);

        for (Circle position : distructorPositions){
            position.setUserData(Character.DISTRUCTOR);
        }

        dozerPositions.add(greyCharacter0);
        dozerPositions.add(greyCharacter1);
        dozerPositions.add(greyCharacter2);
        dozerPositions.add(greyCharacter3);
        dozerPositions.add(greyCharacter4);
        dozerPositions.add(greyCharacter5);
        dozerPositions.add(greyCharacter6);
        dozerPositions.add(greyCharacter7);
        dozerPositions.add(greyCharacter8);
        dozerPositions.add(greyCharacter9);
        dozerPositions.add(greyCharacter10);
        dozerPositions.add(greyCharacter11);

        for (Circle position : dozerPositions){
            position.setUserData(Character.DOZER);
        }

        sprogPositions.add(greenCharacter0);
        sprogPositions.add(greenCharacter1);
        sprogPositions.add(greenCharacter2);
        sprogPositions.add(greenCharacter3);
        sprogPositions.add(greenCharacter4);
        sprogPositions.add(greenCharacter5);
        sprogPositions.add(greenCharacter6);
        sprogPositions.add(greenCharacter7);
        sprogPositions.add(greenCharacter8);
        sprogPositions.add(greenCharacter9);
        sprogPositions.add(greenCharacter10);
        sprogPositions.add(greenCharacter11);

        for (Circle position : sprogPositions){
            position.setUserData(Character.SPROG);
        }

        violetPositions.add(violetCharacter0);
        violetPositions.add(violetCharacter1);
        violetPositions.add(violetCharacter2);
        violetPositions.add(violetCharacter3);
        violetPositions.add(violetCharacter4);
        violetPositions.add(violetCharacter5);
        violetPositions.add(violetCharacter6);
        violetPositions.add(violetCharacter7);
        violetPositions.add(violetCharacter8);
        violetPositions.add(violetCharacter9);
        violetPositions.add(violetCharacter10);
        violetPositions.add(violetCharacter11);

        for (Circle position : violetPositions){
            position.setUserData(Character.VIOLET);
        }
    }

    /**
     * initialize the ImageViews used for the spawn cells
     */
    public void initializeSpawnCellWeapons(){
        blueSpawnCellWeapons.add(blueSpawnCellWeapon0);
        blueSpawnCellWeapons.add(blueSpawnCellWeapon1);
        blueSpawnCellWeapons.add(blueSpawnCellWeapon2);

        for (ImageView imageView : blueSpawnCellWeapons){
            imageView.setVisible(true);
        }

        yellowSpawnCellWeapons.add(yellowSpawnCellWeapon0);
        yellowSpawnCellWeapons.add(yellowSpawnCellWeapon1);
        yellowSpawnCellWeapons.add(yellowSpawnCellWeapon2);

        for (ImageView imageView : yellowSpawnCellWeapons){
            imageView.setVisible(true);
        }

        redSpawnCellWeapons.add(redSpawnCellWeapon0);
        redSpawnCellWeapons.add(redSpawnCellWeapon1);
        redSpawnCellWeapons.add(redSpawnCellWeapon2);

        for (ImageView imageView : redSpawnCellWeapons){
            imageView.setVisible(true);
        }
    }

    /**
     * Initialize the ImageViews for the cards of the player of this client
     */
    public void initializeMyCards(){
        myWeapons.add(myWeaponCard0);
        myWeapons.add(myWeaponCard1);
        myWeapons.add(myWeaponCard2);

        for (ImageView imageView : myWeapons){
            imageView.setVisible(false);
        }

        myPowerups.add(myPowerupCard0);
        myPowerups.add(myPowerupCard1);
        myPowerups.add(myPowerupCard2);

        for (ImageView imageView : myPowerups){
            imageView.setVisible(false);
        }
    }

    /**
     * Initializes the killtrack
     */
    public void initializeKilltrack(){
        killTrackKillTokens.add(killToken0);
        killTrackKillTokens.add(killToken1);
        killTrackKillTokens.add(killToken2);
        killTrackKillTokens.add(killToken3);
        killTrackKillTokens.add(killToken4);
        killTrackKillTokens.add(killToken5);
        killTrackKillTokens.add(killToken6);
        killTrackKillTokens.add(killToken7);

        for (ImageView imageView : killTrackKillTokens){
            imageView.setVisible(false);
        }

        extraKillToken.setVisible(false);

        killTrackOverKillTokens.add(overKillToken0);
        killTrackOverKillTokens.add(overKillToken1);
        killTrackOverKillTokens.add(overKillToken2);
        killTrackOverKillTokens.add(overKillToken3);
        killTrackOverKillTokens.add(overKillToken4);
        killTrackOverKillTokens.add(overKillToken5);
        killTrackOverKillTokens.add(overKillToken6);
        killTrackOverKillTokens.add(overKillToken7);

        for (ImageView imageView : killTrackOverKillTokens){
            imageView.setVisible(false);
        }

        killTrackSkulls.add(skull0);
        killTrackSkulls.add(skull1);
        killTrackSkulls.add(skull2);
        killTrackSkulls.add(skull3);
        killTrackSkulls.add(skull4);
        killTrackSkulls.add(skull5);
        killTrackSkulls.add(skull6);
        killTrackSkulls.add(skull7);

    }

    /**
     * Sets the easyMode (shows only 5 skulls on the killtrack)
     */
    public void setEasyMode(){
        for (int i=0; i<3; i++){
            killTrackSkulls.get(0).setVisible(false);
            killTrackSkulls.remove(0);
            killTrackKillTokens.remove(0);
            killTrackOverKillTokens.remove(0);
        }
    }

    /**
     * Initializes the playerboard of the player of this client
     * @param character Character of the playerboard to show
     * @param first true if the player is the first player
     */
    public void initializeMyPlayerBoard(Character character, boolean first){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXMLFiles/MyPlayerBoardGroup.fxml"));
        try {
            Group myPlayerBoardGroup = loader.load();
            //Puts it in a specific spot in the screen
            myPlayerBoardGroup.setLayoutX(398.0);
            myPlayerBoardGroup.setLayoutY(671.0);
            //Also inside a Pane
            mainPane.getChildren().add(myPlayerBoardGroup);
        }
        catch (LoadException e){
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getStackTrace().toString());
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "MyPlayerBoardGroup.fxml not found");
        }

        myPlayerBoard = loader.getController();
        myPlayerBoard.disableActions();
        myPlayerBoard.configureMyPlayerBoard(client, character, first);
        allPlayerBoards.add(myPlayerBoard);
    }

    /**
     * Initializes the playerboards of the other clients players
     * @param username username of the player
     * @param character character of the player
     * @param first true if the player is first
     * @param index index of the player in the BoardScreen
     */
    public void initializeOtherPlayerBoards(String username, Character character, boolean first, int index){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXMLFiles/PlayerBoardGroup.fxml"));
        try {
            Group playerBoard = loader.load();

            //Using the index puts them in a specific spot in the window
            playerBoard.setLayoutX(842.0);

            switch(index){
                case 0:
                    playerBoard.setLayoutY(33.0);
                    break;
                case 1:
                    playerBoard.setLayoutY(136.0);
                    break;
                case 2:
                    playerBoard.setLayoutY(239.0);
                    break;
                case 3:
                    playerBoard.setLayoutY(342.0);
                    break;
                default:
                    logger.log(Level.SEVERE, "MORE THAN 4 PLAYERS IN THE initializeOtherPlayerBoards METHOD");
                    break;
            }
            //Also adds them in a Pane
            mainPane.getChildren().add(playerBoard);
        }
        catch (LoadException e){
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getStackTrace().toString());
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "PlayerBoardGroup.fxml not found");
        }

        PlayerBoardController playerBoardController = loader.getController();
        playerBoardController.configurePlayerBoard(username, character, first);
        otherPlayerBoards.add(playerBoardController);
        allPlayerBoards.add(playerBoardController);
    }

    /**
     * Orders the playerboards in the allPlayerBoards ArrayList (using the list of usernames in the configuration message)
     */
    public void orderAllPlayerBoards(){
        List<PlayerBoardController> playerBoardControllers = new ArrayList<>();
        for (Character character : configurationMessage.getCharacters()){
            for (PlayerBoardController playerBoardController : allPlayerBoards){
                if (playerBoardController.getCharacter() == character){
                    playerBoardControllers.add(playerBoardController);
                }
            }
        }
        allPlayerBoards = playerBoardControllers;
    }

    /**
     * Gets the Image of an ammo tile
     * @param name name of the ammo
     * @return Image of the ammo with the specified name
     */
    public Image getAmmoImage(String name){
        String path = "/images/ammo/"+name;
        return new Image(path);
    }

    /**
     * Initializes the frenzy label
     * @param frenzyMode true if this game has the frenzy mode
     */
    public void initializeFrenzyLabel(boolean frenzyMode){
        if (frenzyMode){
            frenzyModeLabel.setText("yes");
        }
        else {
            frenzyModeLabel.setText("no");
        }
    }

    /**
     * Initialize the window for the killtrack summary
     */
    public void initializeKillTrackSummary(){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/KillTrackScreen.fxml"));

            Parent root;
            Scene scene;

            try {
                root = fxmlLoader.load();
                scene = new Scene(root);

            }
            catch (IOException e) {
                logger.log(Level.SEVERE, "KillTrackScreen.fxml file not found in BoardController");
                scene = new Scene(new Label("ERROR"));
            }

            killTrackController = fxmlLoader.getController();

            killTrackStage = new Stage();

            killTrackStage.setScene(scene);
            killTrackStage.initOwner(boardImage.getScene().getWindow());

            killTrackStage.setTitle("Killtrack summary");
            killTrackStage.setResizable(false);
        });
    }

    //Method for the update of the controller

    /**
     * Updates the private hand of the player of this client
     * @param privateHand privateHand message
     */
    public void updatePrivateHand(PrivateHand privateHand){
        if (oldPrivateHand == null || !oldPrivateHand.equals(privateHand)){

            //Manages the weapons

            //Array of all the weapons
            List<String> newWeapons = privateHand.getAllWeapons();

            //Sets the new Weapons
            for (int i=0; i<3; i++){

                //Gets the ImageView for the weapon
                ImageView myWeapon = myWeapons.get(i);

                //For all the weapons the player has
                if (i<newWeapons.size() && i<myWeapons.size()){

                    //Gets the Image of the new weapon
                    String newWeaponName = newWeapons.get(i);
                    Image newImage = cardController.getWeaponImage(newWeaponName);

                    //Sets the image
                    myWeapon.setImage(newImage);

                    //Checks if the weapon is visible
                    boolean isVisible = privateHand.getWeaponsLoaded().contains(newWeaponName);

                    //Sets the visibility and shows it
                    CardController.setUnavailable(myWeapon, !isVisible);
                    myWeapon.setVisible(true);
                }
                else {
                    //If the player doesn't have anymore weapons
                    Image weaponsBack = cardController.getWeaponImage("weaponsBack.png");
                    myWeapon.setImage(weaponsBack);
                    myWeapon.setVisible(false);
                }
            }

            //Manages the powerups

            for (int i=0; i<3; i++){

                //Gets the ImageView for the Powerup
                ImageView myPowerup = myPowerups.get(i);

                //For all the weapons the player has
                if (i<privateHand.getPowerups().size()){

                    //Gets the Image of the new weapon
                    String newPowerupName = privateHand.getPowerups().get(i);
                    Colors newPowerupColor = privateHand.getPowerupColors().get(i);
                    Image newImage = cardController.getPowerupImage(newPowerupName, newPowerupColor);

                    //Sets the image
                    myPowerup.setImage(newImage);
                    //Sets the visibility
                    myPowerup.setVisible(true);
                }
                else {
                    //If the player doesn't have anymore powerups
                    Image powerupsBack = cardController.getPowerupImage("powerupsBack.png", Colors.BLUE);
                    myPowerup.setImage(powerupsBack);
                    myPowerup.setVisible(false);
                }
            }
        }

        oldPrivateHand = privateHand;
    }

    /**
     * Updates the BoardScreen
     * @param matchState matchState message
     */
    public void updateMatch(MatchState matchState){

        try {
            semaphore.acquire();


            updateCells(matchState.getCells());
            updatePlayerBoards(matchState.getPlayerBoardMessages(), matchState.getPlayerHands(), matchState.getCurrentPlayer());
            updateLeftActions(matchState.getCurrentPlayerLeftActions());
            updateKillTrack(matchState.getKillSequence(), matchState.getOverkillSequence());
            //If the killTrack is full shows a new token with which the player can access a new screen with a summary of the tokens that are actually used for the kills
            if (matchState.getKillSequence().size()>killTrackSkulls.size()){
                updateKillTrackSummary(matchState.getKillSequence(), matchState.getOverkillSequence());
            }
            updateDecks(matchState.getWeaponsDeckSize(), matchState.getPowerupsDeckSize());
            updateCurrentPlayer(matchState.getCurrentPlayer());

            //If the player of the client doesn't have anymore actions
            if (matchState.getCurrentPlayer() == myPlayerBoard.getCharacter() && matchState.getCurrentPlayerLeftActions()==0){
                showOnlyReload();
            }
            //If the player of the client is not the current player
            if (matchState.getCurrentPlayer() != myPlayerBoard.getCharacter()){
                disableActions();
            }

            oldMatchState = matchState;

            semaphore.release();

        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Disables all the actions except for end turn, use powerup and reload
     */
    public void showOnlyReload(){
        disableActions();
        endTurnButton.setDisable(false);
        usePowerupButton.setDisable(false);
        myPlayerBoard.showReload();
    }

    /**
     * Updates the ammo tiles in the cells and the weapons of the spawn cells
     * @param messageCells List of messageCells
     */
    public void updateCells(List<MessageCell> messageCells){

        if (oldMatchState == null || !oldMatchState.getCells().equals(messageCells)){

            for (MessageCell messageCell : messageCells){

                int cellNumber = messageCell.getCellNumber();

                //If is the blue spawnCell
                if (cellNumber==2){
                    updateSpawnCell(messageCell, blueSpawnCellWeapons);
                }

                //If is the red spawnCell
                if (cellNumber==4){
                    updateSpawnCell(messageCell, redSpawnCellWeapons);
                }

                //If is the yellow spawnCell
                if (cellNumber==11){
                    updateSpawnCell(messageCell, yellowSpawnCellWeapons);
                }

                //Otherwise is a cell with ammo
                if (cellNumber!=2 && cellNumber!=4 && cellNumber!=11){

                    if (messageCell.getAmmoTile()==null){
                        //set the ammoTile not visible if there is no ammoTile
                        boardAmmoTiles.get(getAmmoPosition(messageCell)).setVisible(false);
                    }
                    else {
                        //Gets the ammoTile ImageView (of the cell described in the messageCell) and sets the image, and shows the imageView
                        ImageView cellAmmo = boardAmmoTiles.get(getAmmoPosition(messageCell));
                        Image ammoImage = getAmmoImage(messageCell.getAmmoTile());
                        cellAmmo.setImage(ammoImage);
                        cellAmmo.setVisible(true);
                    }
                }

                //Shows the players inside
                for (Character character : messageCell.getCharacters()){
                    showPlayerPosition(character, messageCell.getCellNumber());
                }
            }

        }
    }

    /**
     * Updates the weapons of a spawn cell
     * @param messageCell messageCell message
     * @param spawnCellWeapons List of ImageViews of the spawn cell associated to the messageCell
     */
    public void updateSpawnCell(MessageCell messageCell, List<ImageView> spawnCellWeapons){
        for (int i=0; i<3; i++){
            //For how many weapons in the cell
            if (i<messageCell.getWeapons().size()){
                String weaponName = messageCell.getWeapons().get(i);

                Image weaponImage = cardController.getWeaponImage(weaponName);
                spawnCellWeapons.get(i).setImage(weaponImage);
                spawnCellWeapons.get(i).setVisible(true);
            }
            //If the weapons are less than the ImageViews of the spawn cell
            else {
                spawnCellWeapons.get(i).setVisible(false);
            }
        }
    }

    /**
     * Method that calculates the position of the ammoTile in the board
     * @param messageCell messageCell of the common cell
     * @return the number of the position of the ammoTile in the boardAmmoTiles list
     */
    public int getAmmoPosition(MessageCell messageCell){
        int result = messageCell.getAmmoPositionNumber();

        if (configurationMessage.getBoardType().equals("Board1.json")){
            if (messageCell.getCellNumber()>8){
                result--;
            }
        }

        else if (configurationMessage.getBoardType().equals("Board3.json")){
            if (messageCell.getCellNumber()>3){
                result--;
            }
        }

        else if (configurationMessage.getBoardType().equals("Board4.json")){
            if (messageCell.getCellNumber()>3){
                result--;
            }

            if (messageCell.getCellNumber()>8){
                result--;
            }
        }

        return result;
    }

    /**
     * Shows the position of a player on the board
     * @param character character of the player
     * @param cellNumber number of the cell where is the player
     */
    public void showPlayerPosition(Character character, int cellNumber){
        List<Circle> positions = new ArrayList<>();

        switch (character){
            case BANSHEE:
                positions = bansheePositions;
                break;
            case DISTRUCTOR:
                positions = distructorPositions;
                break;
            case DOZER:
                positions = dozerPositions;
                break;
            case SPROG:
                positions = sprogPositions;
                break;
            case VIOLET:
                positions = violetPositions;
                break;
            default:
                logger.log(Level.SEVERE, "Not identified character in showPlayerPosition");
        }

        for (Circle position : positions){
            position.setVisible(false);
        }

        positions.get(cellNumber).setVisible(true);
    }

    /**
     * Updates all the playerBoards
     * @param playerBoardMessages list of playerBoardMessages
     * @param playerHands list of playerHands
     * @param currentCharacter current character
     */
    public void updatePlayerBoards(List<PlayerBoardMessage> playerBoardMessages, List<PlayerHand> playerHands, Character currentCharacter){

        if (configurationMessage.isFrenzy()){
            for (PlayerBoardMessage playerBoardMessage : playerBoardMessages){
                if (playerBoardMessage.isFlipped() && !frenzyStarted){
                    updateIsFrenzy(currentCharacter);
                }
            }
        }


        for(int i=0; i<playerBoardMessages.size(); i++){
            if (i==myIndex){
                myPlayerBoard.updatePlayerBoard(playerBoardMessages.get(i));
            }
            else {
                PlayerBoardController otherPlayerBoard = allPlayerBoards.get(i);
                otherPlayerBoard.updatePlayerBoard(playerBoardMessages.get(i));
                otherPlayerBoard.updatePlayerHand(playerHands.get(i));
            }
        }

    }

    /**
     * Updates the board and the playerboards to the frenzy mode
     * @param currentCharacter current character (the one that activates the frenzy mode)
     */
    public void updateIsFrenzy(Character currentCharacter){
        frenzyStarted=true;

        boolean beforeFirst = false;
        List<Character> characters = configurationMessage.getCharacters();

        for (int i=0; i<characters.size(); i++){
            if (characters.get(i).equals(currentCharacter)){
                allPlayerBoards.get(i).setFrenzyMode(false);

                //The before first is true before because the current player changes after the arrive of the first match state
                beforeFirst=true;
            }
            else {
                allPlayerBoards.get(i).setFrenzyMode(beforeFirst);
            }
        }

        //Shows something so that the player knows is the frenzyMode
        frenzyModeRectangle.setFill(Color.RED);
    }

    /**
     * Updates the killtrack
     * @param killSequence List of characters that killed somebody
     * @param overkillSequence List of boolean of the overkills (true if it is an overkill)
     */
    public void updateKillTrack(List<Character> killSequence, List<Boolean> overkillSequence){

        if (oldMatchState == null || !oldMatchState.getKillSequence().equals(killSequence)){


            for (int i=0; i<killSequence.size() && i<killTrackSkulls.size(); i++){

                ImageView token = killTrackKillTokens.get(i);
                PlayerBoardController.changeTokenColor(token, killSequence.get(i));
                token.setVisible(true);
                killTrackSkulls.get(i).setVisible(false);

                //If there's a overkill shows the overKillToken
                if(overkillSequence.get(i)){
                    ImageView tokenOverkill = killTrackOverKillTokens.get(i);
                    PlayerBoardController.changeTokenColor(tokenOverkill, killSequence.get(i));
                    tokenOverkill.setVisible(true);
                }
            }
        }
    }

    /**
     * Updates the labels and the ImageViews of the decks
     * @param weaponsDeckSize Integer of the size of the weaponsDeck
     * @param powerupsDeckSize Integer of the size of the powerupsDeck
     */
    public void updateDecks(Integer weaponsDeckSize, Integer powerupsDeckSize){
        weaponsDeckLabel.setText(weaponsDeckSize.toString());
        powerupsDeckLabel.setText(powerupsDeckSize.toString());

        if (weaponsDeckSize.equals(0)){
            weaponsDeck.setVisible(false);
        }
    }

    /**
     * Updates the label for the current player left actions
     * @param currentPlayerLeftActions Integer of the currentPlayerLeftActions
     */
    public void updateLeftActions(Integer currentPlayerLeftActions){
        leftActionsLabel.setText(currentPlayerLeftActions.toString());
    }

    /**
     * Updates the current player (also the graphic) and starts the timer again
     * @param currentPlayer character of the current player
     */
    public void updateCurrentPlayer(Character currentPlayer){
        if (oldMatchState == null || oldMatchState.getCurrentPlayer() != currentPlayer){
            //Starts the turn timer
            startTimer();

            //Changes the effect around the playerBoard
            for (PlayerBoardController playerBoardController : allPlayerBoards){
                if (playerBoardController.getCharacter()==currentPlayer){
                    playerBoardController.setAsCurrentPlayer();
                }
                else {
                    playerBoardController.removeAsCurrentPlayer();
                }
            }
        }
    }

    /**
     * Updates the killTrackScreen
     * @param killTrack List of characters that killed somebody
     * @param overKillTrack List of boolean of the overkills (true if it is an overkill)
     */
    public void updateKillTrackSummary(List<Character> killTrack, List<Boolean> overKillTrack){
        if (!extraKillToken.isVisible()){
            extraKillToken.setVisible(true);
        }

        if (!oldMatchState.getKillSequence().equals(killTrack)){
            killTrackController.updateTokens(killTrack, overKillTrack);
        }
    }

    /**
     * Shows the stage of the killTrackScreen
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void showKillTrackSummary(MouseEvent mouseEvent){
        killTrackStage.show();
    }

    /**
     * Shows a label
     * @param label label to show
     */
    public void showLabel(Label label){
        label.setVisible(true);
    }

    /**
     * Hides a label
     * @param label label to hide
     */
    public void hideLabel(Label label){
        label.setVisible(false);
    }

    /**
     * Handles the selection of a cell
     * @param cell Pane clicked by the user
     */
    public void cellSelectionHandler(Pane cell){

        BoardCoord boardCoord = (BoardCoord) cell.getUserData();

        //If is a spawncell saves the cells weapons for the grab
        int cellNumber = boardCoord.getCellNumber();
        lastSelectedCell = boardCoord;

        switch (cellNumber){
            case 2:
                selectedSpawnCell = blueSpawnCellWeapons;
                break;
            case 4:
                selectedSpawnCell = redSpawnCellWeapons;
                break;
            case 11:
                selectedSpawnCell = yellowSpawnCellWeapons;
                break;
            default:
                selectedSpawnCell = null;
                lastSelectedCell = null;
        }

        Message message = new Message(client.getUsername());

        if (currentTypeOfAction == TypeOfAction.SHOOT){
            message.createSelectedCellMessage(boardCoord, TypeOfAction.SHOOT, TypeOfMessage.SELECTED_CELL);
        }

        else {
            message.createSelectedCellMessage(boardCoord, currentTypeOfAction, TypeOfMessage.SINGLE_ACTION);
        }

        disableAvailableCells();
        client.send(message);
    }

    //Interactions

    /**
     * Shows or hides the buttons for the actions depending if the player can shoot
     * @param canIShoot true if the player can shoot
     */
    public void canIShoot(boolean canIShoot){

        try {
            semaphore.acquire();


            if (oldMatchState.getCurrentPlayerLeftActions()==0 && iAmTheCurrentPlayer()){
                showOnlyReload();
            }
            else {
                myPlayerBoard.showPossibleActions(canIShoot);
                endTurnButton.setDisable(false);
                usePowerupButton.setDisable(false);
            }

            if (actionReports!=null){
                actionReports.setDamageSession(false);
            }

            semaphore.release();
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Disables all the buttons for the actions
     */
    public void disableActions(){
        myPlayerBoard.disableActions();
        endTurnButton.setDisable(true);
        usePowerupButton.setDisable(true);
    }

    /**
     * Shows specific Panes with onMouseClicked listeners on the BoardScreen representing the cells
     * @param cells List of boardCoord of the cells to show
     * @param typeOfAction typeOfAction in which the player is involved
     */
    public void showAvailableCells(List<BoardCoord> cells, TypeOfAction typeOfAction){
        try {
            semaphore.acquire();

            currentTypeOfAction = typeOfAction;

            disableAvailableCells();

            for (BoardCoord cell : cells){
                selectableCells.get(cell.getCellNumber()).setVisible(true);
            }

            //For the move and the grab it doesn't disable the actions so you can change your mind
            if (typeOfAction != TypeOfAction.MOVE && typeOfAction != TypeOfAction.GRAB){
                disableActions();
            }

            semaphore.release();
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Hides all the Panes representing the cells
     */
    public void disableAvailableCells(){
        for (Pane cell : selectableCells){
            cell.setVisible(false);
        }
    }

    /**
     * Gets a List of Images from the BoardController using the typeOfAction
     * @param typeOfAction typeOfAction in which the player is involved
     * @return List of Images required outside the BoardController
     */
    public List<Image> getImageCards(TypeOfAction typeOfAction){
        try {
            semaphore.acquire();

            List<Image> result = new ArrayList<>();
            List<ImageView> imageViewList;

            if (typeOfAction == TypeOfAction.GRAB){
                imageViewList = selectedSpawnCell;
            }

            else if (typeOfAction == TypeOfAction.RELOAD || typeOfAction == TypeOfAction.SHOOT){
                imageViewList = myWeapons;
            }

            else if (typeOfAction == TypeOfAction.USEPOWERUP){
                imageViewList = myPowerups;
            }

            else if (typeOfAction == TypeOfAction.SPAWN) {

                //Is not going to be used
                imageViewList = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    //For all the weapons the player has
                    if (i < oldPrivateHand.getPowerups().size()) {

                        //Gets the Image of the powerup
                        String newPowerupName = oldPrivateHand.getPowerups().get(i);
                        Colors newPowerupColor = oldPrivateHand.getPowerupColors().get(i);
                        Image newImage = cardController.getPowerupImage(newPowerupName, newPowerupColor);

                        //Adds it to the result list
                        result.add(newImage);
                    }
                }
            }

            else {
                logger.log(Level.SEVERE, "Trying to get instances of Image of cards with a not implemented TypeOfAction in getImageCards");

                //The new instance is for safety reasons
                imageViewList = new ArrayList<>();
            }

            for (ImageView imageView : imageViewList){
                if (imageView.isVisible()){
                    result.add(imageView.getImage());
                }
            }

            semaphore.release();

            return result;
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
            List<Image> imageList = new ArrayList<>();
            return imageList;
        }
    }

    /**
     * Ables specific Circles with onMouseClicked listeners on the BoardScreen representing the players
     * @param characters List of characters to able
     * @param typeOfAction typeOfAction in which the player is involved
     * @param noOption false if the player must chose a player
     */
    public void showSelectablePlayers(List<Character> characters, TypeOfAction typeOfAction, boolean noOption){
        try {
            semaphore.acquire();

            currentTypeOfAction = typeOfAction;
            disablePositions();

            for (Character character : characters){
                switch (character){
                    case BANSHEE:
                        ablePositionSelection(bansheePositions);
                        break;
                    case DISTRUCTOR:
                        ablePositionSelection(distructorPositions);
                        break;
                    case DOZER:
                        ablePositionSelection(dozerPositions);
                        break;
                    case SPROG:
                        ablePositionSelection(sprogPositions);
                        break;
                    case VIOLET:
                        ablePositionSelection(violetPositions);
                        break;
                    default:
                        break;
                }

            }

            selectAPlayerGroup.setVisible(true);

            if (noOption){
                cancelSelectAplayerButton.setVisible(true);
            }

            disableActions();

            semaphore.release();
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Ables, if visible, Circles with onMouseClicked listeners on the BoardScreen representing the players
     * @param positions List of Circles (positions) of a Character
     */
    public void ablePositionSelection(List<Circle> positions){
        for (Circle position : positions){
            if(position.isVisible()){
                position.setDisable(false);
                ablePositions.add(position);
            }
        }
    }

    /**
     * Disables all the Circles representing the positions of the players
     */
    public void disablePositions(){
        List<Circle> circleList = new ArrayList<>(ablePositions);

        for (Circle position : circleList){
            position.setDisable(true);
            ablePositions.remove(position);
        }
    }

    /**
     * Sets an effect to a Circle (Player position) when a specific mouseEvent is caught (onMouseEntered)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void showEffectPosition(MouseEvent mouseEvent){
        DropShadow dropShadow = new DropShadow();

        dropShadow.setHeight(30.0);
        dropShadow.setWidth(30.0);
        dropShadow.setSpread(0.3);
        dropShadow.setColor(Color.BLUE);

        Circle position = (Circle) mouseEvent.getSource();
        position.setEffect(dropShadow);
    }

    /**
     * Disables the effect of a Circle (Player position) when a specific mouseEvent is caught (onMouseExited)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void disableEffectPosition(MouseEvent mouseEvent){
        Circle position = (Circle) mouseEvent.getSource();
        position.setEffect(null);
    }

    /**
     * Handles the event onMouseClicked for the positions of the players
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void handlePositionSelection(MouseEvent mouseEvent){
        Circle position = (Circle) mouseEvent.getSource();
        Character selectedCharacter = (Character) position.getUserData();

        int positionInt = configurationMessage.getCharacters().indexOf(selectedCharacter);

        sendPosition(positionInt);
    }

    /**
     * Handles the choice of the player to don't select anyone
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleNoPositionSelection(ActionEvent actionEvent){
        sendPosition(-1);
    }

    /**
     * Sends the position to chosen to the server
     * @param position int of the player chosen in the usernames List in the configuration message (-1 if no one)
     */
    public void sendPosition(int position){
        disablePositions();
        selectAPlayerGroup.setVisible(false);
        cancelSelectAplayerButton.setVisible(false);

        Message message = new Message(client.getUsername());
        message.createSelectedPlayer(position, currentTypeOfAction);
        client.send(message);
    }


    public Group getMyAmmoGroup(){
        return myPlayerBoard.getAmmoGroup();
    }

    public PlayerBoardMessage getMyCurrentPlayerBoardMessage(){
        return myPlayerBoard.getCurrentPlayerBoardMessage();
    }

    public List<ImageView> getMyWeapons(){
        return myWeapons;
    }

    public PrivateHand getPrivateHand(){
        return oldPrivateHand;
    }

    public BoardCoord getLastSelectedCell(){
        return lastSelectedCell;
    }

    /**
     * Handler for the button End Turn
     * @param actionEvent actionEvent caught
     */
    public void handleEndTurn(ActionEvent actionEvent){
        disableAvailableCells();

        Message message = new Message(client.getUsername());
        message.createEndTurnMessage();
        client.send(message);
    }

    /**
     * Handler for the button Use Powerup
     * @param actionEvent actionEvent caught
     */
    public void handleUsePowerup(ActionEvent actionEvent){
        disableAvailableCells();

        Message message = new Message(client.getUsername());
        message.createAskMessage(TypeOfAction.USEPOWERUP);
        client.send(message);
    }

    /**
     * Checks if the player of this client is the current player
     * @return true if is the current player
     */
    public boolean iAmTheCurrentPlayer(){
        return oldMatchState.getCurrentPlayer() == myPlayerBoard.getCharacter();
    }

    public void setActionReports(ActionReports actionReports) {
        this.actionReports = actionReports;
    }

    /**
     * Checks if somebody has done damage and the shoot is not ended (Useful for Targeting Scope)
     * @return true if somebody has done damage and the shoot is not ended
     */
    public boolean damageSession(){
        try {
            return actionReports.isDamageSession();
        }
        catch (NullPointerException e){
            logger.log(Level.SEVERE, "There's no action report");
            return false;
        }
    }

    public void setInstructionManualStage(Stage instructionManualStage){
        this.instructionManualStage = instructionManualStage;
    }

    /**
     * Handler for the InstructionManual button.
     * If clicked shows the instructionManualStage.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleInstructionButton(ActionEvent actionEvent){
        if (instructionManualStage.isShowing()){
            instructionManualStage.toFront();
        }
        else {
            instructionManualStage.show();
        }
    }

    public void setWeaponsManualStage(Stage weaponsManualStage){
        this.weaponsManualStage = weaponsManualStage;
    }

    /**
     * Handler for the WeaponsManual button.
     * If clicked shows the weaponsManualStage.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleWeaponsManualButton(ActionEvent actionEvent){
        if (weaponsManualStage.isShowing()){
            weaponsManualStage.toFront();
        }
        else {
            weaponsManualStage.show();
        }
    }

    public void setActionReportsStage(Stage actionReportsStage) {
        this.actionReportsStage = actionReportsStage;
    }

    public Stage getActionReportsStage(){
        return actionReportsStage;
    }

    /**
     * Handler for the Action Reports button.
     * If clicked shows the actionReportsStage.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleActionReportsButton(ActionEvent actionEvent){
        if (actionReportsStage.isShowing()){
            actionReportsStage.toFront();
        }
        else {
            actionReportsStage.show();
        }
    }
}


