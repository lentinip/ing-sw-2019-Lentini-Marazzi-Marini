package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestMoveEffect {


    @Test
    public void iCanChooseWhenMoveTargetTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveTargets(2);

        assertTrue(moveEffect.iCanChooseWhenMoveTarget());

        moveEffect.setMoveTargetAfter(true);

        assertFalse(moveEffect.iCanChooseWhenMoveTarget());
    }

    @Test
    public void iCanChooseWhenMoveTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);

        assertTrue(moveEffect.iCanChooseWhenMove());

        moveEffect.setMoveYouBefore(true);

        assertFalse(moveEffect.iCanChooseWhenMove());
    }

    @Test
    public void iCanMoveBeforeTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);

        assertTrue(moveEffect.iCanMoveBefore());

        moveEffect.setMoveYouAfter(true);

        assertFalse(moveEffect.iCanMoveBefore());
    }

    @Test
    public void iCanMoveTargetBeforeTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);
        moveEffect.setMoveTargets(2);

        assertTrue(moveEffect.iCanMoveTargetBefore());

        moveEffect.setMoveTargetAfter(true);

        assertFalse(moveEffect.iCanMoveTargetBefore());
    }

    @Test
    public void iHaveAMoveBeforeTest() {

        MoveEffect moveEffect = new MoveEffect();

        assertFalse(moveEffect.iHaveAMoveBefore());

        moveEffect.setMoveYouBefore(true);

        assertTrue(moveEffect.iHaveAMoveBefore());

        moveEffect.setMoveYouBefore(false);
        moveEffect.setMoveTargetBefore(true);

        assertTrue(moveEffect.iHaveAMoveBefore());
    }

    @Test
    public void iHaveAMoveAfterTest() {

        MoveEffect moveEffect = new MoveEffect();

        assertFalse(moveEffect.iHaveAMoveAfter());

        moveEffect.setMoveTargetAfter(true);

        assertTrue(moveEffect.iHaveAMoveAfter());

        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(true);

        assertTrue(moveEffect.iHaveAMoveAfter());
    }

    @Test
    public void availablePlayersToMoveTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargets(1);
        moveEffect.setVisibility(KindOfVisibility.VISIBLE);

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

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);

        assertEquals(characters, moveEffect.availablePlayersToMove(players, player4));

        moveEffect.setMoveTargetOnYourSquare(true);

        characters.remove(Character.SPROG);
        assertEquals(characters, moveEffect.availablePlayersToMove(players, player4));
    }

    @Test
    public void setMaxTargetsTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMaxTargets(2);

        assertEquals(2, moveEffect.getMaxTargets());
    }

    @Test
    public void setMoveTargetSameDirectionTest() {

        MoveEffect moveEffect = new MoveEffect();

        assertFalse(moveEffect.isMoveTargetSameDirection());

        moveEffect.setMoveTargetSameDirection(true);

        assertTrue(moveEffect.isMoveTargetSameDirection());
    }

    @Test
    public void setObligatoryYouTest() {

        MoveEffect moveEffect = new MoveEffect();

        assertFalse(moveEffect.isObligatoryYou());

        moveEffect.setObligatoryYou(true);

        assertTrue(moveEffect.isObligatoryYou());
    }

    @Test
    public void setObligatoryTargetTest() {

        MoveEffect moveEffect = new MoveEffect();

        assertFalse(moveEffect.isObligatoryTarget());

        moveEffect.setObligatoryTarget(true);

        assertTrue(moveEffect.isObligatoryTarget());
    }

    @Test
    public void availableCellsMoveTargetTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargets(1);

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

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        moveEffect.setObligatoryTarget(true);

        List<Cell> cells = new ArrayList<>();
        cells.add(cell10);

        assertEquals(cells, moveEffect.availableCellsMoveTarget(player4));

        moveEffect.setObligatoryTarget(false);
        moveEffect.setMoveTargetSameDirection(true);

        cells.remove(cell10);
        cells.add(cell11);
        cells.add(cell10);

        assertEquals(cells, moveEffect.availableCellsMoveTarget(player4));
    }
}















