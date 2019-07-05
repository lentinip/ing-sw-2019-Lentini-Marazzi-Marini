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

/**
 * Controller for the SelectAmmoColorScreen.
 *
 * @author lentinip
 */
public class SelectAmmoColorController {

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

    /**
     * Initializes the data in the buttons
     */
    public void initialize(){
        blueButton.setUserData(Colors.BLUE);
        redButton.setUserData(Colors.RED);
        yellowButton.setUserData(Colors.YELLOW);
    }

    /**
     * Method that needs to be called after the controller is instantiated.
     *
     * Manages everything to show.
     *
     * @param client reference to the Client instance
     * @param playerBoardMessage playerBoardMessage of the player of this client
     * @param ammoGroup ammoGroup of the player of this client
     */
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

    /**
     * Handles the button pressed.
     *
     * Sends a message with the choice.
     *
     * @param actionEvent actionEvent caught
     */
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
