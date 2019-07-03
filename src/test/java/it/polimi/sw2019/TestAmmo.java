package it.polimi.sw2019;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.Colors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAmmo {

    @Test
    public void setRedTest() {

        Ammo ammo = new Ammo();
        int i;
        int k=2;

        i = 2;

        ammo.setRed(i);
        assertEquals(k, ammo.getRed());

        k = 3;

        ammo.addRed(1);
        assertEquals(k, ammo.getRed());
        ammo.addRed(1);
        assertEquals(k, ammo.getRed());
    }

    @Test
    public void thirdConstructorTest() {

        Ammo ammo = new Ammo(Colors.YELLOW);

        assertEquals(1, ammo.getYellow());
        assertEquals(0, ammo.getBlue());
        assertEquals(0, ammo.getRed());
    }

    @Test
    public void setYellowTest() {

        Ammo ammo = new Ammo();
        int i;
        int k=3;

        i = 3;

        ammo.setYellow(i);
        assertEquals(k, ammo.getYellow());

        i = 2;

        ammo.addYellow(1);
        assertEquals(k, ammo.getYellow());
        ammo.addYellow(1);
        assertEquals(k, ammo.getYellow());
    }

    @Test
    public void setBlueTest() {

        Ammo ammo = new Ammo();
        int i;
        int k=1;

        i = 1;

        ammo.setBlue(i);
        assertEquals(k, ammo.getBlue());

        k = 2;

        ammo.addBlue(1);
        assertEquals(k, ammo.getBlue());

        k = 3;
        ammo.addBlue(2);
        assertEquals(k, ammo.getBlue());
    }

    @Test
    public void ammoSubtractionTest() {

        Ammo cost = new Ammo(0, 1, 2);

        Ammo ammo = new Ammo(1, 3, 3);

        ammo.ammoSubtraction(cost);

        assertEquals(2, ammo.getYellow());
        assertEquals(1, ammo.getBlue());
        assertEquals(1, ammo.getRed());
    }

    @Test
    public void isZeroTest() {

        Ammo ammo = new Ammo();

        assertTrue(ammo.isZero());

        ammo.setYellow(5);

        assertFalse(ammo.isZero());
    }

    @Test
    public void copyTest() {

        Ammo ammo = new Ammo(2, 0, 0);

        assertEquals(ammo.getYellow(), ammo.copy().getYellow());
        assertEquals(ammo.getRed(), ammo.copy().getRed());
        assertEquals(ammo.getBlue(), ammo.copy().getBlue());
    }
}
