package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Colors;

/**
 * @author poligenius
 * this class contains infos about the selection of a color
 */
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
