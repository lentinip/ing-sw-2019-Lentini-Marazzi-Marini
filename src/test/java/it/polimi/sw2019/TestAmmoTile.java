package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAmmoTile {

    @Test
    public void setPowerupTest() {

        AmmoTile ammoTile = new AmmoTile();
        Boolean powerupTest = false;

        assertFalse(ammoTile.isPowerup());
        ammoTile.setPowerup(true);
        assertTrue(ammoTile.isPowerup());
    }

    @Test
    public void setAmmoTileImgTest() {

        String ammoTileImg = new String();

        AmmoTile ammoTile = new AmmoTile();

        ammoTile.setAmmoTileImg(ammoTileImg);

        assertEquals(ammoTileImg, ammoTile.getAmmoTileImg());
    }

    @Test
    public void setAmmoTileStructureTest() {

        AmmoTile ammoTile = new AmmoTile();
        ammoTile.setAmmoTileStructure(0, 1, 2, true);

        assertEquals(0, ammoTile.getRed());
        assertEquals(1, ammoTile.getBlue());
        assertEquals(2, ammoTile.getYellow());
        assertTrue(ammoTile.isPowerup());
    }
}
