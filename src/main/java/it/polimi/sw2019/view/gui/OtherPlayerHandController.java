package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.messages.PlayerHand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the OtherPlayerHandScreen
 *
 * @author lentinip
 */
public class OtherPlayerHandController {

    /* Attributes */

    private CardController cardController = new CardController();

    private PlayerHand oldPlayerHandMessage;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label noCardsLabel;

    @FXML
    private ImageView weaponImage0;

    @FXML
    private ImageView weaponImage1;

    @FXML
    private ImageView weaponImage2;

    private List<ImageView> weapons = new ArrayList<>();

    @FXML
    private ImageView powerupImage0;

    @FXML
    private ImageView powerupImage1;

    @FXML
    private ImageView powerupImage2;

    private List<ImageView> powerups = new ArrayList<>();

    /* Methods */

    /**
     * Initializes the structures with the imageViews
     */
    public void initialize(){
        weapons.add(weaponImage0);
        weapons.add(weaponImage1);
        weapons.add(weaponImage2);

        powerups.add(powerupImage0);
        powerups.add(powerupImage1);
        powerups.add(powerupImage2);
    }

    /**
     * Method that needs to be called after the controller is instantiated.
     *
     * @param username String with the username of the player associated with the playerHands received by this controller.
     */
    public void configure(String username){
        usernameLabel.setText(username);
    }

    /**
     * Updates the OtherPlayerHandScreen associated with this controller
     * @param playerHand playerHand to show
     */
    public void updatePlayerHand(PlayerHand playerHand){
        if (oldPlayerHandMessage == null || !oldPlayerHandMessage.equals(playerHand)){

            //Shows a label if the player has no cards
            if (playerHand.getWeaponsHidden()==0 && playerHand.getPowerups()==0 && playerHand.getWeaponsUnloaded().isEmpty()){
                noCardsLabel.setVisible(true);
            }

            else {
                noCardsLabel.setVisible(false);
            }

            //MANAGES THE WEAPONS

            int i=0;

            //Shows the unavailable
            for (String weaponName : playerHand.getWeaponsUnloaded()){
                if (i<weapons.size()){

                    Image weaponImage = cardController.getWeaponImage(weaponName);
                    ImageView weapon = weapons.get(i);

                    weapon.setImage(weaponImage);
                    CardController.setUnavailable(weapon, true);
                    weapon.setVisible(true);

                    i++;
                }
            }

            //Shows the one the loaded ones cover
            for (int j=0; j<playerHand.getWeaponsHidden() && i<weapons.size(); j++){
                ImageView weapon = weapons.get(i);
                weapon.setImage(cardController.getWeaponImage("weaponsBack.png"));
                CardController.setUnavailable(weapon, false);
                weapon.setVisible(true);
                i++;
            }

            //Hides the other imageViews
            for (; i<weapons.size(); i++){
                weapons.get(i).setVisible(false);
            }

            //MANAGES THE POWERUPS

            //Shows the cover of the ones that the player has
            int p = 0;
            for (int k = 0; k<playerHand.getPowerups() && p<powerups.size(); k++){
                powerups.get(p).setVisible(true);
                p++;
            }

            //Hides the other ones ImageView

            for (; p<powerups.size(); p++){
                powerups.get(p).setVisible(false);
            }

            oldPlayerHandMessage = playerHand;
        }
    }
}
