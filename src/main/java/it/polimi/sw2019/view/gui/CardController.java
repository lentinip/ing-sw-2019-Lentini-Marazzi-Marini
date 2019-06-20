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

    private Map<String, Integer> weaponsTypes = new HashMap<>();

    private static Logger logger = Logger.getLogger("CardController");

    /* Methods */

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

    public Image getPowerupImage(String name, Colors color){
        String fullname = name + " " + color.toString();
        String path = powerups.get(fullname);
        return new Image(path);
    }

    public int getWeaponType(String name){
        return weaponsTypes.get(name);
    }

    public static void setUnavailable(ImageView card, boolean unloaded) {
        if (unloaded) {
            card.setOpacity(0.4);
        } else {
            card.setOpacity(1.0);
        }
    }

}
