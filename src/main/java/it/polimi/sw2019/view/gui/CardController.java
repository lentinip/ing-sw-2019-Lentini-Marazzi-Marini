package it.polimi.sw2019.view.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardController {

    /* Attributes */

    /* Methods */

    public Image getWeaponImage(String name){
        //TODO implement
        return null;
    }

    public Image getPowerupImage(String name){
        //TODO implement
        return null;
    }

    public void setUnloaded(ImageView weapon, boolean unloaded) {
        if (unloaded) {
            weapon.setOpacity(0.4);
        } else {
            weapon.setOpacity(1.0);
        }
    }

}
