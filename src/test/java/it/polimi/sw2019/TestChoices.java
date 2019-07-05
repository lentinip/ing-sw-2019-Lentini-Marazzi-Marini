package it.polimi.sw2019;

import it.polimi.sw2019.commons.Colors;
import it.polimi.sw2019.commons.TypeOfAction;
import it.polimi.sw2019.commons.messages.BoardCoord;
import it.polimi.sw2019.commons.messages.MatchSetup;
import it.polimi.sw2019.commons.messages.Message;
import it.polimi.sw2019.commons.messages.TypeOfMessage;
import it.polimi.sw2019.controller.Choices;
import it.polimi.sw2019.controller.Controller;
import it.polimi.sw2019.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestChoices {

    /**
     * method to test Reset
     */
    @Test
    public void resetTest(){

        Choices choices = new Choices();

        choices.setSelectedPlayer(new Player());
        choices.setSelectedWeapon(new Weapon());
        choices.reset();
        Assert.assertNull(choices.getSelectedPlayer());
        choices.resetEverything();
        Assert.assertNull(choices.getSelectedWeapon());
    }


    /**
     * method to test if choices handles correctly all the possibly selections
     */
    @Test
    public void selectionHandlerTest(){

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
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        Player prova1 = controller.getMatch().getCurrentPlayer();
        prova1.getWeapons().get(0).setOwner(prova1);
        controller.getMatch().getCurrentPlayer().getPowerups().add(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Choices choices = controller.getTurnManager().getSingleActionManager().getChoices();
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        choices.setCurrentEffect(prova1.getWeapons().get(0).getEffects().get(0));
        controller.getTurnManager().getSingleActionManager().setMatch(controller.getMatch());
        prova1.getPlayerBoard().setAmmo(new Ammo(10000, 10000, 10000));

        Message message = new Message("prova1");
        message.createSelectedCard(0, TypeOfAction.SPAWN);
        choices.selectionHandler(message);

        for (Powerup powerup: controller.getMatch().getBoard().getPowerupsDeck()) {
            prova1.getPowerups().add(0, powerup);
            message.createSelectedCard(0, TypeOfAction.USEPOWERUP);
            choices.setSelectedWeapon(prova1.getWeapons().get(0));
            choices.selectionHandler(message);
        }

        message.createSelectedCard(-1, TypeOfAction.USEPOWERUP);
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        choices.selectionHandler(message);

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()) {
             weapon.setOwner(prova1);
             prova1.getWeapons().add(0, weapon);
             message.createSelectedCard(0, TypeOfAction.SHOOT);
             choices.setSelectedWeapon(prova1.getWeapons().get(0));
             choices.selectionHandler(message);
        }

        choices.getPayment().setPayingPlayer(prova1);
        choices.getPayment().setLeftCost(new Ammo(0, 0, 1));
        choices.getPayment().setSelectedPowerups(new ArrayList<>());
        choices.getPayment().setUsablePowerup(new Powerup());
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        message.createColorSelection(Colors.BLUE);
        choices.selectionHandler(message);
        choices.getPayment().setUsablePowerup(null);

        message.createSelectedEffect(-1);
        message.setTypeOfMessage(TypeOfMessage.SELECTED_EFFECT);
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        choices.selectionHandler(message);

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()) {

            for (Effect effect : weapon.getEffects()) {

                choices.getPayment().setPayingPlayer(prova1);
                choices.getPayment().setLeftCost(new Ammo(0, 0, 1));
                choices.getPayment().setInitialCost(new Ammo(0, 0, 0));
                choices.setCurrentEffect(prova1.getWeapons().get(0).getEffects().get(0));
                prova1.getWeapons().add(0, weapon);
                choices.setSelectedWeapon(prova1.getWeapons().get(0));
                message.createSelectedEffect(weapon.getEffects().indexOf(effect));
                message.setTypeOfMessage(TypeOfMessage.SELECTED_EFFECT);
                choices.selectionHandler(message);
            }
        }

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()) {

            for (Effect effect: weapon.getEffects()) {
                choices.setCurrentEffect(effect);
                choices.setSelectedWeapon(weapon);
                choices.setCurrentEffect(prova1.getWeapons().get(0).getEffects().get(0));
                choices.setSelectedWeapon(prova1.getWeapons().get(0));
                message.createSelectedCellMessage(new BoardCoord(0, 0), TypeOfAction.SHOOT, TypeOfMessage.SELECTED_CELL);
                choices.selectionHandler(message);
            }
        }

    }

    /**
     * method to test the selection of a player
     */
    @Test
    public void selectionPlayerHandlerTest(){

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
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        Player prova1 = controller.getMatch().getCurrentPlayer();
        prova1.getWeapons().get(0).setOwner(prova1);
        controller.getMatch().getCurrentPlayer().getPowerups().add(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Choices choices = controller.getTurnManager().getSingleActionManager().getChoices();
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        choices.setCurrentEffect(prova1.getWeapons().get(0).getEffects().get(0));
        controller.getTurnManager().getSingleActionManager().setMatch(controller.getMatch());
        prova1.getPlayerBoard().setAmmo(new Ammo(10000, 10000, 10000));

        Message message = new Message("prova1");
        choices.setSelectedPowerup(prova1.getPowerups().get(0));
        message.createSelectedPlayer(1, TypeOfAction.USEPOWERUP);
        choices.selectionPlayerHandler(message);


        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            for(Effect effect: weapon.getEffects()){

                if (effect.getMove() != null){

                    choices.setSelectedWeapon(weapon);
                    choices.setCurrentEffect(effect);
                    weapon.setOwner(prova1);
                    choices.selectionPlayerHandler(message);
                    message.createSelectedPlayer(1, TypeOfAction.MOVEBEFORESHOOT);
                }
                break;
            }
        }

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            for(Effect effect: weapon.getEffects()){

                if (effect.getMove() != null){

                    choices.setSelectedWeapon(weapon);
                    choices.setCurrentEffect(effect);
                    weapon.setOwner(prova1);
                    choices.selectionPlayerHandler(message);
                }
                break;
            }
        }

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            for(Effect effect: weapon.getEffects()){

                if (effect.getTargets().getDamages()[0] > 0){

                    choices.setSelectedWeapon(weapon);
                    choices.setCurrentEffect(effect);
                    weapon.setOwner(prova1);
                    message.createSelectedPlayer(1, TypeOfAction.SHOOT);
                    choices.setShootedPlayers(new ArrayList<>());
                    choices.selectionPlayerHandler(message);
                }
                break;
            }
        }

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            for(Effect effect: weapon.getEffects()){

                if (effect.getTargets().getDamages()[0] > 0 && effect.getType() == EffectsKind.MULTIPLE_TARGET){

                    choices.setSelectedWeapon(weapon);
                    choices.setCurrentEffect(effect);
                    weapon.setOwner(prova1);
                    message.createSelectedPlayer(-1, TypeOfAction.SHOOT);
                    choices.selectionPlayerHandler(message);
                }
                break;
            }
        }
    }

    @Test
    public void availablePlayersToMoveTest(){

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
        controller.getMatch().getCurrentPlayer().getWeapons().add(controller.getMatch().getBoard().drawWeapon());
        Player prova1 = controller.getMatch().getCurrentPlayer();
        prova1.getWeapons().get(0).setOwner(prova1);
        controller.getMatch().getCurrentPlayer().getPowerups().add(controller.getMatch().getBoard().drawPowerup());
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Choices choices = controller.getTurnManager().getSingleActionManager().getChoices();
        choices.setSelectedWeapon(prova1.getWeapons().get(0));
        choices.setCurrentEffect(prova1.getWeapons().get(0).getEffects().get(0));
        controller.getTurnManager().getSingleActionManager().setMatch(controller.getMatch());

        choices.availablePlayersToMove();
    }

    @Test
    public void setMoveChoiceTest(){
        Choices choices = new Choices();
        choices.setMoveCell(new Cell());
        Assert.assertNotNull(choices.getMoveCell());
    }

    /**
     * vortex test
     */
    @Test
    public void moveBeforeShootHandlerTest(){

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
        controller.getMatch().getPlayerByUsername("prova1").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova2").setPosition(controller.getMatch().getBoard().getField().get(0));
        controller.getMatch().getPlayerByUsername("prova3").setPosition(controller.getMatch().getBoard().getField().get(0));
        Choices choices = controller.getTurnManager().getSingleActionManager().getChoices();
        controller.getTurnManager().getSingleActionManager().setMatch(controller.getMatch());
        List<Player> movedPlayers = new ArrayList<>();
        movedPlayers.add(prova1);
        choices.setMovedPlayers(movedPlayers);

        for (Weapon weapon: controller.getMatch().getBoard().getWeaponsDeck()){

            if (weapon.getName().equals("VORTEX CANNON")){

                    choices.setCurrentEffect(weapon.getEffects().get(1));
                    choices.setMoveCell(controller.getMatch().getBoard().getField().get(1));
                    choices.setSelectedWeapon(weapon);
                    Message message = new Message("prova1");
                    message.createSelectedPlayer(0, TypeOfAction.MOVEBEFORESHOOT);
                    choices.moveBeforeShootHandler(message);

            }
        }
    }





}
