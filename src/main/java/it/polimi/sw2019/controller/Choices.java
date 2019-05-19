package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class Choices {

    /**
     * Default constructor
     */
    public Choices(Match match, VirtualView view) {
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

    private Player selectedPlayer;

    /* Methods */

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public Effect getCurrentEffect() {
        return currentEffect;
    }

    public void setCurrentEffect(Effect currentEffect) {
        this.currentEffect = currentEffect;
    }

    public Cell getMoveCell() {
        return moveCell;
    }

    public void setMoveCell(Cell moveCell) {
        this.moveCell = moveCell;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public List<Player> getMovedPlayers() {
        return movedPlayers;
    }

    public void setMovedPlayers(List<Player> movedPlayers) {
        this.movedPlayers = movedPlayers;
    }

    public void reset() {

        //TODO implement
        return;
    }

    public void selectionHandler(Message message){

        switch (message.getTypeOfMessage()){
            case SELECTED_CARD:
                selectionCardHandler(message);
                break;
            case SELECTED_COLOR:
                break;
            case SELECTED_EFFECT:
                break;
            case SELECTED_CELL:
                break;
            case SELECTED_PLAYER:
                break;
            default:
                break;
        }
    }

    public void selectionCardHandler(Message message){
        //TODO implement
    }

    public void effectHandler(){
        //TODO implement
    }

    public List<Character> availablePlayersToMove(){
        List<Player> players = new ArrayList<>(match.getPlayers());
        List<Character> characters = new ArrayList<>();

        players.removeAll(movedPlayers);

        for (Player player : players){
            if (player.getPosition().reachableCells(1).contains(moveCell)){
                characters.add(player.getCharacter());
            }
        }
        return characters;
    }

}
