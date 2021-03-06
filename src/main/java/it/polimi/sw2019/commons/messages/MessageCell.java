package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * @author poligenius
 * this class contains infos about a cell
 */
public class MessageCell {

    /**
     * Default Constructor
     */
    public MessageCell(){}

    public MessageCell(int row, int column, List<Character> characters, boolean isEmpty, String ammoTile, List<String> weapons){

        setRow(row);
        setColumn(column);
        setCharacters(characters);
        setEmpty(isEmpty);
        setAmmoTile(ammoTile);
        setWeapons(weapons);
    }

    /* Attributes */

    private int row;
    private int column;
    private List<Character> characters = new ArrayList<>();
    private boolean isEmpty;
    private String ammoTile;
    private List<String> weapons = new ArrayList<>();

    /* Methods */

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void setAmmoTile(String ammoTile) {
        this.ammoTile = ammoTile;
    }

    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public List<String> getWeapons() {
        return weapons;
    }

    public String getAmmoTile() {
        return ammoTile;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getCellNumber(){
        return 4*row + column;
    }

    public int getAmmoPositionNumber(){
        int result = getCellNumber();
        if (result>1){
            result--;
        }
        if (result>3){
            result--;
        }
        return result;
    }

}
