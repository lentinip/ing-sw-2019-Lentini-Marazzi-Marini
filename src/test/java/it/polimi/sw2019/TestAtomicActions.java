package it.polimi.sw2019;

import it.polimi.sw2019.controller.AtomicActions;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.client.rmi.ClientImplementation;
import it.polimi.sw2019.network.server.Client;
import it.polimi.sw2019.network.server.Server;
import it.polimi.sw2019.network.server.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class TestAtomicActions {

    /**
     * verifies if the player has been moved in the selected cell.
     * There is also a commented line in the code at line 42-43 with a print to verify message parameters
     */
    @Test
    public void moveTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Cell cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);

        atomicActions.move(player, cell1);

        assertEquals(cell1, player.getPosition());
    }

    /**
     * verifies if the player receive the correct amount of ammo and verifies if he can get the powerup.
     * There's also a commented print in the code of the method grab to verify if the message gets the correct parameters
     */
    @Test
    public void grabTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Cell cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);
        Ammo ammo = new Ammo(1, 1, 0);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);
        AmmoTile ammoTile = new AmmoTile(0, 1, 1, true);
        cell1.setAmmo(ammoTile);

        atomicActions.grab(player, cell1);

        assertEquals(0, player.getPlayerBoard().getAmmo().getBlue());
        assertEquals(2, player.getPlayerBoard().getAmmo().getRed());
        assertEquals(2, player.getPlayerBoard().getAmmo().getYellow());
        assertEquals(1, player.getPowerups().size());
    }

    /**
     * verifies if the player has been moved to correct cell, then verifies if he has the correct weapon (and also if the weapon is correctly assigned to him)
     * and then verifies that the weapon has been removed from the cell.
     * There's also a commented print in the code of the method grabWeapon to verify if the message gets the correct parameters
     */
    @Test
    public void grabWeaponTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Cell cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);
        Weapon weapon = new Weapon();
        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon);
        weapons.add(weapon1);
        weapons.add(weapon2);
        cell1.setWeapons(weapons);
        PlayerBoard playerBoard = new PlayerBoard();
        player.setPlayerBoard(playerBoard);

        atomicActions.grabWeapon(player, cell1, 1);

        assertEquals(cell1, player.getPosition());
        assertEquals(weapon1, player.getWeaponFromIndex(0));
        assertEquals(player, weapon1.getOwner());
        assertFalse(cell1.getWeapons().contains(weapon1));
    }

    /**
     * this test is similar to the previous one, it verifies also that the selected weapon from the
     * player has been changed with the selected weapon in the cell (and also verifies that the
     * first one as been recharged)
     * There's also a commented print in the code of the method grabWeaponAndReplace to verify if the message gets the correct parameters
     */
    @Test
    public void grabWeaponAndReplaceTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Cell cell1 = new Cell();
        cell1.setRow(5);
        cell1.setColumn(2);
        Weapon weapon = new Weapon();
        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();
        Weapon weapon3 = new Weapon();
        player.addWeapon(weapon3);
        Weapon weapon4 = new Weapon();
        weapon4.setIsLoaded(false);
        player.addWeapon(weapon4);
        Weapon weapon5 = new Weapon();
        player.addWeapon(weapon5);
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon);
        weapons.add(weapon1);
        weapons.add(weapon2);
        cell1.setWeapons(weapons);
        PlayerBoard playerBoard = new PlayerBoard();
        player.setPlayerBoard(playerBoard);

        atomicActions.grabWeaponAndReplace(player, cell1, 1, 1);

        assertEquals(cell1, player.getPosition());
        assertTrue(player.getWeapons().contains(weapon1));
        assertEquals(player, weapon1.getOwner());
        assertFalse(cell1.getWeapons().contains(weapon1));
        assertTrue(weapon4.getIsLoaded());
        assertTrue(cell1.getWeapons().contains(weapon4));
        assertTrue(!player.getWeapons().contains(weapon4));
    }

    /**
     * this test verifies the weapon has been reloaded correctly.
     * There's also a commented print in the code of the method reload to verify if the message gets the correct parameters
     */
    @Test
    public void reloadTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Weapon weapon = new Weapon();
        weapon.setIsLoaded(false);
        player.addWeapon(weapon);

        atomicActions.reload(player, weapon);

        assertTrue(weapon.getIsLoaded());
    }

    /**
     * testing if has been assigned the correct amount of ammo during the conversion from powerup value
     */
    @Test
    public void convertPowerupTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Ammo ammo = new Ammo();
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        Powerup powerup = new Powerup();
        powerup.setColor(Colors.RED);
        Powerup powerup1 = new Powerup();
        powerup1.setColor(Colors.BLUE);
        Powerup powerup2 = new Powerup();
        powerup2.setColor(Colors.YELLOW);

        player.addPowerup(powerup);
        player.addPowerup(powerup1);
        player.addPowerup(powerup2);

        atomicActions.convertPowerup(player, 0);
        assertEquals(1, player.getPlayerBoard().getAmmo().getRed());

        atomicActions.convertPowerup(player, 0);
        assertEquals(1, player.getPlayerBoard().getAmmo().getBlue());

        atomicActions.convertPowerup(player, 0);
        assertEquals(1, player.getPlayerBoard().getAmmo().getYellow());
    }

    /**
     * verifies if it has been removed the correct amount of ammos during the payment
     */
    @Test
    public void payAmmoTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Ammo ammo = new Ammo(2, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        Ammo cost = new Ammo(1, 1, 1);

        atomicActions.payAmmo(player, cost);

        assertEquals(1, player.getPlayerBoard().getAmmo().getYellow());
        assertEquals(1, player.getPlayerBoard().getAmmo().getBlue());
        assertEquals(1, player.getPlayerBoard().getAmmo().getRed());
    }

    /**
     * verifies if we have removed the correct amount of ammos and the powerup selected to pay
     */
    @Test
    public void payAmmoWithPowerupsTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Ammo ammo = new Ammo(0, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);
        Powerup powerup = new Powerup();
        powerup.setColor(Colors.RED);
        player.addPowerup(powerup);
        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup);

        Ammo cost = new Ammo(1, 1, 1);

        atomicActions.payAmmoWithPowerups(player, cost, powerups);

        assertEquals(1, player.getPlayerBoard().getAmmo().getYellow());
        assertEquals(1, player.getPlayerBoard().getAmmo().getBlue());
        assertEquals(0, player.getPlayerBoard().getAmmo().getRed());
        assertFalse(player.getPowerups().contains(powerup));
    }

    /**
     * verifies that the player has a new powerup.
     * There's also a commented print in the code of the method drawPowerup to verify if the message gets the correct parameters
     */
    @Test
    public void drawPowerupTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);
        Ammo ammo = new Ammo(0, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);
        Powerup powerup = new Powerup();
        powerup.setColor(Colors.RED);
        player.addPowerup(powerup);

        atomicActions.drawPowerup(player);

        assertEquals(2, player.getPowerups().size());
    }

    /**
     * verifies if the marks as been added correctly to te player receiver.
     * There's also a commented print in the code of the method mark to verify if the message gets the correct parameters
     *
     */
    @Test
    public void markTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);

        Player receiver = new Player();
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setMarks(new Marks(characters));
        receiver.setPlayerBoard(playerBoard);

        atomicActions.mark(player, receiver, 2);

        characters.remove(Character.DISTRUCTOR);
        characters.add(Character.SPROG);

        assertTrue(characters.containsAll(receiver.getPlayerBoard().getMarks().getMarkSequence()));
        assertEquals(receiver.getPlayerBoard().getMarks().getMarkSequence().size(), 2);
    }

    /**
     * this test verifies if I set the correct amount of damage and the correct state in the receiver of the attack attributes.
     * There's also a commented print in the code of the method dealDamage to verify if the message gets the correct parameters
     */
    @Test
    public void dealDamageTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DISTRUCTOR);

        VirtualView virtualView = new VirtualView(new Server());
        Client client = new Client("Giancarlo", new ClientImplementation(new it.polimi.sw2019.network.client.Client()));
        virtualView.addWaitingPlayer("Giancarlo", client);
        Match match = new Match(false, false, usernames,"Board1.json", virtualView);

        AtomicActions atomicActions = new AtomicActions(match, virtualView);

        Player player = new Player();
        match.setCurrentPlayer(player);
        player.setName("Giancarlo");
        player.setCharacter(Character.SPROG);
        Cell cell = new Cell();
        player.setPosition(cell);

        Player receiver = new Player();
        receiver.setCharacter(Character.DISTRUCTOR);
        PlayerBoard playerBoard = new PlayerBoard();
        DamageTokens damageTokens = new DamageTokens(characters);
        playerBoard.setDamage(damageTokens);
        playerBoard.setMarks(new Marks(characters));
        receiver.setPlayerBoard(playerBoard);

        atomicActions.dealDamage(player, receiver, 3);

        assertEquals(State.ADRENALINIC1, receiver.getState());
        assertEquals(3, receiver.getPlayerBoard().getDamage().getTotalDamage());

        atomicActions.dealDamage(player, receiver, 3);

        assertEquals(State.ADRENALINIC2, receiver.getState());
        assertEquals(6, receiver.getPlayerBoard().getDamage().getTotalDamage());
    }
}
