package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartScreenController {

    /* Attributes */

    private Client client;

    @FXML
    private ComboBox<String> typeOfConnectionBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button startGameButton;

    @FXML
    private Group pleaseWaitGroup;

    private String typeOfConnection;

    private String username;

    private static Logger logger = Logger.getLogger("StartScreenController");

    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public void setTypeOfConnection(ActionEvent actionEvent){
        this.typeOfConnection = typeOfConnectionBox.getSelectionModel().getSelectedItem();
        ableStartButton();
    }

    @FXML
    public void setUsername(ActionEvent actionEvent) {

        if (usernameTextField.getText().equals("all") || usernameTextField.getText().equals("")){
            username = null;
            String whatToShow = String.format("You can't use <%s> as username.",usernameTextField.getText());
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(whatToShow);
            a.show();
        }

        else {
            this.username = usernameTextField.getText();
        }

        ableStartButton();
    }

    public void initialize(){
        typeOfConnectionBox.getItems().removeAll(typeOfConnectionBox.getItems());

        List<String> typeOfConnections = new ArrayList<>();
        typeOfConnections.add("RMI");
        typeOfConnections.add("Socket");

        typeOfConnectionBox.getItems().addAll(typeOfConnections);
    }

    public void ableStartButton(){
        if (typeOfConnection!=null && username!=null){
            startGameButton.setDisable(false);
        }
        else {
            startGameButton.setDisable(true);
        }
    }

    @FXML
    public void handleStartGameButton(ActionEvent actionEvent){

        usernameTextField.setDisable(true);
        typeOfConnectionBox.setDisable(true);
        startGameButton.setDisable(true);

        pleaseWaitGroup.setVisible(true);
        boolean rmi = typeOfConnection.equals("RMI");

        //SEND
        Message message = new Message(username);
        message.createLoginMessage(username, rmi);
        try {
            client.connect(message);
        }
        catch (Exception e){
            hidePleaseWait();
            logger.log(Level.SEVERE, "Connection failed in StartScreenController");
        }
    }

    public void hidePleaseWait(){
        usernameTextField.setDisable(false);
        typeOfConnectionBox.setDisable(false);
        startGameButton.setDisable(false);

        pleaseWaitGroup.setVisible(false);
        username = null;
        ableStartButton();
    }

}