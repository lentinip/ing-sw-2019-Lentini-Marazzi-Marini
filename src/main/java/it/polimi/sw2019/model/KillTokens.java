package java.it.polimi.sw2019.model;

public class KillTokens extends Tokens{

    /** Default constructor */

    public KillTokens() {

    }

    /** Attributes */

    private Character[] killSequence; /** array that represents the killshot track status */

    private boolean[] overkillSequence; /** array to track the overkills in the killshot track */

    private int totalKills;

    /** Methods */

    public Character[] getKillSequence() {
        return killSequence;
    }

    public void setKillSequence(Character[] killSequence) {
        this.killSequence = killSequence;
    }

    public boolean[] getOverkillSequence() {
        return overkillSequence;
    }

    public void setOverkillSequence(boolean[] overkillSequence) {
        this.overkillSequence = overkillSequence;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    /**
     *
     * @param i number of kills you are adding
     * @param opponent Player who killed someone
     */

    public void addKill(int i, Character opponent) {

        //TODO implement
    }

    /**
     *
     * @param i number of overkills
     * @param opponent player who did the overkill
     */

    public void addOverKill(int i, Character opponent) {

        //TODO implement
    }

    /**
     *  method called by the class Score to update the score when the game ends
     */
    public void updateKillScore() {

        //TODO implement

    }
}
