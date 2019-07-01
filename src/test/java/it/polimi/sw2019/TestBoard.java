package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestBoard {

    /**
     * test if we get the correct cell after the selection
     */
    @Test
    public void getCellTest() {

        Board board = new Board();

        List<Cell> field = new ArrayList<>();
        Cell cell = new Cell();
        cell.setColumn(2);
        cell.setRow(3);
        Cell cell1 = new Cell();
        cell1.setColumn(1);
        cell1.setRow(0);
        Cell cell2 = new Cell();
        cell2.setColumn(2);
        cell2.setRow(2);

        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        board.setField(field);

        assertEquals(board.getCell(0, 1), cell1);
    }

    /**
     * test if I get the correct room after the selection
     */
    @Test
    public void getRoomByColorTest() {

        Board board = new Board();

        List<Room> rooms = new ArrayList<>();
        Room room = new Room(Colors.YELLOW, null, null);
        Room room1 = new Room(Colors.BLUE, null, null);
        Room room2 = new Room(Colors.RED, null, null);

        rooms.add(room2);
        rooms.add(room1);
        rooms.add(room);
        board.setRooms(rooms);

        assertEquals(board.getRoomByColor(Colors.YELLOW), room);
    }

    /**
     * tests if I have drawed the correct weapon and if it has been removed from the weapon deck (and also tests if the method weaponsDeckIsEmpty() is correct)
     */
    @Test
    public void drawWeaponTest() {

        Board board = new Board();

        Weapon weapon = new Weapon();
        Weapon weapon1 = new Weapon();
        Weapon weapon2 = new Weapon();

        List<Weapon> weaponsDeck = new ArrayList<>();
        weaponsDeck.add(weapon);
        weaponsDeck.add(weapon1);
        weaponsDeck.add(weapon2);
        board.setWeaponsDeck(weaponsDeck);

        assertFalse(board.weaponsDeckIsEmpty());

        assertEquals(board.drawWeapon(), weapon2);

        assertFalse(board.weaponsDeckIsEmpty());

        assertEquals(board.drawWeapon(), weapon1);

        assertFalse(board.weaponsDeckIsEmpty());

        assertEquals(board.drawWeapon(), weapon);

        assertTrue(board.weaponsDeckIsEmpty());
    }

    /**
     *tests if I have drawed the correct powerup and if it has been removed from the powerup deck
     */
    @Test
    public void drawPowerupTest() {

        Board board = new Board();

        Powerup powerup = new Powerup();
        Powerup powerup1 = new Powerup();
        Powerup powerup2 = new Powerup();
        List<Powerup> powerupsDeck = new ArrayList<>();
        powerupsDeck.add(powerup);
        powerupsDeck.add(powerup1);
        powerupsDeck.add(powerup2);
        board.setPowerupsDeck(powerupsDeck);

        assertEquals(board.drawPowerup(), powerup2);
        assertEquals(board.drawPowerup(), powerup1);
        assertEquals(board.drawPowerup(), powerup);
        assertEquals(board.drawPowerup(), powerup2);
        //TODO add powerups discarded
    }

    @Test
    public void updateKillTrackTest() {

        Board board1 = new Board();
        Board board2 = new Board();

        Player deadPlayer1 = new Player();
        Player deadPlayer2 = null;

        try{
            board1.updateKillTrack(deadPlayer2);
            fail();
        } catch (NullPointerException e) {System.out.print("fallito");}


        deadPlayer2 = new Player();

        List<Character> list = new ArrayList<>();
        list.add(Character.SPROG);
        list.add(Character.BANSHEE);
        list.add(Character.DISTRUCTOR);
        KillTokens killTrack = new KillTokens(list);
        killTrack.addKill(Character.SPROG);
        KillTokens killTrack1 = new KillTokens(list);


        List<Character> damageSequence = new ArrayList<>();
        for(int i = 0; i<7; i++) {

            damageSequence.add(Character.BANSHEE);
        }
        damageSequence.add(Character.DISTRUCTOR);
        damageSequence.add(Character.DISTRUCTOR);
        damageSequence.add(Character.SPROG);
        damageSequence.add(Character.SPROG);
        damageSequence.add(Character.SPROG);

        PlayerBoard playerBoard = new PlayerBoard();
        deadPlayer1.setPlayerBoard(playerBoard);
        DamageTokens damageTokens = new DamageTokens(list);
        playerBoard.setDamage(damageTokens);
        deadPlayer1.getPlayerBoard().getDamage().setTotalDamage(11);
        deadPlayer1.getPlayerBoard().getDamage().setDamageSequence(damageSequence);
        KillTokens kills = new KillTokens(list);
        board1.setKillTrack(kills);
        board1.updateKillTrack(deadPlayer1);
        assertEquals(killTrack.getKillSequence(), board1.getKillTrack().getKillSequence());
        assertEquals(killTrack.getOverkillSequence(), board1.getKillTrack().getOverkillSequence());


        killTrack1.addOverkill(Character.SPROG);
        deadPlayer2.setPlayerBoard(playerBoard);
        playerBoard.setDamage(damageTokens);
        deadPlayer2.getPlayerBoard().getDamage().setTotalDamage(12);
        deadPlayer2.getPlayerBoard().getDamage().setDamageSequence(damageSequence);
        KillTokens kills1 = new KillTokens(list);
        board2.setKillTrack(kills1);
        board2.updateKillTrack(deadPlayer2);
        assertEquals(killTrack1.getKillSequence(), board2.getKillTrack().getKillSequence());
        assertEquals(killTrack1.getOverkillSequence(), board2.getKillTrack().getOverkillSequence());

    }

}