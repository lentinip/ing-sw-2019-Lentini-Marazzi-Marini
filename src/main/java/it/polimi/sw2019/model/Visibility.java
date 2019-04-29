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

    /**
     * depending on the kindOfVisibility this method calls the correspondent method and give it the correct input
     * @param visibility
     * @param player
     * @param movesAway
     * @param exactly
     * @return
     */
    public List<Cell> visibility(KindOfVisibility visibility, Player player, int movesAway, boolean exactly){

        List<Cell> reachableCells = new ArrayList<>();

        if (visibility == visibility.VISIBLE) {

            reachableCells = visible(player, movesAway, exactly);
        }

        else if (visibility == visibility.NONVISIBLE){

            reachableCells = nonVisible(player);
        }

        else if (visibility == visibility.RAILGUN){

            reachableCells = railGun(player);
        }

        else if (visibility == visibility.DIFF_ROOM){

            reachableCells = diffRoom(player);
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

               int row = reachableCells.get(i).getRow();
               int column = reachableCells.get(i).getColumn();

               /* check if the cell is exactly movesAway from player position */
               if(Math.abs(playerRow - row) == movesAway || Math.abs(playerColumn-column) == movesAway){

                   reachableCells.add(visibleCells.get(i));
               }
           }
       }

       else {

           for (int i = 0; i < visibleCells.size(); i++) {

               int row = reachableCells.get(i).getRow();
               int column = reachableCells.get(i).getColumn();

               /* check if the cell is at least movesAway from the player position */
               if (Math.abs(playerRow - row) >= movesAway || Math.abs(playerColumn - column) >= movesAway) {

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

        List<Cell> reachableCells = board.getField();
        List<Cell> visibleCells = player.visibleCells();


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

        /* removing from the visible cells the cells of the player room */
        reachableCells.removeAll(playerRoomCells);

        return reachableCells;
    }

}