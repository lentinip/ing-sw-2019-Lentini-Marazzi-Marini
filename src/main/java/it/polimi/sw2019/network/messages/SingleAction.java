package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.model.Character;

import java.io.Serializable;

/**
 * This class is used for the communication between the view and the controller;
 * Contains the data needed for a single action;
 */
public class SingleAction implements Serializable {

    /**
     * Default constructor
     */
    public SingleAction(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    /* Attributes */

    private TypeOfAction type;
    private Character currentCharacter;
    private int selectedCellRow;
    private int selectedCellColumn;
    private int selectedPowerupIndex;
    private int selectedWeaponIndex;
    private Character selectedCharacter;

    /* Methods */

    public TypeOfAction getType() {
        return type;
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public int getSelectedCellRow() {
        return selectedCellRow;
    }

    public void setSelectedCellRow(int selectedCellRow) {
        this.selectedCellRow = selectedCellRow;
    }

    public int getSelectedCellColumn() {
        return selectedCellColumn;
    }

    public void setSelectedCellColumn(int selectedCellColumn) {
        this.selectedCellColumn = selectedCellColumn;
    }

    public int getSelectedPowerupIndex() {
        return selectedPowerupIndex;
    }

    public void setSelectedPowerupIndex(int selectedPowerupIndex) {
        this.selectedPowerupIndex = selectedPowerupIndex;
    }

    public int getSelectedWeaponIndex() {
        return selectedWeaponIndex;
    }

    public void setSelectedWeaponIndex(int selectedWeaponIndex) {
        this.selectedWeaponIndex = selectedWeaponIndex;
    }

    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setSelectedCharacter(Character selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

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

    /**
     * Private method called by setMove and setGrab for the setting of all the attributes of the class
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     */
    private void setMoveOrGrab(int selectedCellRow, int selectedCellColumn){
        this.selectedCellRow = selectedCellRow;
        this.selectedCellColumn = selectedCellColumn;

        //Unused values (some with non sense values)
        this.selectedCharacter = null;
        this.selectedPowerupIndex = 5;
        this.selectedWeaponIndex = 5;
    }

    /**
     * This method is made for the move() of the controller that handles the move to a cell distant 1 cell
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     */
    public void setMove(int selectedCellRow, int selectedCellColumn){
        this.type = TypeOfAction.MOVE;
        setMoveOrGrab(selectedCellRow, selectedCellColumn);
    }

    /**
     * This method is made for the grab of an Ammo
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     */
    public void setGrab(int selectedCellRow, int selectedCellColumn){
        this.type = TypeOfAction.GRAB;
        setMoveOrGrab(selectedCellRow, selectedCellColumn);
    }

    /**
     * This method is made for the grab of a Weapon
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     * @param selectedWeaponIndex Index of the position in the Weapon array in Cell
     */
    public void setGrabWeapon(int selectedCellRow, int selectedCellColumn, int selectedWeaponIndex){
        this.type = TypeOfAction.GRAB;
        setMoveOrGrab(selectedCellRow, selectedCellColumn);
        this.selectedWeaponIndex = selectedWeaponIndex;
    }

    /**
     * This method is made for the following powerups: Targeting Scope, the Newton and the Tagback Granade.
     * @param selectedPowerupIndex Index of the position in the Powerup array in Player
     * @param selectedCharacter selected Character
     */
    public void setUsePowerup(int selectedPowerupIndex, Character selectedCharacter){
        this.type = TypeOfAction.USEPOWERUP;
        this.selectedPowerupIndex = selectedPowerupIndex;
        //selectedCharacter can be null
        this.selectedCharacter = selectedCharacter;

        //Unused values (some with non sense values)
        this.selectedCellRow = 10;
        this.selectedCellColumn = 10;
        this.selectedWeaponIndex = 5;
    }

    /**
     * This method is made for the Teleporter powerup.
     * @param selectedPowerupIndex Index of the position in the Powerup array in Player
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     */
    public void setUsePowerup(int selectedPowerupIndex, int selectedCellRow, int selectedCellColumn){
        this.type = TypeOfAction.USEPOWERUP;
        this.selectedPowerupIndex = selectedPowerupIndex;
        this.selectedCellRow = selectedCellRow;
        this.selectedCellColumn = selectedCellColumn;

        //Unused values (some with non sense values)
        this.selectedCharacter = null;
        this.selectedWeaponIndex = 5;
    }

    /**
     * Method for weapons that shoot a single character
     * @param selectedWeaponIndex Index of the position in the Weapon array in Player
     * @param selectedCharacter selected Character
     */
    public void setShoot(int selectedWeaponIndex, Character selectedCharacter){
        this.type = TypeOfAction.SHOOT;
        this.selectedWeaponIndex = selectedWeaponIndex;
        this.selectedCharacter = selectedCharacter;

        //Unused values (some with non sense values)
        this.selectedCellRow = 10;
        this.selectedCellColumn = 10;
        this.selectedPowerupIndex = 5;
    }

    /**
     * Method for weapons that shoot a cell
     * @param selectedWeaponIndex Index of the position in the Weapon array in Player
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     */
    public void setShoot(int selectedWeaponIndex, int selectedCellRow, int selectedCellColumn){
        this.type = TypeOfAction.SHOOT;
        this.selectedWeaponIndex = selectedWeaponIndex;
        this.selectedCellRow = selectedCellRow;
        this.selectedCellColumn = selectedCellColumn;

        //Unused values (some with non sense values)
        this.selectedCharacter = null;
        this.selectedPowerupIndex = 5;
    }

    /**
     * Method for weapons that shoot a cell and a specific character
     * @param selectedWeaponIndex Index of the position in the Weapon array in Player
     * @param selectedCellRow Cell row
     * @param selectedCellColumn Cell column
     * @param selectedCharacter selected Character
     */
    public void setShoot(int selectedWeaponIndex, int selectedCellRow, int selectedCellColumn, Character selectedCharacter){
        this.type = TypeOfAction.SHOOT;
        this.selectedWeaponIndex = selectedWeaponIndex;
        this.selectedCharacter = selectedCharacter;
        this.selectedCellRow = selectedCellRow;
        this.selectedCellColumn = selectedCellColumn;

        //Unused values (some with non sense values)
        this.selectedPowerupIndex = 5;
    }

    public void setReload(int selectedWeaponIndex){
        this.type = TypeOfAction.RELOAD;
        this.selectedWeaponIndex = selectedWeaponIndex;

        //Unused values (some with non sense values)
        this.selectedCellRow = 10;
        this.selectedCellColumn = 10;
        this.selectedPowerupIndex = 5;
        this.selectedCharacter = null;
    }

    public void setEndTurn(){
        this.type = TypeOfAction.ENDTURN;

        //Unused values (some with non sense values)
        this.selectedCellRow = 10;
        this.selectedCellColumn = 10;
        this.selectedPowerupIndex = 5;
        this.selectedWeaponIndex = 5;
        this.selectedCharacter = null;
    }

}