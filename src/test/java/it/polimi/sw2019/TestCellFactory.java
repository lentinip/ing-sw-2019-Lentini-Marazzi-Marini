package it.polimi.sw2019;

import it.polimi.sw2019.commons.Colors;
import it.polimi.sw2019.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCellFactory {

    @Test
    public void TestSetColor() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setColor(Colors.RED);

        assertEquals(Colors.RED, cellFactory.getColor());
    }

    @Test
    public void TestSetRow() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setRow(3);

        assertEquals(3, cellFactory.getRow());
    }

    @Test
    public void TestSetColumn() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setColumn(3);

        assertEquals(3, cellFactory.getColumn());
    }

    @Test
    public void TestSetUp() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setUp(3);

        assertEquals(3, cellFactory.getUp());
    }

    @Test
    public void TestSetDown() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setDown(3);

        assertEquals(3, cellFactory.getDown());
    }

    @Test
    public void TestSetLeft() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setLeft(3);

        assertEquals(3, cellFactory.getLeft());
    }

    @Test
    public void TestSetRight() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setRight(3);

        assertEquals(3, cellFactory.getRight());
    }

    @Test
    public void TestSetCommon() {

        CellFactory cellFactory = new CellFactory();

        cellFactory.setCommon(true);

        assertTrue(cellFactory.isCommon());
    }

    @Test
    public void TestSetCell() {

        List<Cell> cells = new ArrayList<>();

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

        List<Cell> list1 = new ArrayList<>();
        list1.add(cell);
        list1.add(cell1);
        list1.add(cell2);
        list1.add(cell3);
        Room yellowRoom = new Room(Colors.YELLOW, cell3, list1);
        List<Player> players1 = new ArrayList<>();
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
        blueRoom.setPlayers(players2);
        cell4.setRoom(blueRoom);
        cell5.setRoom(blueRoom);
        cell6.setRoom(blueRoom);


        List<Cell> list3 = new ArrayList<>();
        list3.add(cell7);
        list3.add(cell8);
        Room redRoom = new Room(Colors.RED, cell8, list3);
        List<Player> players3 = new ArrayList<>();
        redRoom.setPlayers(players3);
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players4 = new ArrayList<>();
        greyRoom.setPlayers(players4);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        List<Room> roooms = new ArrayList<>();
        roooms.add(yellowRoom);
        roooms.add(blueRoom);
        roooms.add(redRoom);
        roooms.add(greyRoom);

        Cell cell0 = new Cell();

        cells.add(cell0);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);
        cells.add(cell5);
        cells.add(cell6);
        cells.add(cell7);
        cells.add(cell8);
        cells.add(cell9);
        cells.add(cell10);
        cells.add(cell11);

        CellFactory cellFactory = new CellFactory();

        cellFactory.setRow(2);
        cellFactory.setColumn(2);
        cell.setCommon(false);
        cell0.setCommon(false);
        cellFactory.setUp(1);
        cellFactory.setRight(3);
        cellFactory.setColor(Colors.YELLOW);

        List<CellFactory> factories = new ArrayList<>();

        cellFactory.setCell(cells, 0, roooms);

        assertEquals(cell.getDown(), cells.get(0).getDown());
        assertEquals(cell.getRoom(), cells.get(0).getRoom());
        assertEquals(cell.getColumn(), cells.get(0).getColumn());
        assertEquals(cell.getRow(), cells.get(0).getRow());
        assertEquals(cell.getLeft(), cells.get(0).getLeft());
        assertEquals(cell.getRight(), cells.get(0).getRight());
        assertEquals(cell.getUp(), cells.get(0).getUp());
        assertEquals(cell.isCommon(), cells.get(0).isCommon());

    }
}
