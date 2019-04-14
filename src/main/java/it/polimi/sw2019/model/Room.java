package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    /**
     * Default constructor
     */
    public Room(){

    }

    /* Attributes */

    private Colors color;

    private SpawnCell spawnCell;

    private List<Cell> roomCells = new ArrayList<>();




    /* Methods */

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public SpawnCell getSpawnCell() {
        return spawnCell;
    }

    public void setSpawnCell(SpawnCell spawnCell) {
        this.spawnCell = spawnCell;
    }

    public List<Cell> getRoomCells() {
        return roomCells;
    }

    public List<Player> playersInside(List<Player> players){

    List<Player> playersInside = new ArrayList<>();

        for (int i = 0; i < roomCells.size(); i++) {

            playersInside.addAll(roomCells.get(i).playersInCell(players));

        }

        return playersInside;
    }

    public void addCell(Cell cell) {

         roomCells.add(cell);

    }
}
