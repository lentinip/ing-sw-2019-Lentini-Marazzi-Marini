package it.polimi.sw2019.model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class was written to call its methods to create the board, the powerup, weapon and ammoTile decks; by reading JSON files
 */
public class Factory {


    /**
     * Default Constructor
     */
    public Factory(){}

    /* Attributes */

    private Visibility visibilityClass; /* this attribute is given to every Weapon created */

    /* Methods */

    public void setVisibilityClass(Visibility visibilityClass) {
        this.visibilityClass = visibilityClass;
    }

    public Visibility getVisibilityClass() {
        return visibilityClass;
    }

    /**
     * This method creates the weapons deck by reading every weapon json file using the weaponsDictionary file
     * @return the ArrayList associated to the weapons deck
     * @throws FileNotFoundException
     */
    public List<Weapon> createWeaponDeck() throws FileNotFoundException {

        List<Weapon> weaponDeck = new ArrayList<>();

        Gson gson = new Gson();
        File jsonFile = Paths.get("/src/main/resources/WeaponsDictionary").toFile();

        String[] fileNames = gson.fromJson(new FileReader(jsonFile), String[].class);

        for (String fileName : fileNames) {

            weaponDeck.add(createWeapon(fileName));
        }


        return weaponDeck;
    }

    /**
     * this method creates the weapon class by reading the correspondent json file
     * @param fileName
     * @return
     */
    public Weapon createWeapon(String fileName) throws FileNotFoundException{

        Weapon weapon;

        Gson gson = new Gson();
        File jsonFile = Paths.get(fileName).toFile();

        WeaponFactory weaponFactory = gson.fromJson(new FileReader(jsonFile), WeaponFactory.class);

        weapon = weaponFactory.createWeapon();

        for(Effect effect: weapon.getEffects()){

            effect.setVisibilityClass(visibilityClass);
        }

        return weapon;
    }

    /**
     * this method read the name of every file json correspondent to a powerup from the "powerupsDictionary" and calls the method that creates the arrayList of every copy of that powerup
     * @return the ArrayList associated to the powerups deck.
     * @throws FileNotFoundException
     */
    public List<Powerup> createPowerupDeck() throws FileNotFoundException {

        List<Powerup> powerupDeck = new ArrayList<>();

        Gson gson = new Gson();
        File jsonFile = Paths.get("/src/main/resources/PoweupsDictionary").toFile();

        String[] fileNames = gson.fromJson(new FileReader(jsonFile), String[].class);

        for (String fileName : fileNames) {

            powerupDeck.addAll(createPoweup(fileName));
        }

        return powerupDeck;
    }

    /**
     * this method creates every copy of the cards correspondent to the selected powerup using also PowerupFactory class
     * @param fileName json File of the powerup
     * @return all the copies of that powerup
     * @throws FileNotFoundException
     */
    public List<Powerup> createPoweup(String fileName) throws FileNotFoundException {

        List<Powerup> powerup = new ArrayList<>();

        Gson gson = new Gson();

        File jsonFile = Paths.get(fileName).toFile();

        Type foundListType = new TypeToken<ArrayList<PowerupFactory>>(){}.getType(); /* to deserialize the array into an arrayList */

        List<PowerupFactory> powerups = gson.fromJson(new FileReader(jsonFile), foundListType);

        for (PowerupFactory powerupKind: powerups){

            powerup.addAll(powerupKind.createPowerups());
        }

        return powerup;
    }

    /**
     * this method creates the ammoTiles deck by reading every kind of ammoTile from a file json and using AmmoTileFactory class
     * @return
     * @throws FileNotFoundException
     */
    public List<AmmoTile> createAmmoDeck() throws FileNotFoundException {

        List<AmmoTile> ammoDeck = new ArrayList<>();

        Gson gson = new Gson();
        File jsonFile = Paths.get("/src/main/resources/AmmoTiles/Ammo.json").toFile();

        Type foundListType = new TypeToken<ArrayList<AmmoTileFactory>>(){}.getType(); /* to deserialize the array into an arrayList */

        List<AmmoTileFactory> ammoTiles = gson.fromJson(new FileReader(jsonFile), foundListType);

        for (AmmoTileFactory ammoTileKind : ammoTiles) {

            ammoDeck.addAll(ammoTileKind.createAmmoTiles());
        }

        return ammoDeck;
    }

    /**
     * this method is called by the initialize match and creates the board by reading its correspondent json file
     * @param fileName the name of the json file correspondent to the kind of board chosen by logged client
     * @return
     */
    public Board createBoard(String fileName) throws FileNotFoundException{

        Board board = new Board();

        List<AmmoTile> ammoDeck = createAmmoDeck();
        Collections.shuffle(ammoDeck);
        board.setAmmoDeck(ammoDeck);

        List<Powerup> powerupsDeck = createPowerupDeck();
        Collections.shuffle(powerupsDeck);
        board.setPowerupsDeck(powerupsDeck);

        List<Weapon> weaponsDeck = createWeaponDeck();
        Collections.shuffle(weaponsDeck);
        board.setWeaponsDeck(weaponsDeck);

        //TODO implement the file reading part that set ups all the Cells

        return board;
    }

}
