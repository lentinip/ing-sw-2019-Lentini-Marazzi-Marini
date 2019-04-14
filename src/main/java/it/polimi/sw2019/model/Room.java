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

    public Player[] playersInside(Player[] players){

        /**
         * array to save the players in the room
         */
        Player[] result = new Player[5];
        /**
         * array to get the players in every single cell
         */
        Player[] playersInCell = new Player[5];
        /**
         * three index to scan the arrays: roomCells, result and playersInCell
         */
        int index, playersIndex, playersInIndex;


        playersInIndex = 0;
        for(index = 0; index<4; index++) {

            if(roomCells[index] != null) {

                playersInCell = roomCells[index].playersInside(players);
                for(playersIndex = 0; playersIndex<5; playersIndex++) {

                    if(playersInCell[playersIndex] != null) {

                        result[playersInIndex] = playersInCell[playersIndex];
                        playersInIndex++;
                    }
                    else{
                        playersIndex = 4;
                    }
                }
            }
            else{
                index = 3;
            }
        }

        return result;
    }

    public void addCell(Cell cell, int index) {
        this.roomCells[index] = cell;
        return;
    }
}
