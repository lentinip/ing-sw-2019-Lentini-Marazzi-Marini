package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestEffect {

    @Test
    public void reachableCellsTest() {

        Effect effect = new Effect();

        Board board = new Board();

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

        List<Cell> field = new ArrayList<>();
        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        field.add(cell3);
        field.add(cell4);
        field.add(cell5);
        field.add(cell6);
        field.add(cell7);
        field.add(cell8);
        field.add(cell9);
        field.add(cell10);
        field.add(cell11);

        board.setField(field);

        Player player1 = new Player("Enzo", it.polimi.sw2019.model.Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", it.polimi.sw2019.model.Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", it.polimi.sw2019.model.Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", it.polimi.sw2019.model.Character.DOZER);
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

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        board.setRooms(roooms);

        Visibility visibility = new Visibility(board);

        effect.setVisibilityClass(visibility);
        int[] movesAvay = new int[4];
        movesAvay[0] = 2;
        effect.setMovesAway(movesAvay);
        effect.setExactly(true);
        MoveEffect move = new MoveEffect();
        move.setMoveYou(1);
        effect.setMove(move);
        effect.setVisibility(KindOfVisibility.VISIBLE);

        assertEquals(visibility.visibility(KindOfVisibility.VISIBLE, player5, 2, true, 1), effect.reachableCells(player5));
    }

    @Test
    public void shootableCellsTest() {

        Effect effect = new Effect();

        Board board = new Board();

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

        List<Cell> field = new ArrayList<>();
        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        field.add(cell3);
        field.add(cell4);
        field.add(cell5);
        field.add(cell6);
        field.add(cell7);
        field.add(cell8);
        field.add(cell9);
        field.add(cell10);
        field.add(cell11);

        board.setField(field);

        Player player1 = new Player("Enzo", it.polimi.sw2019.model.Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", it.polimi.sw2019.model.Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", it.polimi.sw2019.model.Character.VIOLET);
        player3.setPosition(cell5);

        Player player4 = new Player("Luca", it.polimi.sw2019.model.Character.DOZER);
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

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        board.setRooms(roooms);

        Visibility visibility = new Visibility(board);

        effect.setVisibilityClass(visibility);
        int[] movesAvay = new int[4];
        movesAvay[0] = 1;
        effect.setMovesAway(movesAvay);
        effect.setExactly(true);
        MoveEffect move = new MoveEffect();
        move.setMoveYou(0);
        effect.setMove(move);
        effect.setVisibility(KindOfVisibility.VISIBLE);

        List<Cell> cells = new ArrayList<>();
        cells.add(cell5);

        assertEquals(cells, effect.shootableCells(player1));
    }

    @Test
    public void reachablePlayersTest() {

        Effect effect = new Effect();

        Board board = new Board();

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

        List<Cell> field = new ArrayList<>();
        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        field.add(cell3);
        field.add(cell4);
        field.add(cell5);
        field.add(cell6);
        field.add(cell7);
        field.add(cell8);
        field.add(cell9);
        field.add(cell10);
        field.add(cell11);

        board.setField(field);

        Player player1 = new Player("Enzo", it.polimi.sw2019.model.Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", it.polimi.sw2019.model.Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", it.polimi.sw2019.model.Character.VIOLET);
        player3.setPosition(cell5);

        Player player4 = new Player("Luca", it.polimi.sw2019.model.Character.DOZER);
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

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        board.setRooms(roooms);

        Visibility visibility = new Visibility(board);

        effect.setVisibilityClass(visibility);
        int[] movesAvay = new int[4];
        movesAvay[0] = 1;
        effect.setMovesAway(movesAvay);
        effect.setExactly(false);
        MoveEffect move = new MoveEffect();
        move.setMoveYou(0);
        effect.setMove(move);
        effect.setVisibility(KindOfVisibility.VISIBLE);

        List<Player> players = new ArrayList<>();
        players.add(player2);
        players.add(player3);

        assertEquals(players, effect.reachablePlayers(player1));
    }

    @Test
    public void usableEffectTest() {

        Effect effect = new Effect();

        Board board = new Board();

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

        List<Cell> field = new ArrayList<>();
        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        field.add(cell3);
        field.add(cell4);
        field.add(cell5);
        field.add(cell6);
        field.add(cell7);
        field.add(cell8);
        field.add(cell9);
        field.add(cell10);
        field.add(cell11);

        board.setField(field);

        Player player1 = new Player("Enzo", it.polimi.sw2019.model.Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", it.polimi.sw2019.model.Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", it.polimi.sw2019.model.Character.VIOLET);
        player3.setPosition(cell5);

        Player player4 = new Player("Luca", it.polimi.sw2019.model.Character.DOZER);
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

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        board.setRooms(roooms);

        Visibility visibility = new Visibility(board);

        effect.setVisibilityClass(visibility);
        int[] movesAvay = new int[4];
        movesAvay[0] = 1;
        effect.setMovesAway(movesAvay);
        effect.setExactly(true);
        MoveEffect move = new MoveEffect();
        move.setMoveYou(0);
        effect.setMove(move);
        effect.setVisibility(KindOfVisibility.VISIBLE);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        PlayerBoard playerBoard = new PlayerBoard();
        Ammo ammo = new Ammo();
        ammo.setBlue(1);
        ammo.setYellow(0);
        ammo.setRed(2);
        Ammo cost = new Ammo();
        cost.setBlue(0);
        cost.setYellow(1);
        cost.setRed(1);
        effect.setCost(cost);
        playerBoard.setAmmo(ammo);
        player1.setPlayerBoard(playerBoard);

        assertFalse(effect.usableEffect(player1, players));

        cost.setYellow(0);

        effect.setMove(null);

        assertTrue(effect.usableEffect(player1, players));

        player2.setPlayerBoard(playerBoard);
        player3.setPlayerBoard(playerBoard);
        player4.setPlayerBoard(playerBoard);
        player5.setPlayerBoard(playerBoard);

        assertFalse(effect.usableEffect(player4, players));

        move.setMoveTargetBefore(true);
        effect.setMove(move);

        //TODO complete test
    }

    @Test
    public void allowedCellsTest() {

        Effect effect = new Effect();

        Board board = new Board();

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

        List<Cell> field = new ArrayList<>();
        field.add(cell);
        field.add(cell1);
        field.add(cell2);
        field.add(cell3);
        field.add(cell4);
        field.add(cell5);
        field.add(cell6);
        field.add(cell7);
        field.add(cell8);
        field.add(cell9);
        field.add(cell10);
        field.add(cell11);

        board.setField(field);

        Player player1 = new Player("Enzo", it.polimi.sw2019.model.Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", it.polimi.sw2019.model.Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", it.polimi.sw2019.model.Character.VIOLET);
        player3.setPosition(cell5);

        Player player4 = new Player("Luca", it.polimi.sw2019.model.Character.DOZER);
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

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        board.setRooms(roooms);

        Visibility visibility = new Visibility(board);

        effect.setVisibilityClass(visibility);
        int[] movesAvay = new int[4];
        movesAvay[0] = 1;
        effect.setMovesAway(movesAvay);
        effect.setExactly(true);
        MoveEffect move = new MoveEffect();
        move.setMoveYou(1);
        effect.setMove(move);
        effect.setVisibility(KindOfVisibility.VISIBLE);

        List<Cell> cells = new ArrayList<>();
        cells.add(cell1);
        cells.add(cell5);
        cells.add(cell);
        cells.add(cell2);

        assertEquals(cells, effect.allowedCells(player1));
    }
}
