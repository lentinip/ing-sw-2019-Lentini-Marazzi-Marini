package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCommonCell {

    @Test
    public void setAmmoTest() {

        CommonCell commonCell = new CommonCell();

        AmmoTile ammoTile = null;
        try{
            commonCell.setAmmo(ammoTile);
            fail();
        } catch (NullPointerException e) {System.out.print("fallito");}

        ammoTile = new AmmoTile();
        ammoTile.setYellow(1);
        ammoTile.setRed(1);
        ammoTile.setPowerup(true);

        commonCell.setAmmo(ammoTile);
        assertEquals(ammoTile, commonCell.getAmmo());
    }

    @Test
    public void setIsEmptyTest() {

        CommonCell commonCell = new CommonCell();

        commonCell.setIsEmpty(false);
        assertFalse(commonCell.isEmpty());
        commonCell.setIsEmpty(true);
        assertTrue(commonCell.isEmpty());
    }
}
