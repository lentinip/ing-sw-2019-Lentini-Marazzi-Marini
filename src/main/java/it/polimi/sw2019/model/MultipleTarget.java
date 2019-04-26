package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class MultipleTarget extends Effect{

    /**
     * Default Constructor
     */
    public MultipleTarget(){

        //TODO implement here
    }

    /* Attributes */

    private int[] damages = new int[4];

    private int[] marks = new int[4];

    private boolean differentSquares; /* true if the targets have to be on different squares */

    private boolean differentPlayers; /* true if the target have to be a different one from the previously choosen */

    private int maxTargets; /* max number of targets you can hit */

    /* methods */

    public int[] getDamages() {
        return damages;
    }

    public void setDamages(int[] damages) {
        this.damages = damages;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }

    public boolean isDifferentSquares() {
        return differentSquares;
    }

    public void setDifferentSquares(boolean differentSquares) {
        this.differentSquares = differentSquares;
    }

    public boolean isDifferentPlayers() {
        return differentPlayers;
    }

    public void setDifferentPlayers(boolean differentPlayers) {
        this.differentPlayers = differentPlayers;
    }

    public int getMaxTargets() {
        return maxTargets;
    }

    public void setMaxTargets(int maxTargets) {
        this.maxTargets = maxTargets;
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
