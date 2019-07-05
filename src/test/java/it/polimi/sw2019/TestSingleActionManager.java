package it.polimi.sw2019;

import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.commons.messages.*;
import it.polimi.sw2019.controller.Choices;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.controller.SingleActionManager;
import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.Effect;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Weapon;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestSingleActionManager {

    /**
     * method to test the success of every single action message
     */
    @Test
    public void testSingleActionHandler() {

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
        SingleActionManager singleActionManager = controller.getTurnManager().getSingleActionManager();

        Message message = new Message("prova1");
        message.createSelectedCellMessage(new BoardCoord(0,0), TypeOfAction.MOVE, TypeOfMessage.SINGLE_ACTION);
        singleActionManager.singleActionHandler(message);

        message.createSelectedCellMessage(new BoardCoord(0,0), TypeOfAction.GRAB, TypeOfMessage.SINGLE_ACTION);
        singleActionManager.singleActionHandler(message);

        message.createSelectedCellMessage(new BoardCoord(0,2), TypeOfAction.GRAB, TypeOfMessage.SINGLE_ACTION);
        singleActionManager.singleActionHandler(message);

        message.setTypeOfAction(TypeOfAction.RELOAD);
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        message.createSingleActionReload(0);
        singleActionManager.singleActionHandler(message);

        message.setTypeOfAction(TypeOfAction.ENDTURN);
        singleActionManager.singleActionHandler(message);



        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().setCurrentPlayer(controller.getMatch().getPlayerByUsername("prova1"));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        message.setUsername("prova1");
        Player prova1 = controller.getMatch().getPlayerByUsername("prova1");
        prova1.getWeapons().get(0).setIsLoaded(true);
        singleActionManager.setMatch(controller.getMatch());
        prova1.getWeapons().get(0).setOwner(prova1);
        message.createSelectedCellMessage(new BoardCoord(0, 0), TypeOfAction.MOVEBEFORESHOOT, TypeOfMessage.SINGLE_ACTION);
        singleActionManager.singleActionHandler(message);

    }

    /**
     * method to test the grab of a Weapon
     */
    @Test
    public void grabWeaponHandlerTest(){

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
        SingleActionManager singleActionManager = controller.getTurnManager().getSingleActionManager();
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());

        Message message = new Message("prova1");
        message.createSingleActionGrabWeapon(new GrabWeapon(0, 0, new BoardCoord(0, 2)));
        singleActionManager.grabWeaponHandler(message);

        message.createSingleActionGrabWeapon(new GrabWeapon(0, -1, new BoardCoord(0, 2)));
        singleActionManager.grabWeaponHandler(message);
    }

    /**
     * method to test use of a powerup
     */
    @Test
    public void usePowerupHandlerTest(){

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
        SingleActionManager singleActionManager = controller.getTurnManager().getSingleActionManager();

        Message message = new Message("prova1");
        message.createSelectedCellMessage(new BoardCoord(0, 0), null, null);
        singleActionManager.usePowerupHandler(message);

        singleActionManager.getChoices().setSelectedPlayer(controller.getMatch().getPlayers().get(0));
        message.createSelectedCellMessage(new BoardCoord(0, 0), null, null);
        singleActionManager.usePowerupHandler(message);
    }

    /**
     * method to test the correct reload of a Weapon
     */
    @Test
    public void reloadHandlerTest(){

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
        SingleActionManager singleActionManager = controller.getTurnManager().getSingleActionManager();
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());

        Message message = new Message("prova1");
        message.createSelectedCard(0, TypeOfAction.RELOAD);
        singleActionManager.reloadHandler(message);
    }

    @Test
    public void movebeforeSAhootHandlerTest(){

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
        controller.getMatch().getCurrentPlayer().getPowerups().add(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Choices choices = controller.getTurnManager().getSingleActionManager().getChoices();
        controller.getTurnManager().getSingleActionManager().setMatch(controller.getMatch());
        prova1.getPlayerBoard().setAmmo(new Ammo(10000, 10000, 10000));
        Weapon specificWeapon = new Weapon();
        Effect specificEffect = new Effect();
        SingleActionManager singleActionManager = controller.getTurnManager().getSingleActionManager();

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            for(Effect effect: weapon.getEffects()){

                if (effect.getMove() != null && effect.getMove().isMoveTargetBefore()){

                    choices.setSelectedPlayer(prova1);
                    choices.setShootedPlayers(new ArrayList<>());
                    specificWeapon = weapon;
                    choices.setSelectedWeapon(weapon);
                    specificEffect = effect;
                    effect.getTargets().setDamages(new int[]{ 1,2,3,4,5,6,7,8,9,10 });
                    effect.getTargets().setMarks(new int[]{ 1,2,3,4,5,6,7,8,9,10 });
                    choices.setCurrentEffect(effect);
                    mes.createSelectedCellMessage(new BoardCoord(0,0), TypeOfAction.MOVEBEFORESHOOT, TypeOfMessage.SINGLE_ACTION);
                    singleActionManager.moveBeforeShootHandler(mes);
                }
            }
        }

    }

}
