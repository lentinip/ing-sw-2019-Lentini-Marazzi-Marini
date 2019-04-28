package it.polimi.sw2019;

import it.polimi.sw2019.model.Cell;
import it.polimi.sw2019.model.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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

        List<Player> testPlayer = new ArrayList<>();
        testPlayer.add(player1);
        testPlayer.add(player2);
        testPlayer.add(player3);
        testPlayer.add(player4);

        List<Player> resultPlayer = new ArrayList<>();
        resultPlayer.add(player1);
        resultPlayer.add(player3);

        assertEquals(resultPlayer, cell1.playersInCell(testPlayer));

        List<Player> nullList = null;
        try{
            cell1.playersInCell(nullList);
            fail();
        } catch (NullPointerException e) {System.out.print("fallito");}
    }


}
