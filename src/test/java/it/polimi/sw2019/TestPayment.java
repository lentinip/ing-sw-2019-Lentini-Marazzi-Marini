package it.polimi.sw2019;

import it.polimi.sw2019.controller.Payment;
import it.polimi.sw2019.controller.SingleActionManager;
import it.polimi.sw2019.controller.TurnManager;
import it.polimi.sw2019.model.Match;
import it.polimi.sw2019.model.Powerup;
import it.polimi.sw2019.network.server.Server;
import it.polimi.sw2019.network.server.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestPayment {

    /**
     * this test verifies if the method reset sets the attributes leftcost, initialCost, selectedPowerups and usablePowerup to null
     */
    @Test
    public void resetTest() {

        Match match = new Match();
        VirtualView virtualView = new VirtualView(new Server());
        SingleActionManager singleActionManager = new SingleActionManager(match, virtualView, new TurnManager(match, virtualView));
        Payment payment = new Payment(match, virtualView, singleActionManager);

        payment.reset();

        assertNull(payment.getLeftCost());
        assertNull(payment.getInitialCost());
        List<Powerup> powerups = new ArrayList<>();
        assertEquals(powerups, payment.getSelectedPowerups());
        assertNull(payment.getUsablePowerup());
    }

    @Test
    public void payAndThenTest() {


    }
}
