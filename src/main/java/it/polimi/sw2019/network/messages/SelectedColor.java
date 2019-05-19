package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Colors;

public class SelectedColor {

    public SelectedColor(Colors color){
        setColor(color);
    }


    /* Attributes */

    private Colors color;

    /* Methods */

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }
}
