package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;

import java.util.List;

public class  AtomicActions {

    /**
     * Default Constructor
     */
    public AtomicActions(Match match){
        this.match = match;
    }

    /* Attributes */

    private Match match;

    /* Methods */

    /**
     * Changes the Player position to a specific Cell
     * @param player the Player that is going to move
     * @param selectedCell destination Cell
     */
    public void move(Player player, Cell selectedCell){
        if(player!= null && selectedCell!=null){
            player.setPosition(selectedCell);

            //Sets the match as changed
            match.notifyMatchState();
        }
    }

    /**
     * Moves the player to the selectedCell, performs the grab in a common Cell, adds the correct stuff to the Player and then discards the ammoTile.
     * The cell at the end of the method is empty and with Ammo = null.
     * @param grabbingPlayer the grabbing Player
     * @param selectedCell the selected common Cell
     */
    public void grab(Player grabbingPlayer, Cell selectedCell){

        //First moves the player to the selected cell
        move(grabbingPlayer, selectedCell);

        Ammo playerAmmo = grabbingPlayer.getPlayerBoard().getAmmo();
        AmmoTile cellTile = selectedCell.getAmmo();

        //The player receives the ammo described in the tile
        playerAmmo.addRed(cellTile.getRed());
        playerAmmo.addBlue(cellTile.getBlue());
        playerAmmo.addYellow(cellTile.getYellow());

        //If the AmmoTile has signed the powerup and the player has less than 3 powerups, he draws one
        if(cellTile.isPowerup() && grabbingPlayer.getPowerups().size()<3){
            Powerup powerup = match.getBoard().drawPowerup();
            if (powerup!=null){
                grabbingPlayer.addPowerup(powerup);
            }
        }

        //Removes the AmmoTile from the CommonCell
        selectedCell.setAmmo(null);
        selectedCell.setIsEmpty(false);

        //Addss the AmmoTile to the discarded ones in the Board
        match.getBoard().discardAmmo(cellTile);

        //Sets the match as changed
        match.notifyMatchState();
    }

    /**
     * Performs the grab of a Weapon in a spawn Cell, adds it to the player and removes it from the cell (moves also the player)
     * @param grabbingPlayer the grabbing Player
     * @param selectedCell the selected spawn Cell
     * @param weaponIndex the weapon index in the spawn Cell
     */
    public void grabWeapon(Player grabbingPlayer, Cell selectedCell, int weaponIndex){

        Weapon grabbedWeapon = selectedCell.getWeapons().get(weaponIndex);

        //Moves the player
        move(grabbingPlayer, selectedCell);

        //Adds the weapon to the Players weapon
        grabbingPlayer.addWeapon(grabbedWeapon);

        //Removes it from the cell
        selectedCell.getWeapons().remove(grabbedWeapon);

        //Sets the match as changed
        match.notifyPrivateHand(match.getCurrentPlayer());
    }

    /**
     * Performs the grab of a Weapon in a SpawnCell when the Player needs to replace one of his weapons (moves also the player)
     * @param grabbingPlayer the grabbing Player
     * @param selectedCell the selected spawn Cell
     * @param cellWeaponIndex the weapon index in the spawn Cell
     * @param playerWeaponIndex the weapon index in the Player
     */
    public void grabWeaponAndReplace(Player grabbingPlayer, Cell selectedCell, int cellWeaponIndex, int playerWeaponIndex){

        Weapon weaponToReplace = grabbingPlayer.getWeapons().get(playerWeaponIndex);

        //Removes the weapon from the player
        grabbingPlayer.discardWeapon(playerWeaponIndex);

        //Performs the normal grabWeapon
        grabWeapon(grabbingPlayer, selectedCell, cellWeaponIndex);

        //"Recharges" the Player's weapon
        weaponToReplace.setIsLoaded(true);

        //Adds the Player's weapon to the SpawnCell
        selectedCell.getWeapons().add(weaponToReplace);

        //Sets the match as changed
        match.notifyMatchState();
    }

