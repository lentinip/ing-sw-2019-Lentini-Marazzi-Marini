package it.polimi.sw2019;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestWeapon {

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

        Player player = new Player();
        player.setState(State.FRENZYBEFOREFIRST);
        player.setPosition(cell1);

        //TODO continue

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

        Player player5 = new Player("Sandra", Character.SPROG);
        player5.setPosition(cell8);

        //TODO continue
    }

    @Test
    public void hasOneUsableEffectTest() {

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

        //TODO continue
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
}
