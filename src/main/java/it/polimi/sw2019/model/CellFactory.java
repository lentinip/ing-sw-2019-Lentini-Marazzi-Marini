package it.polimi.sw2019.model;

import java.util.List;

/**
 * this class was written to put into it the information inside the board.json files
 */
public class CellFactory {

    /**
     * Default Constructor
     */
    public CellFactory(){}

    /* Attributes */

    private Colors color;
    private int row;
    private int column;
    private int up;     /* these int correspond to the index of the near Cells in the ArrayList */
    private int down;
    private int left;
    private int right;
    private boolean isCommon;

    /* Methods */

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getUp() {
        return up;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getDown() {
        return down;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getLeft() {
        return left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getRight() {
        return right;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }

    public boolean isCommon() {
        return isCommon;
    }

    public void setCell(List<CellFactory> cells, List<Cell> field, int index, List<Room> rooms){

        Cell cell = field.get(index);

        for (Room room: rooms){ /* set of room attribute */

            if ( room.getColor() == this.getColor()){

                cell.setRoom(room);
            }
        }

        cell.setRow(this.row);
        cell.setColumn(this.column);
        cell.setCommon(this.isCommon);
        cell.setUp(field.get(this.up));
        cell.setDown(field.get(this.down));
        cell.setLeft(field.get(this.left));
        cell.setRight(field.get(this.right));
    }

}
