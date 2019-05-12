package it.polimi.sw2019.network.messages;

import java.util.List;

public class AvailableCards {

    /**
     * Default constructor
     */
    public AvailableCards(){}

    /* Attributes */

    private List<Card> availableCards;

    /* Methods */

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<Card> availableCards) {
        this.availableCards = availableCards;
    }

}
