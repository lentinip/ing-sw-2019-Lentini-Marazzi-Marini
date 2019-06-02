package it.polimi.sw2019;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.Target;
import it.polimi.sw2019.model.WeaponFactory;
import it.polimi.sw2019.model.WeaponsType;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestWeaponFactory {

    @Test
    public void testSetName() {

        WeaponFactory weaponFactory = new WeaponFactory();

        String name = "ciao";
        weaponFactory.setName(name);

        assertEquals(name, weaponFactory.getName());
    }

    @Test
    public void testSetDescription() {

        WeaponFactory weaponFactory = new WeaponFactory();

        String name = "arma";
        weaponFactory.setDescription(name);

        assertEquals(name, weaponFactory.getDescription());
    }

    @Test
    public void testSetGrabCost() {

        WeaponFactory weaponFactory = new WeaponFactory();

        Ammo grabCost = new Ammo(1, 2, 3);
        weaponFactory.setGrabCost(grabCost);

        assertEquals(grabCost, weaponFactory.getGrabCost());
    }

    @Test
    public void testSetReloadCost() {

        WeaponFactory weaponFactory = new WeaponFactory();

        Ammo reloadCost = new Ammo(1, 2, 3);
        weaponFactory.setReloadCost(reloadCost);

        assertEquals(reloadCost, weaponFactory.getReloadCost());
    }

    @Test
    public void testSetType() {

        WeaponFactory weaponFactory = new WeaponFactory();

        WeaponsType weaponsType = WeaponsType.MODES;
        weaponFactory.setType(WeaponsType.MODES);

        assertEquals(weaponsType, weaponFactory.getType());
    }

    @Test
    public void testSetEffects() {

        WeaponFactory weaponFactory = new WeaponFactory();

        String[] effects = new String[3];
        effects[0] = "shoot";
        effects[1] = "shoot 2 times";
        effects[2] = "move one time and shoot";

        weaponFactory.setEffects(effects);

        assertArrayEquals(effects, weaponFactory.getEffects());
    }

    @Test
    public void testSetHasAnOrder() {

        WeaponFactory weaponFactory = new WeaponFactory();

        weaponFactory.setHasAnOrder(true);

        assertTrue(weaponFactory.isHasAnOrder());
    }

    @Test
    public void testSetHasAMoveTypeEffect() {

        WeaponFactory weaponFactory = new WeaponFactory();

        weaponFactory.setHasAMoveTypeEffect(true);

        assertTrue(weaponFactory.isHasAMoveTypeEffect());
    }

}
