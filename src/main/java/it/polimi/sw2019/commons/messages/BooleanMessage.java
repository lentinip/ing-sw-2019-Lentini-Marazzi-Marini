package it.polimi.sw2019.commons.messages;

/**
 * @author poligenius
 * contains the answer of a yes or no question
 */
public class BooleanMessage {

    /**
     * Default constructor
     */
    public BooleanMessage(){}


    public BooleanMessage(boolean answer){

        setAnswer(answer);
    }

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
