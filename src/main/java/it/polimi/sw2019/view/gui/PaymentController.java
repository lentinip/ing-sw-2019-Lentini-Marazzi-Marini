package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.commons.messages.IndexMessage;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.PaymentMessage;
import it.polimi.sw2019.commons.messages.PlayerBoardMessage;
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

    public void initialize(){
        cardImage0.setUserData(0);
        cardImage1.setUserData(1);
        cardImage2.setUserData(2);

        cards.add(cardImage0);
        cards.add(cardImage1);
        cards.add(cardImage2);
    }

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

    public void configure(Client client, PaymentMessage paymentMessage, List<Image> images, PlayerBoardMessage playerBoardMessage, Group ammoGroup, boolean anyColor){
        this.anyColor = anyColor;
        this.ammoGroup = ammoGroup;
        this.playerBoardMessage = playerBoardMessage;

        configure(client, paymentMessage, images);
    }

    @FXML
    public void showSelection(MouseEvent actionEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(40.0);
        dropShadow.setHeight(40.0);
        dropShadow.setSpread(0.6);

        ImageView imageView = (ImageView) actionEvent.getSource();

        imageView.setEffect(dropShadow);
    }

    @FXML
    public void disableEffect(MouseEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        imageView.setEffect(null);
    }

    @FXML
    public void handleSelection(MouseEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        int selection = (int) imageView.getUserData();

        sendPayMessage(selection);
        closeWindow();
    }

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

                SelecAmmoColorController selecAmmoColorController = fxmlLoader.getController();
                selecAmmoColorController.configure(client, playerBoardMessage, ammoGroup);

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

    public void sendPayMessage(int selection){
        Message message = new Message(client.getUsername());
        message.createPaymentSelection(selection);
        client.send(message);
    }

    public void closeWindow(){
        Stage stage = (Stage) payWithAmmoButton.getScene().getWindow();
        stage.close();
    }

}
