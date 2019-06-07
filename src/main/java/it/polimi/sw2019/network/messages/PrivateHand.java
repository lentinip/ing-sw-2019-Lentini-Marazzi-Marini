package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is sent only to one client, contains its weapons and powerups that are secret to everyone else
 */
public class PrivateHand {

    /**
     * Default constructor
     */
    public PrivateHand(List<String> weaponsLoaded, List<String> weaponsUnloaded, List<String> powerups, List<String> allWeapons, List<Colors> powerupColors){
        setWeaponsLoaded(weaponsLoaded);
        setWeaponsUnloaded(weaponsUnloaded);
        setPowerups(powerups);
        setAllWeapons(allWeapons);
        setPowerupColors(powerupColors);
    }

    /* Attributes */

    private List<String> allWeapons = new ArrayList<>();

    private List<String> weaponsUnloaded = new ArrayList<>();

    private List<String> weaponsLoaded = new ArrayList<>();

    private List<String> powerups = new ArrayList<>();

    private List<Colors> powerupColors = new ArrayList<>();

    /* Methods */

    public void setPowerupColors(List<Colors> powerupColors) {
        this.powerupColors = powerupColors;
    }

    public List<Colors> getPowerupColors() {
        return powerupColors;
    }

    public List<String> getAllWeapons() {
        return allWeapons;
    }

    public void setAllWeapons(List<String> allWeapons) {
        this.allWeapons = allWeapons;
    }

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


