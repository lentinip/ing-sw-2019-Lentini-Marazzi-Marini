package it.polimi.sw2019;

import it.polimi.sw2019.controller.Payment;
import it.polimi.sw2019.controller.SingleActionManager;
import it.polimi.sw2019.controller.TurnManager;
import it.polimi.sw2019.model.Match;
import it.polimi.sw2019.model.Powerup;
import it.polimi.sw2019.network.server.Server;
import it.polimi.sw2019.network.server.VirtualView;
import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.commons.messages.MatchSetup;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.controller.Payment;
import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.Player;
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
    public void payAndThenUsePowerupTest() {

        MatchSetup matchSetup = new MatchSetup(true, false, "Board1.json");
        Message mes = new Message("prova1");
        mes.createMessageMatchSetup(matchSetup);
        List<String> usernames = new ArrayList<>();
        usernames.add("prova1");
        usernames.add("prova2");
        usernames.add("prova3");
        VirtualViewSimulator view = new VirtualViewSimulator(usernames);
        Controller controller = view.getController();
        controller.initializeMatch(mes);
        Player prova1 = controller.getMatch().getCurrentPlayer();
        Payment payment = controller.getTurnManager().getSingleActionManager().getPayment();
        payment.setPayingPlayer(prova1);
        prova1.addPowerup(controller.getMatch().getBoard().drawPowerup());
        payment.payAndThanUsePowerup(0);

    }


    @Test
    public void payAndContinueTest() {

        MatchSetup matchSetup = new MatchSetup(true, false, "Board1.json");
        Message mes = new Message("prova1");
        mes.createMessageMatchSetup(matchSetup);
        List<String> usernames = new ArrayList<>();
        usernames.add("prova1");
        usernames.add("prova2");
        usernames.add("prova3");
        VirtualViewSimulator view = new VirtualViewSimulator(usernames);
        Controller controller = view.getController();
        controller.initializeMatch(mes);
        Player prova1 = controller.getMatch().getCurrentPlayer();
        Payment payment = controller.getTurnManager().getSingleActionManager().getPayment();
        payment.setPayingPlayer(prova1);
        prova1.addPowerup(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        prova1.getWeapons().get(0).setOwner(prova1);
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Message message = new Message("prova1");
        message.createSingleActionReload(0);
        payment.setPendingMessage(message);
        payment.setInitialCost(new Ammo(0, 0, 0));

        payment.setReloadInFrenzy(true);
        payment.payAndContinue();

        payment.setPayingPlayer(prova1);
        prova1.addPowerup(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        prova1.getWeapons().get(0).setOwner(prova1);
        message.createSingleActionReload(0);
        payment.setPendingMessage(message);
        payment.setInitialCost(new Ammo(0, 0, 0));
        payment.setReloadInFrenzy(false);
        payment.payAndContinue();

        message.setTypeOfAction(TypeOfAction.MOVE);
        payment.setPendingMessage(message);
        payment.setPayingPlayer(prova1);
        prova1.addPowerup(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        prova1.getWeapons().get(0).setOwner(prova1);
        payment.setInitialCost(new Ammo(0, 0, 0));
        payment.setReloadInFrenzy(false);
        payment.payAndContinue();

    }
}
