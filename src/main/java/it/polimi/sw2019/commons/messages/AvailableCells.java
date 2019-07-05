package it.polimi.sw2019.commons.messages;

import java.util.List;

/**
 * @author poligenius
 * class written to send infos about the cells the player can choose
 */
public class AvailableCells {

    /**
     * Default constructor
     */
    public  AvailableCells(){}

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
