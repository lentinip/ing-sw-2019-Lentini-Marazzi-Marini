package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.SingleAction;

/**
 * This class handles a single move
 */
public class TurnManager {

    /**
     * Default Constructor
     */
    public TurnManager(Match match) {
        this.match = match;
    }

    /* Attributes */

    private Match match;

    private Cell selectedCell;

    private Player currentPlayer;

    private Powerup selectedPowerup;

    private Weapon selectedWeapon;

    private Player selectedPlayer;

    private TypeOfAction typeOfAction;

    private int powerupPaid;

    private ShootingChoices shootingChoices;

    /* Methods */

    public Match getMatch() {
        return match;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setSelectedPowerup(Powerup selectedPowerup) {
        this.selectedPowerup = selectedPowerup;
    }

    public Powerup getSelectedPowerup() {
        return selectedPowerup;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setTypeOfAction(TypeOfAction typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public void setSelectedPlayer(Player selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setPowerupPaid(int powerupPaid) {
        this.powerupPaid = powerupPaid;
    }

    public int getPowerupPaid() {
        return powerupPaid;
    }

    public void setShootingChoices(ShootingChoices shootingChoices) {
        this.shootingChoices = shootingChoices;
    }

    public ShootingChoices getShootingChoices() {
        return shootingChoices;
    }

    public void getSingleAction(SingleAction singleAction){

        currentPlayer = match.getPlayerFromCharacter(singleAction.getCurrentCharacter());
        typeOfAction = singleAction.getType();

        /*
        NOTE:
        From now on I'm going to use some values for the attributes that means that the value is not going to be considered.

        Here's the list:
        selectedCellRow = 10
        selectedCellColumn = 10
        selectedPowerupIndex = 5
        selectedWeaponIndex = 5
        selectedCharacter = null
         */

        //A list of safe accesses
        if(singleAction.getSelectedCellRow()!=10 && singleAction.getSelectedCellColumn()!=10){
            selectedCell = match.getBoard().getCell(singleAction.getSelectedCellRow(), singleAction.getSelectedCellColumn());
        }

        if(singleAction.getSelectedPowerupIndex()!=5){
            selectedPowerup = currentPlayer.getPowerups().get(singleAction.getSelectedPowerupIndex());
        }

        if(singleAction.getSelectedWeaponIndex()!=5){
            selectedWeapon = currentPlayer.getWeapons().get(singleAction.getSelectedWeaponIndex());
        }

        if(singleAction.getSelectedCharacter()!=null){
            selectedPlayer = match.getPlayerFromCharacter(singleAction.getSelectedCharacter());
        }
    }

    public void endTurn() {

        //TODO implement
        return;
    }

    public void timer()  {

        //TODO implement
        return;
    }

    public void reset()  {

        //TODO implement
        return;
    }

    public void handleSingleAction(SingleAction singleAction){
        //TODO implement
    }

    public void move(Player player, Cell selectedCell){
        //TODO implement
    }

    public void grab(Player player, Cell selectedCell){
        //TODO implement
    }

}
