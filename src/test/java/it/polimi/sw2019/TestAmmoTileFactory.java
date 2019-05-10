package it.polimi.sw2019;

import it.polimi.sw2019.model.AmmoTile;
import it.polimi.sw2019.model.AmmoTileFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestAmmoTileFactory {

    @Test
    public void createAmmoTilesTest() {

        AmmoTile ammoTile1 = new AmmoTile(2, 0, 0, true);

        List<AmmoTile> resultAmmoTiles = new ArrayList<>();
        resultAmmoTiles.add(ammoTile1);
        resultAmmoTiles.add(ammoTile1);
        resultAmmoTiles.add(ammoTile1);

        AmmoTileFactory ammoTileFactory = new AmmoTileFactory(ammoTile1, 3);

        assertEquals(resultAmmoTiles, ammoTileFactory.createAmmoTiles());
    }

    @Test
    public void setQuantityTest() {

        AmmoTileFactory ammoTileFactory = new AmmoTileFactory();
        ammoTileFactory.setQuantity(3);

        assertEquals(3, ammoTileFactory.getQuantity());
    }

    @Test
    public void setAmmoTileTest() {

        AmmoTile ammoTile1 = new AmmoTile(2, 0, 0, true);

        AmmoTileFactory ammoTileFactory = new AmmoTileFactory();
        ammoTileFactory.setAmmoTile(ammoTile1);

        assertEquals(ammoTile1, ammoTileFactory.getAmmoTile());
    }
}

