package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Character;

import java.util.List;

public class Players {

    /**
     * Default constructor
     */
    public Players(){}

    /**
     * customize constructor
     */
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
