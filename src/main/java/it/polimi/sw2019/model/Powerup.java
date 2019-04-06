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


}
