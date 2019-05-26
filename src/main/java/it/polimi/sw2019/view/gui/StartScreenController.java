package it.polimi.sw2019.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
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
        this.username = usernameTextField.getText();
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
    }

}
