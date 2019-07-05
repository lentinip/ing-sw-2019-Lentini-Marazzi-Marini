package it.polimi.sw2019;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.KillTokens;
import it.polimi.sw2019.model.Score;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestKillTokens {

    @Test
    public void addKillTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DOZER);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.VIOLET);
        KillTokens killTokens = new KillTokens(characters);

        killTokens.addKill(Character.BANSHEE);
        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.BANSHEE);
        List<Boolean> overkillSequence = new ArrayList<>();
        overkillSequence.add(false);
        assertEquals(resultCharacters, killTokens.getKillSequence());
        assertEquals(overkillSequence, killTokens.getOverkillSequence());
        assertEquals(1, killTokens.getTotalKills());

        killTokens.addKill(Character.DISTRUCTOR);
        resultCharacters.add(Character.DISTRUCTOR);
        overkillSequence.add(false);
        assertEquals(resultCharacters, killTokens.getKillSequence());
        assertEquals(overkillSequence, killTokens.getOverkillSequence());
        assertEquals(2, killTokens.getTotalKills());
    }

    @Test
    public void addOverkillTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DOZER);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);
        characters.add(Character.VIOLET);
        KillTokens killTokens = new KillTokens(characters);

        killTokens.addOverkill(Character.BANSHEE);
        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.BANSHEE);
        List<Boolean> overkillSequence = new ArrayList<>();
        overkillSequence.add(true);
        assertEquals(resultCharacters, killTokens.getKillSequence());
        assertEquals(overkillSequence, killTokens.getOverkillSequence());
        assertEquals(1, killTokens.getTotalKills());

        killTokens.addOverkill(Character.DISTRUCTOR);
        resultCharacters.add(Character.DISTRUCTOR);
        overkillSequence.add(true);
        assertEquals(resultCharacters, killTokens.getKillSequence());
        assertEquals(overkillSequence, killTokens.getOverkillSequence());
        assertEquals(2, killTokens.getTotalKills());
    }

    @Test
    public void getRankingTest() {

        List<Character> resultCharacters = new ArrayList<>();
        resultCharacters.add(Character.DOZER);
        resultCharacters.add(Character.BANSHEE);
        resultCharacters.add(Character.DISTRUCTOR);
        resultCharacters.add(Character.VIOLET);
        resultCharacters.add(Character.SPROG);

        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);

        KillTokens killTokens = new KillTokens(characters);

        killTokens.addOverkill(Character.DOZER);
        killTokens.addOverkill(Character.BANSHEE);
        killTokens.addKill(Character.DISTRUCTOR);
        killTokens.addKill(Character.BANSHEE);
        killTokens.addOverkill(Character.DISTRUCTOR);
        killTokens.addOverkill(Character.DOZER);
        killTokens.addOverkill(Character.DOZER);
        killTokens.addKill(Character.VIOLET);

        resultCharacters.remove(Character.SPROG);

        assertEquals(resultCharacters, killTokens.getRanking());
    }

    @Test
    public void updateScoreTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.VIOLET);
        characters.add(Character.SPROG);

        KillTokens killTokens = new KillTokens(characters);

        Score score = new Score(characters, killTokens);
        killTokens.updateScore(score);

        killTokens.addKill(Character.DISTRUCTOR);
        killTokens.addKill(Character.DISTRUCTOR);
        killTokens.addKill(Character.SPROG);

        killTokens.updateScore(score);

        int i = score.getMap().get(Character.DISTRUCTOR);

        assertEquals(8, i);

        int k = score.getMap().get(Character.SPROG);

        assertEquals(6, k);
    }

    @Test
    public void getRankingTest1() {
        //Checks if the ranking is empty if nobody did a single kill
        List<Character> characters = new ArrayList<>();
        characters.add(Character.BANSHEE);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);

        KillTokens killTokens = new KillTokens(characters);

        List<Character> ranking = killTokens.getRanking();
        List<Character> expectedResult = new ArrayList<>();

        assertEquals(expectedResult, ranking);
    }
}
