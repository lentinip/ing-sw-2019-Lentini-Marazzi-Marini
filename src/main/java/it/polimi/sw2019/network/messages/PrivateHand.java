package it.polimi.sw2019.network.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is sent only to one client, contains its weapons and powerups that are secret to everyone else
 */
public class PrivateHand {

    /**
     * Default constructor
     */
    public PrivateHand(){}

    /* Attributes */

    private List<String> weaponsUnloaded = new ArrayList<>();

    private List<String> weaponsLoaded = new ArrayList<>();

    private List<String> powerups = new ArrayList<>();

    /* Methods */

    public List<String> getPowerups() {
        return powerups;
    }

    public void setPowerups(List<String> powerups) {
        this.powerups = powerups;
    }

    public List<String> getWeaponsLoaded() {
        return weaponsLoaded;
    }

    public void setWeaponsLoaded(List<String> weaponsLoaded) {
        this.weaponsLoaded = weaponsLoaded;
    }

    public List<String> getWeaponsUnloaded() {
        return weaponsUnloaded;
    }

    public void setWeaponsUnloaded(List<String> weaponsUnloaded) {
        this.weaponsUnloaded = weaponsUnloaded;
    }
}


