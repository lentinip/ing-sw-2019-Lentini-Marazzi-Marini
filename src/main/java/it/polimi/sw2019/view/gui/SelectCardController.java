package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the SelectCardScreen and the SelectCardScreenForSpawn.
 *
 * @author lentinip
 */
public class SelectCardController {

    /* Attributes */

    private Client client;

    private BoardController boardController;

    @FXML
    private ImageView cardImage0;

    @FXML
    private ImageView cardImage1;

    @FXML
    private ImageView cardImage2;

    @FXML
    private ImageView cardImage3;

    @FXML
    private Button closeButton;

    @FXML
    private Label mainLabel;

    private Stage weaponsManualStage;

    private List<ImageView> cards = new ArrayList<>();

    private TypeOfAction currentTypeOfAction;

    private List<ImageView> myWeapons;

    private Integer firstSelection;

    private BoardCoord lastSelectedCell;

    private static Logger logger = Logger.getLogger("SelectCardController");

    /* Methods */

    /**
     * Initialize the structures for the ImageViews
     */
    public void initialize(){
        cardImage0.setUserData(new Integer(0));
        cardImage1.setUserData(new Integer(1));
        cardImage2.setUserData(new Integer(2));
        cardImage3.setUserData(new Integer(3));

        cards.add(cardImage0);
        cards.add(cardImage1);
        cards.add(cardImage2);
        cards.add(cardImage3);
    }

    /**
     * Method that needs to be called after the controller is instantiated (you can choose between two different configure methods).
     *
     * Manages everything to show
     *
     * @param client reference to the Client instance
     * @param boardController reference to the boardController instance
     * @param availableCards availableCards message
     * @param typeOfAction typeOfAction in which the player is involved
     * @param images List of images to show
     * @param lastSelectedCell BoardCoord of the last selected cell in the screen (needed if the typeOfAction is a GRAB)
     * @param noOption false if the player must choose
     */
    public void configure(Client client, BoardController boardController,  AvailableCards availableCards, TypeOfAction typeOfAction, List<Image> images, BoardCoord lastSelectedCell, boolean noOption){
        this.client = client;
        this.boardController = boardController;
        currentTypeOfAction = typeOfAction;
        this.lastSelectedCell = lastSelectedCell;


        if (!availableCards.areWeapons() && typeOfAction != TypeOfAction.SPAWN){
            mainLabel.setText("CHOOSE A POWERUP");
        }

        List<IndexMessage> indexMessages = availableCards.getAvailableCards();

        showCards(indexMessages, images, false);

        closeButton.setVisible(noOption);
    }

    /**
     * Method that needs to be called after the controller is instantiated (you can choose between two different configure methods).
     * This method has to be called if the player has already 3 weapons and the type of message is a GRAB.
     *
     * Manages everything to show and the discard of a weapon.
     *
     * @param client reference to the Client instance
     * @param boardController reference to the boardController instance
     * @param availableCards availableCards message
     * @param images List of images to show
     * @param lastSelectedCell BoardCoord of the last selected cell in the screen (needed if the typeOfAction is a GRAB)
     * @param myWeapons List of the ImageViews of the weapons of the player of the client
     */
    public void configure(Client client, BoardController boardController, AvailableCards availableCards, List<Image> images, BoardCoord lastSelectedCell, List<ImageView> myWeapons){
        this.myWeapons = myWeapons;
        configure(client, boardController, availableCards, TypeOfAction.GRAB, images, lastSelectedCell, true);
    }

    /**
     * Method that shows the cards and sets the opacity to 1.0 if the card is in the indexMessages
     * @param indexMessages list of indexMessages from the availableCards message
     * @param images list of images to show
     * @param disable true if the showing cars are not selectable
     */
    public void showCards(List<IndexMessage> indexMessages, List<Image> images, boolean disable){
        for (int i = 0; i<cards.size(); i++){
            if (i<images.size()){
                cards.get(i).setImage(images.get(i));
                cards.get(i).setVisible(true);
            }
            else {
                cards.get(i).setVisible(false);
            }
        }

        for (IndexMessage indexMessage : indexMessages){
            ImageView card = cards.get(indexMessage.getSelectionIndex());
            CardController.setUnavailable(card, false);
            card.setDisable(disable);
        }
    }

