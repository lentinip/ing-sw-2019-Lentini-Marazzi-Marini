package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.PaymentMessage;
import it.polimi.sw2019.network.messages.PlayerBoardMessage;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the PaymentControllerScreen
 *
 * @author lentinip
 */
public class PaymentController {

    /* Attributes */

    private Client client;

    @FXML
    private ImageView cardImage0;

    @FXML
    private ImageView cardImage1;

    @FXML
    private ImageView cardImage2;

    @FXML
    private Button payWithAmmoButton;

    private List<ImageView> cards = new ArrayList<>();

    private boolean anyColor = false;

    private Group ammoGroup;

    private PlayerBoardMessage playerBoardMessage;

    private static Logger logger = Logger.getLogger("PaymentController");

    private double xOffset = 0;

    private double yOffset = 0;

    /* Methods */

    /**
     * Initialize the structures with the imageViews
     */
    public void initialize(){
        cardImage0.setUserData(0);
        cardImage1.setUserData(1);
        cardImage2.setUserData(2);

        cards.add(cardImage0);
        cards.add(cardImage1);
        cards.add(cardImage2);
    }

    /**
     * Method that needs to be called after the controller is instantiated (you can choose between two different configure methods).
     *
     * Manages everything to show
     * @param client reference to the Client instance
     * @param paymentMessage paymentMessage to show
     * @param images List of Images to show
     */
    public void configure(Client client, PaymentMessage paymentMessage, List<Image> images){
        this.client = client;

        for (Image image : images) {
            ImageView card = cards.get(images.indexOf(image));
            card.setImage(image);
            card.setVisible(true);
            card.setDisable(true);
        }


        for (IndexMessage indexMessage : paymentMessage.getUsablePowerups()){
            ImageView card = cards.get(indexMessage.getSelectionIndex());
            CardController.setUnavailable(card, false);
            card.setDisable(false);
        }

        if (!paymentMessage.isMustPay()){
            payWithAmmoButton.setVisible(true);
        }
    }

    /**
     * Method that needs to be called after the controller is instantiated (you can choose between two different configure methods).
     *
     * This method has to be called if the payment involves a payment with the possibility to pay with an ammo of any color.
     *
     * @param client reference to the Client instance
     * @param paymentMessage paymentMessage to show
     * @param images List of Images to show
     * @param playerBoardMessage playerBoardMessage of the player
     * @param ammoGroup Group with the Nodes representing the ammo of the player
     * @param anyColor true if the player can pay with an ammo of any color, false if he can't
     */
    public void configure(Client client, PaymentMessage paymentMessage, List<Image> images, PlayerBoardMessage playerBoardMessage, Group ammoGroup, boolean anyColor){
        this.anyColor = anyColor;
        this.ammoGroup = ammoGroup;
        this.playerBoardMessage = playerBoardMessage;

        configure(client, paymentMessage, images);
    }

    /**
     * Sets an effect to the ImageView when a specific mouseEvent is caught (onMouseEntered)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void showSelection(MouseEvent mouseEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(40.0);
        dropShadow.setHeight(40.0);
        dropShadow.setSpread(0.6);

        ImageView imageView = (ImageView) mouseEvent.getSource();

        imageView.setEffect(dropShadow);
    }

    /**
     * Disables the effect to the ImageView when a specific mouseEvent is caught (onMouseExited)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void disableEffect(MouseEvent mouseEvent){
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setEffect(null);
    }

    /**
     * Handles the selection of a card (ImageView with onMouseClicked listener)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void handleSelection(MouseEvent mouseEvent){
        ImageView imageView = (ImageView) mouseEvent.getSource();
        int selection = (int) imageView.getUserData();

        sendPayMessage(selection);
        closeWindow();
    }

    /**
     * Handles the pay with ammo button.
     *
     * If anyColor is true changes the scene to a SelectAmmoColorScreen, if false sends a pay message with index -1.
     */
    @FXML
    public void payWithAmmo(){

        if (anyColor){
            Platform.runLater(()->{
                Stage oldStage = (Stage) payWithAmmoButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/FXMLFiles/SelectAmmoColorScreen.fxml"));

                Parent root;
                Scene scene;

                try {
                    root = fxmlLoader.load();
                    scene = new Scene(root);
                }
                catch (IOException e){
                    logger.log(Level.SEVERE, "SelectAmmoColorScreen.fxml file not found in PaymentController");
                    scene = new Scene(new Label("ERROR"));
                    root = null;
                }

                SelectAmmoColorController selectAmmoColorController = fxmlLoader.getController();
                selectAmmoColorController.configure(client, playerBoardMessage, ammoGroup);

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
                            oldStage.setX(event.getScreenX() - xOffset);
                            oldStage.setY(event.getScreenY() - yOffset);
                        }
                    });
                }
                catch (NullPointerException e){
                    logger.log(Level.SEVERE, "Problem with the listeners of the window: root may be null");
                }

                oldStage.setScene(scene);
            });
        }

        else {
            sendPayMessage(-1);
            closeWindow();
        }
    }

    /**
     * Sends a paymentSelection message
     * @param selection index of the selection
     */
    public void sendPayMessage(int selection){
        Message message = new Message(client.getUsername());
        message.createPaymentSelection(selection);
        client.send(message);
    }

    /**
     * Closes the stage with the PaymentScreen
     */
    public void closeWindow(){
        Stage stage = (Stage) payWithAmmoButton.getScene().getWindow();
        stage.close();
    }

}
