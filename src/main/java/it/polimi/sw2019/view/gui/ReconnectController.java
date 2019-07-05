package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the ReconnectScreen
 *
 * @author lentinip
 */
public class ReconnectController {

    /* Attributes */

    private Client client;

    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Handles the exit button.
     *
     * If pressed closes the program.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleExit(ActionEvent actionEvent){
        Platform.exit();
        System.exit(0);
    }

    /**
     * Handles the reconnect button.
     *
     * If pressed send a reconnection message and closes the window with the ReconnectScreen.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleReconnect(ActionEvent actionEvent){
        Message reconnectionMessage = new Message(client.getUsername());
        reconnectionMessage.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
        client.send(reconnectionMessage);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
