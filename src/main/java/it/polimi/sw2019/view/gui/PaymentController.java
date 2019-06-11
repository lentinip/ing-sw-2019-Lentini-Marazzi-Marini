package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.PaymentMessage;
import it.polimi.sw2019.network.messages.PlayerBoardMessage;
import javafx.event.ActionEvent;
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

        for (ImageView weapon : cards){
            weapon.setImage(images.get(cards.indexOf(weapon)));
        }

        for (IndexMessage indexMessage : paymentMessage.getUsablePowerups()){
            ImageView card = cards.get(indexMessage.getSelectionIndex());
            CardController.setUnavailable(card, false);
            card.setDisable(false);
        }

        if (paymentMessage.isMustPay()){
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
    public void showSelection(ActionEvent actionEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(40.0);
        dropShadow.setHeight(40.0);
        dropShadow.setSpread(0.6);

        ImageView imageView = (ImageView) actionEvent.getSource();

        imageView.setEffect(dropShadow);
    }

    @FXML
    public void disableEffect(ActionEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        imageView.setEffect(null);
    }

    @FXML
    public void handleSelection(ActionEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        int selection = (int) imageView.getUserData();

        sendPayMessage(selection);
    }

    @FXML
    public void payWithAmmo(){

        if (anyColor){
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
            }

            SelecAmmoColorController selecAmmoColorController = fxmlLoader.getController();
            selecAmmoColorController.configure(client, playerBoardMessage, ammoGroup);

            oldStage.setScene(scene);
        }

        else {
            sendPayMessage(-1);
        }

        closeWindow();
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
