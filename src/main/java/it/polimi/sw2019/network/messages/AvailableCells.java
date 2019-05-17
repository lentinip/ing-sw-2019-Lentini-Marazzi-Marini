package it.polimi.sw2019.network.messages;

import java.util.List;

public class AvailableCells {

    /**
     * Default constructor
     */
    public  AvailableCells(){}

    /**
     * customizeConstructors
     */
    public AvailableCells(List<BoardCoord> availableCells){

        setAvailableCells(availableCells);
    }

    /* Attributes */

    private List<BoardCoord> availableCells;

    /* Methods */

    public List<BoardCoord> getAvailableCells() {
        return availableCells;
    }

    public void setAvailableCells(List<BoardCoord> availableCells) {
        this.availableCells = availableCells;
    }
}
