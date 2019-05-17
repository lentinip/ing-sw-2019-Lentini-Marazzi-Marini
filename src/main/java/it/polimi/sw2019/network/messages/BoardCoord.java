package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Board;

public class BoardCoord {

    /**
     * Default Constructor
     */
    public BoardCoord(){}

    /**
     * customize Constructor
     */
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
}
