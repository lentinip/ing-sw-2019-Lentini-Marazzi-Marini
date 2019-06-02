package it.polimi.sw2019.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class StartScreenController {

    /* Attributes */

    @FXML
    private ComboBox<String> typeOfConnectionBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button startGameButton;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ToolBar toolBar;

    private String typeOfConnection;

    private String username;

    /* Methods */

    public String getTypeOfConnection() {
        return typeOfConnection;
    }

    @FXML
    public void setTypeOfConnection(ActionEvent actionEvent){
        this.typeOfConnection = typeOfConnectionBox.getSelectionModel().getSelectedItem();
        ableStartButton();
    }

    public String getUsername() {
        return username;
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

}

//TODO implement alert for wrong username
