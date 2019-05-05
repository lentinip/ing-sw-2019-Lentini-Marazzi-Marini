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
        int i = -1;
        int k = 2;

        try{
            ammo.setRed(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

        i = 4;

        try{
            ammo.setRed(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

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
        int i = -2;
        int k = 3;

        try{
            ammo.setYellow(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

        i = 7;

        try{
            ammo.setYellow(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

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
        int i = -5;
        int k = 1;

        try{
            ammo.setBlue(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

        i = 5;

        try{
            ammo.setBlue(i);
            fail();
        } catch (IllegalArgumentException e) {System.out.print("fallito\n");}

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
