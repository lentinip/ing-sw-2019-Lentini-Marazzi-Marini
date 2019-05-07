package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class SpawnCell extends Cell{

    /**
     * Default constructor
     */

    public SpawnCell() {

    }

    /* Attributes */

    private List<Weapon> weapons = new ArrayList<>(); /* max 3 weapons */

    /* Methods */

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void addWeapon(Weapon weapon) {

        weapons.add(weapon);
    }
}
