package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAmmoTile {

    /**
     * verifies if I set the correct value of the boolean
     */
    @Test
    public void setPowerupTest() {

        AmmoTile ammoTile = new AmmoTile();
        Boolean powerupTest = false;

        assertFalse(ammoTile.isPowerup());
        ammoTile.setPowerup(true);
        assertTrue(ammoTile.isPowerup());
    }
}
