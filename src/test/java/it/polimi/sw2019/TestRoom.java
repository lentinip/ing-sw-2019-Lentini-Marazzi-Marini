package it.polimi.sw2019;

import it.polimi.sw2019.model.Cell;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Room;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestRoom {

    @Test
    public void TestPlayersInside() {

        Room room = new Room();

        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();
        Cell cell4 = new Cell();

        cell1.setColumn(1);
        cell1.setRow(1);
        cell2.setColumn(1);
        cell2.setRow(2);
        cell3.setColumn(2);
        cell3.setRow(1);
        cell4.setColumn(2);
        cell4.setRow(2);

        room.addCell(cell1);
        room.addCell(cell2);
        room.addCell(cell3);

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();

        player1.setPosition(cell1);
        player2.setPosition(cell2);
        player3.setPosition(cell3);
        player4.setPosition(cell4);

        List<Player> testPlayers = new ArrayList<>();
        testPlayers.add(player1);
        testPlayers.add(player2);
        testPlayers.add(player3);
        testPlayers.add(player4);

        List<Player> resultPlayers = new ArrayList<>();
        resultPlayers.add(player1);
        resultPlayers.add(player2);
        resultPlayers.add(player3);

        assertEquals(resultPlayers, room.playersInside(testPlayers));
    }

    @Test
    public void TestAddCell() {

        Room room = new Room();

        Cell cell1 = new Cell();
        Cell cell2 = new Cell();

        List<Cell> testList = new ArrayList<>();
        testList.add(cell1);
        testList.add(cell2);

        room.addCell(cell1);
        room.addCell(cell2);
        assertEquals(testList, room.getRoomCells());

        Cell cell3 = null;

        try{
            room.addCell(cell3);
            fail();
        } catch (NullPointerException e) {System.out.print("fallito");}

    }
}
