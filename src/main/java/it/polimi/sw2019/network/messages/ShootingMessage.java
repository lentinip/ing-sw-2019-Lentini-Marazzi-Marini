package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;

import java.io.Serializable;

public class ShootingMessage implements Serializable {

    /**
     * Default constructor
     */
    public ShootingMessage(){

    }

    /* Attributes */

    private TypeOfAction type;
    private Character shooter;
    private Character receiver;
    private int selectedCellRow;
    private int selectedCellColumn;
    private boolean isAll = false;
    private boolean isRoom = false;
    private int tokens;

    /* Methods */

    public TypeOfAction getType() {
        return type;
    }

    public Character getShooter() {
        return shooter;
    }

    public void setShooter(Character shooter) {
        this.shooter = shooter;
    }

    public Character getReceiver() {
        return receiver;
    }

    public void setReceiver(Character receiver) {
        this.receiver = receiver;
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

    public boolean isAll() {
        return isAll;
    }

    public void setIsAll(boolean all) {
        isAll = all;
    }

    public boolean isRoom() {
        return isRoom;
    }

    public void setIsRoom(boolean room) {
        isRoom = room;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    /*
    NOTE:
    From now on I'm going to use some values for the attributes that means that the value is not going to be considered.

    Here's the list:
    receiver = null
    selectedCellRow = 10
    selectedCellColumn = 10
     */

    private void setCellToNullValues(){
        this.selectedCellRow = 10;
        this.selectedCellColumn = 10;
    }

    public void setDealDamage(Character shooter, Character receiver, int damage){
        type = TypeOfAction.DEALDAMAGE;
        setShooter(shooter);
        setReceiver(receiver);
        setTokens(damage);
        setCellToNullValues();
    }

    public void setDealDamageAll(Character shooter, boolean isRoom, int selectedCellRow, int selectedCellColumn, int damage){
        type = TypeOfAction.DEALDAMAGE;
        setShooter(shooter);
        this.receiver = null;
        setIsRoom(isRoom);
        isAll = true;
        setSelectedCellRow(selectedCellRow);
        setSelectedCellColumn(selectedCellColumn);
        setTokens(damage);
    }

    public  void setMark(Character shooter, Character receiver, int marks){
        type = TypeOfAction.MARK;
        setShooter(shooter);
        setReceiver(receiver);
        setTokens(marks);
        setCellToNullValues();
    }

    public void setMarkAll(Character shooter, boolean isRoom, int selectedCellRow, int selectedCellColumn, int marks){
        type = TypeOfAction.MARK;
        setShooter(shooter);
        this.receiver=null;
        setIsRoom(isRoom);
        isAll = true;
        setSelectedCellRow(selectedCellRow);
        setSelectedCellColumn(selectedCellColumn);
        setTokens(marks);
    }

}
