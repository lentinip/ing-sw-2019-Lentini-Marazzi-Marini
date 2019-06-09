package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.Client;
import it.polimi.sw2019.network.messages.AvailableCards;
import it.polimi.sw2019.network.messages.IndexMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SelectWeaponController {

    /* Attributes */

    private Client client;

    private CardController cardController = new CardController();

    @FXML
    private ImageView weaponImage0;

    @FXML
    private ImageView weaponImage1;

    @FXML
    private ImageView weaponImage2;

    private List<ImageView> weapons = new ArrayList<>();

    private TypeOfAction currentTypeOfAction;

    /* Methods */

    public void initialize(){
        weaponImage0.setUserData(new IndexMessage(0));
        weaponImage1.setUserData(new IndexMessage(1));
        weaponImage1.setUserData(new IndexMessage(2));

        weapons.add(weaponImage0);
        weapons.add(weaponImage1);
        weapons.add(weaponImage2);
    }

    public void configure(Client client, AvailableCards availableCards, TypeOfAction typeOfAction, List<Image> images){
        this.client = client;
        currentTypeOfAction = typeOfAction;

        List<IndexMessage> indexMessages = availableCards.getAvailableCards();

        for (ImageView weapon : weapons){
            weapon.setImage(images.get(weapons.indexOf(weapon)));
        }

        for (IndexMessage indexMessage : indexMessages){
            ImageView weapon = weapons.get(indexMessage.getSelectionIndex());
            cardController.setUnloaded(weapon, false);
        }

        //TODO check
    }

    @FXML
    public void showSelection(ActionEvent actionEvent){

        DropShadow dropShadow = new DropShadow();

        dropShadow.setColor(Color.BLUE);
        dropShadow.setWidth(40.0);
        dropShadow.setHeight(40.0);
        dropShadow.setSpread(0.6);

        ImageView imageView = (ImageView) actionEvent.getSource();

        imageView.setEffect(dropShadow);
    }

    @FXML
    public void disableEffect(ActionEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        imageView.setEffect(null);
    }

    @FXML
    public void handleSelection(ActionEvent actionEvent){
        ImageView imageView = (ImageView) actionEvent.getSource();
        IndexMessage indexMessage = (IndexMessage) imageView.getUserData();

        //TODO Implement send
    }


}
