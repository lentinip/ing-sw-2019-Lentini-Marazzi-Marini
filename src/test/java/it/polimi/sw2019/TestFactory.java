package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TestFactory {

    @Test
    public void createPowerupDeckTest(){

        Factory factory = new Factory();
        List<Powerup> deck = factory.createPowerupDeck();
        Assert.assertNotNull(deck);
    }

    @Test
    public void createWeaponDeckTest(){

        Factory factory = new Factory();
        List<Weapon> deck = factory.createWeaponDeck();
        Assert.assertNotNull(deck);
    }

    @Test
    public void createBoardTest(){

        Factory factory = new Factory();

        Player player1 = new Player();
        player1.setCharacter(Character.DISTRUCTOR);
        Player player2 = new Player();
        player2.setCharacter(Character.BANSHEE);
        Player player3 = new Player();
        player3.setCharacter(Character.VIOLET);

        List<Player> players = new ArrayList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);

        Board board = factory.createBoard("Board2.json",players );

        Assert.assertNotNull(board);
        Assert.assertNotNull(board.getRooms());
        Assert.assertEquals(board.getRooms().get(0).getPlayers(), players);
    }

    @Test
    public void createAmmoTileDeckTest(){

        Factory factory = new Factory();

        List<AmmoTile> deck = factory.createAmmoDeck();

        Assert.assertTrue(!deck.isEmpty());
    }
}
