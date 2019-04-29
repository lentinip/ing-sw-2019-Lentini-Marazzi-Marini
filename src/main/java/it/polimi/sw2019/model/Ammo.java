package it.polimi.sw2019.model;

public class Ammo {

    /**
     * Default constructor
     */
    public Ammo(){

    }

    /* Attributes */

    private int red;

    private int yellow;

    private int blue;


    /* Methods */

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void addRed(int red){
        this.red += red;
        if (this.red > 3){
            this.red = 3;
        }
    }

    public void addYellow(int yellow){
        this.yellow += yellow;
        if (this.yellow > 3){
            this.yellow = 3;
        }
    }

    public void addBlue(int blue){
        this.blue += blue;
        if (this.blue > 3){
            this.blue = 3;
        }
    }
}
