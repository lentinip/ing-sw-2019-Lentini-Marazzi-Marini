package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Visibility {

    /**
     * Default Constructor
     */

    public Visibility(){}

    public Visibility(Board board) {
        setBoard(board);
    }

    /* Attributes */

    private Board board;

    /* Methods */

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * depending on the kindOfVisibility this method calls the correspondent method and give it the correct input
     * @param visibility
     * @param player
     * @param movesAway
     * @param exactly
     * @param moves
     * @return
     */
    public List<Cell> visibility(KindOfVisibility visibility, Player player, int movesAway, boolean exactly, int moves){

        List<Cell> reachableCells = new ArrayList<>();

        if (visibility == KindOfVisibility.VISIBLE) {

            reachableCells = visible(player, movesAway, exactly);
        }

        else if (visibility == KindOfVisibility.NONVISIBLE){

            reachableCells = nonVisible(player);
        }

        else if (visibility == KindOfVisibility.RAILGUN){

            reachableCells = railGun(player);
        }

        else if (visibility == KindOfVisibility.DIFF_ROOM){

            reachableCells = diffRoom(player);
        }

        else if (visibility == KindOfVisibility.MOVE){

            reachableCells = move(player, moves);
        }

        return  reachableCells;
    }


    /**
     * @param player the shooting player
     * @param movesAway number of moves the targets have to be away from  the shooter
     * @param exactly true if movesAway has to be EXACTLY
     * @return the visible cells distant "exactly" "movesAway" from the player
     */
    public List<Cell> visible(Player player, int movesAway, boolean exactly){

       List<Cell> reachableCells = new ArrayList<>();
       List<Cell> visibleCells = player.visibleCells();

       int playerRow = player.getPosition().getRow();
       int playerColumn = player.getPosition().getColumn();

       if (exactly){

           for (int i = 0; i < visibleCells.size(); i++){

               int row = visibleCells.get(i).getRow();
               int column = visibleCells.get(i).getColumn();

               /* check if the cell is exactly movesAway from player position */
               if(Math.abs(playerRow - row) + Math.abs(playerColumn-column) == movesAway){

                   reachableCells.add(visibleCells.get(i));
               }
           }
       }

       else {

           for (int i = 0; i < visibleCells.size(); i++) {

               int row = visibleCells.get(i).getRow();
               int column = visibleCells.get(i).getColumn();

               /* check if the cell is at least movesAway from the player position */
               if (Math.abs(playerRow - row) + Math.abs(playerColumn - column) >= movesAway) {

                   reachableCells.add(visibleCells.get(i));
               }
           }
       }

       return reachableCells;
    }

    /**
     * @param player
     * @return the cells the player cannot see from his position
     */
    public List<Cell> nonVisible(Player player){

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> visibleCells = player.visibleCells();

        for(int i = 0; i < board.getField().size(); i++) {

            reachableCells.add(board.getField().get(i));
        }

        /* Removes from the reachableCells every cell the player can see */
        for (int i = 0; i < visibleCells.size(); i++ ){

            reachableCells.remove(visibleCells.get(i));
        }

        return reachableCells;
    }

    /**
     * @param player
     * @return the cells in cardinal direction from the player
     */
    public List<Cell> railGun(Player player){

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> allCells = board.getField();
        int playerRow = player.getPosition().getRow();
        int playerColumn = player.getPosition().getColumn();

        /* check if the cell is in the same row or column of the player */
        for (int i = 0; i < allCells.size(); i++ ){

            int row = allCells.get(i).getRow();
            int column = allCells.get(i).getColumn();

            if (row == playerRow || column == playerColumn){

                reachableCells.add(allCells.get(i));
            }
        }

        return reachableCells;
    }

    /**
     * @param player
     * @return the cells of the rooms the player can see in (excluding the one he is in)
     */
    public List<Cell> diffRoom(Player player){

        List<Cell> reachableCells = player.visibleCells();
        List<Cell> playerRoomCells = player.getPosition().getRoom().getRoomCells();

        /* removing from the visible cells the */
        reachableCells.removeAll(playerRoomCells);

        return reachableCells;
    }

    /**
     *
     * @param player
     * @param moves max moves he can do
     * @return the cells that the player can reach
     */
    public List<Cell> move(Player player, int moves){

        List<Cell> reachableCells = player.getPosition().reachableCells(moves);

        return reachableCells;
    }

}
