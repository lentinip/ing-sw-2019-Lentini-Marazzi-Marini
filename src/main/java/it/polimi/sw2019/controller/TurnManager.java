package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    /**
     * Default constructor
     */
    public TurnManager(Match match, VirtualView view){
        this.match = match;
        this.view = view;
        this.currentPlayer = match.getCurrentPlayer();
        this.singleActionManager = new SingleActionManager(match, view, this);
    }

    /* Attributes */

    //The number of actions left is in the Player

    private Match match;

    private VirtualView view;

    private Player currentPlayer;

    private Player firstPlayer;

    private boolean isFirstRound = true;

    private SingleActionManager singleActionManager;

    //Cells without AmmoTiles
    private List<Cell> emptyCommonCells = new ArrayList<>();

    //SpawnCells where the player took the Weapon
    private List<Cell> emptySpawnCells = new ArrayList<>();

    /* Methods */

    public List<Cell> getEmptyCommonCells() {
        return emptyCommonCells;
    }

    public List<Cell> getEmptySpawnCells() {
        return emptySpawnCells;
    }

    public SingleActionManager getSingleActionManager() {
        return singleActionManager;
    }

    public void setSingleActionManager(SingleActionManager singleActionManager) {
        this.singleActionManager = singleActionManager;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }

    public void endTurn(){

        //Refills the CommonCells
        if(!emptyCommonCells.isEmpty()){
            for(Cell commonCell : emptyCommonCells){

                AmmoTile newAmmoTile = match.getBoard().drawAmmo();
                commonCell.setAmmo(newAmmoTile);
                commonCell.setIsEmpty(false);
                emptyCommonCells.remove(commonCell);
            }
        }

        //Refills the SpawnCells
        if(!emptySpawnCells.isEmpty()){

            for(Cell spawnCell : emptySpawnCells){

               if (!match.getBoard().weaponsDeckIsEmpty()){
                   Weapon weapon = match.getBoard().drawWeapon();
                   spawnCell.getWeapons().add(weapon);
               }

               if (match.getBoard().weaponsDeckIsEmpty() || spawnCell.getWeapons().size()==3){
                   emptySpawnCells.remove(spawnCell);
               }

            }
        }

        //Calls endTurn of the Match in the model
        match.endTurn();

        //Changes the parameters here
        currentPlayer = match.getCurrentPlayer();
    }

    public void spawn(Character character, int powerupIndex){
        Player player = match.getPlayerFromCharacter(character);

        //Gets the powerup selected
        Powerup powerup = player.getPowerups().get(powerupIndex);

        //Gets the room with the color of the powerup
        Room room = match.getBoard().getRoomByColor(powerup.getColor());

        //Removes the powerup from the player
        player.getPowerups().remove(powerupIndex);

        //Discards the powerup
        match.getBoard().discardPowerup(powerup);

        //The player is moved to the SpawnCell of the room
        player.setPosition(room.getSpawnCell());
    }
}
