package it.polimi.sw2019.network.messages;

/**
 * this class contains the index of a selection
 */
public class IndexMessage {

    /**
     * Default constructor
     */
    public IndexMessage(){}

    /**
     * customize constructor
     */
    public IndexMessage(int selectionIndex){

        setSelectionIndex(selectionIndex);
    }

    /* Attributes */

    private int selectionIndex;

    /* Methods */

    public int getSelectionIndex() {
        return selectionIndex;
    }

    public void setSelectionIndex(int selectionIndex) {
        this.selectionIndex = selectionIndex;
    }
}
