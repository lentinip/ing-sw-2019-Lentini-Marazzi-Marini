package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import it.polimi.sw2019.model.Cell;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCommonCell {

    @Test
    public void setAmmoTest() {

        Cell commonCell = new Cell();

        AmmoTile ammoTile = null;
        commonCell.setAmmo(ammoTile);

        ammoTile = new AmmoTile();
        ammoTile.setYellow(1);
        ammoTile.setRed(1);
        ammoTile.setPowerup(true);

        commonCell.setAmmo(ammoTile);
        assertEquals(ammoTile, commonCell.getAmmo());
    }

    @Test
    public void setIsEmptyTest() {

        Cell commonCell = new Cell();

        commonCell.setIsEmpty(false);
        assertFalse(commonCell.isEmpty());
        commonCell.setIsEmpty(true);
        assertTrue(commonCell.isEmpty());
    }
}
