package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Character;

import java.util.List;

/**
 * @author poligenius
 * this class contains info about characters in the game
 */
public class Players {

    /**
     * Default constructor
     */
    public Players(){}

    public Players(List<Character> characters){

        setCharacters(characters);
    }

    /* Attributes */

    private List<Character> characters;

    /* Methods */

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
