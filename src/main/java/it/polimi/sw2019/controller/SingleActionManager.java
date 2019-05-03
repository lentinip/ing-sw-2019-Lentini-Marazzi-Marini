package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.SingleAction;

/**
 * This class handles a single action
 */
public class SingleActionManager {

    /**
     * Default Constructor
     */
    public SingleActionManager(Match match, TurnManager turnManager) {
        this.match = match;
        this.currentPlayer = match.getCurrentPlayer();
        this.turnManager = turnManager;
    }

    /* Attributes */

    private Match match;

    private AtomicActions atomicActions = match.getAtomicActions();

    private TurnManager turnManager;

    private Player currentPlayer;

    private Cell selectedCell;

    private Powerup selectedPowerup;

    private int powerupIndex;

    private Weapon selectedWeapon;

    private int weaponIndex;

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

    public int getPowerupIndex() {
        return powerupIndex;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setWeaponIndex(int weaponIndex) {
        this.weaponIndex = weaponIndex;
    }

    public int getWeaponIndex() {
        return weaponIndex;
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

        if (currentPlayer != match.getPlayerFromCharacter(singleAction.getCurrentCharacter())){
            //TODO implement exception, note: Powerup Granade
        };
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
            powerupIndex = singleAction.getSelectedPowerupIndex();
            selectedPowerup = currentPlayer.getPowerups().get(singleAction.getSelectedPowerupIndex());
        }

        if(singleAction.getSelectedWeaponIndex()!=5){
            weaponIndex = singleAction.getSelectedWeaponIndex();
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

    public void handleSingleAction(){
        //TODO implement
    }

    //The following methods do the action calling the action function in the AtomicAction class

    public void move(){
        atomicActions.move(currentPlayer, selectedCell);
    }

    public void grab(){

        if (selectedCell instanceof CommonCell){
            //Grab
            atomicActions.grab(currentPlayer, (CommonCell) selectedCell);
            //Add the cell to a list of empty cells (for the end of the turn)
            turnManager.getEmptyCommonCells().add((CommonCell) selectedCell);
        }

        if (selectedCell instanceof SpawnCell){
            //TODO manage if the player has to replace a weapon
            //Grab
            atomicActions.grabWeapon(currentPlayer,(SpawnCell) selectedCell, weaponIndex);
            //Add the cell to a list of empty cells (for the end of the turn)
            turnManager.getEmptySpawnCells().add((SpawnCell) selectedCell);
        }
    }

}
