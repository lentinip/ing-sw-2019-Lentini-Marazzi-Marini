package it.polimi.sw2019.view.gui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.sw2019.model.Colors;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that manages the cards JSONs and Images for the client
 *
 * @author lentinip
 */
public class CardController {

    public CardController(){
        Gson json = new Gson();
        try {
            JsonReader jsonReaderWeapons = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/WeaponsAssetsDictionary.json")));
            weapons = json.fromJson(jsonReaderWeapons, Map.class);
            JsonReader jsonReaderPowerups = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/PowerupsAssetsDictionary.json")));
            powerups = json.fromJson(jsonReaderPowerups, Map.class);
            JsonReader jsonReaderWeaponsTypes= new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/WeaponsTypesDictionary.json")));
            weaponsTypes = json.fromJson(jsonReaderWeaponsTypes, Map.class);
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Json files not found in class CardController");
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    /* Attributes */

    private Map<String, String> weapons = new HashMap<>();

    private Map<String, String> powerups = new HashMap<>();

    private Map<String, Double> weaponsTypes = new HashMap<>();

    private static Logger logger = Logger.getLogger("CardController");

    /* Methods */

    /**
     * Method for getting a weapon Image
     * @param name weapon's name (as in the WeaponsAssetsDictionary.json)
     * @return the Image of the weapon with the specified name
     */
    public Image getWeaponImage(String name){
        String path = weapons.get(name);
        try{
            return new Image(path);
        }
        catch (NullPointerException e){
            logger.log(Level.SEVERE, "\nASSET MISSING: " + name + "\n");
            return new Image("/images/cards/weaponsBack.png");
        }
    }

    /**
     * Method for getting a powerup Image
     * @param name powerup's name (as in the PowerupsAssetsDictionary.json)
     * @param color color of the wanted powerup
     * @return the Image of the powerup with the specified name and color
     */
    public Image getPowerupImage(String name, Colors color){
        if (name.equals("powerupsBack.png")){
            return new Image(powerups.get(name));
        }
        String fullname = name + " " + color.toString();
        String path = powerups.get(fullname);
        return new Image(path);
    }

    /**
     * Method for getting the type of a weapon (needed for the SelectEffectController).
     * The type of a weapon defines how the player can select the effects.
     * @param name weapon's name as in the WeaponsTypesDictionary.json
     * @return Integer of the weapon's type
     */
    public Integer getWeaponType(String name){
        try {
            return new Integer(weaponsTypes.get(name).intValue());
        }
        catch (ClassCastException e){
            logger.log(Level.SEVERE, e.getMessage());
            logger.log(Level.SEVERE, weaponsTypes.toString());
            return 1;
        }

    }

    /**
     * Applies an effect to an imageView depending on the boolean unloaded
     * @param card imageView to which is going to be applied the effect
     * @param unloaded if true the opacity of the imageView is going to be 0.4, if false is going to be 1.0.
     */
    public static void setUnavailable(ImageView card, boolean unloaded) {
        if (unloaded) {
            card.setOpacity(0.4);
        } else {
            card.setOpacity(1.0);
        }
    }

}
