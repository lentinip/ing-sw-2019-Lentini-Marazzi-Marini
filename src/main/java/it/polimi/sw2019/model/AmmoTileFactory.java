package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class was written to create the ammoTiles based on the quantity attribute and then returns the list to the Factory class
 */
public class AmmoTileFactory {

    /* Attributes */

    private int quantity; /* number of copy of the single tile in the match, attribute used by constructor to create the correct number of ammotiles ( see Factory ) */

    private AmmoTile ammoTileKind;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setAmmoTile(AmmoTile ammoTile) {
        this.ammoTileKind = ammoTile;
    }

    public AmmoTile getAmmoTile() {
        return ammoTileKind;
    }

    /* Methods */

    public List<AmmoTile> createAmmoTiles(){

        List<AmmoTile> ammoTiles = new ArrayList<>();

        for (int i = 0; i < quantity; i++){

            ammoTiles.add(ammoTileKind);
        }

        return ammoTiles;
    }
}
