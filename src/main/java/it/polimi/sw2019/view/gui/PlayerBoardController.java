package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.PlayerBoardMessage;
import it.polimi.sw2019.network.messages.PlayerHand;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerBoardController {

    /* Attributes */

    //Information

    private Client client;

    private String username;

    private Character character;

    private boolean isFirst = false;

    private boolean frenzy = false;

    private boolean beforeFirst = false;

    private boolean isFlipped = false;

    private boolean areActionsAvailable = false;

    private boolean canIShoot = false;

    private PlayerBoardMessage oldPlayerBoardMessage;


    //Graphic

    @FXML
    private ImageView playerBoardImage;

    @FXML
    private ImageView actionTileImage;

    @FXML
    private ImageView myFirstPlayerMarker;

    @FXML
    private Group mySkullsGroup;

    @FXML
    private ImageView mySkull0;

    @FXML
    private ImageView mySkull1;

    @FXML
    private ImageView mySkull2;

    @FXML
    private ImageView mySkull3;

    @FXML
    private ImageView mySkull4;

    @FXML
    private ImageView mySkull5;

    private List<ImageView> skullList = new ArrayList<>();

    @FXML
    private ImageView myDamageToken0;

    @FXML
    private ImageView myDamageToken1;

    @FXML
    private ImageView myDamageToken2;

    @FXML
    private ImageView myDamageToken3;

    @FXML
    private ImageView myDamageToken4;

    @FXML
    private ImageView myDamageToken5;

    @FXML
    private ImageView myDamageToken6;

    @FXML
    private ImageView myDamageToken7;

    @FXML
    private ImageView myDamageToken8;

    @FXML
    private ImageView myDamageToken9;

    @FXML
    private ImageView myDamageToken10;

    @FXML
    private ImageView myDamageToken11;

    private List<ImageView> damageSequence = new ArrayList<>();

    @FXML
    private ImageView myMark0;

    @FXML
    private ImageView myMark1;

    @FXML
    private ImageView myMark2;

    @FXML
    private ImageView myMark3;

    @FXML
    private ImageView myMark4;

    @FXML
    private ImageView myMark5;

    @FXML
    private ImageView myMark6;

    @FXML
    private ImageView myMark7;

    @FXML
    private ImageView myMark8;

    @FXML
    private ImageView myMark9;

    @FXML
    private ImageView myMark10;

    @FXML
    private ImageView myMark11;

    private List<ImageView> markSequence = new ArrayList<>();

    @FXML
    private Label myUsername;

    @FXML
    private Pane moveAvailable;

    @FXML
    private Pane grabAvailable;

    @FXML
    private Pane shootAvailable;

    @FXML
    private Pane reloadAvailable;

    @FXML
    private Pane shootBeforeFirstAvailable;

    @FXML
    private Pane moveBeforeFirstAvailable;

    @FXML
    private Pane grabBeforeFirstAvailable;

    @FXML
    private Pane shootAfterFirstAvailable;

    @FXML
    private Pane grabAfterFirstAvailable;

    @FXML
    private Rectangle myAmmoBlue0;

    @FXML
    private Rectangle myAmmoBlue1;

    @FXML
    private Rectangle myAmmoBlue2;

    private List<Rectangle> blueAmmo = new ArrayList<>();

    @FXML
    private Rectangle myAmmoRed0;

    @FXML
    private Rectangle myAmmoRed1;

    @FXML
    private Rectangle myAmmoRed2;

    private List<Rectangle> redAmmo = new ArrayList<>();

    @FXML
    private Rectangle myAmmoYellow0;

    @FXML
    private Rectangle myAmmoYellow1;

    @FXML
    private Rectangle myAmmoYellow2;

    private List<Rectangle> yellowAmmo = new ArrayList<>();

    @FXML
    private Group ammoGroup;

    private Stage playerHandStage;

    private OtherPlayerHandController otherPlayerHandController;

    private static Logger logger = Logger.getLogger("PlayerBoardController");

    /* Methods */

    public void initialize(){
        initializeMySkulls();
        initializeDamageSequence();
        initializeMarkSequence();
        initializeAmmo();
        initializeActions();
    }

    public void configurePlayerBoard(String username, Character character, boolean isFirst){
        this.username = username;
        initializeUsername();

        this.character = character;
        initializePlayerBoardImage();

        this.isFirst = isFirst;
        initializeIsFirst();

        if (client == null){
            initializePlayerHand(username);
        }
    }

    public void configureMyPlayerBoard(Client client, Character character, boolean isFirst){
        this.client = client;
        configurePlayerBoard(client.getUsername(), character, isFirst);
    }

    public void updateAmmo(int blue, int red, int yellow){

        int i = 0;

        for (; i<blue; i++){
            blueAmmo.get(i).setVisible(true);
        }
        for(; i<3; i++)
            blueAmmo.get(i).setVisible(false);

        i=0;

        for (; i<red; i++){
            redAmmo.get(i).setVisible(true);
        }
        for(; i<3; i++)
            redAmmo.get(i).setVisible(false);

        i=0;

        for (; i<yellow; i++){
            yellowAmmo.get(i).setVisible(true);
        }
        for(; i<3; i++)
            yellowAmmo.get(i).setVisible(false);

    }

    public void initializeMySkulls(){
        skullList.add(mySkull0);
        skullList.add(mySkull1);
        skullList.add(mySkull2);
        skullList.add(mySkull3);
        skullList.add(mySkull4);
        skullList.add(mySkull5);

        for (ImageView skull : skullList){
            skull.setVisible(false);
        }
    }

    public void initializeActions(){
        moveAvailable.setUserData(TypeOfAction.MOVE);
        grabAvailable.setUserData(TypeOfAction.GRAB);
        shootAvailable.setUserData(TypeOfAction.SHOOT);
        reloadAvailable.setUserData(TypeOfAction.RELOAD);
        shootBeforeFirstAvailable.setUserData(TypeOfAction.SHOOT);
        moveBeforeFirstAvailable.setUserData(TypeOfAction.MOVE);
        grabBeforeFirstAvailable.setUserData(TypeOfAction.GRAB);
        shootAfterFirstAvailable.setUserData(TypeOfAction.SHOOT);
        grabAfterFirstAvailable.setUserData(TypeOfAction.GRAB);
    }


    public static void changeTokenColor(ImageView token, Character owner){
        String url = "";

        switch (owner){
            case BANSHEE:
                url = "/images/tokens/BlueToken.png";
                break;
            case DISTRUCTOR:
                url = "/images/tokens/YellowToken.png";
                break;
            case DOZER:
                url = "/images/tokens/GreyToken.png";
                break;
            case SPROG:
                url = "/images/tokens/GreenToken.png";
                break;
            case VIOLET:
                url = "/images/tokens/VioletToken.png";
                break;
        }

        token.setImage(new Image(url));

    }

    public void initializePlayerBoardImage(){
        String url = "";

        switch (character){
            case BANSHEE:
                url = "/images/PlayerBoardBansheeFront.png";
                break;
            case DISTRUCTOR:
                url = "/images/PlayerBoardDistructorFront.png";
                break;
            case DOZER:
                url = "/images/PlayerBoardDozerFront.png";
                break;
            case SPROG:
                url = "/images/PlayerBoardSprogFront.png";
                break;
            case VIOLET:
                url = "/images/PlayerBoardVioletFront.png";
                break;
        }

        playerBoardImage.setImage(new Image(url));
    }

    public void initializeDamageSequence(){
        damageSequence.add(myDamageToken0);
        damageSequence.add(myDamageToken1);
        damageSequence.add(myDamageToken2);
        damageSequence.add(myDamageToken3);
        damageSequence.add(myDamageToken4);
        damageSequence.add(myDamageToken5);
        damageSequence.add(myDamageToken6);
        damageSequence.add(myDamageToken7);
        damageSequence.add(myDamageToken8);
        damageSequence.add(myDamageToken9);
        damageSequence.add(myDamageToken10);
        damageSequence.add(myDamageToken11);

        for (ImageView imageView : damageSequence) {
            imageView.setVisible(false);
        }
    }

    public void initializeMarkSequence(){
        markSequence.add(myMark0);
        markSequence.add(myMark1);
        markSequence.add(myMark2);
        markSequence.add(myMark3);
        markSequence.add(myMark4);
        markSequence.add(myMark5);
        markSequence.add(myMark6);
        markSequence.add(myMark7);
        markSequence.add(myMark8);
        markSequence.add(myMark9);
        markSequence.add(myMark10);
        markSequence.add(myMark11);

        for (ImageView imageView : markSequence){
            imageView.setVisible(false);
        }
    }

    public void initializeAmmo(){
        redAmmo.add(myAmmoRed0);
        redAmmo.add(myAmmoRed1);
        redAmmo.add(myAmmoRed2);

        blueAmmo.add(myAmmoBlue0);
        blueAmmo.add(myAmmoBlue1);
        blueAmmo.add(myAmmoBlue2);

        yellowAmmo.add(myAmmoYellow0);
        yellowAmmo.add(myAmmoYellow1);
        yellowAmmo.add(myAmmoYellow2);

        updateAmmo(1,1,1);
    }

    public void initializeUsername(){
        myUsername.setText(username);
    }

    public void initializeIsFirst(){
        if (!isFirst){
            myFirstPlayerMarker.setVisible(false);
        }
    }

    public void setFrenzyMode(boolean beforeFirst){
        this.frenzy = true;
        this.beforeFirst=beforeFirst;
        showActionTile();
    }

    public void initializePlayerHand(String username){

        System.out.print("\n");
        System.out.print("Inside initializePlayerHand\n");
        System.out.print("\n");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/OtherPlayerHandScreen.fxml"));

        Parent root;
        Scene scene;

        try {
            root = fxmlLoader.load();
            scene = new Scene(root);

        }
        catch (LoadException e) {
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getStackTrace().toString());
            scene = new Scene(new Label("ERROR"));
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "OtherPlayerHandScreen.fxml file not found in PlayerBoardController");
            scene = new Scene(new Label("ERROR"));
        }

        otherPlayerHandController = fxmlLoader.getController();
        otherPlayerHandController.configure(username);
        System.out.print("\n");
        System.out.print("\nPlayerHandController: ");
        System.out.print(otherPlayerHandController.toString());
        System.out.print("\n");

        playerHandStage = new Stage();
        playerHandStage.initOwner(playerBoardImage.getScene().getWindow());

        playerHandStage.setScene(scene);

        playerHandStage.setTitle("Player hand");
        playerHandStage.setResizable(false);
    }

    public void setAsCurrentPlayer(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);
        dropShadow.setHeight(18.0);
        dropShadow.setWidth(18.0);
        dropShadow.setSpread(0.4);

        playerBoardImage.setEffect(dropShadow);
    }

    public void removeAsCurrentPlayer(){
        playerBoardImage.setEffect(null);
    }

    @FXML
    public void showPlayerPrivateHand(MouseEvent actionEvent){
        playerHandStage.show();

    }

    public void updatePlayerBoard(PlayerBoardMessage playerBoardMessage){
        if (oldPlayerBoardMessage == null || !oldPlayerBoardMessage.equals(playerBoardMessage)){
            //Updates the damage tokens and the marks
            updateSequence(damageSequence, playerBoardMessage.getDamageSequence());
            updateSequence(markSequence, playerBoardMessage.getMarkSequence());

            //Updates if is flipped
            if (playerBoardMessage.isFlipped()){
                updateFlipped();
            }

            if (!playerBoardMessage.isFlipped()){
                //Updates the number of deaths if is not flipped
                updateNumberOfDeaths(playerBoardMessage.getNumOfDeaths());
            }

            //Updates the ammo
            updateAmmo(playerBoardMessage.getBlueAmmo(), playerBoardMessage.getRedAmmo(), playerBoardMessage.getYellowAmmo());

            //Updates the oldPlayerBoardMessage
            oldPlayerBoardMessage = playerBoardMessage;
        }
    }

    public void updatePlayerHand(PlayerHand playerHand){
        if (client == null){
            otherPlayerHandController.updatePlayerHand(playerHand);
        }
    }

    public void updateSequence(List<ImageView> sequence, List<Character> characters){
        for (Character owner : characters){
            ImageView token = sequence.get(characters.indexOf(owner));
            changeTokenColor(token, owner);
            token.setVisible(true);
        }

        if (characters.isEmpty()){
            for(ImageView token : sequence){
                token.setVisible(false);
            }
        }
    }

    public void updateNumberOfDeaths(int numberOfDeaths){
        for (int i=0; i<numberOfDeaths; i++){
            skullList.get(i).setVisible(true);
        }
    }

    public void updateFlipped(){
        if (!isFlipped){
            String url = "";

            switch (character){
                case BANSHEE:
                    url = "@../images/PlayerBoardBansheeBack.png";
                    break;
                case DISTRUCTOR:
                    url = "@../images/PlayerBoardDistruttoreBack.png";
                    break;
                case DOZER:
                    url = "@../images/PlayerBoardDozerBack.png";
                    break;
                case SPROG:
                    url = "@../images/PlayerBoardSprogBack.png";
                    break;
                case VIOLET:
                    url = "@../images/PlayerBoardVioletBack.png";
                    break;
            }

            playerBoardImage.setImage(new Image(url));
            actionTileImage.setVisible(false);

            mySkullsGroup.setVisible(false);

            isFlipped=true;
        }
    }

    public void showActionTile(){

        if (!actionTileImage.isVisible()){
            String url = "";

            switch (character){
                case BANSHEE:
                    url = "@../images/actionTiles/BansheeActionTileFrenzy.png";
                    break;
                case DISTRUCTOR:
                    url = "@../images/actionTiles/DistructorActionTileFrenzy.png";
                    break;
                case DOZER:
                    url = "@../images/images/actionTiles/DozerActionTileFrenzy.png";
                    break;
                case SPROG:
                    url = "@../images/actionTiles/SprogActionTileFrenzy.png";
                    break;
                case VIOLET:
                    url = "@../images/actionTiles/VioletActionTileFrenzy.png";
                    break;
            }

            actionTileImage.setImage(new Image(url));
            actionTileImage.setVisible(true);
        }

        if (areActionsAvailable){
            showPossibleActions(canIShoot);
        }
    }

    public Character getCharacter() {
        return character;
    }


    //Interactions
    public void showPossibleActions(boolean canIShoot){
        areActionsAvailable = true;
        this.canIShoot = canIShoot;

        if (!frenzy){
            moveAvailable.setVisible(true);
            grabAvailable.setVisible(true);
            shootAvailable.setVisible(canIShoot);
            reloadAvailable.setVisible(true);
        }
        else {
            shootBeforeFirstAvailable.setVisible(beforeFirst);
            moveBeforeFirstAvailable.setVisible(beforeFirst);
            grabBeforeFirstAvailable.setVisible(beforeFirst);
            shootAfterFirstAvailable.setVisible(!beforeFirst);
            grabAfterFirstAvailable.setVisible(!beforeFirst);
        }
    }

    public void disableActions(){
        areActionsAvailable = false;

        moveAvailable.setVisible(false);
        grabAvailable.setVisible(false);
        shootAvailable.setVisible(false);
        reloadAvailable.setVisible(false);

        shootBeforeFirstAvailable.setVisible(false);
        moveBeforeFirstAvailable.setVisible(false);
        grabBeforeFirstAvailable.setVisible(false);
        shootAfterFirstAvailable.setVisible(false);
        grabAfterFirstAvailable.setVisible(false);
    }

    public void showReload(){
        reloadAvailable.setVisible(true);
    }

    @FXML
    public void showSelection(MouseEvent actionEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(21.0);
        dropShadow.setHeight(21.0);
        dropShadow.setSpread(0.0);

        Group group = (Group) actionEvent.getSource();

        for (Node node : group.getChildren()){
            node.setEffect(dropShadow);
        }
    }

    @FXML
    public void disableEffect(MouseEvent actionEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLACK);
        dropShadow.setWidth(21.0);
        dropShadow.setHeight(21.0);
        dropShadow.setSpread(0.0);


        Group group = (Group) actionEvent.getSource();

        for (Node node : group.getChildren()){
            node.setEffect(null);
        }
    }

    public Group getAmmoGroup(){
        return ammoGroup;
    }

    public PlayerBoardMessage getCurrentPlayerBoardMessage(){
        return oldPlayerBoardMessage;
    }


    //ACTIONS

    public void sendAskMessage(TypeOfAction typeOfAction){
        Message message = new Message(username);
        message.createAskMessage(typeOfAction);
        client.send(message);
    }

    @FXML
    public void handleAction(MouseEvent actionEvent){
        Pane pane = (Pane) actionEvent.getSource();
        TypeOfAction typeOfAction = (TypeOfAction) pane.getUserData();
        sendAskMessage(typeOfAction);
    }



}
