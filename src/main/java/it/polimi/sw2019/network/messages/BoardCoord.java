package it.polimi.sw2019.network.messages;

/**
 * @author poligenius
 * this class was written to put into it coordinates of cell position on the board
 */
public class BoardCoord {

    /**
     * Default Constructor
     */
    public BoardCoord(){}

    public BoardCoord(int row, int column){

        setColumn(column);
        setRow(row);
    }

    /* Attributes */

    private int row;
    private int column;

    /* Methods */

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getCellNumber(){
        return 4*row + column;
    }
}
