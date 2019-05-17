package it.polimi.sw2019.network.messages;

import java.util.List;

public class AvailableCards {

    /**
     * Default constructor
     */
    public AvailableCards(){}

    /**
     *  Customize constructor
     */
    public AvailableCards(List<IndexMessage> availableCards, boolean areWeapons){

        setAvailableCards(availableCards);
        setAreWeapons(areWeapons);
    }

    /* Attributes */

    private List<IndexMessage> availableCards;

    private boolean areWeapons;

    /* Methods */

    public List<IndexMessage> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<IndexMessage> availableCards) {
        this.availableCards = availableCards;
    }

    public boolean areWeapons() {
        return areWeapons;
    }

    public void setAreWeapons(boolean areWeapons) {
        this.areWeapons = areWeapons;
    }
}
