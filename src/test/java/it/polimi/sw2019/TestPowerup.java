package it.polimi.sw2019;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.Colors;
import it.polimi.sw2019.model.Powerup;
import it.polimi.sw2019.model.TypeOfAction;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestPowerup {

    @Test
    public void useToPayTest() {

        Powerup powerup = new Powerup("Lancia", Colors.YELLOW);

        Ammo ammo = new Ammo(0, 1, 1);

        assertTrue(powerup.useToPay(ammo));

        Ammo ammo1 = new Ammo();

        assertFalse(powerup.useToPay(ammo1));
    }

    @Test
    public void setiNeedToPayTest() {

        Powerup powerup = new Powerup();

        assertFalse(powerup.isiNeedToPay());

        powerup.setiNeedToPay(true);

        assertTrue(powerup.isiNeedToPay());
    }

    @Test
    public void setDuringDamageActionTest() {

        Powerup powerup = new Powerup();

        assertFalse(powerup.isDuringDamageAction());

        powerup.setDuringDamageAction(true);

        assertTrue(powerup.isDuringDamageAction());
    }

    @Test
    public void setIsDuringYourturnTest() {

        Powerup powerup = new Powerup();

        assertFalse(powerup.isDuringYourTurn());

        powerup.setDuringYourTurn(true);

        assertTrue(powerup.isDuringYourTurn());
    }

    @Test
    public void setValueTest() {

        Powerup powerup = new Powerup();

        powerup.setValue(5);

        assertEquals(5, powerup.getValue());
    }

    @Test
    public void setDescriptionTest() {

        Powerup powerup = new Powerup();

        powerup.setDescription("powerup");

        assertEquals("powerup", powerup.getDescription());
    }

    /*@Test
    public void setTypeOfActionTest() {

        Powerup powerup = new Powerup();

        powerup.setTypeOfAction(TypeOfAction.RELOAD);

        assertEquals(TypeOfAction.RELOAD, powerup.getTypeOfAction());
    }*/
}
