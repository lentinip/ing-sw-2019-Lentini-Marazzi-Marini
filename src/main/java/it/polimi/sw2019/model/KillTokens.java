package it.polimi.sw2019.model;

import static it.polimi.sw2019.model.Character.*;
import java.util.Arrays

public class KillTokens extends Tokens{

    /**
     * Default constructor
     */

    public KillTokens() {

        Arrays.fill(overkillSequence, false); //Set all the overkillSequence to false

    }

    /* Attributes */

    /**
     * array that represents the killshot track status
     */
    private Character[] killSequence = new Character[40];

    /**
     * array to track the overkills in the killshot track
     */
    private boolean[] overkillSequence = new boolean[40];

    private int totalKills = 0;

    /* Methods */

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
     * @param killer Player who killed someone
     */
    public void addKill(Character killer) {

        switch (killer){ //Each case adds a point depending on the Player and adds the Character to the killSequence
            case BANSHEE:
                setBanshee(getBanshee()+1);
                killSequence[totalKills] = BANSHEE;
            case DISTRUCTOR:
                setDistructor(getDistructor()+1);
                killSequence[totalKills] = DISTRUCTOR;
            case DOZER:
                setDozer(getDozer()+1);
                killSequence[totalKills] = DOZER;
            case SPROG:
                setSprog(getSprog()+1);
                killSequence[totalKills] = SPROG;
            case VIOLET:
                setViolet(getViolet()+1);
                killSequence[totalKills] = VIOLET;
        }

        totalKills++; //Updates the number of totalKills

        //At the end of the function killSequence[totalKills] points to null
    }

    /**
     *
     * @param killer player who did the overkill
     */
    public void addOverkill(Character killer) {

        addKill(killer);

        switch (killer){ //Each case adds a point depending on the Player and signs the overkill into the overkillSequence
            case BANSHEE:
                setBanshee(getBanshee()+1);
                overkillSequence[totalKills - 1] = true; //there is totalKills - 1 because addKill already updates the value
            case DISTRUCTOR:
                setDistructor(getDistructor()+1);
                overkillSequence[totalKills - 1] = true;
            case DOZER:
                setDozer(getDozer()+1);
                overkillSequence[totalKills - 1] = true;
            case SPROG:
                setSprog(getSprog()+1);
                overkillSequence[totalKills - 1] = true;
            case VIOLET:
                setViolet(getViolet()+1);
                overkillSequence[totalKills - 1] = true;

    }

    /**
     *  method called by the class Score to update the score when the game ends
     */
    public void updateScore(Score score) {

        //TODO implement

    }

    public Character[]  getRanking() {

        Character[] result;

        result = orderArray();





        return result;
    }
}
