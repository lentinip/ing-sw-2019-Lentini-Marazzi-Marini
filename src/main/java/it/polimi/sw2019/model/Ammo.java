package it.polimi.sw2019.model;

/**
 * @author lentinip
 * this class is used as data struction for the ammo that a player owns
 */
public class Ammo {

    /**
     * Default constructor
     */
    public Ammo(){

        setRed(0);
        setYellow(0);
        setBlue(0);
    }

    public Ammo(int red, int yellow, int blue){

        setRed(red);
        setYellow(yellow);
        setBlue(blue);
    }

    public Ammo(Colors color){
        setRed(0);
        setYellow(0);
        setBlue(0);
        switch (color){
            case RED:
                addRed(1);
                break;
            case BLUE:
                addBlue(1);
                break;
            case YELLOW:
                addYellow(1);
                break;
            default:
                System.console().printf("Error in switch of Ammo class");
        }
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

    /**
     * Subtract the input Ammo to the Ammo that calls the method
     * @param toSubtract Ammo to subtract
     */
    public void ammoSubtraction(Ammo toSubtract){
        red = red - toSubtract.getRed();
        yellow = yellow - toSubtract.getYellow();
        blue = blue - toSubtract.getBlue();
    }

    /**
     * @return if a player has zero ammo
     */
    public boolean isZero(){
        return (red + yellow + blue == 0);
    }

    public Ammo copy(){
        Ammo result = new Ammo();
        result.setRed(red);
        result.setBlue(blue);
        result.setYellow(yellow);
        return result;
    }
}
