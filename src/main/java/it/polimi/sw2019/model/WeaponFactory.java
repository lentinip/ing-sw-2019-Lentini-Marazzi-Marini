package it.polimi.sw2019.model;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

/**
 * @author poligenius
 * This class was written to create the weapon by reading a json File, it creates a weapon class and read the json
 * correspondent to the effects of the weapon and then creates the Effect classes and the real Weapon
 */
public class WeaponFactory {

    /**
     * Default constructor
     */
    public WeaponFactory() {}

    /* Attributes */

    private String  name;

    private String description;

    private Ammo grabCost;

    private Ammo reloadCost;

    private WeaponsType type;

    private String[] effects;

    private boolean hasAnOrder;

    private boolean hasAMoveTypeEffect;

    /* Methods */

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setGrabCost(Ammo grabCost) {
        this.grabCost = grabCost;
    }

    public Ammo getGrabCost() {
        return grabCost;
    }

    public void setReloadCost(Ammo reloadCost) {
        this.reloadCost = reloadCost;
    }

    public Ammo getReloadCost() {
        return reloadCost;
    }

    public void setType(WeaponsType type) {
        this.type = type;
    }

    public WeaponsType getType() {
        return type;
    }

    public void setEffects(String[] effects) {
        this.effects = effects;
    }

    public String[] getEffects() {
        return effects;
    }

    public void setHasAnOrder(boolean hasAnOrder) {
        this.hasAnOrder = hasAnOrder;
    }

    public boolean isHasAnOrder() {
        return hasAnOrder;
    }

    public void setHasAMoveTypeEffect(boolean hasAMoveTypeEffect) {
        this.hasAMoveTypeEffect = hasAMoveTypeEffect;
    }

    public boolean isHasAMoveTypeEffect() {
        return hasAMoveTypeEffect;
    }

    /**
     * Method that creates the Weapon Class and reads the json file to create also the effects class
     * @return weapon
     */
    public Weapon createWeapon(){

        Weapon weapon = new Weapon();

        /* copying factory fields into the weapon fields */
        weapon.setName(name);
        weapon.setDescription(description);
        weapon.setGrabCost(grabCost);
        weapon.setReloadCost(reloadCost);
        weapon.setIsLoaded(true);
        weapon.setOwner(null);
        weapon.setType(type);
        weapon.setHasAnOrder(hasAnOrder);
        weapon.setHasAMoveTypeEffect(hasAMoveTypeEffect);

        /* Creating the effects classes */
        for(String fileName: effects){

            Gson gson = new Gson();
            fileName = "/Weapons/" + fileName;
            JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
            Effect effect = gson.fromJson(jsonReader, Effect.class);
            weapon.addEffect(effect);
        }

        return weapon;
    }
}
