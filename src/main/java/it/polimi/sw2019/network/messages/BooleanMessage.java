package it.polimi.sw2019.network.messages;

/**
 * contains the answer of a yes or no question
 */
public class BooleanMessage {

    /**
     * Default constructor
     */
    public BooleanMessage(){}

    /* Attributes */

    private boolean answer;

    /* Methods */

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
