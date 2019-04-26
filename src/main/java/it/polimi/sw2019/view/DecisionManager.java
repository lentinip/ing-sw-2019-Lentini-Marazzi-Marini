package it.polimi.sw2019.view;

import java.util.List;
import it.polimi.sw2019.model.*;

public class DecisionManager {

    /* methods */


    /**
     * choose a cell from an arrayList
     * @param ChoosableCells
     * @return
     */
    public Cell askCells(List<Cell> ChoosableCells){

        Cell selectedCell = new Cell();

        //TODO implement

        return selectedCell;

    }

    /**
     * choose a cell from an arrayList
     * @param ChoosablePlayers
     * @return
     */
    public Player askPlayers(List<Player> ChoosablePlayers){

        Player selectedPlayer = new Player();

        //TODO implement

        return selectedPlayer;
    }


    /**
     * choose a weapon from an arrayList
     * @param ChoosableWeapons
     * @return
     */
    public Weapon askWeapon(List<Weapon> ChoosableWeapons){

        Weapon selectedWeapon = new Weapon();

        //TODO implement

        return selectedWeapon;
    }

    /**
     * choose a weapon from an arrayList
     * @param ChoosablePoweups
     * @return
     */
    public Powerup askPowerup(List<Weapon> ChoosablePoweups){

        Powerup selectedPoweup = new Powerup();

        //TODO implement

        return selectedPoweup;
    }

}
