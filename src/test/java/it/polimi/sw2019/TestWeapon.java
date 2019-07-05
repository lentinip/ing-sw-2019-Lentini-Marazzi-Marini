package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.commons.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import it.polimi.sw2019.commons.Colors;
import static org.junit.Assert.*;

public class TestWeapon {

    /**
     * the test verifies if the effect has been added to the list
     */
    @Test
    public void addEffectTest() {

        Weapon weapon = new Weapon();

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        Effect effect2 = new Effect();

        List<Effect> effects = new ArrayList<>();
        effects.add(effect);
        effects.add(effect1);
        effects.add(effect2);

        weapon.addEffect(effect);
        weapon.addEffect(effect1);
        weapon.addEffect(effect2);

        assertEquals(effects, weapon.getEffects());
    }

    /**
     * the test verifies if the effects list has been setted successfully
     */
    @Test
    public void setEffectsTest() {

        Weapon weapon = new Weapon();

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        Effect effect2 = new Effect();

        List<Effect> effects = new ArrayList<>();
        effects.add(effect);
        effects.add(effect1);
        effects.add(effect2);

        weapon.setEffects(effects);

        assertEquals(effects, weapon.getEffects());
    }

    /**
     * verifies if I get the correct effect with the type "MOVE"
     */
    @Test
    public void getMoveTypeEffectTest() {

        Weapon weapon = new Weapon();

        Effect effect = new Effect();
        effect.setType(EffectsKind.MOVE);
        weapon.addEffect(effect);
        Effect effect1 = new Effect();
        effect1.setType(EffectsKind.SINGLE_TARGET);
        weapon.addEffect(effect1);
        Effect effect2 = new Effect();
        effect2.setType(EffectsKind.MOVE);
        weapon.addEffect(effect2);

        assertEquals(effect, weapon.getMoveTypeEffect());

        Weapon weapon1 = new Weapon();

        weapon1.addEffect(effect1);

        try {
            weapon1.getMoveTypeEffect();
            fail();
        } catch (NullPointerException e) {
            System.out.print("fallito");
        }
    }

