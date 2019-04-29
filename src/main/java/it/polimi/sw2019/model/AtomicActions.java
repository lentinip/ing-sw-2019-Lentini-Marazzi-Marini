package it.polimi.sw2019.model;

public class AtomicActions {

    /**
     * Default Constructor
     */
    public AtomicActions(){

        //TODO implement here
    }

    /* Methods */

    /**
     * Changes the Player position to a specific Cell
     * @param player the Player that is going to move
     * @param selectedCell destination Cell
     */
    public void move(Player player, Cell selectedCell){
        if(player!= null && selectedCell!=null){
            player.setPosition(selectedCell);
        }
    }

    public void grabPowerUp(Player grabbingPlayer, CommonCell selectedCell){

        //TODO implement here
    }

    public void grabWeapon(Player grabbingPlayer, SpawnCell selectedCell, Weapon selectedWeapon){

        //TODO implement here

    }

    public void DealDamage(Player shooter, Player receiver, int damage){

        //TODO implement here

    }

    public  void DealDamageAll(Player shooter, Cell selectedCell, int damage){

        //TODO implement here

    }

    public void Mark(Player shooter, Player receiver, int mark){

        //TODO implement here

    }

    public void MarkAll(Player shooter, Cell selectedCell, int mark){

        //TODO implement here
    }

    public void reload(Player reloader, Weapon reloadedWeapon){

        //TODO implement here

    }
}
