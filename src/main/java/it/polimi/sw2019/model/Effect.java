package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

public class Effect {

    /**
     * Default Constructor
     */
    public Effect(){}

    /* Attributes */

    private String name;

    private String description;

    private Ammo cost;

    private EffectsKind type;

    private Visibility visibilityClass;

    private KindOfVisibility visibility;

    private int[] movesAway = new int[4]; /* number of moves everyCharacter has to be away from you to allow you  to shoot him */

    private boolean exactly;  /* true if it has to be exactly tot moves away */

    private boolean sameDirection; /* true if the moves away have to be in the same direction */

    private MoveEffect move;

    private Target targets;

    /* methods */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ammo getCost() {
        return cost;
    }

    public void setCost(Ammo cost) {
        this.cost = cost;
    }

    public EffectsKind getType() {
        return type;
    }

    public void setType(EffectsKind type) {
        this.type = type;
    }

    public Visibility getVisibilityClass() {
        return visibilityClass;
    }

    public void setVisibilityClass(Visibility visibilityClass) {
        this.visibilityClass = visibilityClass;
    }

    public KindOfVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(KindOfVisibility visibility) {
        this.visibility = visibility;
    }

    public int[] getMovesAway() {
        return movesAway;
    }

    public void setMovesAway(int[] movesAway) {
        this.movesAway = movesAway;
    }

    public boolean isExactly() {
        return exactly;
    }

    public void setExactly(boolean exactly) {
        this.exactly = exactly;
    }

    public boolean isSameDirection() {
        return sameDirection;
    }

    public void setSameDirection(boolean sameDirection) {
        this.sameDirection = sameDirection;
    }

    public MoveEffect getMove() {
        return move;
    }

    public void setMove(MoveEffect move) {
        this.move = move;
    }

    public void setTargets(Target targets) {
        this.targets = targets;
    }

    public Target getTargets() {
        return targets;
    }

    /**
     *
     * @param owner the player that is shooting
     * @return the cells where the weapon can shoot
     */
    public List<Cell> reachableCells(Player owner){

        List<Cell> reachableCells = new ArrayList<>();

        reachableCells = visibilityClass.visibility(visibility, owner, movesAway[0], exactly, move.getMoveYou());

        return reachableCells;
    }

    /**
     *
     * @param owner player who is shooting
     * @return the cells where the weapon can shoot with at least one player inside
     */
    public List<Cell> shootableCells(Player owner) {

        List<Cell> shootableCells = new ArrayList<>();
        List<Cell> reachableCells = reachableCells(owner);
        List<Player> targets = new ArrayList<>();

        for (int i = 0; i < reachableCells.size(); i++) {

            targets.addAll(reachableCells.get(i).playersInCell()); /* removing the player who is shooting from the list of the possible targets */

            if (targets.contains(owner)) { targets.remove(owner); }

            if (!targets.isEmpty()){

                shootableCells.add(reachableCells.get(i));
            }

        }

        return shootableCells;
    }

    /**
     *
     * @param owner player who is shooting
     * @return players you can shoot at
     */
    public List<Player> reachablePlayers(Player owner) {

        List<Player> reachablePlayers = new ArrayList<>();
        List<Cell> shootableCells = shootableCells(owner);

        for( int i = 0; i < shootableCells.size(); i++){

            reachablePlayers.addAll(shootableCells.get(i).playersInCell());
        }

        if ( reachablePlayers.contains(owner) ){

            reachablePlayers.remove(owner); /* removing the player who is shooting from the list of the possible targets */
        }

        return reachablePlayers;
    }

    /**
     * CALL ONLY IF YOU KNOW THE EFFECT IS NOT MOVE TYPE
     * @param allPlayers contains the list of all the players, it will be passed from the view to usableWeapon that calls this method
     * @param owner the player who is using the weapon
     * @return true if the player can actually pay and apply the effect
     */
    public boolean usableEffect(Player owner, List<Player> allPlayers){

        List<Player> targets = new ArrayList<>();

        if (!owner.canIPay(cost)) { /* can I pay the cost of the effect? */

            return false;
        }

        if (!shootableCells(owner).isEmpty()){ /* can I shoot someone? */

            return true;
        }

        if (move == null){ /* I can't shoot anyone and I don't have a move */

            return false;
        }

        targets.addAll(allPlayers);
        targets.remove(owner);

        if (move.iCanMoveTargetBefore()){  /* I can move targets and after the move I can shoot them */

           for(Player target: allPlayers){

               for(Cell reachableCell: target.getPosition().reachableCells(move.getMoveTargets())){

                   if (reachableCells(owner).contains(reachableCell)){

                       return true;
                   }
               }
           }
        }

        if (move.iCanMoveBefore()){ /* I can move myself and after the move I can shoot someone */

            Cell startingPosition = owner.getPosition(); /* saving my starting position */

            Player copy = new Player();

            copy.setPosition(startingPosition);

            for (Cell reachableCell: startingPosition.reachableCells(move.getMoveYou())){

                copy.setPosition(reachableCell);

                if( !shootableCells(copy).isEmpty() ){

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * if the player has chosen to do a shoot action this method method is called when the player wants to do a move before the shooting using the effect
     * @param owner of the weapon
     * @return only the cells where he can move in order to be able to shoot someone
     */
    List<Cell> allowedCells(Player owner) {

        List<Cell> allowedCells = new ArrayList<>();

        Cell startingPosition = owner.getPosition(); /* saving my starting position */

        Player copy = new Player();

        copy.setPosition(startingPosition);

        for (Cell reachableCell : startingPosition.reachableCells(move.getMoveYou())) {

            copy.setPosition(reachableCell);

            if (!shootableCells(copy).isEmpty()) {

                allowedCells.add(reachableCell);
            }
        }

        return allowedCells;
    }
}