    /**
     * Deal a specific amount of damage to a Player, plus his marks.
     * Also changes the receiver's state and sets if he's dead.
     * @param shooter Player who shoots
     * @param receiver Player who receives the damage
     * @param damage Number of tokens that the shooter gives
     */
    public void dealDamage(Player shooter, Player receiver, int damage){
        DamageTokens receiverDamage = receiver.getPlayerBoard().getDamage();

        //Adds the shooter's marks
        int shooterMarks = receiver.getPlayerBoard().getMarks().removeMarks(shooter.getCharacter());

        //Calculates the new damage
        int newDamage = damage + shooterMarks;

        //Finally adds the damage
        receiverDamage.addDamage(newDamage, shooter.getCharacter());

        int totalDamage = receiverDamage.getTotalDamage();

        //If we're not in the frenzy mode and if the player is still alive, change his mode
        if (!match.getFrenzyMode() && totalDamage<11) {

            if (totalDamage>2 && totalDamage<6){
                receiver.setState(State.ADRENALINIC1);
            }

            if (totalDamage>5){
                receiver.setState(State.ADRENALINIC2);
            }
        }

        //If the player has more than 10 damages he's dead
        if (totalDamage>10){
            receiver.setDead(true);
        }

        //Sets the match as changed
        match.notifyMatchState();
    }


    public void mark(Player shooter, Player receiver, int mark){
        Marks receiverMarks = receiver.getPlayerBoard().getMarks();
        receiverMarks.addMark(mark, shooter.getCharacter());

        //Sets the match as changed
        match.notifyMatchState();
    }

    public void reload(Player reloader, Weapon reloadedWeapon){
        payAmmo(reloader, reloadedWeapon.getReloadCost());

        //Sets the match as changed
        match.notifyPrivateHand(reloader);
    }

    /**
     * Adds one cube of ammo to the Ammo of the Player owner and than discards it
     * @param owner Player owner of the powerup
     * @param powerupIndex index of the powerup in the Powerups of Player
     */
    public void convertPowerup(Player owner, int powerupIndex){
        Ammo playerAmmo = owner.getPlayerBoard().getAmmo();
        Powerup powerupToDiscard = owner.getPowerups().get(powerupIndex);

        //Set the ammo to the player
        switch (powerupToDiscard.getColor()){
            case RED:
                playerAmmo.setRed(playerAmmo.getRed()+1);
                break;
            case BLUE:
                playerAmmo.setBlue(playerAmmo.getBlue()+1);
                break;
            case YELLOW:
                playerAmmo.setYellow(playerAmmo.getYellow()+1);
                break;
            default:
                //It should never come here
        }

        //Removes the powerup from the powerups of the Player
        owner.discardPowerup(powerupIndex);
        //Adds it to the discarded ones in the Board
        match.getBoard().discardPowerup(powerupToDiscard);
        match.notifyPrivateHand(match.getCurrentPlayer());
    }

    /**
     * Removes a cost from the Player
     * @param owner Player
     * @param cost Ammo that is going to be removed
     */
    public void payAmmo(Player owner, Ammo cost){
        owner.getPlayerBoard().getAmmo().ammoSubtraction(cost);

        //Sets the match as changed
        match.notifyMatchState();
    }

    /**
     * Removes a cost from the Player's Ammo using also Powerups
     * @param owner Player
     * @param cost Ammo that is going to be removed
     * @param powerups List of Powerup used to pay a cost
     */
    public void payAmmoWithPowerups(Player owner, Ammo cost, List<Powerup> powerups){

        //Converts the powerups in Ammo for the player
        for (Powerup powerup: powerups){
            convertPowerup(owner, owner.getPowerups().indexOf(powerup));
        }

        //Then removes the cost
        payAmmo(owner, cost);
    }

    /**
     * this method is used to add a powerup to the player and removing it from the powerups deck
     * @param drawer who draws
     */
    public void drawPowerup(Player drawer){

        if ( drawer.getPowerups().size() < 3){

            Powerup drawn = match.getBoard().drawPowerup();
            drawer.addPowerup(drawn);
        }
        match.notifyPrivateHand(match.getCurrentPlayer());
    }
}
