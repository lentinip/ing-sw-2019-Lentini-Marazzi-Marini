package it.polimi.sw2019.network.messages;

import java.util.List;

public class AvailableEffects {

    /**
     * Default constructor
     */
    public AvailableEffects(){}

    public AvailableEffects(List<IndexMessage> indexMessageList, List<String> names){

        setIndexes(indexMessageList);
        setNames(names);
    }

    /* Attributes */

    private List<IndexMessage> indexes;
    private List<String> names;

    /* Methods */

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<IndexMessage> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<IndexMessage> indexes) {
        this.indexes = indexes;
    }
}

