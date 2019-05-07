package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAmmoTile {

    @Test
    public void setPowerupTest() {

        AmmoTile ammoTile = new AmmoTile();
        Boolean powerupTest = false;

        assertFalse(ammoTile.isPowerup());
        ammoTile.setPowerup(true);
        assertTrue(ammoTile.isPowerup());
    }
}