    /**
     * Sets an effect to the ImageView when a specific mouseEvent is caught (onMouseEntered)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void showSelection(MouseEvent mouseEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(40.0);
        dropShadow.setHeight(40.0);
        dropShadow.setSpread(0.6);

        ImageView imageView = (ImageView) mouseEvent.getSource();

        imageView.setEffect(dropShadow);
    }

    /**
     * Disables the effect to the ImageView when a specific mouseEvent is caught (onMouseExited)
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void disableEffect(MouseEvent mouseEvent){
        ImageView imageView = (ImageView) mouseEvent.getSource();
        imageView.setEffect(null);
    }

    /**
     * Handles the selection of a card (ImageView with onMouseClicked listener)
     *
     * Manages if the player has 3 cards and has to discard one
     *
     * @param mouseEvent mouseEvent caught
     */
    @FXML
    public void handleSelection(MouseEvent mouseEvent){
        ImageView imageView = (ImageView) mouseEvent.getSource();
        Integer integer = (Integer) imageView.getUserData();

        if (currentTypeOfAction == TypeOfAction.GRAB){
            if (myWeapons != null && firstSelection==null){
                firstSelection = integer;

                List<Image> weapons = new ArrayList<>();
                List<IndexMessage> areLoaded = new ArrayList<>();

                for (ImageView weapon : myWeapons){
                    weapons.add(weapon.getImage());
                    areLoaded.add(new IndexMessage(myWeapons.indexOf(weapon)));
                }

                showCards(areLoaded, weapons, false);
                mainLabel.setText("CHOOSE A WEAPON TO DISCARD");
            }

            else{
                Message message = new Message(client.getUsername());
                if (firstSelection!=null){
                    message.createSingleActionGrabWeapon(new GrabWeapon(firstSelection.intValue(), integer.intValue(), lastSelectedCell));
                }
                else {
                    message.createSingleActionGrabWeapon(new GrabWeapon(integer.intValue(), -1, lastSelectedCell));
                }

                client.send(message);
                closeWindow();
            }
        }
        else if (currentTypeOfAction == TypeOfAction.RELOAD){
            Message message = new Message((client.getUsername()));
            message.createSingleActionReload(((Integer) imageView.getUserData()).intValue());
            boardController.showOnlyReload();
            client.send(message);
            closeWindow();
        }
        else {
            Message message = new Message((client.getUsername()));
            message.createSelectedCard(((Integer) imageView.getUserData()).intValue(), currentTypeOfAction);

            client.send(message);
            closeWindow();
        }


    }

    /**
     * Handles the close button.
     *
     * If the currentTypeOfAction is a USEPOWERUP and the damageSession method returns true sends a message with index -1 and closes the window.
     *
     * Otherwise just closes the window.
     *
     * @param actionEvent actionEvent caught
     */
    @FXML
    public void handleCloseButton(ActionEvent actionEvent){

        if (currentTypeOfAction == TypeOfAction.USEPOWERUP){

            if (boardController.damageSession()){
                Message message = new Message(client.getUsername());
                message.createSelectionForUsePowerup(-1);
                client.send(message);
            }

            closeWindow();

        }
        else if (currentTypeOfAction == TypeOfAction.GRAB || currentTypeOfAction == TypeOfAction.RELOAD ){
            closeWindow();
        }
        else {
            logger.log(Level.SEVERE, "TypeOfAction not managed in the handleCloseButton method in SelectCardController");
        }
    }

    /**
     * Closes the window with the SelectCardControllerStage
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
