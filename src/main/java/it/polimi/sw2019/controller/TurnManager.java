package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.CommonCell;
import it.polimi.sw2019.model.Match;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.SpawnCell;

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
}
