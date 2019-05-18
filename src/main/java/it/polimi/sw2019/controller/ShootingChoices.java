package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class ShootingChoices {

    /**
     * Default constructor
     */
    public ShootingChoices(Match match, VirtualView view) {
        this.match = match;
        this.view = view;
    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private Weapon selectedWeapon;

    private Powerup selectedPowerup;

    private Effect currentEffect;

    private List<Effect> usedEffect = new ArrayList<>();

    private Cell moveCell;

    private List<Player> movedPlayers = new ArrayList<>();

    private List<Player> shootedPlayers = new ArrayList<>();

    private List<Player> damagedPlayers = new ArrayList<>(); //Useful for powerups

    private List<Cell> shootedCells = new ArrayList<>();

    /* Methods */



    public void reset() {

        //TODO implement
        return;
    }

    public void selectionHandler(Message message){
        //TODO implement
    }

}
