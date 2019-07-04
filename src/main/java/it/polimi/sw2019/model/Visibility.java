package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author poligenius
 * class that contains some game logic, its method are used to know where a
 * player can shoot
 */
public class Visibility {


    public Visibility() {
    }

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
     * @param visibility visibility of the effect
     * @param player owner of the weapon
     * @param movesAway how many moves targets have to be away from the shooter
     * @param exactly if they have to be away EXACTLY moves away
     * @param moves how many moves the player can move during the effect
     * @return list of reachable cells
     */
    public List<Cell> visibility(KindOfVisibility visibility, Player player, int movesAway, boolean exactly, int moves) {

        List<Cell> reachableCells = new ArrayList<>();

        if (visibility == KindOfVisibility.VISIBLE || visibility == KindOfVisibility.THOR) {

            reachableCells = visible(player, movesAway, exactly);
        } else if (visibility == KindOfVisibility.NONVISIBLE) {

            reachableCells = nonVisible(player);
        } else if (visibility == KindOfVisibility.RAILGUN) {

            reachableCells = railGun(player);
        } else if (visibility == KindOfVisibility.DIFF_ROOM) {

            reachableCells = diffRoom(player);
        } else if (visibility == KindOfVisibility.MOVE) {

            reachableCells = move(player, moves);
        }

        return reachableCells;
    }


    /**
     * used to know targets for VISIBLE effects
     * @param player    the shooting player
     * @param movesAway number of moves the targets have to be away from  the shooter
     * @param exactly   true if movesAway has to be EXACTLY
     * @return the visible cells distant "exactly" "movesAway" from the player
     */
    public List<Cell> visible(Player player, int movesAway, boolean exactly) {

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> visibleCells = player.visibleCells();

        int playerRow = player.getPosition().getRow();
        int playerColumn = player.getPosition().getColumn();

        if (exactly) {

            for (int i = 0; i < visibleCells.size(); i++) {

                int row = visibleCells.get(i).getRow();
                int column = visibleCells.get(i).getColumn();

                /* check if the cell is exactly movesAway from player position */
                if (Math.abs(playerRow - row) + Math.abs(playerColumn - column) == movesAway) {

                    reachableCells.add(visibleCells.get(i));
                }
            }
        } else {

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
     * used to know targets for NONVISIBLE effects
     * @param player shooter
     * @return the cells the player cannot see from his position
     */
    public List<Cell> nonVisible(Player player) {

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> visibleCells = player.visibleCells();

        for (int i = 0; i < board.getField().size(); i++) {

            reachableCells.add(board.getField().get(i));
        }

        /* Removes from the reachableCells every cell the player can see */
        for (int i = 0; i < visibleCells.size(); i++) {

            reachableCells.remove(visibleCells.get(i));
        }

        return reachableCells;
    }

    /**
     * used to know targets for RAILGUN effects
     * @param player shooter
     * @return the cells in cardinal direction from the player
     */
    public List<Cell> railGun(Player player) {

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> allCells = board.getField();
        int playerRow = player.getPosition().getRow();
        int playerColumn = player.getPosition().getColumn();

        /* check if the cell is in the same row or column of the player */
        for (int i = 0; i < allCells.size(); i++) {

            int row = allCells.get(i).getRow();
            int column = allCells.get(i).getColumn();

            if (row == playerRow || column == playerColumn) {

                reachableCells.add(allCells.get(i));
            }
        }

        return reachableCells;
    }

    /**
     * used to know targets for DIFFROOM effects
     * @param player shooter
     * @return the cells of the rooms the player can see in (excluding the one he is in)
     */
    public List<Cell> diffRoom(Player player) {

        List<Cell> reachableCells = player.visibleCells();
        List<Cell> playerRoomCells = player.getPosition().getRoom().getRoomCells();

        /* removing from the visible cells the */
        reachableCells.removeAll(playerRoomCells);

        return reachableCells;
    }

    /**
     * used to know cells for MOVE effects
     * @param player shooter
     * @param moves  max moves he can do
     * @return the cells that the player can reach
     */
    public List<Cell> move(Player player, int moves) {

        List<Cell> reachableCells = player.getPosition().reachableCells(moves);

        return reachableCells;
    }

    /**
     * called by controller
     * @param shootedPlayer players already shooted
     * @param shooter       player who is shooting
     * @return the new targets available
     */
    public List<Player> railgunUpdate(Player shootedPlayer, Player shooter){

        List<Cell> allCells = board.getField();
        List<Player> targets = new ArrayList<>();

        if (shootedPlayer.getPosition().getColumn() == shooter.getPosition().getColumn() && shootedPlayer.getPosition().getRow() == shooter.getPosition().getRow()){

            for (Cell cell : allCells) {

                if (cell.getRow() == shooter.getPosition().getRow() || cell.getColumn() == shooter.getPosition().getColumn()) {

                    targets.addAll(cell.playersInCell());
                }
            }

        }

        else if (shootedPlayer.getPosition().getColumn() > shooter.getPosition().getColumn()) {

            for (Cell cell : allCells) {

                if (cell.getRow() == shooter.getPosition().getRow() && cell.getColumn() >= shooter.getPosition().getColumn()) {

                    targets.addAll(cell.playersInCell());
                }
            }
        }

        else if (shootedPlayer.getPosition().getColumn() < shooter.getPosition().getColumn()){

            for (Cell cell: allCells){

                if ( cell.getRow() == shooter.getPosition().getRow() && cell.getColumn() <= shooter.getPosition().getColumn()){

                    targets.addAll(cell.playersInCell());
                }
            }
        }

        else if (shootedPlayer.getPosition().getRow() > shooter.getPosition().getRow()){

            for (Cell cell: allCells){

                if ( cell.getColumn() == shooter.getPosition().getColumn() && cell.getRow() >= shooter.getPosition().getRow()){

                    targets.addAll(cell.playersInCell());
                }
            }
        }

        else if (shootedPlayer.getPosition().getRow() < shooter.getPosition().getRow()){

            for (Cell cell: allCells){

                if ( cell.getColumn() == shooter.getPosition().getColumn() && cell.getRow() <= shooter.getPosition().getRow()){

                    targets.addAll(cell.playersInCell());
                }
            }
        }

        List<Player> playerList = new ArrayList<>(targets);

        //removing the shooter and the shooted player
        for (Player player: playerList){

            if (player.equals(shootedPlayer) || player.equals(shooter)){
                targets.remove(player);
            }
        }

        return targets;
    }

    /**
     * called by choices to update visibility
     * @param shooterCell cell of the player who shooted
     * @param shootedCell cell of the first player shooted
     * @return the adjacent cell in the same direction to the shooted cell
     */
    public Cell cellInSameDirection(Cell shooterCell, Cell shootedCell){

        Cell nextCell;

        //checking if I have to go up, down, right or left
        if ( shooterCell.getUp() == shootedCell){

            nextCell = shootedCell.getUp();
        }

        else if (shooterCell.getDown() == shootedCell){

            nextCell = shootedCell.getDown();
        }

        else if (shooterCell.getLeft() == shootedCell){

            nextCell = shootedCell.getLeft();
        }

        else if (shooterCell.getRight() == shootedCell){

            nextCell = shootedCell.getRight();
        }

        else {

            nextCell = null;
        }

        return nextCell;
    }
}
