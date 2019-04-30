package it.polimi.sw2019.model;


public class Powerup {

    /**
     * Default constructor
     */

    public Powerup() {

    }

    /* Attributes */

    private String name;

    private Colors color;

    /* Methods */

    public String getName(){
        return(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colors getColor() {
        return(color);
    }

    public void setColor(Colors color) { this.color = color; }

    public void effect() {

        //TODO implement

        return;
    }

    /**
     *
     * @param cost
     * @return true if the powerup can be used to pay part of the cost, false otherwise
     */
    public boolean useToPay(Ammo cost){

        if (color == Colors.RED && cost.getRed() > 0 || color == Colors.BLUE && cost.getBlue() > 0 || color == Colors.YELLOW && cost.getYellow() > 0) {

            return true;
        }

        else return false;
    }


}
