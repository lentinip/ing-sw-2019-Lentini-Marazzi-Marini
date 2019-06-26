package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.messages.*;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Payment {

    public Payment(Match match, VirtualView view, SingleActionManager singleActionManager){

        this.match = match;
        this.view = view;
        this.singleActionManager = singleActionManager;

    }

    /* Attributes */

    private Match match;

    private VirtualView view;

    private SingleActionManager singleActionManager;

    private Ammo leftCost;

    private Ammo initialCost;

    private Player payingPlayer;

    private Cell spawnCell;

    private Message pendingMessage;

    private List<Powerup> selectedPowerups = new ArrayList<>();

    private Powerup usablePowerup; // this attribute is here to know if we are paying the cost of a powerup effect

    private boolean reloadInFrenzy = false; //used to know if we are reloading and after reload there is a shoot

    private static final Logger LOGGER = Logger.getLogger("Payment");


    public boolean isReloadInFrenzy() {
        return reloadInFrenzy;
    }

    public void setSpawnCell(Cell spawnCell) {
        this.spawnCell = spawnCell;
    }

    public void setReloadInFrenzy(boolean reloadInFrenzy) {
        this.reloadInFrenzy = reloadInFrenzy;
    }

    public Ammo getLeftCost() {
        return leftCost;
    }

    public void setLeftCost(Ammo leftCost) {
        this.leftCost = leftCost;
    }

    public Ammo getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(Ammo initialCost) {
        this.initialCost = initialCost;
    }

    public Message getPendingMessage() {
        return pendingMessage;
    }

    public void setPendingMessage(Message pendingMessage) {
        this.pendingMessage = pendingMessage;
    }

    public List<Powerup> getSelectedPowerups() {
        return selectedPowerups;
    }

    public void setSelectedPowerups(List<Powerup> selectedPowerups) {
        this.selectedPowerups = selectedPowerups;
    }

    /**
     * This method reset all the attributes to avoid errors the next time I receive a new payment message
     * I don't need to reset the pendingMessage or the payingPlayer cause they are overwritten every time
     * in the paymentStarter method
     */
    public void reset(){
        leftCost = null;
        initialCost = null;
        selectedPowerups.clear();
        usablePowerup = null;
    }

    /**
     * Pay the Ammo cost with Ammo and Powerups and continue the pending action based on the pendingMessage.
     */
    public void payAndContinue(){
        //First pays
        singleActionManager.getAtomicActions().payAmmoWithPowerups(payingPlayer, initialCost, selectedPowerups);

        reset();

        switch (pendingMessage.getTypeOfAction()){
            case GRABWEAPON:
                singleActionManager.grabWeaponHandler(pendingMessage);
                break;
            case RELOAD:
                //in this case I have to shoot after
                if (reloadInFrenzy ){
                    IndexMessage indexMessage = pendingMessage.deserializeIndexMessage();
                    Player player = match.getCurrentPlayer();
                    Weapon weapon = player.getWeaponFromIndex(indexMessage.getSelectionIndex());
                    singleActionManager.getAtomicActions().reload(player, weapon);
                    reloadInFrenzy = false;
                    pendingMessage.setTypeOfAction(TypeOfAction.SHOOT); //we set that to reload, now set back to shoot
                    singleActionManager.getChoices().selectionCardHandler(pendingMessage);
                }
                // in this case is a normal reload
                else {
                    singleActionManager.reloadHandler(pendingMessage);
                }
                break;
            case SHOOT:
                singleActionManager.getChoices().effectAnalyzer();
                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;
        }

    }

    /**
     * Pay the Ammo cost with Ammo  and continue the pending action based on the pendingMessage.
     * used to pay targeting scope
     */
    public void payAndThanUsePowerup(Colors color){
        Ammo toPay = new Ammo(color);

        singleActionManager.getAtomicActions().payAmmo(payingPlayer, toPay);
        reset();
        singleActionManager.getChoices().damagedTargets();
    }

    /**
     * pay the Ammo cost with powerup
     * used to pay targeting scope
     * @param powerupIndex index of the powerup discarded to pay the cost
     */
    public void payAndThanUsePowerup(int powerupIndex){
        Powerup powerup = payingPlayer.getPowerupFromIndex(powerupIndex);
        Ammo toPay = new Ammo(powerup.getColor());
        List<Powerup> powerups = new ArrayList<>();
        powerups.add(powerup);

        singleActionManager.getAtomicActions().payAmmoWithPowerups(payingPlayer, toPay, powerups);
        reset();
        singleActionManager.getChoices().damagedTargets();
    }

    /**
     * Checks if the player can pay with a powerup.
     * If the player can pay shows wich powerups can use.
     * Otherwise pays (calls payAndContinue)
     */
    public void checkPaymentMethod(){

        //List of powerups that the player can use for paying the cost
        List<Powerup> powerups = payingPlayer.payingPoweups(leftCost);

        //Removes the selectedPowerups from the list
        if (!powerups.isEmpty()){
            powerups.removeAll(selectedPowerups);
        }

        if (powerups.isEmpty()){
            payAndContinue();
        }

        else {
            Message message = new Message(payingPlayer.getName());

            List<IndexMessage> powerupsIndex = new ArrayList<>();

            for (Powerup powerup : powerups){
                powerupsIndex.add(new IndexMessage(payingPlayer.getPowerups().indexOf(powerup)));
            }

            //Creates the Payment Message checking if the player MUST pay with powerups
            message.createPaymentMessage(powerupsIndex, payingPlayer.mustPayWithPowerup(leftCost));

            view.display(message);
        }
    }

    /**
     * Updates the left cost and handles if the client doesn't want to pay with Powerups anymore (if the powerup index is negative).
     * Calls checkPaymentMethod() and payAndContinue().
     * @param message Message with the powerup choice.
     */
    public void paymentHandler(Message message){

        //If targeting scope
        if(usablePowerup!=null){
            if(message.getTypeOfMessage()== TypeOfMessage.SELECTED_COLOR){
                SelectedColor selectedColor = message.deserializeSelectedColor();
                payAndThanUsePowerup(selectedColor.getColor());
            }
            else {
                IndexMessage indexMessage = message.deserializeIndexMessage();
                payAndThanUsePowerup(indexMessage.getSelectionIndex());
            }
        }

        else {

            IndexMessage indexMessage = message.deserializeIndexMessage();

            //If the player choose not to use a powerup
            if (indexMessage.getSelectionIndex()<0){
                payAndContinue();
            }
            else {
                Powerup powerup = payingPlayer.getPowerupFromIndex(indexMessage.getSelectionIndex());
                selectedPowerups.add(powerup);

                //Reduces the leftCost
                leftCost.ammoSubtraction(new Ammo(powerup.getColor()));

                checkPaymentMethod();
            }
        }
    }

    /**
     * Called the fist time you have to pay.
     * Sets the attributes in the instance of the class.
     * NOTE: Before calling this method for USEPOWERUP check first if there's actually a Powerup and if it has a cost.
     * @param message message that has to be executed at the end of the payment
     */
    public void paymentStarter(Message message){

        //First I save the message that the view sent
        pendingMessage = message;
        payingPlayer = match.getPlayerByUsername(message.getUsername());

        switch (message.getTypeOfAction()){
            case GRABWEAPON:
                GrabWeapon grabWeapon = message.deserializeGrabWeapon();
                Weapon weapon = spawnCell.getWeapons().get(grabWeapon.getGrabbedWeapon());
                initialCost = weapon.getGrabCost();
                leftCost = initialCost.copy();
                break;
            case USEPOWERUP:
                IndexMessage indexMessage = message.deserializeIndexMessage();
                usablePowerup = payingPlayer.getPowerupFromIndex(indexMessage.getSelectionIndex());
                break;
            case RELOAD:
                IndexMessage reloadCardMessage = message.deserializeIndexMessage();
                Weapon reloadWeapon = payingPlayer.getWeaponFromIndex(reloadCardMessage.getSelectionIndex());
                initialCost = reloadWeapon.getReloadCost();
                leftCost = initialCost.copy();
                break;
            case SHOOT:
                IndexMessage effectMessage = message.deserializeIndexMessage();
                Weapon selectedWeapon = singleActionManager.getChoices().getSelectedWeapon();
                Effect effect = selectedWeapon.getEffects().get(effectMessage.getSelectionIndex());
                initialCost = effect.getCost();
                leftCost = initialCost.copy();
                break;
            default:
                LOGGER.log(Level.SEVERE, "switch error");
                break;
        }

        if(usablePowerup!=null){
            powerupCostHandler();
        }
        else {
            checkPaymentMethod();
        }
    }

    /**
     * NOTE: for paying a powerup that has a generic single ammo cost.
     * Checks if the player can pay with a powerup.
     * If the player can pay shows wich powerups can use.
     * Otherwise pays (calls payAndContinue)
     */
    public void powerupCostHandler(){

        List<Powerup> powerups = new ArrayList<>(payingPlayer.getPowerups());

        powerups.remove(usablePowerup);

        Message message = new Message(payingPlayer.getName());

        List<IndexMessage> powerupsIndex = new ArrayList<>();

        if (powerups.isEmpty()){

            message.createPaymentMessage(powerupsIndex, false);
            message.setTypeOfMessage(TypeOfMessage.ASK);
        }

        else {

            for (Powerup powerup : powerups){
                powerupsIndex.add(new IndexMessage(payingPlayer.getPowerups().indexOf(powerup)));
            }

            //Creates the Payment Message checking if the player MUST pay with powerups
            message.createPaymentMessage(powerupsIndex, !payingPlayer.getPlayerBoard().hasAtLeastOneAmmo());
        }

        view.display(message);

    }


}
