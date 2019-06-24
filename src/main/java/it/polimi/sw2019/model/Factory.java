package it.polimi.sw2019.model;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
    public Factory(){
        //default constructor
    }

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
     */
    public List<Weapon> createWeaponDeck(){

        List<Weapon> weaponDeck = new ArrayList<>();

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/WeaponsDictionary.json")));
        String[] fileNames = gson.fromJson(jsonReader, String[].class);

        for (String fileName : fileNames) {

            weaponDeck.add(createWeapon(fileName));
        }


        return weaponDeck;
    }

    /**
     * this method creates the weapon class by reading the correspondent json file
     * @param fileName file name
     * @return weapon
     */
    public Weapon createWeapon(String fileName) {

        Weapon weapon;
        Gson gson = new Gson();
        fileName = "/Weapons/" + fileName;
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        WeaponFactory weaponFactory = gson.fromJson(jsonReader, WeaponFactory.class);
        weapon = weaponFactory.createWeapon();
        for(Effect effect: weapon.getEffects()){

            effect.setVisibilityClass(visibilityClass);
        }

        return weapon;
    }

    /**
     * this method read the name of every file json correspondent to a powerup from the "powerupsDictionary" and calls the method that creates the arrayList of every copy of that powerup
     * @return the ArrayList associated to the powerups deck.
     */
    public List<Powerup> createPowerupDeck(){

        List<Powerup> powerupDeck = new ArrayList<>();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/PowerupsDictionary.json")));
        String[] fileNames = gson.fromJson(jsonReader, String[].class);
        for (String fileName : fileNames) {

            powerupDeck.addAll(createPowerup(fileName));
        }

        return powerupDeck;
    }

    /**
     * this method creates every copy of the cards correspondent to the selected powerup using also PowerupFactory class
     * @param fileName json File of the powerup
     * @return all the copies of that powerup
     */
    private List<Powerup> createPowerup(String fileName){

        List<Powerup> powerup = new ArrayList<>();
        Gson gson = new Gson();
        fileName = "/Powerups/" + fileName;
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        Type foundListType = new TypeToken<ArrayList<PowerupFactory>>(){}.getType(); /* to deserialize the array into an arrayList */
        List<PowerupFactory> powerups = gson.fromJson(jsonReader, foundListType);
        for (PowerupFactory powerupKind: powerups){

            powerup.addAll(powerupKind.createPowerups());
        }

        return powerup;
    }

    /**
     * this method creates the ammoTiles deck by reading every kind of ammoTile from a file json and using AmmoTileFactory class
     * @return list of ammo tiles
     */
    public List<AmmoTile> createAmmoDeck(){

        List<AmmoTile> ammoDeck = new ArrayList<>();

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/AmmoTiles/Ammo.json")));
        Type foundListType = new TypeToken<ArrayList<AmmoTileFactory>>(){}.getType(); /* to deserialize the array into an arrayList */
        List<AmmoTileFactory> ammoTiles = gson.fromJson(jsonReader, foundListType);
        for (AmmoTileFactory ammoTileKind : ammoTiles) {

            ammoDeck.addAll(ammoTileKind.createAmmoTiles());
        }

        return ammoDeck;
    }

    /**
     * this method is called by the initialize match and creates the board by reading its correspondent json file
     * THIS METHOD DOES NOT SET THE "PLAYERS" ATTRIBUTE IN THE ROOMS, IT MUST BE DONE IN MATCH CLASS AND DOES NOT SET
     * THE "KILLTRACK"
     * @param fileName the name of the json file correspondent to the kind of board chosen by logged client
     * @return board created
     */
    public Board createBoard(String fileName, List<Player> players){

        Board board = new Board();

        List<AmmoTile> ammoDeck = createAmmoDeck();
        Collections.shuffle(ammoDeck);
        board.setAmmoDeck(ammoDeck);

        List<Powerup> powerupsDeck = createPowerupDeck();
        Collections.shuffle(powerupsDeck);
        Collections.shuffle(powerupsDeck);
        board.setPowerupsDeck(powerupsDeck);

        List<Weapon> weaponsDeck = createWeaponDeck();
        Collections.shuffle(weaponsDeck);
        board.setWeaponsDeck(weaponsDeck);

        Gson gson = new Gson();

        fileName = "/Board/" + fileName;
        JsonReader jsonReader = new JsonReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        Type foundListType = new TypeToken<ArrayList<CellFactory>>(){}.getType();
        List<CellFactory> cells = gson.fromJson(jsonReader, foundListType);
        List<Cell> field = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++){ /* creating the correct number of cells and putting them in field attribute */

            field.add(new Cell());
        }

        List<Room> rooms = createRooms(cells, field, players);

        board.setRooms(rooms);

        for (CellFactory cell: cells){

            cell.setCell(field, cells.indexOf(cell), rooms);
        }

        board.setField(field);

        for(Cell cell: field){

            if (cell.isCommon()){

                cell.setAmmo(board.drawAmmo());
            }

            else {

                for (int i = 0; i < 3; i++){

                    cell.addWeapon(board.drawWeapon());
                }
            }
        }

        createKillTrack(board, players);

        return board;
    }

    /**
     * this method creates every room giving it the pointers to every cell it contains
     * @param cells cells
     * @param field fields
     * @return rooms created
     */
    private List<Room> createRooms(List<CellFactory> cells, List<Cell> field, List<Player> players){

        List<Room> rooms = new ArrayList<>();

        List<Colors> colorsDone = new ArrayList<>(); /* I use this list to know if I have already created a room of a specific color */

        for(CellFactory cellKind: cells){

            if ( !colorsDone.contains(cellKind.getColor())){ /* new room if I have not already created a room of that color */

                Room room = new Room();

                Colors color = cellKind.getColor();

                colorsDone.add(color);

                room.setColor(color);

                room.setPlayers(players);

                for (CellFactory cell: cells){ /* adding to the room every Cell (not CellFactory) of the same color */

                    if( cell.getColor() == color ){

                        room.addCell(field.get(cells.indexOf(cell)));

                        if (!cell.isCommon()){

                            room.setSpawnCell(field.get(cells.indexOf(cell)));
                        }
                    }
                }

                rooms.add(room);
            }
        }

        return  rooms;
    }

    /**
     * this method creates the killTrack by taking the characters from every player in the game
     * and set the board "killTrack" attribute
     * @param board board
     * @param players players
     */
    private void createKillTrack(Board board, List<Player> players){

        List<Character> charactersInGame = new ArrayList<>();

        for (Player player: players){

            charactersInGame.add(player.getCharacter());
        }

        KillTokens killTrack = new KillTokens(charactersInGame);

        board.setKillTrack(killTrack);
    }

}
