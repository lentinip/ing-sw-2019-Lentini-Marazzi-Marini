package it.polimi.sw2019;

import it.polimi.sw2019.commons.messages.MatchSetup;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.controller.TurnManager;
import it.polimi.sw2019.model.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestTurnManager {

    /**
     * testing if spawn cells refills correctly
     */
    @Test
    public void refillSpawnCellTest(){

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
        TurnManager turnManager = controller.getTurnManager();
        Cell cell = controller.getMatch().getBoard().getField().get(2);
        cell.getWeapons().remove(2);
        cell.getWeapons().remove(1);
        turnManager.getEmptySpawnCells().add(cell);
        turnManager.refillSpawnCell();
        Assert.assertEquals(3, cell.getWeapons().size());

    }

   /**
     * simulating the end of the match
     */
    @Test
    public void endTurnTest(){

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
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().setEnded(true);
        TurnManager turnManager = controller.getTurnManager();
        turnManager.setCurrentPlayer(controller.getMatch().getCurrentPlayer());
        turnManager.endTurn();

    }
}
