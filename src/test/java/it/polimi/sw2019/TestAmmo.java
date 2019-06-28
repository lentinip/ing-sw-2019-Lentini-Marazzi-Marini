package it.polimi.sw2019;

import it.polimi.sw2019.model.Ammo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
}
