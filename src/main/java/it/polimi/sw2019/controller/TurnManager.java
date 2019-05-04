package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    /**
     * Default constructor
     */
    public TurnManager(Match match){
        this.match = match;
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

        //Refils the CommonCells
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
    }
}
