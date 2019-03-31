package java.it.polimi.sw2019.model;

public class SpawnCell extends Cell{

    /** Default constructor **/

    public SpawnCell() {

    }

    /** Attributes **/

    private Weapon[] weapons; //max 3 weapons

    /** Methods **/

    public Weapon getWeapon(int i) {
        return weapons[i];
    }

    public void setWeapon (int i, Weapon weapon) {
        this.weapons[i] = weapon;
    }

}
