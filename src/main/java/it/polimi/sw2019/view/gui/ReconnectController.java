package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.messages.TypeOfMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ReconnectController {

    /* Attributes */

    private Client client;



    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void handleExit(ActionEvent actionEvent){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleReconnect(ActionEvent actionEvent){
        Message reconnectionMessage = new Message(client.getUsername());
        reconnectionMessage.setTypeOfMessage(TypeOfMessage.RECONNECTION_REQUEST);
        client.send(reconnectionMessage);
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
