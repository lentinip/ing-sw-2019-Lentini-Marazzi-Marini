package it.polimi.sw2019.model;

/**
 * @author poligenius
 * enumerstion to keep track of the range of action of the different type of effects
 */
public enum KindOfVisibility {

    VISIBLE,
    NONVISIBLE,
    THOR,
    RAILGUN,
    DIFF_ROOM,
    MOVE, /* for the MOVE type effects */
    ALL /* used for move effect that can move every target */
}
