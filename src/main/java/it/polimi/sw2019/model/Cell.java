package it.polimi.sw2019.model;

public class Cell {

    /**
     * Default constructor
     */

    public Cell() {

    }

    /* Attributes */

    private Colors color;

    private int row;

    private int column;

    private Cell up; //type of the upSide of the Cell

    private Cell down; //type of the downSide of the Cell

    private Cell left; //type of the leftSide of the Cell

    private Cell right; //type of the rightSide of the Cell

    private boolean isCommon; //tell if the Cell is Common or Spawn

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

    public Cell getUp() {
        return up;
    }

    public void setUp(Cell up) {
        this.up = up;
    }

    public Cell getDown() {
        return down;
    }

    public void setDown(Cell down) {
        this.down = down;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public boolean getCommon(){
        return this.isCommon;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }

    public Player[] playersInside(){

        Player[] result = new Player[1];

        //TODO implement

        return result;
    }
}
