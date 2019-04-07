package it.polimi.sw2019.model;

public class Room {

    /**
     * Default constructor
     */
    public Room(){

    }

    /* Attributes */

    private Colors color;

    private SpawnCell spawnCell;

    private Cell[] roomCells = new Cell[4];


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

    public Player[] playersInside(){

        Player[] result = new Player[1];

        //TODO implement

        return result;
    }

}
