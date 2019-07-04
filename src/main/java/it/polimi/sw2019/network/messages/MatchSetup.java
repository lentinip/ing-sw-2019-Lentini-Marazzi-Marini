package it.polimi.sw2019.network.messages;

/**
 * @author poligenius
 * info sent to the controller to create the match
 */
public class MatchSetup {

    /**
     * Default constructor
     */
    public MatchSetup(){}

    public MatchSetup(boolean frenzy, boolean eightSkulls, String boardJsonName){

        setFrenzy(frenzy);
        setEightSkulls(eightSkulls);
        setBoardJsonName(boardJsonName);
    }

    /* Attributes */

    private boolean frenzy;

    private boolean eightSkulls;

    private String boardJsonName;

    /* Methods */

    public String getBoardJsonName() {
        return boardJsonName;
    }

    public void setFrenzy(boolean frenzy) {
        this.frenzy = frenzy;
    }

    public boolean isEightSkulls() {
        return eightSkulls;
    }

    public void setEightSkulls(boolean eightSkulls) {
        this.eightSkulls = eightSkulls;
    }

    public boolean isFrenzy() {
        return frenzy;
    }

    public void setBoardJsonName(String boardJsonName) {
        this.boardJsonName = boardJsonName;
    }

}
