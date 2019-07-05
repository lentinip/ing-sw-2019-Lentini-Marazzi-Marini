package it.polimi.sw2019.network.server;

/**
 * @author poligenius
 * class written to contains every timer of the game
 */
public class TimeConfigurations {

    public TimeConfigurations(){}

    /* Attributes*/

    private long matchCreationTimer; //time is in seconds

    private long turnTimer;

    private long counterAttackPowerupTimer;

    private long matchSetupTimer;

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

    public long getMatchSetupTimer() {
        return matchSetupTimer;
    }
}
