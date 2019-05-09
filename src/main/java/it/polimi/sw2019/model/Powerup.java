package it.polimi.sw2019.model;


public class Powerup {

    /**
     * Default constructor
     */

    public Powerup() {

    }

    public Powerup(String name, Colors color) {

        setName(name);
        setColor(color);
    }


    /* Attributes */

    private String name;

    private String description;

    private TypeOfAction typeOfAction;

    private int value;  /* for example the amount of damage */

    private boolean duringYourTurn;

    private boolean duringDamageAction; /* condition to activate the powerup */

    private MoveEffect move; /* up to know != null only for "Newton" powerup */

    private Colors color;

    private boolean iNeedToPay = false;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(TypeOfAction typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isDuringYourTurn() {
        return duringYourTurn;
    }

    public void setDuringYourTurn(boolean duringYourTurn) {
        this.duringYourTurn = duringYourTurn;
    }

    public boolean isDuringDamageAction() {
        return duringDamageAction;
    }

    public void setDuringDamageAction(boolean duringDamageAction) {
        this.duringDamageAction = duringDamageAction;
    }

    public MoveEffect getMove() {
        return move;
    }

    public void setMove(MoveEffect move) {
        this.move = move;
    }

    public boolean isiNeedToPay() {
        return iNeedToPay;
    }

    public void setiNeedToPay(boolean iNeedToPay) {
        this.iNeedToPay = iNeedToPay;
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
