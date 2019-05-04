package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class was written to have more references of the powerup useful in Factory class
 */
public class PowerupFactory {

    /* Attributes */

    private int quantity;

    private Powerup powerupKind;

    /* Methods */

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Powerup getPowerup() {
        return powerupKind;
    }

    public void setPowerup(Powerup powerup) {
        this.powerupKind = powerup;
    }

    public List<Powerup> createPowerups(){

        List<Powerup> powerups = new ArrayList<>();

        for (int i = 0; i < quantity; i++){

            powerups.add(powerupKind);
        }

        return powerups;
    }
}
