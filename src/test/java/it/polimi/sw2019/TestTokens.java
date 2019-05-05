package it.polimi.sw2019;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.Tokens;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTokens {

    @Test
    public void addTokensTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.SPROG);
        characters.add(Character.DOZER);

        Tokens tokens = new Tokens(characters);
        tokens.addTokens(2, Character.SPROG);

        int i = 2;
        assertEquals(i, tokens.getTokensOfCharacter(Character.SPROG));

        i = 4;
        tokens.addTokens(2, Character.SPROG);
        assertEquals(i, tokens.getTokensOfCharacter(Character.SPROG));
    }

    @Test
    public void orderArrayByComparatorTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.SPROG);
        characters.add(Character.DOZER);

        Tokens tokens = new Tokens(characters);
        tokens.addTokens(4, Character.DOZER);
        tokens.addTokens(1, Character.BANSHEE);
        tokens.addTokens(3, Character.DISTRUCTOR);
        tokens.addTokens(2, Character.SPROG);

        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.DOZER);
        resultCharacters.add(Character.DISTRUCTOR);
        resultCharacters.add(Character.SPROG);
        resultCharacters.add(Character.BANSHEE);

        assertEquals(resultCharacters, tokens.orderArray());

    }

    @Test
    public void sortByValuesTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.SPROG);
        characters.add(Character.DOZER);

        Tokens tokens = new Tokens(characters);
        tokens.addTokens(4, Character.DOZER);
        tokens.addTokens(1, Character.BANSHEE);
        tokens.addTokens(3, Character.DISTRUCTOR);
        tokens.addTokens(2, Character.SPROG);

        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.DOZER);
        resultCharacters.add(Character.DISTRUCTOR);
        resultCharacters.add(Character.SPROG);
        resultCharacters.add(Character.BANSHEE);
        Tokens resultTokens = new Tokens(resultCharacters);
        resultTokens.addTokens(4, Character.DOZER);
        resultTokens.addTokens(1, Character.BANSHEE);
        resultTokens.addTokens(3, Character.DISTRUCTOR);
        resultTokens.addTokens(2, Character.SPROG);

        assertEquals(resultTokens.getMap(), tokens.getMap());
    }

}
