package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.commons.Colors;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.PlayerBoardMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelecAmmoColorController {

    /* Attributes */

    private Client client;

    @FXML
    private Group ammoGroup;

    @FXML
    private Button blueButton;

    @FXML
    private Button redButton;

    @FXML
    private Button yellowButton;



    /* Methods */

    public void initialize(){
        blueButton.setUserData(Colors.BLUE);
        redButton.setUserData(Colors.RED);
        yellowButton.setUserData(Colors.YELLOW);
    }

    public void configure(Client client, PlayerBoardMessage playerBoardMessage, Group ammoGroup){
        this.client = client;
        this.ammoGroup = ammoGroup;

        if (playerBoardMessage.getBlueAmmo()==0){
            blueButton.setDisable(true);
        }

        if (playerBoardMessage.getRedAmmo()==0){
            redButton.setDisable(true);
        }

        if (playerBoardMessage.getYellowAmmo()==0){
            yellowButton.setDisable(true);
        }
    }

    @FXML
    public void handleButton(ActionEvent actionEvent){
        Button button = (Button) actionEvent.getSource();
        Colors selectedColor = (Colors) button.getUserData();

        Message message = new Message(client.getUsername());
        message.createColorSelection(selectedColor);
        client.send(message);

        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

}
