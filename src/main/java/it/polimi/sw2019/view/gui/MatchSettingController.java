package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.MatchSetup;
import it.polimi.sw2019.network.messages.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * Controller for the MatchSettingScreen
 *
 * @author lentinip
 */
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

    /**
     * Sets the number of the players in the match in the label labelNumberOfPlayers
     * @param numberOfPlayers Integer with the number of the players in the match
     */
    public void setNumberOfPlayers(Integer numberOfPlayers) {

        //Setting the number of players in the label
        labelNumberOfPlayers.setText(numberOfPlayers.toString());
    }

    /**
     * Initializes the toggles data
     */
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

    /**
     * Handles the frenzy mode CheckBox selection
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleFrenzyModeCheckBox(ActionEvent actionEvent){
        frenzy = checkBoxFrenzyMode.isSelected();
    }

    /**
     * Handles the easy mode CheckBox selection
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleEasyModeCheckBox(ActionEvent actionEvent){
        easyMode = checkBoxEasyMode.isSelected();
    }

    /**
     * Handles the set match button.
     *
     * Sends the message with the selection and than closes the stage with the MatchSettingScreen
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleSetMatch(ActionEvent actionEvent){

        MatchSetup matchSetup = new MatchSetup(frenzy, easyMode, selectedBoard);

        Message message = new Message(client.getUsername());
        message.createMessageMatchSetup(matchSetup);
        client.send(message);

        Stage stage = (Stage) labelNumberOfPlayers.getScene().getWindow();
        stage.close();
    }
}
