package java.it.polimi.sw2019.model;

public class Cell {

    /* Default constructor */

    public Cell() {

    }

    /* Attributes */

    private Colors color;

    private int row;

    private int column;

    private RoomElement up; //type of the upSide of the Cell

    private RoomElement down; //type of the downSide of the Cell

    private RoomElement left; //type of the leftSide of the Cell

    private RoomElement right; //type of the rightSide of the Cell

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

    public RoomElement getUp() {
        return up;
    }

    public void setUp(RoomElement up) {
        this.up = up;
    }

    public RoomElement getDown() {
        return down;
    }

    public void setDown(RoomElement down) {
        this.down = down;
    }

    public RoomElement getLeft() {
        return left;
    }

    public void setLeft(RoomElement left) {
        this.left = left;
    }

    public RoomElement getRight() {
        return right;
    }

    public void setRight(RoomElement right) {
        this.right = right;
    }

    public boolean isCommon() {
        return isCommon;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }
}
