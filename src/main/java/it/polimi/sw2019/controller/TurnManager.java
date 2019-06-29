package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.IndexMessage;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurnManager {

    /**
     * Default constructor
     */
    public TurnManager(Match match, VirtualView view){
        this.match = match;
        this.view = view;
        this.currentPlayer = match.getCurrentPlayer();
        this.singleActionManager = new SingleActionManager(match, view, this);
    }

    /* Attributes */

    //The number of actions left is in the Player

    private Match match;

    private VirtualView view;

    private Player currentPlayer;

    private Player firstPlayer;

    private boolean isFirstRound = true;

    private SingleActionManager singleActionManager;

    //Cells without AmmoTiles
    private List<Cell> emptyCommonCells = new ArrayList<>();

    //SpawnCells where the player took the Weapon
    private List<Cell> emptySpawnCells = new ArrayList<>();


    /* Methods */

    public List<Cell> getEmptyCommonCells() {
        return emptyCommonCells;
    }

    public List<Cell> getEmptySpawnCells() {
        return emptySpawnCells;
    }

    public SingleActionManager getSingleActionManager() {
        return singleActionManager;
    }

    public void setSingleActionManager(SingleActionManager singleActionManager) {
        this.singleActionManager = singleActionManager;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public void setFirstRound(boolean firstRound) {
        isFirstRound = firstRound;
    }

    public void endTurn(){

        //stopping the timer because the player ended his turn
        view.getTurn().cancel();

        //Refills the CommonCells
        if(!emptyCommonCells.isEmpty()){
            refillCommonCell();
        }

        //Refills the SpawnCells
        if(!emptySpawnCells.isEmpty()){
            refillSpawnCell();
        }

        //Calls endTurn of the Match in the model
        match.endTurn();

        //Changes the parameters here
        currentPlayer = match.getCurrentPlayer();
        view.setMessageSender(currentPlayer.getName());
        view.setCurrentPlayer(currentPlayer.getName());

        //sending the winner message if the match is ended
        if (match.isEnded()){

            Map<Character, Integer> pointsMap = match.getScore().getMap();
            List<Character> disconnected = new ArrayList<>();

            //removing disconnected players from the leader board
            for (String name: view.getUsernames()){

                if (!view.getWaitingPlayers().get(name).getConnected()) {
                    disconnected.add(match.getPlayerByUsername(name).getCharacter());
                    pointsMap.remove(match.getPlayerByUsername(name).getCharacter());
                }
            }

            Map<Character, Integer> leaderboard = match.getScore().getRankingMap(disconnected);

            Message endMessage = new Message("All");
            endMessage.createLeaderBoard(leaderboard, pointsMap);
            view.display(endMessage);
        }

        else {
            view.display(spawningHandler());
        }

        //in case of timer elapses
        singleActionManager.getPayment().reset();
        singleActionManager.getChoices().reset();
        singleActionManager.getChoices().setSelectedWeapon(null);
    }

    /**
     * refills the empty spawn cell
     */
    public void refillSpawnCell(){

        List<Cell> cellList = new ArrayList<>(emptySpawnCells);

        for(Cell spawnCell : cellList){
            while (spawnCell.getWeapons().size()<3 && !match.getBoard().weaponsDeckIsEmpty()){
                Weapon weapon = match.getBoard().drawWeapon();
                spawnCell.getWeapons().add(weapon);
            }

            if (match.getBoard().weaponsDeckIsEmpty() || spawnCell.getWeapons().size()==3){
                emptySpawnCells.remove(spawnCell);
            }

            match.notifyMatchState();
        }
    }

    /**
     * refills the empty common cells
     */
    public void refillCommonCell(){

        List<Cell> cellList = new ArrayList<>(emptyCommonCells);

        for(Cell commonCell : cellList){

            AmmoTile newAmmoTile = match.getBoard().drawAmmo();
            commonCell.setAmmo(newAmmoTile);
            commonCell.setIsEmpty(false);
            emptyCommonCells.remove(commonCell);
            match.notifyMatchState();
        }
    }

    /**
     * this method respawns the player and continues the game flow by sending the correct message to the next player
     * @param spawningPlayer player who is spawning
     * @param powerupIndex powerup discarded
     */
    public void spawn(Player spawningPlayer, int powerupIndex){

        Message message = new Message();

        //Gets the powerup selected
        Powerup powerup = spawningPlayer.getPowerups().get(powerupIndex);

        System.out.print("\nI'm inside the spawn\n");

        System.out.print("\n PowerupIndex: " + powerupIndex + "\n");


        //Gets the room with the color of the powerup
        Room room = match.getBoard().getRoomByColor(powerup.getColor());

        //Removes the powerup from the player
        spawningPlayer.getPowerups().remove(powerupIndex);

        //Discards the powerup
        match.getBoard().discardPowerup(powerup);

        //The player is moved to the SpawnCell of the room
        spawningPlayer.setPosition(room.getSpawnCell());

        match.notifyPrivateHand(spawningPlayer);

        // updating the model and the player attribute isDead
        spawningPlayer.setDead(false);
        match.getDeadPlayers().remove(spawningPlayer);

        // checking if it is the first spawn or not
        if (isFirstRound){

            if (currentPlayer.getName().equals(spawningPlayer.getName())){
                view.startTurnTimer(currentPlayer.getName());
            }

            // if it is the last player that has his first turn I set first round to false
            if (match.getPlayers().indexOf(match.getCurrentPlayer()) == (match.getPlayers().size()-1)){

                isFirstRound = false;
            }

            message.setUsername(spawningPlayer.getName());

            //sending the canIshootMessage to start the turn
            message.createMessageCanIShoot(spawningPlayer.canIshootBeforeComplexAction());
        }

        else {

            // sending the spawn message to the next player with the available Powerups to discard
            message = spawningHandler();

        }

        match.notifyPrivateHand(spawningPlayer);
        //sending the message
        view.display(message);
    }

    /**
     * see if there are other players to spawn, if not doSomething the message to continue the turn to the current player
     * @return message to send
     */
    public Message spawningHandler(){

        Message message = new Message();
        Player receiver;

        if (!match.getDeadPlayers().isEmpty()){

            System.out.print("\n Size of the dead players array: " + match.getDeadPlayers().size() + "\n");
            receiver = match.getDeadPlayers().get(0);

            singleActionManager.getAtomicActions().drawPowerup(receiver);

            //in the first round players draw 2 powerups before the spawn
            if (isFirstRound){

                singleActionManager.getAtomicActions().drawPowerup(receiver);
            }

            message.setUsername(receiver.getName());

            List<Powerup> powerups = receiver.getPowerups();
            List<IndexMessage> options = new ArrayList<>();
            for (Powerup card: powerups){

                options.add(new IndexMessage(receiver.getPowerupIndex(card)));
            }
            message.createAvailableCardsMessage(TypeOfAction.SPAWN, options, false);
            //starting the spawn answer timer
            view.setMessageSender(receiver.getName());
            view.startSpawnMessage();
        }

        //sending the can I shoot message to the new current player
        else {
            message.setUsername(currentPlayer.getName());
            message.createMessageCanIShoot(currentPlayer.canIshootBeforeComplexAction());

            //restarting the time because a new player is having his turn
            view.startTurnTimer(currentPlayer.getName());
        }

        return message;
    }
}
