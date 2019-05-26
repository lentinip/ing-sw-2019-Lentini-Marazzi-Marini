package it.polimi.sw2019.view.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.awt.event.ActionEvent;


public class MatchSettingController {

    /* Attributes */

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
    private ToggleGroup boardGroup;


    private boolean frenzy;

    private boolean easyMode;

    private String selectedBoard;

    /* Methods */

    public boolean isFrenzy() {
        return frenzy;
    }

    public boolean isEasyMode() {
        return easyMode;
    }

    public String getSelectedBoard() {
        return selectedBoard;
    }

    public void initialize(){
        selectedBoard = "boardAll";

        boardAll.setUserData("boardAll");
        board3players.setUserData("board3players");
        board4players.setUserData("board4players");

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
        //TODO implement
    }
}
