package model;

public class Weapon {

    /** Default constructor **/

    public Weapon() {

    }

    /** Attributes **/

    private String name;

    private Ammo grabCost;

    private Ammo reloadCost;

    private boolean isLoaded;

    /** Methods **/

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Ammo getGrabCost() {
        return grabCost;
    }

    public void setGrabCost(Ammo grabCost) {
        this.grabCost = grabCost;
    }

    public Ammo getReloadCost() {
        return reloadCost;
    }

    public void setReloadCost(Ammo grabCost) {
        this.reloadCost = reloadCost;
    }

    public boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void effect() {  // use the weapon effect

        //TODO implement

        return null;
    }
}
