package it.polimi.sw2019.network.messages;

import java.util.List;

public class AvailableEffects {

    /**
     * Default constructor
     */
    public AvailableEffects(){}

    public AvailableEffects(List<IndexMessage> indexMessageList){

        setIndexes(indexMessageList);
    }

    /* Attributes */

    private List<IndexMessage> indexes;

    /* Methods */

    public List<IndexMessage> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexMessage> indexes) {
        this.indexes = indexes;
    }
}

