package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class SingleTarget extends Effect {

    /**
     * Default Constructor
     */
    public SingleTarget(){

        //TODO implement here

        };

    /* Attributes */

    private  int damages;

    private  int marks;

    /* methods */

    public int getDamages() {
        return damages;
    }

    public void setDamages(int damages) {
        this.damages = damages;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }


    public List<Cell> reachableCells(Player owner){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }

    public List<Player> reachablePlayers(Player owner){

        List<Player> reachablePlayers = new ArrayList<>();

        //TODO implement here

        return reachablePlayers;
    }
}
