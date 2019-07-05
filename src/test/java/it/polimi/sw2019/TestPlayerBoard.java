package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.commons.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.*;

public class TestPlayerBoard {

    @Test
    public void hasAtLeastOneAmmoTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        assertTrue(playerBoard.hasAtLeastOneAmmo());

        Ammo ammo = new Ammo(0, 0, 0);
        playerBoard.setAmmo(ammo);

        assertFalse(playerBoard.hasAtLeastOneAmmo());
    }

    @Test
    public void setFirstPlayerTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        assertFalse(playerBoard.isFirstPlayer());

        playerBoard.setFirstPlayer(true);

        assertTrue(playerBoard.isFirstPlayer());
    }

    @Test
    public void updateScoreTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);

        KillTokens killTokens = new KillTokens(characters);
        Score score = new Score(characters, killTokens);

        DamageTokens damageTokens = new DamageTokens(characters);
        playerBoard.setDamage(damageTokens);

        damageTokens.addDamage(2, Character.VIOLET);
        damageTokens.addDamage(3, Character.DISTRUCTOR);
        damageTokens.addDamage(4, Character.SPROG);
        playerBoard.updateScore(score);

        int i = score.getMap().get(Character.VIOLET);

        assertEquals(5, i);

        i = score.getMap().get(Character.DISTRUCTOR);
        assertEquals(6, i);

        i = score.getMap().get(Character.SPROG);
        assertEquals(8, i);
    }

    @Test
    public void setNumOfDeathsTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        playerBoard.setNumOfDeaths(5);

        assertEquals(5, playerBoard.getNumOfDeaths());
    }

    @Test
    public void setFlippedTest() {


        PlayerBoard playerBoard = new PlayerBoard();

        assertFalse(playerBoard.isFlipped());

        playerBoard.setFlipped(true);

        assertTrue(playerBoard.isFlipped());
    }

    @Test
    public void constructorTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DOZER);
        characters.add(Character.DISTRUCTOR);

        PlayerBoard playerBoard = new PlayerBoard(characters);

        assertNotNull(playerBoard.getDamage());
        assertNotNull(playerBoard.getMarks());
    }

    @Test
    public void updateFrenzyScoreTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);

        KillTokens killTokens = new KillTokens(characters);
        Score score = new Score(characters, killTokens);

        DamageTokens damageTokens = new DamageTokens(characters);
        playerBoard.setDamage(damageTokens);

        damageTokens.addDamage(2, Character.VIOLET);
        damageTokens.addDamage(3, Character.DISTRUCTOR);
        damageTokens.addDamage(4, Character.SPROG);
        playerBoard.updateFrenzyScore(score);

        int i = score.getMap().get(Character.VIOLET);

        assertEquals(1, i);

        i = score.getMap().get(Character.DISTRUCTOR);
        //assertEquals(1, i);

        //TODO verificare chi prende due punti coi danni in frenzy

        i = score.getMap().get(Character.SPROG);
        assertEquals(2, i);
    }

    @Test
    public  void createPlayerboardTest() {

        PlayerBoard playerBoard = new PlayerBoard();

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);

        KillTokens killTokens = new KillTokens(characters);
        Score score = new Score(characters, killTokens);

        DamageTokens damageTokens = new DamageTokens(characters);
        playerBoard.setDamage(damageTokens);
        playerBoard.setMarks(new Marks(characters));

        damageTokens.addDamage(2, Character.VIOLET);
        damageTokens.addDamage(3, Character.DISTRUCTOR);
        damageTokens.addDamage(4, Character.SPROG);

        assertNotNull(playerBoard.createPlayerBoard());
    }
}
