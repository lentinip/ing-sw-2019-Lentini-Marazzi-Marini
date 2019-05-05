package it.polimi.sw2019.network.messages;

public class RespawnMessage {

    /**
     * Default constructor
     */
    public RespawnMessage(Character deadCharacter, int powerupIndex){
        this.deadCharacter = deadCharacter;
        this.powerupIndex = powerupIndex;
    }

    /* Attributes */

    Character deadCharacter;
    int powerupIndex;

    /* Methods */

    public Character getDeadCharacter() {
        return deadCharacter;
    }

    public void setDeadCharacter(Character deadCharacter) {
        this.deadCharacter = deadCharacter;
    }

    public int getPowerupIndex() {
        return powerupIndex;
    }

    public void setPowerupIndex(int powerupIndex) {
        this.powerupIndex = powerupIndex;
    }
}
