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

    private Player firstPlayer;

    private boolean isFirstRound = true;

    private SingleActionManager singleActionManager = new SingleActionManager(match, this);

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

        //Resets the player's numberOfActions
        currentPlayer.resetNumberOfActions();

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
