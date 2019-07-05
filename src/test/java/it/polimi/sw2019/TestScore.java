package it.polimi.sw2019;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.KillTokens;
import it.polimi.sw2019.model.Score;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Mi97ch, lentinip
 */
public class TestScore {

    /**
     * verifies if the method adds the correct number of points in the map
     */
    @Test
    public void addPointsTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.SPROG);
        characters.add(Character.DOZER);

        KillTokens killTrack = new KillTokens(characters);

        Score score = new Score(characters, killTrack);

        score.addPoints(3, Character.DISTRUCTOR);

        int i = score.getMap().get(Character.DISTRUCTOR);

        assertEquals(3, i);
    }



    @Test
    public void getRankingMapTest0() {

        //Case same points but different killTrack
        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);

        KillTokens killTokens = new KillTokens(characters);
        killTokens.addKill(Character.SPROG);
        killTokens.addKill(Character.SPROG);

        killTokens.addOverkill(Character.DISTRUCTOR);

        Score score = new Score(characters, killTokens);
        score.addPoints(2, Character.SPROG);
        score.addPoints(2, Character.DISTRUCTOR);
        score.addPoints(2, Character.DOZER);

        killTokens.updateScore(score);

        Map<Character, Integer> ranking = score.getRankingMap(new ArrayList<>());
        assertEquals(1, ranking.get(Character.SPROG).intValue());
        assertEquals(2, ranking.get(Character.DISTRUCTOR).intValue());
        assertEquals(3, ranking.get(Character.DOZER).intValue());
    }

    @Test
    public void getRankingTest1(){

        //Case same points
        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);

        KillTokens killTokens = new KillTokens(characters);

        Score score = new Score(characters, killTokens);
        score.addPoints(1, Character.SPROG);
        score.addPoints(2, Character.DISTRUCTOR);
        score.addPoints(2, Character.DOZER);

        Map<Character, Integer> ranking = score.getRankingMap(new ArrayList<>());
        assertEquals(2, ranking.get(Character.SPROG).intValue());
        assertEquals(1, ranking.get(Character.DISTRUCTOR).intValue());
        assertEquals(1, ranking.get(Character.DOZER).intValue());
    }

    @Test
    public void getRankingTest2(){

        //Case offline players
        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);

        KillTokens killTokens = new KillTokens(characters);

        Score score = new Score(characters, killTokens);
        score.addPoints(5, Character.SPROG);
        score.addPoints(1, Character.DISTRUCTOR);
        score.addPoints(5, Character.DOZER);

        List<Character> offlinePlayers = new ArrayList<>();
        offlinePlayers.add(Character.SPROG);

        Map<Character, Integer> ranking = score.getRankingMap(offlinePlayers);
        assertEquals(2, ranking.get(Character.DISTRUCTOR).intValue());
        assertEquals(1, ranking.get(Character.DOZER).intValue());
    }

    @Test
    public void getRankingTest3(){

        //Case 5 players
        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);
        characters.add(Character.VIOLET);
        characters.add(Character.BANSHEE);

        KillTokens killTokens = new KillTokens(characters);

        Score score = new Score(characters, killTokens);
        score.addPoints(2, Character.SPROG);
        score.addPoints(1, Character.DISTRUCTOR);
        score.addPoints(5, Character.DOZER);
        score.addPoints(3, Character.BANSHEE);
        score.addPoints(6, Character.VIOLET);

        Map<Character, Integer> expectation = new LinkedHashMap<>();
        expectation.put(Character.VIOLET,1);
        expectation.put(Character.DOZER, 2);
        expectation.put(Character.BANSHEE, 3);
        expectation.put(Character.SPROG, 4);
        expectation.put(Character.DISTRUCTOR, 5);

        Map<Character, Integer> ranking = score.getRankingMap(new ArrayList<>());
        assertEquals(expectation, ranking);
    }
}
