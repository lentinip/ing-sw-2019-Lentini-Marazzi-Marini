package it.polimi.sw2019;

import it.polimi.sw2019.commons.Colors;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.commons.Character;
import it.polimi.sw2019.network.server.Server;
import it.polimi.sw2019.network.server.VirtualView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.sw2019.commons.Colors.YELLOW;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;

public class TestMatch {

    /**
     * verifies if I get the correct player corrisponding to the selected username and the value "null" if doesn't exist a player with that name
     */
    @Test
    public void getPlayerByUsernameTest() {

        Match match = new Match();

        Player player = new Player();
        player.setName("Franco");
        Player player1 = new Player();
        player1.setName("Carlo");
        Player player2 = new Player();
        player2.setName("Piero");
        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player1);
        players.add(player2);

        match.setPlayers(players);

        assertEquals(player1, match.getPlayerByUsername("Carlo"));
        assertEquals(null, match.getPlayerByUsername("Enzo"));
    }

    /**
     * verifies if I create a not-null board
     */
    @Test
    public void initializeMatchTest() {

        Match match = new Match();

        match.initializeMatch("Board1.json");

        assertNotNull(match.getBoard());
    }


    @Test
    public void endMatchTest() {

        Match match = new Match();

        Player player = new Player();
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setFlipped(true);

        Player player1 = new Player();
        PlayerBoard playerBoard1 = new PlayerBoard();

        List<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player1);

        List<Character> characters = new ArrayList<>();
        characters.add(Character.SPROG);
        characters.add(Character.DOZER);

        Board board = new Board();
        board.setKillTrack(new KillTokens(characters));
        match.setBoard(board);
        match.endMatch();

        assertTrue(match.isEnded());
    }

    /**
     * verify if I get the correct player of the list and if I get null if the match is ended
     */
    @Test
    public void setNextPlayerTest() {

        Match match = new Match();

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.BANSHEE);
        characters.add(Character.DOZER);
        characters.add(Character.DISTRUCTOR);

        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setDamage(new DamageTokens(characters));
        playerBoard.setMarks(new Marks(characters));
        Ammo ammo = new Ammo(0, 1, 2);
        playerBoard.setAmmo(ammo);

        List<Player> players = new ArrayList<>();
        Player player = new Player();
        player.setCharacter(Character.VIOLET);
        player.setPlayerBoard(playerBoard);
        players.add(player);
        Player player1 = new Player();
        player1.setCharacter(Character.BANSHEE);
        player1.setPlayerBoard(playerBoard);
        players.add(player1);
        Player player2 = new Player();
        player2.setCharacter(Character.DOZER);
        player2.setPlayerBoard(playerBoard);
        players.add(player2);
        Player player3 = new Player();
        player3.setCharacter(Character.DISTRUCTOR);
        player3.setPlayerBoard(playerBoard);
        players.add(player3);
        match.setPlayers(players);

        match.setBoard(match.getFactory().createBoard("Board1.json", match.getPlayers()));
        match.setNumberOfPlayers(4);
        assertEquals(4, match.getNumberOfPlayers());

        match.setCurrentPlayer(player);
        match.setNextPlayer();
        assertEquals(player1, match.getCurrentPlayer());
        match.setNextPlayer();
        assertEquals(player2, match.getCurrentPlayer());
        match.setNextPlayer();
        assertEquals(player3, match.getCurrentPlayer());
        match.setNextPlayer();
        assertEquals(player, match.getCurrentPlayer());

        match.setEnded(true);
        match.setNextPlayer();
        assertEquals(null, match.getCurrentPlayer());
    }

    @Test
    public void endTurnTest() {

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

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");

        List<Character> characters = new ArrayList<>();
        characters.add(Character.VIOLET);
        characters.add(Character.BANSHEE);
        characters.add(Character.DOZER);

        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setDamage(new DamageTokens(characters));
        playerBoard.setMarks(new Marks(characters));
        Ammo ammo = new Ammo(0, 1, 2);
        playerBoard.setAmmo(ammo);
        PlayerBoard playerBoard1 = new PlayerBoard();
        playerBoard1.setFlipped(true);
        playerBoard1.setDamage(new DamageTokens(characters));
        playerBoard1.setMarks(new Marks(characters));
        playerBoard1.setAmmo(ammo);
        PlayerBoard playerBoard2 = new PlayerBoard();
        playerBoard2.setDamage(new DamageTokens(characters));
        playerBoard2.setMarks(new Marks(characters));
        playerBoard2.setAmmo(ammo);

        List<Player> players = new ArrayList<>();
        Player player = new Player();
        player.setCharacter(Character.VIOLET);
        player.setPosition(cell);
        player.setDead(true);
        player.setPlayerBoard(playerBoard);
        players1.add(player);
        players.add(player);
        Player player1 = new Player();
        player1.setCharacter(Character.BANSHEE);
        player1.setPosition(cell11);
        player1.setPlayerBoard(playerBoard1);
        player1.setDead(true);
        players.add(player1);
        Player player2 = new Player();
        player2.setCharacter(Character.DOZER);
        player2.setPosition(null);
        players.add(player2);
        player2.setPlayerBoard(playerBoard2);

        Match match = new Match(false, false, usernames, "Board1.json", new VirtualView(new Server()));
        match.setPlayers(players);

        match.setFrenzyMode(true);
        match.setLastPlayer(player2);
        assertEquals(player2, match.getLastPlayer());
        match.setCurrentPlayer(player2);
        KillTokens killTokens = new KillTokens(characters);
        match.setScore(new Score(characters, killTokens));
        match.endTurn();

        assertTrue(match.getDeadPlayers().contains(player));
        assertTrue(match.getDeadPlayers().contains(player1));

        match.setFrenzyMode(false);
        assertFalse(match.getFrenzyMode());
        match.setLastPlayer(null);
        /*match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);
        match.getBoard().getKillTrack().addKill(Character.VIOLET);*/
        match.getBoard().getKillTrack().setTotalKills(8);
        match.setIWantFrenzyMode(true);
        assertTrue(match.isiWantFrenzyMode());
        match.setCurrentPlayer(player1);

        match.endTurn();

        assertEquals(State.FRENZYAFTERFIRST, player.getState());
        assertEquals(State.FRENZYBEFOREFIRST, player2.getState());
    }

    /*@Test
    public void setIDPartitaTest() {

        List<String> usernames = new ArrayList<>();
        usernames.add("Carlo");
        usernames.add("Fausto");
        usernames.add("Beppe");
        usernames.add("Fulvio");
        usernames.add("Renato");

        Match match = new Match(false, false, usernames, "Board1.json", new VirtualViewSimulator(new Server()));
        match.setIdPartita(5);

        assertEquals(5, match.getIdPartita());
    }*/

    @Test
    public void setFactoryTest() {

        Match match = new Match();

        Factory factory = new Factory();
        match.setFactory(factory);

        assertEquals(factory, match.getFactory());
    }

    @Test
    public void setDeadPlayersTest() {

        Match match = new Match();

        List<Player> players = new ArrayList<>();
        Player player = new Player();
        Player player1 = new Player();
        players.add(player);
        players.add(player1);

        match.setDeadPlayers(players);

        assertEquals(players, match.getDeadPlayers());
    }



}
