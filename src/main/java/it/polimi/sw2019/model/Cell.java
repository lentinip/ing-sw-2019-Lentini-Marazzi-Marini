package it.polimi.sw2019.model;


import it.polimi.sw2019.network.messages.BoardCoord;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    /**
     * Default constructor
     */

    public Cell() {

    }

    public Cell(int i, int k, Cell up, Cell down, Cell left, Cell right, boolean isCommon) {

        setRow(i);
        setColumn(k);
        setCellNeighbors(up, down, left, right);
        setCommon(isCommon);
    }

    public Cell(int i, int k, Cell up, Cell down, Cell left, Cell right, boolean isCommon, Room room) {

        setCellStructure(room, row, column, isCommon);
        setCellNeighbors(up, down, left, right);
    }

    /* Attributes */

    private Room room;

    private int row;

    private int column;

    private Cell up; //type of the upSide of the Cell

    private Cell down; //type of the downSide of the Cell

    private Cell left; //type of the leftSide of the Cell

    private Cell right; //type of the rightSide of the Cell

    private boolean isCommon; //tell if the Cell is Common or Spawn

    private boolean isEmpty = false; // at the beginning of the game every cell has a tile

    private AmmoTile ammoTile;

    private List<Weapon> weapons = new ArrayList<>(); /* max 3 weapons */

    /* Methods */

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Cell getUp() {
        return up;
    }

    public void setUp(Cell up) {
        this.up = up;
    }

    public Cell getDown() {
        return down;
    }

    public void setDown(Cell down) {
        this.down = down;
    }

    public Cell getLeft() {
        return left;
    }

    public void setLeft(Cell left) {
        this.left = left;
    }

    public Cell getRight() {
        return right;
    }

    public void setRight(Cell right) {
        this.right = right;
    }

    public boolean isCommon(){
        return this.isCommon;
    }

    public void setCommon(boolean common) {
        isCommon = common;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean empty) {
        isEmpty = empty;
    }

    public AmmoTile getAmmo() {
        return ammoTile;
    }

    public void setAmmo(AmmoTile ammoTile){

        if(ammoTile == null) {
            throw new NullPointerException("ammoTile parameter can't be null");
        }
        this.ammoTile = ammoTile;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void addWeapon(Weapon weapon) {

        weapons.add(weapon);
    }

    /**
     * @return the players inside the cell;
     */
    public List<Player> playersInCell(){

        List<Player> playersInCell = new ArrayList<>();
        Cell position;

        if(room.getPlayers().isEmpty()) {
            throw new NullPointerException("playersInCell parameter can't be null");}

        for( int i = 0; i < room.getPlayers().size(); i++) {

            position = room.getPlayers().get(i).getPosition();

            if (position.getRow() == this.row && position.getColumn() == this.column) {

                playersInCell.add(room.getPlayers().get(i));

            }
        }

        return playersInCell;
    }

    public void setCellStructure(Room room, int row, int column, boolean isCommon) {
        this.room = room;
        this.row = row;
        this.column = column;
        this.isCommon = isCommon;
    }

    public void setCellNeighbors(Cell up, Cell down, Cell left, Cell right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    /**
     * @return the adjacent cells (considering walls)
     */
    public List<Cell> nearCells(){
        List<Cell> nearCells = new ArrayList<>();

        if (this.up != null){

            nearCells.add(this.up);
        }

        if (this.down != null){

            nearCells.add(this.down);
        }

        if (this.left != null){

            nearCells.add(this.left);
        }

        if (this.right != null){

            nearCells.add(this.right);
        }

        return nearCells;

    }

    /**
     *
     * @param numberOfMoves
     * @return the cells you can reach with numberOfMoves moves
     */
    public List<Cell> reachableCells(int numberOfMoves){

        List<Cell> reachableCells = new ArrayList<>();
        List<Cell> reachableCellsCopy = new ArrayList<>();
        reachableCells.add(this);
        reachableCellsCopy.add(this);

        int counter = 0;

        while ( counter < numberOfMoves ){

            for (Cell cell: reachableCells){

                reachableCellsCopy.addAll(cell.nearCells());
            }

            reachableCells.clear();
            reachableCells.addAll(reachableCellsCopy);
            counter++;
        }

        /* now I remove duplicates from the list */

        reachableCells.clear();

        for (Cell cell: reachableCellsCopy){

            if (!reachableCells.contains(cell)){

                reachableCells.add(cell);
            }
        }

        return reachableCells;
    }

    public BoardCoord getCoord(){
        return new BoardCoord(row, column);
    }
}
