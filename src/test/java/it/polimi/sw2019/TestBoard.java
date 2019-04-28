package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestBoard {

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