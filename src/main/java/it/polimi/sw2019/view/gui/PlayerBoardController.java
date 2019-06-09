package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.PlayerBoardMessage;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoardController {

    /* Attributes */

    //Informations

    private String username;

    private Character character;

    private boolean isFirst;

    private PlayerBoardMessage oldPlayerBoardMessage;


    //Graphic

    @FXML
    private ImageView playerBoardImage;

    @FXML
    private ImageView myFirstPlayerMarker;

    @FXML
    private Group playerCards;

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

    /* Methods */

    public void initialize(){
        initializeMySkulls();
        initializeDamageSequence();
        initializeMarkSequence();
        initializeAmmo();
    }

    public void configurePlayerBoard(String username, Character character, boolean isFirst){
        this.username = username;
        initializeUsername();

        this.character = character;
        initializePlayerBoardImage();

        this.isFirst = isFirst;
        initializeIsFirst();
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


    public static void changeTokenColor(ImageView token, Character owner){
        String url = "";

        switch (owner){
            case BANSHEE:
                url = "@../images/tokens/BlueToken.png";
                break;
            case DISTRUCTOR:
                url = "@../images/tokens/YellowToken.png";
                break;
            case DOZER:
                url = "@../images/tokens/GreyToken.png";
                break;
            case SPROG:
                url = "@../images/tokens/GreenToken.png";
                break;
            case VIOLET:
                url = "@../images/tokens/VioletToken.png";
                break;
        }

        token.setImage(new Image(url));

    }

    public void initializePlayerBoardImage(){
        String url = "";

        switch (character){
            case BANSHEE:
                url = "@../images/PlayerBoardBansheeFront.png";
                break;
            case DISTRUCTOR:
                url = "@../images/PlayerBoardDistructorFront.png";
                break;
            case DOZER:
                url = "@../images/PlayerBoardDozerFront.png";
                break;
            case SPROG:
                url = "@../images/PlayerBoardSprogFront.png";
                break;
            case VIOLET:
                url = "@../images/PlayerBoardVioletFront.png";
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

    public void setAsCurrentPlayer(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLUE);
        dropShadow.setHeight(10.0);
        dropShadow.setWidth(10.0);

        playerBoardImage.setEffect(dropShadow);
    }

    public void removeAsCurrentPlayer(){
        playerBoardImage.setEffect(null);
    }

    public void showPlayerPrivateHand(){
        //Create new window
        //TODO implement

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

            //Updates the number of deaths
            updateNumberOfDeaths(playerBoardMessage.getNumOfDeaths());

            //Updates the ammo
            updateAmmo(playerBoardMessage.getBlueAmmo(), playerBoardMessage.getRedAmmo(), playerBoardMessage.getYellowAmmo());

            //Updates the oldPlayerBoardMessage
            oldPlayerBoardMessage = playerBoardMessage;
        }
    }

    public void updateSequence(List<ImageView> sequence, List<Character> characters){
        for (Character character : characters){
            ImageView token = sequence.get(characters.indexOf(character));
            changeTokenColor(token, character);
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

                //TODO implement the changing of the buttons for the frenzyMode in myPlayerboard
        }

        playerBoardImage.setImage(new Image(url));
    }

    public Character getCharacter() {
        return character;
    }


    //Interactions
    public void showPossibleActions(boolean canIShoot){
        moveAvailable.setVisible(true);
        grabAvailable.setVisible(true);
        if (canIShoot){
            shootAvailable.setVisible(true);
        }
        else {
            shootAvailable.setVisible(false);
        }
        reloadAvailable.setVisible(true);
    }

    public void disableActions(){
        moveAvailable.setVisible(false);
        grabAvailable.setVisible(false);
        shootAvailable.setVisible(false);
        reloadAvailable.setVisible(false);
    }
}
