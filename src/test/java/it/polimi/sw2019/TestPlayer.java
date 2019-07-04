package it.polimi.sw2019;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.sw2019.model.Colors.*;
import static org.junit.Assert.*;

public class TestPlayer {

    @Test
    public void constructorTest() {

        Player player = new Player("Carlo", Character.SPROG);
        assertEquals("Carlo", player.getName());
        assertEquals(Character.SPROG, player.getCharacter());
        assertEquals(false, player.isDead());
        assertEquals(State.NORMAL, player.getState());
    }

    @Test
    public void getPowerupsAfterShootTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);

        Player player = new Player("Carlo", Character.DISTRUCTOR, characters);

        Powerup powerup = new Powerup();
        Powerup powerup1 = new Powerup();
        powerup1.setDuringDamageAction(true);
        powerup1.setDuringYourTurn(true);
        Powerup powerup2 = new Powerup();
        player.addPowerup(powerup);
        player.addPowerup(powerup1);
        player.addPowerup(powerup2);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup1);

        assertEquals(powerups, player.getPowerupsAfterShoot());
    }

    @Test
    public void hasCounterAttackPowerupTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);

        Player player = new Player("Carlo", Character.DISTRUCTOR, characters);

        Powerup powerup = new Powerup();
        Powerup powerup1 = new Powerup();
        powerup1.setDuringDamageAction(true);
        powerup1.setDuringYourTurn(false);
        Powerup powerup2 = new Powerup();
        player.addPowerup(powerup);
        player.addPowerup(powerup1);
        player.addPowerup(powerup2);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup1);

        assertEquals(true, player.hasCounterAttackPowerups());
    }

    @Test
    public void addWeaponTest() {

        Player player = new Player();

        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();
        Weapon weapon3 = new Weapon();
        Weapon weapon4 = null;

        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon2);
        weapons.add(weapon3);

        try{
            player.addWeapon(weapon4);
            fail();
        } catch (NullPointerException e) {}

        weapon4 = new Weapon();

        player.addWeapon(weapon1);
        player.addWeapon(weapon2);
        player.addWeapon(weapon3);
        player.addWeapon(weapon4);

        assertEquals(weapons, player.getWeapons());
    }

    @Test
    public void useWeaponTest() {

        Player player = new Player();

        Weapon weapon = new Weapon();
        weapon.setIsLoaded(true);

        player.addWeapon(weapon);
        player.useWeapon(player.getWeapons().get(0));

        assertFalse(player.getWeapons().get(0).getIsLoaded());
    }

    @Test
    public void canIPayPowerupTest() {

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.DISTRUCTOR);
        characters.add(Character.BANSHEE);

        Player player = new Player("Carlo", Character.DISTRUCTOR, characters);
        PlayerBoard playerBoard = new PlayerBoard();
        Ammo ammo = new Ammo(3, 3, 3);
        playerBoard.setAmmo(ammo);

        Powerup powerup = new Powerup();
        Powerup powerup1 = new Powerup();
        powerup1.setDuringDamageAction(true);
        powerup1.setDuringYourTurn(true);
        Powerup powerup2 = new Powerup();
        player.addPowerup(powerup);

        assertTrue(player.canIPayPowerup());

        player.addPowerup(powerup2);

        assertTrue(player.canIPayPowerup());
    }

    @Test
    public void discardWeaponTest() {

        Player player = new Player();

        Weapon weapon = new Weapon();
        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();

        player.addWeapon(weapon);
        player.addWeapon(weapon1);
        player.addWeapon(weapon2);

        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon);
        weapons.add(weapon2);

        player.discardWeapon(1);

        assertTrue(weapons.containsAll(player.getWeapons()));
    }

    @Test
    public void discardPowerupTest() {

        Player player = new Player();

        player.discardPowerup(1);

        Powerup powerup = new Powerup();
        player.addPowerup(powerup);
        Powerup powerup1 = new Powerup();
        player.addPowerup(powerup1);
        Powerup powerup2 = new Powerup();
        player.addPowerup(powerup2);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup);
        powerups.add(powerup2);

        player.discardPowerup(1);

        assertTrue(powerups.containsAll(player.getPowerups()));
    }

    @Test
    public void visiblePlayerTest() {

        Player player = new Player();

        Player player1 = new Player();

        player.setState(State.NORMAL);

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setLeft(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);

        List<Cell> list1 = new ArrayList<>();
        list1.add(cell);
        list1.add(cell1);
        list1.add(cell2);
        list1.add(cell3);
        Room yellowRoom = new Room(YELLOW, cell3, list1);
        List<Player> players1 = new ArrayList<>();
        players1.add(player);
        players1.add(player1);
        yellowRoom.setPlayers(players1);
        cell.setRoom(yellowRoom);
        cell1.setRoom(yellowRoom);
        cell2.setRoom(yellowRoom);
        cell3.setRoom(yellowRoom);

        List<Cell> list2 = new ArrayList<>();
        list2.add(cell4);
        list2.add(cell5);
        list2.add(cell6);
        Room blueRoom = new Room(Colors.BLUE, cell5, list2);
        List<Player> players7 = new ArrayList<>();
        cell4.setRoom(blueRoom);
        cell5.setRoom(blueRoom);
        cell6.setRoom(blueRoom);


        List<Cell> list3 = new ArrayList<>();
        list3.add(cell7);
        list3.add(cell8);
        Room redRoom = new Room(Colors.RED, cell8, list3);
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        player.setPosition(cell);
        player1.setPosition(cell1);
        Player player2 = new Player();
        player2.setPosition(cell4);
        players7.add(player2);

        List<Player> players = new ArrayList<>();
        players.add(player1);

        assertEquals(player.visibilePlayer(), players);
    }

    @Test
    public void allowedCellsShotTest() {

        Player player = new Player();

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setLeft(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);

        List<Cell> list1 = new ArrayList<>();
        list1.add(cell);
        list1.add(cell1);
        list1.add(cell2);
        list1.add(cell3);
        Room yellowRoom = new Room(YELLOW, cell3, list1);
        List<Player> players1 = new ArrayList<>();
        players1.add(player);
        yellowRoom.setPlayers(players1);
        cell.setRoom(yellowRoom);
        cell1.setRoom(yellowRoom);
        cell2.setRoom(yellowRoom);
        cell3.setRoom(yellowRoom);

        List<Cell> list2 = new ArrayList<>();
        list2.add(cell4);
        list2.add(cell5);
        list2.add(cell6);
        Room blueRoom = new Room(Colors.BLUE, cell5, list2);
        List<Player> players7 = new ArrayList<>();
        cell4.setRoom(blueRoom);
        cell5.setRoom(blueRoom);
        cell6.setRoom(blueRoom);


        List<Cell> list3 = new ArrayList<>();
        list3.add(cell7);
        list3.add(cell8);
        Room redRoom = new Room(Colors.RED, cell8, list3);
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        player.setPosition(cell11);

        List<Cell> cells = new ArrayList<>();
        cells.add(cell11);
        cells.add(cell10);
        cells.add(cell9);

        player.setState(State.NORMAL);

        assertTrue(cells.containsAll(player.allowedCellsShoot()));

        player.setState(State.ADRENALINIC2);
        Weapon weapon = new Weapon();
        weapon.setIsLoaded(true);
        player.addWeapon(weapon);

        assertTrue(cells.containsAll(player.allowedCellsShoot()));

    }

    @Test
    public void reloadableWeaponsTest() {

        Weapon weapon = new Weapon();
        weapon.setIsLoaded(true);
        Weapon weapon1 = new Weapon();
        Ammo cost1 = new Ammo(1, 0, 0);
        weapon1.setReloadCost(cost1);
        Weapon weapon2 = new Weapon();
        Ammo cost2 = new Ammo(0, 1, 1);
        weapon2.setReloadCost(cost2);
        List<Weapon> reloadableWeapons = new ArrayList<>();
        reloadableWeapons.add(weapon2);

        Player player = new Player();
        player.addWeapon(weapon);
        player.addWeapon(weapon1);
        player.addWeapon(weapon2);
        Ammo ammo = new Ammo(0, 1, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        assertTrue(reloadableWeapons.containsAll(player.reloadableWeapons()));
    }

    @Test
    public void usePowerupTest() {

        Player player = new Player();

        Powerup powerup1 = new Powerup();
        Powerup powerup2 = new Powerup();

        player.addPowerup(powerup1);
        player.addPowerup(powerup2);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup2);

        player.usePowerup(player.getPowerups().get(0));

        assertEquals(powerups, player.getPowerups());
    }

    @Test
    public void addPowerupTest() {

        Player player = new Player();

        Powerup powerup1 = new Powerup();
        Powerup powerup2 = new Powerup();
        Powerup powerup3 = null;

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup1);
        powerups.add(powerup2);

        player.addPowerup(powerup1);
        player.addPowerup(powerup2);
        player.addPowerup(powerup3);

        assertEquals(powerups, player.getPowerups());
    }

    @Test
    public void loadedWeaponsTest() {

        Player player = new Player();

        Weapon weapon1 = new Weapon();
        weapon1.setIsLoaded(true);
        Weapon weapon2 = new Weapon();
        weapon2.setIsLoaded(false);
        Weapon weapon3 = new Weapon();
        weapon3.setIsLoaded(true);

        player.addWeapon(weapon1);
        player.addWeapon(weapon2);
        player.addWeapon(weapon3);

        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon3);

        assertEquals(weapons, player.loadedWeapons());
    }

    @Test
    public void mustPayWithPowerupTest() {

        Player player = new Player();
        Ammo ammo = new Ammo(0, 1, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        Ammo cost0 = new Ammo();

        assertFalse(player.mustPayWithPowerup(cost0));

        Ammo cost = new Ammo(1, 1, 2);

        assertTrue(player.mustPayWithPowerup(cost));
    }

    @Test
    public void notifyPrivateHandTest() {

        Player player = new Player();

        Weapon weapon = new Weapon();
        weapon.setName("0");
        weapon.setIsLoaded(true);
        player.addWeapon(weapon);
        Weapon weapon1 = new Weapon();
        weapon1.setName("1");
        player.addWeapon(weapon1);
        weapon1.setIsLoaded(true);
        List<Weapon> loadedWeapons = new ArrayList<>();
        loadedWeapons.add(weapon);
        loadedWeapons.add(weapon1);
        Weapon weapon2 = new Weapon();
        weapon2.setName("2");
        player.addWeapon(weapon2);
        List<Weapon> unLoadedWeapons = new ArrayList<>();
        unLoadedWeapons.add(weapon2);
        List<Weapon> allWeapons = new ArrayList<>();
        allWeapons.add(weapon);
        allWeapons.add(weapon1);
        allWeapons.add(weapon2);

        List<Powerup> powerupSerialized = new ArrayList<>();
        List<Colors> powerupColors = new ArrayList<>();
        Powerup powerup = new Powerup();
        powerup.setName("0");
        powerup.setColor(YELLOW);
        powerupSerialized.add(powerup);
        powerupColors.add(YELLOW);
        Powerup powerup1 = new Powerup();
        powerup1.setName("1");
        powerup1.setColor(RED);
        powerupSerialized.add(powerup1);
        powerupColors.add(RED);
        Powerup powerup2 = new Powerup();
        powerup2.setName("2");
        powerup2.setColor(BLUE);
        powerupSerialized.add(powerup2);
        powerupColors.add(BLUE);
        player.addPowerup(powerup);
        player.addPowerup(powerup1);
        player.addPowerup(powerup2);

        List<String> weaponsName = new ArrayList<>();
        weaponsName.add(weapon.getName());
        weaponsName.add(weapon1.getName());
        weaponsName.add(weapon2.getName());

        assertTrue(weaponsName.containsAll(player.notifyPrivateHand().deserializePrivateHand().getAllWeapons()));

        weaponsName.remove(weapon2.getName());

        assertTrue(weaponsName.containsAll(player.notifyPrivateHand().deserializePrivateHand().getWeaponsLoaded()));

        weaponsName.remove(weapon1.getName());
        weaponsName.remove(weapon.getName());
        weaponsName.add(weapon2.getName());

        assertTrue(weaponsName.containsAll(player.notifyPrivateHand().deserializePrivateHand().getWeaponsUnloaded()));

        weaponsName.add(weapon1.getName());
        weaponsName.add(weapon.getName());

        assertTrue(weaponsName.containsAll(player.notifyPrivateHand().deserializePrivateHand().getPowerups()));
        assertTrue(powerupColors.containsAll(player.notifyPrivateHand().deserializePrivateHand().getPowerupColors()));
    }



    //TODO implement canIshootBeforeComplexAction test

    //TODO implement avaliableWeapons test

    //TODO implement allowedCellsShot test

    @Test
    public void getMovesTest() {

        Player player = new Player();

        player.setState(State.NORMAL);
        assertEquals(3, player.getMoves());
        player.setState(State.FRENZYBEFOREFIRST);
        assertEquals(4, player.getMoves());
        player.setState(State.FRENZYAFTERFIRST);
        assertEquals(0, player.getMoves());
    }

    @Test
    public void allowedCellsMoveTest() {

        Player player = new Player();

        player.setState(State.NORMAL);

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setLeft(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);

        player.setPosition(cell);

        List<Cell> resultCells = new ArrayList<>();
        resultCells.add(cell);
        resultCells.add(cell1);
        resultCells.add(cell3);
        resultCells.add(cell5);
        resultCells.add(cell2);
        resultCells.add(cell6);
        resultCells.add(cell4);

        assertEquals(resultCells, player.allowedCellsMove());
    }

    @Test
    public void getMovesForGrabTest() {

        Player player = new Player();

        player.setState(State.NORMAL);
        assertEquals(1, player.getMovesForGrab());
        player.setState(State.ADRENALINIC1);
        assertEquals(2, player.getMovesForGrab());
        player.setState(State.FRENZYAFTERFIRST);
        assertEquals(3, player.getMovesForGrab());
    }

    @Test
    public void allowesCellsGrabTest() {

        Player player = new Player();

        player.setState(State.NORMAL);

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setLeft(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);

        player.setPosition(cell);

        List<Cell> resultCells = new ArrayList<>();
        resultCells.add(cell);
        resultCells.add(cell1);
        resultCells.add(cell3);

        assertEquals(resultCells, player.allowedCellsGrab());
    }

    @Test
    public void visibleCellsTest() {

        Player player = new Player();

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setLeft(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);

        List<Cell> list1 = new ArrayList<>();
        list1.add(cell);
        list1.add(cell1);
        list1.add(cell2);
        list1.add(cell3);
        Room yellowRoom = new Room(YELLOW, cell3, list1);
        List<Player> players1 = new ArrayList<>();
        players1.add(player);
        yellowRoom.setPlayers(players1);
        cell.setRoom(yellowRoom);
        cell1.setRoom(yellowRoom);
        cell2.setRoom(yellowRoom);
        cell3.setRoom(yellowRoom);

        List<Cell> list2 = new ArrayList<>();
        list2.add(cell4);
        list2.add(cell5);
        list2.add(cell6);
        Room blueRoom = new Room(Colors.BLUE, cell5, list2);
        cell4.setRoom(blueRoom);
        cell5.setRoom(blueRoom);
        cell6.setRoom(blueRoom);


        List<Cell> list3 = new ArrayList<>();
        list3.add(cell7);
        list3.add(cell8);
        Room redRoom = new Room(Colors.RED, cell8, list3);
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        player.setPosition(cell);

        List<Cell> resultCells = new ArrayList<>();
        resultCells.add(cell);
        resultCells.add(cell1);
        resultCells.add(cell2);
        resultCells.add(cell3);

        assertEquals(resultCells, player.visibleCells());
    }


    //TODO implement the next test
    /*
    @Test
    public void canIshootBeforeComplexAction() {

        Cell cell = new Cell();
        cell.setRow(2);
        cell.setColumn(2);

        Cell cell1 = new Cell();
        cell1.setRow(1);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(1);
        cell2.setColumn(3);
        Cell cell3 = new Cell();
        cell3.setRow(2);
        cell3.setColumn(3);
        Cell cell4 = new Cell();
        cell4.setRow(0);
        cell4.setColumn(3);
        Cell cell5 = new Cell();
        cell5.setRow(0);
        cell5.setColumn(2);
        Cell cell6 = new Cell();
        cell6.setRow(0);
        cell6.setColumn(1);
        Cell cell7 = new Cell();
        cell7.setRow(1);
        cell7.setColumn(1);
        Cell cell8 = new Cell();
        cell8.setRow(2);
        cell8.setColumn(1);
        Cell cell9 = new Cell();
        cell9.setRow(2);
        cell9.setColumn(0);
        Cell cell10 = new Cell();
        cell10.setRow(1);
        cell10.setColumn(0);
        Cell cell11 = new Cell();
        cell11.setRow(0);
        cell11.setColumn(0);

        cell.setUp(cell1);
        cell.setRight(cell3);

        cell1.setUp(cell5);
        cell1.setRight(cell2);
        cell1.setDown(cell);

        cell2.setLeft(cell1);
        cell2.setDown(cell3);

        cell3.setUp(cell2);
        cell3.setLeft(cell);

        cell4.setLeft(cell5);

        cell5.setRight(cell4);
        cell5.setDown(cell1);
        cell5.setRight(cell6);

        cell6.setRight(cell5);
        cell6.setDown(cell7);

        cell7.setUp(cell6);
        cell7.setDown(cell8);

        cell8.setUp(cell7);
        cell8.setLeft(cell9);

        cell9.setRight(cell8);
        cell9.setUp(cell10);

        cell10.setUp(cell11);
        cell10.setDown(cell9);

        cell11.setDown(cell10);


        Player player1 = new Player("Enzo", Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", Character.DOZER);
        player4.setPosition(cell11);

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell8);

        List<Cell> list1 = new ArrayList<>();
        list1.add(cell);
        list1.add(cell1);
        list1.add(cell2);
        list1.add(cell3);
        Room yellowRoom = new Room(Colors.YELLOW, cell3, list1);
        List<Player> players1 = new ArrayList<>();
        players1.add(player1);
        players1.add(player2);
        yellowRoom.setPlayers(players1);
        cell.setRoom(yellowRoom);
        cell1.setRoom(yellowRoom);
        cell2.setRoom(yellowRoom);
        cell3.setRoom(yellowRoom);

        List<Cell> list2 = new ArrayList<>();
        list2.add(cell4);
        list2.add(cell5);
        list2.add(cell6);
        Room blueRoom = new Room(Colors.BLUE, cell5, list2);
        List<Player> players2 = new ArrayList<>();
        players2.add(player3);
        blueRoom.setPlayers(players2);
        cell4.setRoom(blueRoom);
        cell5.setRoom(blueRoom);
        cell6.setRoom(blueRoom);


        List<Cell> list3 = new ArrayList<>();
        list3.add(cell7);
        list3.add(cell8);
        Room redRoom = new Room(Colors.RED, cell8, list3);
        List<Player> players3 = new ArrayList<>();
        players3.add(player5);
        redRoom.setPlayers(players3);
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players4 = new ArrayList<>();
        players4.add(player4);
        greyRoom.setPlayers(players4);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        Player player = new Player();

        player.setState(State.FRENZYAFTERFIRST);
        player.setPosition(cell);

        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();
        Weapon weapon3 = new Weapon();
        player.addWeapon(weapon1);
        player.addWeapon(weapon2);
        player.addWeapon(weapon3);
    }*/

    //TODO implement availableWeapons test

    @Test
    public void virtualAmmoTest() {

        Player player = new Player();
        PlayerBoard playerBoard = new PlayerBoard();
        Ammo ammo = new Ammo();
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        Powerup powerup1 = new Powerup("Targeting scope", YELLOW);
        Powerup powerup2 = new Powerup("Newton", Colors.RED);
        Powerup powerup3 = new Powerup("Tagback grenade", Colors.BLUE);

        player.addPowerup(powerup1);
        player.addPowerup(powerup2);
        player.addPowerup(powerup3);

        int[] virtualAmmoResult = new int[3];
        virtualAmmoResult[0] = 1;
        virtualAmmoResult[1] = 1;
        virtualAmmoResult[2] = 1;

        int[] virtualAmmo = new int[3];
        virtualAmmo = player.virtualAmmo();
        assertEquals(virtualAmmoResult[0], virtualAmmo[0]);
        assertArrayEquals(virtualAmmoResult, virtualAmmo);
    }

    @Test
    public void canIPayTest() {

        Player player = new Player();
        PlayerBoard playerBoard = new PlayerBoard();
        Ammo ammo = new Ammo();
        ammo.setBlue(2);
        ammo.setRed(1);
        playerBoard.setAmmo(ammo);
        player.setPlayerBoard(playerBoard);

        Powerup powerup1 = new Powerup("Targeting scope", YELLOW);
        Powerup powerup2 = new Powerup("Newton", Colors.RED);
        Powerup powerup3 = new Powerup("Tagback grenade", Colors.BLUE);

        player.addPowerup(powerup1);
        player.addPowerup(powerup2);
        player.addPowerup(powerup3);

        Ammo cost = new Ammo();
        cost.setYellow(1);
        cost.setBlue(1);
        assertTrue(player.canIPay(cost));

        Player player1 = new Player();
        PlayerBoard playerBoard1 = new PlayerBoard();
        Ammo ammo1 = new Ammo();
        playerBoard1.setAmmo(ammo1);
        player1.setPlayerBoard(playerBoard1);
        assertFalse(player1.canIPay(cost));
    }

    @Test
    public void payingPowerupsTest() {

        Ammo cost = new Ammo();
        cost.setRed(2);
        cost.setBlue(2);

        Powerup powerup1 = new Powerup("Targeting scope", Colors.RED);
        Powerup powerup2 = new Powerup("Newton", Colors.RED);
        Powerup powerup3 = new Powerup("Tagback grenade", Colors.BLUE);
        Powerup powerup4 = new Powerup("Teleporter", Colors.BLUE);
        Powerup powerup5 = new Powerup("Targeting scope", YELLOW);

        Player player = new Player();
        player.addPowerup(powerup1);
        player.addPowerup(powerup2);
        player.addPowerup(powerup3);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup1);
        powerups.add(powerup2);
        powerups.add(powerup3);

        assertEquals(powerups, player.payingPoweups(cost));
    }

    @Test
    public void getMovesForShootTest() {

        Player player = new Player();

        player.setState(State.ADRENALINIC1);
        assertEquals(0, player.getMovesForShoot());

        player.setState(State.ADRENALINIC2);
        assertEquals(1, player.getMovesForShoot());

        player.setState(State.FRENZYAFTERFIRST);
        assertEquals(2, player.getMovesForShoot());
    }

    @Test
    public void usablePowerupsTest() {

        Player player = new Player();

        Powerup powerup1 = new Powerup();
        powerup1.setDuringDamageAction(false);
        powerup1.setDuringYourTurn(true);
        Powerup powerup2 = new Powerup();
        powerup2.setDuringDamageAction(true);
        powerup2.setDuringYourTurn(true);
        Powerup powerup3 = new Powerup();
        powerup3.setDuringDamageAction(false);
        powerup3.setDuringYourTurn(true);

        player.addPowerup(powerup1);
        player.addPowerup(powerup2);
        player.addPowerup(powerup3);

        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup1);
        powerups.add(powerup3);

        assertEquals(powerups, player.usablePowerups());
    }

    @Test
    public void createPlayerHandTest() {

        Player player = new Player();

        Weapon weapon = new Weapon();
        weapon.setIsLoaded(true);
        player.addWeapon(weapon);
        weapon.setName("loaded");
        Weapon weapon1 = new Weapon();
        weapon1.setIsLoaded(true);
        player.addWeapon(weapon1);
        weapon1.setName("loaded");
        Weapon weapon2 = new Weapon();
        weapon2.setIsLoaded(false);
        player.addWeapon(weapon2);
        weapon2.setName("unloaded");

        assertEquals(2, player.createPlayerHand().getWeaponsHidden());

        List<String> weaponsName = new ArrayList<>();
        weaponsName.add(weapon2.getName());

        assertTrue(weaponsName.containsAll(player.createPlayerHand().getWeaponsUnloaded()));

        Powerup powerup = new Powerup();
        player.addPowerup(powerup);
        Powerup powerup1 = new Powerup();
        player.addPowerup(powerup1);
        Powerup powerup2 = new Powerup();
        player.addPowerup(powerup2);

        assertEquals(3, player.createPlayerHand().getPowerups());
    }
}
