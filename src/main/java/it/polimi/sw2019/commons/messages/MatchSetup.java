package it.polimi.sw2019.commons.messages;

/**
 * @author poligenius
 * info sent to the controller to create the match
 */
public class MatchSetup {

    /**
     * Default constructor
     */
    public MatchSetup(){}

    public MatchSetup(boolean frenzy, boolean easyMode, String boardJsonName){

        setFrenzy(frenzy);
        setEasyMode(easyMode);
        setBoardJsonName(boardJsonName);
    }

    /* Attributes */

    private boolean frenzy;

    private boolean easyMode;

    private String boardJsonName;

    /* Methods */

    public String getBoardJsonName() {
        return boardJsonName;
    }

    public void setFrenzy(boolean frenzy) {
        this.frenzy = frenzy;
    }

    public boolean isEasyMode() {
        return easyMode;
    }

    public void setEasyMode(boolean eightSkulls) {
        this.easyMode = eightSkulls;
    }

    public boolean isFrenzy() {
        return frenzy;
    }

    public void setBoardJsonName(String boardJsonName) {
        this.boardJsonName = boardJsonName;
    }

}
