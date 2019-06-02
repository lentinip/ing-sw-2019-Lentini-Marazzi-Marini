package it.polimi.sw2019.model;

public class Ammo implements Cloneable{

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

        if((red > 3) || (red < 0)) {
            throw new IllegalArgumentException("illegal red ammo number");
        }
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {

        if((yellow > 3) || (yellow < 0)) {
            throw new IllegalArgumentException("illegal yellow ammo number");
        }
        this.yellow = yellow;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {

        if((blue > 3) || (blue < 0)) {
            throw new IllegalArgumentException("illegal blue ammo number");
        }
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

    @Override
    public Ammo clone(){
        Ammo result = new Ammo();
        result.setRed(red);
        result.setBlue(blue);
        result.setYellow(yellow);
        return result;
    }
}
