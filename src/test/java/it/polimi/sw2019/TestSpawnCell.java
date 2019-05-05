package it.polimi.sw2019;

import it.polimi.sw2019.model.SpawnCell;
import it.polimi.sw2019.model.Weapon;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestSpawnCell {

    @Test
    public void setWeaponsTest() {

        SpawnCell spawnCell = new SpawnCell();

        List<Weapon> weapons = new ArrayList<>();
        Weapon weapon1 = new Weapon();
        weapon1.setName("Cannone");
        Weapon weapon2 = new Weapon();
        weapon2.setName("Pistola");

        spawnCell.setWeapons(weapons);
        assertEquals(weapons, spawnCell.getWeapons());
    }

    @Test
    public void addWeaponTest() {

        SpawnCell spawnCell = new SpawnCell();

        List<Weapon> resultWeapons = new ArrayList<>();
        Weapon weapon1 = new Weapon();
        weapon1.setName("Cannone");
        resultWeapons.add(weapon1);
        Weapon weapon2 = new Weapon();
        weapon2.setName("Pistola");
        resultWeapons.add(weapon2);
        Weapon weapon3 = new Weapon();
        weapon3.setName("Fucile");
        resultWeapons.add(weapon3);

        List<Weapon> weapons = new ArrayList<>();
        weapons.add(weapon1);
        weapons.add(weapon2);
        spawnCell.setWeapons(weapons);
        spawnCell.addWeapon(weapon3);

        assertEquals(resultWeapons, spawnCell.getWeapons());
    }
}
