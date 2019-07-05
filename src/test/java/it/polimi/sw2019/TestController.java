package it.polimi.sw2019;

import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.model.Board;
import it.polimi.sw2019.network.messages.MatchSetup;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.Server;
import it.polimi.sw2019.network.server.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestController {

    @Test
    public void initializeMatchTest() {

        /*
        VirtualView virtualView = new VirtualView(new Server());
        List<String> usernames = new ArrayList<>();
        //usernames.add("Giulio");
        virtualView.setUsernames(usernames);

        Controller controller = new Controller(virtualView);

        Message message = new Message();
        MatchSetup matchSetup = new MatchSetup();
        matchSetup.setFrenzy(true);
        matchSetup.setEightSkulls(false);
        matchSetup.setBoardJsonName("Board1.json");
        message.createMessageMatchSetup(matchSetup);

        controller.initializeMatch(message);

        assertTrue(controller.getMatch().isiWantFrenzyMode());
        assertTrue(!controller.getMatch().isEasyMode());
        assertTrue(usernames.containsAll(controller.getMatch().getPlayers()));
    */}
}
