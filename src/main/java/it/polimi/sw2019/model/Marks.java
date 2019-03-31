package java.it.polimi.sw2019.model;

public class Marks extends Tokens {

    /** Default constructor */

    public Marks() {

    }

    /** Attributes */

    private Character markSequence[15]; /** max 3 marks from another player */

    /** Methods */

    public Character[] getMarkSequence() {
        return markSequence;
    }

    public void setMarkSequence(Character[] markSequence) {
        this.markSequence = markSequence;
    }

    /**
     *
     * @param i number of marks you are adding
     * @param opponent Player who is giving marks
     */

    public void addMark(int i, Character opponent) {

        //TODO implement
    }

    /**
     *
     * @param i number of marks converted into damage
     * @param opponent Player who gave those marks
     */

    public void removeMark(int i, Character opponent) {

        //TODO implement
    }

    /**
     *  when marks from a player convert into damage we need to reorder the markSequence array
     */
    public void orderMark(){

        //TODO implement
    }

}
