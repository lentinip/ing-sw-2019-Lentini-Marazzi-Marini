package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Visibility {

    /**
     * Default Constructor
     */

    public Visibility(){};

    /* Attributes */

    private Board board;

    /* Methods */



    public List<Cell> visibility(KindOfVisibility visibility, Player player, int[] movesAway, boolean exactly, Boolean sameDirection){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here

        return  reachableCells;
    }

    public List<Cell> visible(Player player, int[] movesAway, boolean exactly, boolean sameDirection){

       List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }


    public List<Cell> nonVisible(Player player){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }

    public List<Cell> thor(Player player){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }

    public List<Cell> railGun(Player player){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }

    public List<Cell> diffRoom(Player player){

        List<Cell> reachableCells = new ArrayList<>();

        //TODO implement here


        return reachableCells;
    }

}
