package it.polimi.sw2019.commons.messages;



import it.polimi.sw2019.commons.Character;

import java.util.List;

/**
 * @author poligenius
 * this class is sent to the client to update the gui or the cli
 */
public class MatchState {

    /**
     * Default constructor
     */
    public MatchState(){}

    /* Attributes */

    private List<MessageCell> cells;

    private List<PlayerBoardMessage> playerBoardMessages;

    private int currentPlayerLeftActions;

    private List<PlayerHand> playerHands;

    private List<Character> killSequence;

    private List<Boolean> overkillSequence;

    private int weaponsDeckSize;

    private int powerupsDeckSize;

    private Character currentPlayer;

    /* Methods */

    public int getPowerupsDeckSize() {
        return powerupsDeckSize;
    }

    public void setCells(List<MessageCell> cells) {
        this.cells = cells;
    }

    public int getWeaponsDeckSize() {
        return weaponsDeckSize;
    }

    public void setPlayerBoardMessages(List<PlayerBoardMessage> playerBoardMessages) {
        this.playerBoardMessages = playerBoardMessages;
    }

    public List<MessageCell> getCells() {
        return cells;
    }

    public void setPlayerHands(List<PlayerHand> playerHands) {
        this.playerHands = playerHands;
    }

    public List<PlayerBoardMessage> getPlayerBoardMessages() {
        return playerBoardMessages;
    }

    public int getCurrentPlayerLeftActions() {
        return currentPlayerLeftActions;
    }

    public void setCurrentPlayerLeftActions(int currentPlayerLeftActions) {
        this.currentPlayerLeftActions = currentPlayerLeftActions;
    }

    public void setPowerupsDeckSize(int powerupsDeckSize) {
        this.powerupsDeckSize = powerupsDeckSize;
    }

    public List<PlayerHand> getPlayerHands() {
        return playerHands;
    }

    public void setWeaponsDeckSize(int weaponsDeckSize) {
        this.weaponsDeckSize = weaponsDeckSize;
    }

    public List<Boolean> getOverkillSequence() {
        return overkillSequence;
    }

    public void setOverkillSequence(List<Boolean> overkillSequence) {
        this.overkillSequence = overkillSequence;
    }

    public List<Character> getKillSequence() {
        return killSequence;
    }

    public void setKillSequence(List<Character> killSequence) {
        this.killSequence = killSequence;
    }

    public void setCurrentPlayer(Character currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Character getCurrentPlayer() {
        return currentPlayer;
    }
}
