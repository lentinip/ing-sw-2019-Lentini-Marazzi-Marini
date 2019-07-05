package it.polimi.sw2019;

import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.commons.messages.BoardCoord;
import it.polimi.sw2019.commons.messages.MatchSetup;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.model.Match;
import it.polimi.sw2019.network.server.VirtualView;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestController {


    /**
     * testing if the controller initializes the match in the correct way
     */
    @Test
    public void initializeMatchTest(){

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

        Assert.assertNotNull(controller.getMatch());
        Assert.assertTrue(controller.getMatch().isiWantFrenzyMode());
        Assert.assertFalse(controller.getMatch().isEasyMode());
        Assert.assertEquals("prova1", controller.getMatch().getCurrentPlayer().getName());
        Assert.assertEquals("prova1", controller.getTurnManager().getCurrentPlayer().getName());
        Assert.assertNotNull(controller.getTurnManager().getView());
    }

    @Test
    public void setMatchTest(){

        Controller controller = new Controller(new VirtualView());
        Match match = new Match();
        controller.setMatch(match);

        Assert.assertEquals(controller.getMatch(), match);
    }

    /**
     * testing if the method ask send message to the virtual view simulator in the correct way for every message type
     */
    @Test
    public void askManagerTest(){

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
        controller.getMatch().getCurrentPlayer().setPosition(controller.getMatch().getBoard().getField().get(0));

        Message message = new Message("prova1");

        message.setTypeOfMessage(TypeOfMessage.ASK);
        message.setTypeOfAction(TypeOfAction.RELOAD);
        controller.askManager(message);
        Assert.assertTrue(view.getMessageReceived().deserializeAvailableCards().getAvailableCards().isEmpty());

        message.setTypeOfAction(TypeOfAction.MOVE);
        controller.askManager(message);
        Assert.assertNotNull(view.getMessageReceived().deserializeAvailableCells().getAvailableCells());

        message.setTypeOfAction(TypeOfAction.GRAB);
        controller.askManager(message);
        Assert.assertNotNull(view.getMessageReceived().deserializeAvailableCells().getAvailableCells());

        message.setTypeOfAction(TypeOfAction.SHOOT);
        controller.askManager(message);
        Assert.assertTrue(view.getMessageReceived().deserializeAvailableCards().getAvailableCards().isEmpty());

        message.setTypeOfAction(TypeOfAction.USEPOWERUP);
        controller.askManager(message);
    }

    /**
     * testing update to see if controller does the correct calls
     */
    @Test
    public void updateTest(){

        MatchSetup matchSetup = new MatchSetup(true, false, "Board1.json");
        Message mes = new Message("prova1");
        mes.createMessageMatchSetup(matchSetup);
        List<String> usernames = new ArrayList<>();
        usernames.add("prova1");
        usernames.add("prova2");
        usernames.add("prova3");
        VirtualViewSimulator view = new VirtualViewSimulator(usernames);
        Controller controller = view.getController();


        controller.update(view, mes);

        Message message = new Message("prova1");

        message.setTypeOfMessage(TypeOfMessage.ASK);
        message.setTypeOfAction(TypeOfAction.SHOOT);
        controller.update(view, message);

        message.setTypeOfMessage(TypeOfMessage.SINGLE_ACTION);
        message.setTypeOfAction(TypeOfAction.MOVE);
        message.createSelectedCellMessage(new BoardCoord(0,0), TypeOfAction.MOVE, TypeOfMessage.SINGLE_ACTION);
        controller.update(view, message);

        controller.getMatch().getCurrentPlayer().addPowerup(controller.getMatch().getBoard().drawPowerup());
        message.setTypeOfMessage(TypeOfMessage.SELECTED_CARD);
        message.createSelectionForUsePowerup(0);
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        controller.getMatch().getCurrentPlayer().getWeapons().get(0).setOwner(controller.getMatch().getCurrentPlayer());
        controller.getTurnManager().getSingleActionManager().getChoices().setSelectedWeapon(controller.getMatch().getCurrentPlayer().getWeapons().get(0));
        controller.getTurnManager().getSingleActionManager().getChoices().setCurrentEffect(controller.getMatch().getCurrentPlayer().getWeapons().get(0).getEffects().get(0));
        controller.update(view, message);

        message.setTypeOfAction(null);
        message.setTypeOfMessage(null);
        controller.update(view, message);
        Assert.assertEquals(TypeOfMessage.ACTION_REPORT, view.getMessageReceived().getTypeOfMessage());
    }








}
