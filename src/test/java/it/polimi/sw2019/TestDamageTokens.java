package it.polimi.sw2019;

import it.polimi.sw2019.commons.Character;
import it.polimi.sw2019.model.DamageTokens;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDamageTokens {

    /**
     * verifies if I add the correct amount of damages in DamageTokens
     */
    @Test
    public void addDamageTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        DamageTokens damageTokens = new DamageTokens(characters);

        int i;
        List<Character> resultCharacters = new ArrayList<>();
        for(i = 6; i>0; i--) {
            resultCharacters.add(Character.BANSHEE);
        }
        i = 6;

        damageTokens.addDamage(6, Character.BANSHEE);

        assertEquals(resultCharacters, damageTokens.getDamageSequence());
        assertEquals(i, damageTokens.getTotalDamage());

        for(i = 6; i>0; i--) {
            resultCharacters.add(Character.DOZER);
        }
        i = 12;

        damageTokens.addDamage(7, Character.DOZER);

        assertEquals(resultCharacters, damageTokens.getDamageSequence());
        assertEquals(i, damageTokens.getTotalDamage());

        damageTokens.addDamage(1, Character.DOZER);
        assertEquals(i, damageTokens.getTotalDamage());
    }

    /**
     * verifies if the damageTokens have been set to the initial values correctly
     */
    @Test
    public void resetTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        DamageTokens resultTokens = new DamageTokens(characters);

        DamageTokens damageTokens = new DamageTokens(characters);
        damageTokens.addDamage(1, Character.DOZER);
        damageTokens.addDamage(6, Character.BANSHEE);

        damageTokens.reset();

        assertEquals(resultTokens.getMap(), damageTokens.getMap());
        assertEquals(resultTokens.getDamageSequence(), damageTokens.getDamageSequence());
        assertEquals(resultTokens.getTotalDamage(), damageTokens.getTotalDamage());

    }

    /**
     * verifies if I get the correct ranking list of players who did the damages
     */
    @Test
    public void getRankingTest() {

        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.DOZER);
        resultCharacters.add(Character.DISTRUCTOR);
        resultCharacters.add(Character.BANSHEE);
        resultCharacters.add(Character.VIOLET);

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        DamageTokens damageTokens = new DamageTokens(characters);
        damageTokens.addDamage(5, Character.DOZER);
        damageTokens.addDamage(2, Character.BANSHEE);
        damageTokens.addDamage(1, Character.VIOLET);
        damageTokens.addDamage(3, Character.DISTRUCTOR);

        assertEquals(resultCharacters, damageTokens.getRanking());
    }

    @Test
    public void setTotalDamageTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        DamageTokens damageTokens = new DamageTokens(characters);

        damageTokens.setTotalDamage(5);

        int i = damageTokens.getTotalDamage();

        assertEquals(5, i);
    }

    @Test
    public void setDamageSequenceTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        DamageTokens damageTokens = new DamageTokens(characters);

        List<Character> damageSequence = new ArrayList<>();
        damageSequence.add(Character.DISTRUCTOR);
        damageSequence.add(Character.DISTRUCTOR);
        damageSequence.add(Character.DOZER);
        damageSequence.add(Character.VIOLET);
        damageSequence.add(Character.VIOLET);
        damageSequence.add(Character.VIOLET);
        damageSequence.add(Character.BANSHEE);
        damageSequence.add(Character.BANSHEE);

        damageTokens.setDamageSequence(damageSequence);

        List<Character> damageSequence1 =  damageTokens.getDamageSequence();

        assertEquals(damageSequence, damageSequence1);
    }

}
