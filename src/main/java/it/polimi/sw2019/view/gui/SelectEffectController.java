package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.AvailableEffects;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the SelectEffectCardScreen
 *
 * @author lentinip
 */
public class SelectEffectController {

    /* Attributes */

    private Client client;

    private CardController cardController = new CardController();

    @FXML
    private Group optional1;

    @FXML
    private RadioButton radioButton0;

    @FXML
    private RadioButton radioButton1;

    private List<RadioButton> radioButtonsGroup0 = new ArrayList<>();

    @FXML
    private ToggleGroup toggleGroup0;

    @FXML
    private Group optional2;

    @FXML
    private RadioButton radioButton2;

    @FXML
    private RadioButton radioButton3;

    @FXML
    private RadioButton radioButton4;

    private List<RadioButton> radioButtonsGroup1 = new ArrayList<>();

    @FXML
    private ToggleGroup toggleGroup1;

    @FXML
    private Group selectable3;

    @FXML
    private CheckBox checkBox0;

    @FXML
    private CheckBox checkBox1;

    @FXML
    private CheckBox checkBox2;

    private List<CheckBox> checkBoxes = new ArrayList<>();

    private List<Control> controls = new ArrayList<>();

    private int choice = -1;

    private boolean fourthOption = false;

    @FXML
    Button closeButton;

    @FXML
    private ImageView weaponImageView;

    private Stage weaponsManualStage;

    private static Logger logger = Logger.getLogger("SelectEffectController");

    /* Methods */

    /**
     * Initializes the controls
     */
    public void initialize(){

        initializeGroup0();
        initializeGroup1();
        initializeGroup2();

        //Adds the listeners for the radiobuttons
        toggleGroup0.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (toggleGroup0.getSelectedToggle()!=null){
                    choice = (int) toggleGroup0.getSelectedToggle().getUserData();
                }
            }
        });

        toggleGroup1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (toggleGroup1.getSelectedToggle()!=null){
                    choice = (int) toggleGroup1.getSelectedToggle().getUserData();
                }
            }
        });
    }

    /**
     * Initialize the controls for the weapons with type 1
     */
    public void initializeGroup0(){
        radioButton0.setUserData(0);
        radioButton1.setUserData(1);

        radioButtonsGroup0.add(radioButton0);
        radioButtonsGroup0.add(radioButton1);
    }

    /**
     * Initialize the controls for the weapons with type 2
     */
    public void initializeGroup1(){
        radioButton2.setUserData(0);
        radioButton3.setUserData(1);
        radioButton4.setUserData(2);

        radioButtonsGroup1.add(radioButton2);
        radioButtonsGroup1.add(radioButton3);
        radioButtonsGroup1.add(radioButton4);
    }

    /**
     * Initialize the controls for the weapons with type 3
     */
    public void initializeGroup2(){
        checkBoxes.add(checkBox0);
        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
    }

    /**
     * Shows the controls specified in the parameter
     * @param controlsList list of the controls to show
     */
    public void showControls(List<Control> controlsList){
        for (Control control : controlsList){
            control.setVisible(true);
        }
    }

    /**
     * Method that needs to be called after the controller is instantiated.
     *
     * Manages everything to show.
     *
     * @param client reference to the Client instance
     * @param availableEffects availableEffect message
     * @param noOption false if the player must choose
     */
    public void configure(Client client, AvailableEffects availableEffects, boolean noOption){

        this.client = client;

        if(noOption){
            closeButton.setVisible(true);
        }

        //First sets the image
        Image image = cardController.getWeaponImage(availableEffects.getName());
        weaponImageView.setImage(image);

        //Gets the type of the image
        Integer type = cardController.getWeaponType(availableEffects.getName());

        //Shows the possible selections depending on the type of the weapon
        switch (type){
            case 1:
                optional1.setVisible(true);
                controls.addAll(radioButtonsGroup0);

                break;
            case 2:
                optional2.setVisible(true);
                controls.addAll(radioButtonsGroup1);
                break;
            case 3:
                selectable3.setVisible(true);
                controls.addAll(checkBoxes);
                break;
            default:
                logger.log(Level.SEVERE, "Type of weapon not defined in configure method in SelectEffectController");
                String string = "Type of weapon number = " + type + " ";
                logger.log(Level.SEVERE, string);
                break;
        }
        showControls(controls);

        //Ables the ones available
        ableControls(availableEffects.getIndexes());
    }

    /**
     * Ables the controls using the indexes in the indexMessages
     * @param indexMessages list of indexMessages
     */
    public void ableControls(List<IndexMessage> indexMessages){
        for (IndexMessage indexMessage : indexMessages){
            if (indexMessage.getSelectionIndex() == 3){
                fourthOption = true;
            }
            else {
                controls.get(indexMessage.getSelectionIndex()).setDisable(false);
            }
        }
    }

    /**
     * Handles the selection of the checkboxes
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleCheckBoxSelection(ActionEvent actionEvent){
        if (checkBox0.isSelected() && !checkBox1.isSelected() && !checkBox2.isSelected()){
            choice = 0;
        }

        else if (checkBox0.isSelected() && checkBox1.isSelected() && !checkBox2.isSelected()){
            choice = 1;
        }

        else if (checkBox0.isSelected() && !checkBox1.isSelected() && checkBox2.isSelected()){
            choice = 2;
        }

        else if (checkBox0.isSelected() && checkBox1.isSelected() && checkBox2.isSelected()){
            if (fourthOption){
                choice = 3;
            }
            else {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("You can't select all the three check boxes");
                a.setContentText("Please select another effect");
                a.show();
                checkBox0.setSelected(false);
                checkBox1.setSelected(false);
                checkBox2.setSelected(false);
            }
        }
        else {
            choice = -2;
        }
    }

    /**
     * Handles the send button
     *
     * Creates also alerts if the player didn't make a possible choice
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleSendButton(ActionEvent actionEvent){
        if (choice < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong selection");
            if (choice == -1){
                alert.setContentText("You didn't make a choice");
            }
            if (choice == -2){
                alert.setContentText("You have to select also the main effect");
            }
            alert.show();
        }
        else {
            sendIndex(choice);

            closeWindow();
        }

    }

    /**
     * Handles the close button.
     *
     * Sends the index -1.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleCloseButton(ActionEvent actionEvent){
        sendIndex(-1);
        closeWindow();
    }

    /**
     * Sends an index.
     *
     * @param index index to be sent
     */
    public void sendIndex(int index){
        Message message = new Message(client.getUsername());
        message.createSelectedEffect(index);
        client.send(message);
    }

    /**
     * Closes the window with the SelectEffectStage.
     */
    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setWeaponsManualStage(Stage weaponsManualStage){
        this.weaponsManualStage = weaponsManualStage;
    }

    /**
     * Handler for the WeaponsManual button.
     * If clicked shows the weaponsManualStage.
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleWeaponsManualButton(ActionEvent actionEvent){
        if (weaponsManualStage.isShowing()){
            weaponsManualStage.toFront();
        }
        else {
            weaponsManualStage.show();
        }
    }
}
