package it.polimi.sw2019.model;

import it.polimi.sw2019.commons.Colors;

import java.util.List;

/**
 * @author poligenius
 * this class was written to put into it the information inside the board.json files
 */
public class CellFactory {

    /**
     * Default Constructor
     */
    public CellFactory(){

        //I apply bigger values to check if the near cells are null or not
        setUp(500);
        setDown(500);
        setRight(500);
        setLeft(500);
    }

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

    /**
     * this method is used to initialize every cell on the board
     * @param field all the cells of the board
     * @param index index of the cell to initialize
     * @param rooms all the rooms of the board
     */
    public void setCell(List<Cell> field, int index, List<Room> rooms){

        Cell cell = field.get(index);

        for (Room room: rooms){ /* set of room attribute */

            if ( room.getColor() == this.getColor()){

                cell.setRoom(room);
            }
        }

        cell.setRow(this.row);
        cell.setColumn(this.column);
        cell.setCommon(this.isCommon);
        if(this.up != 500) {
            cell.setUp(field.get(this.up));
        }
        if(this.down != 500) {
            cell.setDown(field.get(this.down));
        }
        if(this.left != 500) {
            cell.setLeft(field.get(this.left));
        }
        if(this.right != 500) {
            cell.setRight(field.get(this.right));
        }
    }

}
