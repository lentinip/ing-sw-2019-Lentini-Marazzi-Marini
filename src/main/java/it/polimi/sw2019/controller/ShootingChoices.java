package it.polimi.sw2019.controller;
import it.polimi.sw2019.model.*;

public class ShootingChoices {

    /**
     * Default constructor
     */

    public ShootingChoices() {

    }

    /* Attributes */

    private boolean effect1;

    private boolean effect2;

    private boolean effect3;

    private Weapon selectedWeapon;

    private Player[] targets;

    private Room roomTarget;

    private Cell cellTarget;

    /* Methods */

    public void setEffect1(boolean effect1) {
        this.effect1 = effect1;
    }

    public boolean getEffect1() {
        return effect1;
    }

    public void setEffect2(boolean effect2) {
        this.effect2 = effect2;
    }

    public boolean getEffect2() {
        return effect1;
    }

    public void setEffect3(boolean effect3) {
        this.effect3 = effect3;
    }

    public boolean getEffect3() {
        return effect3;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setTargets(Player[] targets) {
        this.targets = targets;
    }

    public Player[] getTargets() {
        return targets;
    }

    public void setRoomTarget(Room roomTarget) {
        this.roomTarget = roomTarget;
    }

    public Room getRoomTarget() {
        return roomTarget;
    }

    public void setCellTarget(Cell cellTarget) {
        this.cellTarget = cellTarget;
    }

    public Cell getCellTarget() {
        return cellTarget;
    }

    public void reset() {

        //TODO implement
        return;
    }

}
