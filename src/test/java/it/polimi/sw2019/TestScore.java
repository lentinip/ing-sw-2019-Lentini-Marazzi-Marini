package it.polimi.sw2019;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.KillTokens;
import it.polimi.sw2019.model.Score;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

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
    public void getRankingMapTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.DOZER);

        KillTokens killTokens = new KillTokens(characters);

        Score score = new Score(characters, killTokens);
    }

}
