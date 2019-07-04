package it.polimi.sw2019.network.messages;

import java.util.List;

/**
 * @author poligenius
 * class written to send infos about the effects a player can choose
 */
public class AvailableEffects {

    /**
     * Default constructor
     */
    public AvailableEffects(){}

    public AvailableEffects(List<IndexMessage> indexMessageList, List<String> names, String weaponName){

        setIndexes(indexMessageList);
        setNames(names);
        setName(weaponName);
    }

    /* Attributes */

    private List<IndexMessage> indexes;
    private List<String> names;
    private String name;  //name of the weapon that the player is using

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

