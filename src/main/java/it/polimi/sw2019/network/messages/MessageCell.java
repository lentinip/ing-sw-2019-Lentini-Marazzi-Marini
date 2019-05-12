package it.polimi.sw2019.network.messages;

import java.util.ArrayList;
import java.util.List;

public class MessageCell {

    /**
     * Default Constructor
     */
    public MessageCell(){}

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

}
