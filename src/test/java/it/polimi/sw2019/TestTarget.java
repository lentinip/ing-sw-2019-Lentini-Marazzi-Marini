package it.polimi.sw2019;

import it.polimi.sw2019.model.Target;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTarget {

    @Test
    public void setDamagesTest() {

        Target target = new Target();

        int[] damages = new int[4];
        damages[0] = 2;
        damages[2] = 1;

        target.setDamages(damages);
        assertEquals(damages, target.getDamages());
    }

    @Test
    public void setMarksTest() {

        Target target = new Target();

        int[] marks = new int[4];
        marks[0] = 1;
        marks[1] = 1;

        target.setMarks(marks);
        assertEquals(marks, target.getMarks());
    }

    @Test
    public void setDifferentSquaresTest() {

        Target target = new Target();

        target.setDifferentSquares(true);

        assertTrue(target.isDifferentSquares());
    }

    @Test
    public void setDifferentPlayersTest() {

        Target target = new Target();

        target.setDifferentPlayers(true);

        assertTrue(target.isDifferentPlayers());
    }

    @Test
    public void setMaxTargetsTest() {

        Target target = new Target();

        target.setMaxTargets(2);

        assertEquals(2, target.getMaxTargets());
    }
}
