package it.polimi.sw2019;

import it.polimi.sw2019.model.Cell;
import it.polimi.sw2019.model.Room;
import it.polimi.sw2019.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestCell {

    @Test
    public void TestPlayersInCell() {

        Cell cell1 = new Cell();
        cell1.setRow(2);
        cell1.setColumn(2);
        Cell cell2 = new Cell();
        cell2.setRow(2);
        cell2.setColumn(1);
        Cell cell3 = new Cell();
        cell3.setRow(1);
        cell3.setColumn(1);

        Player player1 = new Player();
        player1.setPosition(cell1);
        Player player2 = new Player();
        player2.setPosition(cell2);
        Player player3 = new Player();
        player3.setPosition(cell1);
        Player player4 = new Player();
        player4.setPosition(cell3);
        Player player5 = new Player();
        player5.setPosition(cell3);

        List<Player> testPlayer = new ArrayList<>();
        testPlayer.add(player1);
        testPlayer.add(player2);
        testPlayer.add(player3);
        testPlayer.add(player4);
        testPlayer.add(player5);

        Room room = new Room();

        room.setPlayers(testPlayer);

        cell1.setRoom(room);

        List<Player> resultPlayer = new ArrayList<>();
        resultPlayer.add(player1);
        resultPlayer.add(player3);

        assertEquals(resultPlayer, cell1.playersInCell());

        Room room2 = new Room();
        cell2.setRoom(room2);
        assertTrue(cell2.playersInCell().isEmpty());
    }

    @Test
    public void nearCellsTest() {

        Cell cell1 = new Cell();
        cell1.setColumn(1);
        cell1.setRow(1);
        Cell cell2 = new Cell();
        cell2.setColumn(1);
        cell2.setRow(0);
        Cell cell3 = new Cell();
        cell3.setColumn(1);
        cell3.setRow(2);

        List<Cell> resultCells = new ArrayList<>();
        resultCells.add(cell1);
        resultCells.add(cell2);
        resultCells.add(cell3);

        Cell cell = new Cell();
        cell.setUp(cell1);
        cell.setDown(cell2);
        cell.setLeft(cell3);

        assertEquals(resultCells, cell.nearCells());
    }

    @Test
    public void reachableCellsTest() {

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

        List<Cell> resultCells = new ArrayList<>();
        resultCells.add(cell);
        resultCells.add(cell1);
        resultCells.add(cell3);
        resultCells.add(cell5);
        resultCells.add(cell2);

        assertEquals(resultCells, cell.reachableCells(2));

    }
}
