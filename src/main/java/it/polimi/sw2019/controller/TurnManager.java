package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    /**
     * Default constructor
     */
    public TurnManager(Match match){
        this.match = match;
        this.currentPlayer = match.getCurrentPlayer();
    }

    /* Attributes */

    //The number of actions left is in the Player

    private Match match;

    private Player currentPlayer;

    private SingleActionManager singleActionManager = new SingleActionManager(match, this);

    //Cells without AmmoTiles
    private List<CommonCell> emptyCommonCells = new ArrayList<>();

    //SpawnCells where the player took the Weapon
    private List<SpawnCell> emptySpawnCells = new ArrayList<>();

    /* Methods */

    public List<CommonCell> getEmptyCommonCells() {
        return emptyCommonCells;
    }

    public List<SpawnCell> getEmptySpawnCells() {
        return emptySpawnCells;
    }

    public void endTurn(){

        //Resets the player's numberOfActions
        currentPlayer.resetNumberOfActions();

        //Refills the CommonCells
        if(!emptyCommonCells.isEmpty()){
            for(CommonCell commonCell : emptyCommonCells){

                AmmoTile newAmmoTile = match.getBoard().drawAmmo();
                commonCell.setAmmo(newAmmoTile);
                commonCell.setIsEmpty(false);
                emptyCommonCells.remove(commonCell);
            }
        }

        //Refills the SpawnCells
        if(!emptySpawnCells.isEmpty()){

            for(SpawnCell spawnCell : emptySpawnCells){
               Weapon weapon = match.getBoard().drawWeapon();

               if (!match.getBoard().weaponsDeckIsEmpty()){
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
        singleActionManager.setCurrentPlayer(currentPlayer);
    }

    public void respawn(Character deadCharacter, int powerupIndex){
        Player deadPlayer = match.getPlayerFromCharacter(deadCharacter);

        //Gets the powerup selected
        Powerup powerup = deadPlayer.getPowerups().get(powerupIndex);

        //Gets the room with the color of the powerup
        Room room = match.getBoard().getRoomByColor(powerup.getColor());

        //Removes the powerup from the player
        deadPlayer.getPowerups().remove(powerupIndex);

        //Discards the powerup
        match.getBoard().discardPowerup(powerup);

        //The player is moved to the SpawnCell of the room
        deadPlayer.setPosition(room.getSpawnCell());
    }
}
