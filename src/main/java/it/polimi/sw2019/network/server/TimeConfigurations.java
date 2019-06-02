package it.polimi.sw2019.network.server;

public class TimeConfigurations {

    public TimeConfigurations(){}

    /* Attributes*/

    private long matchCreationTimer; //time is in seconds

    private long turnTimer;

    private long counterAttackPowerupTimer;

    /* Methods */

    public long getCounterAttackPowerupTimer() {
        return counterAttackPowerupTimer;
    }

    public long getMatchCreationTime() {
        return matchCreationTimer;
    }

    public long getTurnTimer() {
        return turnTimer;
    }

    public void setTurnTimer(int turnTimer) {
        this.turnTimer = turnTimer;
    }

    public void setCounterAttackPowerupTimer(int counterAttackPowerupTimer) {
        this.counterAttackPowerupTimer = counterAttackPowerupTimer;
    }

    public void setMatchCreationTime(int matchCreationTime) {
        this.matchCreationTimer = matchCreationTime;
    }
}
