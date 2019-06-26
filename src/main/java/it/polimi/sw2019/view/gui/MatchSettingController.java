package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.MatchSetup;
import it.polimi.sw2019.network.messages.Message;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MatchSettingController {

    /* Attributes */

    private Client client;

    @FXML
    private CheckBox checkBoxFrenzyMode;

    @FXML
    private CheckBox checkBoxEasyMode;

    @FXML
    private Toggle boardAll;

    @FXML
    private Toggle board3players;

    @FXML
    private Toggle board4players;

    @FXML
    private Toggle generic;

    @FXML
    private ToggleGroup boardGroup;

    @FXML
    private Label labelNumberOfPlayers;

    private boolean frenzy = false;

    private boolean easyMode = false;

    private String selectedBoard = "Board1.json";

    /* Methods */

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {

        //Setting the number of players in the label
        labelNumberOfPlayers.setText(numberOfPlayers.toString());
    }

    public void initialize(){

        //Setting the toggles
        boardAll.setUserData("Board1.json");
        board3players.setUserData("Board4.json");
        board4players.setUserData("Board2.json");
        generic.setUserData("Board3.json");

        //listener for the changes of the toggles for the UI
        boardGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (boardGroup.getSelectedToggle()!=null){
                    selectedBoard = (String) boardGroup.getSelectedToggle().getUserData();
                }
            }
        });
    }

    @FXML
    public void handleFrenzyModeCheckBox(ActionEvent actionEvent){
        frenzy = checkBoxFrenzyMode.isSelected();
    }

    @FXML
    public void handleEasyModeCheckBox(ActionEvent actionEvent){
        easyMode = checkBoxEasyMode.isSelected();
    }

    @FXML
    public void handleSetMatch(ActionEvent actionEvent){

        System.out.print("\n");
        System.out.print("We're in handleSetMatch\n");
        System.out.print("1\n");
        System.out.print("\n");



        MatchSetup matchSetup = new MatchSetup(frenzy, easyMode, selectedBoard);

        System.out.print("\n");
        System.out.print("We're in handleSetMatch\n");
        System.out.print(client.getUsername());
        System.out.print("\n");

        Message message = new Message(client.getUsername());
        message.createMessageMatchSetup(matchSetup);
        client.send(message);

        Stage stage = (Stage) labelNumberOfPlayers.getScene().getWindow();
        stage.close();
    }
}
