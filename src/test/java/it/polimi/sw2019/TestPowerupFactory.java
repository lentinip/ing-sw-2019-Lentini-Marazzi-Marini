package it.polimi.sw2019;

import it.polimi.sw2019.model.Powerup;
import it.polimi.sw2019.model.PowerupFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestPowerupFactory {

    @Test
    public void createPowerupsTest() {

        PowerupFactory powerupFactory = new PowerupFactory();
        Powerup powerup = new Powerup();
        powerupFactory.setPowerup(powerup);
        powerupFactory.setQuantity(3);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup);
        powerups.add(powerup);
        powerups.add(powerup);

        assertEquals(powerupFactory.createPowerups(), powerups);
    }
}
