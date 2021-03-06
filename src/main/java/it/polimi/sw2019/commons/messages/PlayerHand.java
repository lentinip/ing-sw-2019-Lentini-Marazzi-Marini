package it.polimi.sw2019.commons.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * @author poligenius
 * this class contains infos about a player hand
 */
public class PlayerHand {

    /**
     * Default Constructor
     */
    public PlayerHand(){}

    /* Attributes */

    private int weaponsHidden;
    private List<String> weaponsUnloaded = new ArrayList<>();
    private int powerups;

    /* Methods */

    public List<String> getWeaponsUnloaded() {
        return weaponsUnloaded;
    }

    public int getPowerups() {
        return powerups;
    }

    public int getWeaponsHidden() {
        return weaponsHidden;
    }

    public void setWeaponsUnloaded(List<String> weaponsUnloaded) {
        this.weaponsUnloaded = weaponsUnloaded;
    }

    public void setPowerups(int powerups) {
        this.powerups = powerups;
    }

    public void setWeaponsHidden(int weaponsHidden) {
        this.weaponsHidden = weaponsHidden;
    }
}