    /**
     *
     */
    @Test
    public void usableWeaponBeforeComplexActionTest() {

        Weapon weapon = new Weapon();

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

        Player player1 = new Player("Enzo", Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", Character.DOZER);
        player4.setPosition(cell11);
        weapon.setOwner(player4);
        Ammo ammo = new Ammo(2, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player4.setPlayerBoard(playerBoard);

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell9);

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
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players3 = new ArrayList<>();
        players3.add(player4);
        players3.add(player5);
        greyRoom.setPlayers(players3);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        effect.setType(EffectsKind.SINGLE_TARGET);
        effect1.setType(EffectsKind.MOVE);
        effect.setVisibility(KindOfVisibility.VISIBLE);
        effect.setVisibilityClass(new Visibility());
        int[] movesAway = new int[5];
        movesAway[0] = 0;
        effect.setMovesAway(movesAway);
        effect1.setVisibility(KindOfVisibility.VISIBLE);
        effect1.setVisibilityClass(new Visibility());
        effect1.setCost(new Ammo(2, 0, 0));
        effect1.setMovesAway(movesAway);
        Effect effect2 = new Effect();
        effect2.setType(EffectsKind.MOVE);
        effect2.setCost(new Ammo(3, 0, 0));
        effect2.setVisibility(KindOfVisibility.VISIBLE);
        effect2.setVisibilityClass(new Visibility());
        effect2.setMovesAway(movesAway);
        weapon.addEffect(effect);
        weapon.addEffect(effect1);
        weapon.addEffect(effect2);

        player4.setState(State.ADRENALINIC2);

        assertTrue(weapon.usableWeaponBeforeComplexAction(players));
    }

    @Test
    public void usableWeaponTest() {

        Weapon weapon = new Weapon();

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

        Player player1 = new Player("Enzo", Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", Character.DOZER);
        player4.setPosition(cell11);
        weapon.setOwner(player4);
        Ammo ammo = new Ammo(2, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player4.setPlayerBoard(playerBoard);

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell9);

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
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players3 = new ArrayList<>();
        players3.add(player4);
        players3.add(player5);
        greyRoom.setPlayers(players3);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Effect effect = new Effect();
        effect.setType(EffectsKind.SINGLE_TARGET);
        effect.setVisibility(KindOfVisibility.VISIBLE);
        effect.setVisibilityClass(new Visibility());
        int[] movesAway = new int[5];
        movesAway[0] = 0;
        effect.setMovesAway(movesAway);
        weapon.addEffect(effect);

        assertTrue(weapon.usableWeapon(players));

        Effect effect1 = new Effect();
        effect1.setType(EffectsKind.MOVE);
        effect1.setVisibility(KindOfVisibility.VISIBLE);
        effect1.setVisibilityClass(new Visibility());
        effect1.setCost(new Ammo(2, 0, 0));
        effect1.setMovesAway(movesAway);
        Effect effect2 = new Effect();
        effect2.setType(EffectsKind.MOVE);
        effect2.setCost(new Ammo(3, 0, 0));
        effect2.setVisibility(KindOfVisibility.VISIBLE);
        effect2.setVisibilityClass(new Visibility());
        effect2.setMovesAway(movesAway);
        weapon.addEffect(effect1);
        weapon.addEffect(effect2);

        assertTrue(weapon.usableWeapon(players));

        List<Effect> effects = new ArrayList<>();
        MoveEffect moveEffect = new MoveEffect();
        moveEffect.setMoveYou(0);
        effect2.setMove(moveEffect);
        effects.add(effect2);
        weapon.setEffects(effects);

        assertFalse(weapon.usableWeapon(players));

        Weapon weapon1 = new Weapon();
        weapon1.setOwner(player4);
        player5.setPosition(cell8);
        player4.addWeapon(weapon1);
        moveEffect.setMoveYou(2);
        effect2.setType(EffectsKind.MOVE);
        weapon1.setHasAMoveTypeEffect(true);
        weapon1.addEffect(effect2);

        assertFalse(weapon1.usableWeapon(players));
    }


    @Test
    public void listTest(){
        List<Weapon> weapons = new ArrayList<>();
        Weapon weapon1 = new Weapon();
        weapon1.setName("weapon1");
        Weapon weapon2 = new Weapon();
        weapon2.setName("weapon2");
        Weapon weapon3 = new Weapon();
        weapon3.setName("weapon3");

        weapons.add(weapon2);
        weapons.add(weapon1);
        weapons.add(weapon3);

        for (Weapon weapon : weapons){
            System.out.print("\n"+weapon.getName());
        }

        System.out.print("\n");

        for (int i = 0; i<weapons.size(); i++){
            System.out.print("\n"+weapons.get(i).getName());
        }

        System.out.print("\n");
        System.out.print("\n");

        System.out.print("\n"+weapons.get(0).getName());
    }

    /**
     * verifies if the method returns the correct index
     */
    @Test
    public void getIndexByEffectTest() {

        Weapon weapon = new Weapon();

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        weapon.addEffect(effect);
        weapon.addEffect(effect1);

        assertEquals(1, weapon.getIndexByEffect(effect1));
    }

    /**
     * verifies if the method unloadWeapon assigns the correct value
     */
    @Test
    public void unloadWeaponTest() {

        Weapon weapon = new Weapon();
        weapon.setIsLoaded(true);
        weapon.unloadWeapon();
        assertFalse(weapon.getIsLoaded());
    }

    /**
     * verifies if I get the correct usable effect from the method usableEffects
     */
    @Test
    public void usableEffectsTest() {

        Weapon weapon = new Weapon();

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

        Player player1 = new Player("Enzo", Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", Character.DOZER);
        player4.setPosition(cell11);
        weapon.setOwner(player4);
        Ammo ammo = new Ammo(2, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player4.setPlayerBoard(playerBoard);

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell9);

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
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players3 = new ArrayList<>();
        players3.add(player4);
        players3.add(player5);
        greyRoom.setPlayers(players3);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        effect.setType(EffectsKind.SINGLE_TARGET);
        effect1.setType(EffectsKind.MOVE);
        effect.setVisibility(KindOfVisibility.VISIBLE);
        effect.setVisibilityClass(new Visibility());
        int[] movesAway = new int[5];
        movesAway[0] = 0;
        effect.setMovesAway(movesAway);
        effect1.setVisibility(KindOfVisibility.VISIBLE);
        effect1.setVisibilityClass(new Visibility());
        effect1.setCost(new Ammo(2, 0, 0));
        effect1.setMovesAway(movesAway);
        Effect effect2 = new Effect();
        effect2.setType(EffectsKind.MOVE);
        effect2.setCost(new Ammo(3, 0, 0));
        effect2.setVisibility(KindOfVisibility.VISIBLE);
        effect2.setVisibilityClass(new Visibility());
        effect2.setMovesAway(movesAway);
        weapon.addEffect(effect);
        weapon.addEffect(effect1);
        weapon.addEffect(effect2);

        List<Effect> result = new ArrayList<>();
        result.add(effect);
        result.add(effect1);

        //System.out.print(weapon.usableEffects(players));

        assertTrue(weapon.usableEffects(players).containsAll(result));
        assertTrue(result.containsAll(weapon.usableEffects(players)));
    }

    /**
     * the test is similar to the previous one, now we verify if there is at least a usable effect
     */
    @Test
    public void hasOneUsableEffectTest() {

        Weapon weapon = new Weapon();

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

        Player player1 = new Player("Enzo", Character.BANSHEE);
        player1.setPosition(cell1);

        Player player2 = new Player("Carl", Character.DISTRUCTOR);
        player2.setPosition(cell3);

        Player player3 = new Player("Anna", Character.VIOLET);
        player3.setPosition(cell6);

        Player player4 = new Player("Luca", Character.DOZER);
        player4.setPosition(cell11);
        weapon.setOwner(player4);
        Ammo ammo = new Ammo(2, 2, 2);
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setAmmo(ammo);
        player4.setPlayerBoard(playerBoard);

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell9);

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
        cell7.setRoom(redRoom);
        cell8.setRoom(redRoom);

        List<Cell> list4 = new ArrayList<>();
        list4.add(cell9);
        list4.add(cell10);
        list4.add(cell11);
        Room greyRoom = new Room(Colors.GREY, cell10, list4);
        List<Player> players3 = new ArrayList<>();
        players3.add(player4);
        players3.add(player5);
        greyRoom.setPlayers(players3);
        cell9.setRoom(greyRoom);
        cell10.setRoom(greyRoom);
        cell11.setRoom(greyRoom);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);

        Effect effect = new Effect();
        Effect effect1 = new Effect();
        effect.setType(EffectsKind.SINGLE_TARGET);
        effect1.setType(EffectsKind.MOVE);
        effect.setVisibility(KindOfVisibility.VISIBLE);
        effect.setVisibilityClass(new Visibility());
        int[] movesAway = new int[5];
        movesAway[0] = 0;
        effect.setMovesAway(movesAway);
        effect1.setVisibility(KindOfVisibility.VISIBLE);
        effect1.setVisibilityClass(new Visibility());
        effect1.setCost(new Ammo(2, 0, 0));
        effect1.setMovesAway(movesAway);
        Effect effect2 = new Effect();
        effect2.setType(EffectsKind.MOVE);
        effect2.setCost(new Ammo(3, 0, 0));
        effect2.setVisibility(KindOfVisibility.VISIBLE);
        effect2.setVisibilityClass(new Visibility());
        effect2.setMovesAway(movesAway);
        weapon.addEffect(effect);
        weapon.addEffect(effect1);
        weapon.addEffect(effect2);

        assertTrue(weapon.hasOneUsableEffect(players));
    }



}














