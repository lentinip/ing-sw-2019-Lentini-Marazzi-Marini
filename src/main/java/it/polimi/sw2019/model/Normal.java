package it.polimi.sw2019.model;

public class Normal implements ComplexAction {

    /**
     * Default Constructor
     */

    public Normal() {

        this.maxMove = 3;
        this.maxMoveGrab = 1;
        this.maxMoveShoot = 0;

    }


    /* Attributes */

    /**
     *  3 is max num of move you can make when you are in normal state
     */

     protected int maxMove;

    /**
     *  1 max num of move you can make before a grab action when you are in normal state
     */

     protected int maxMoveGrab;

    /**
     *  0 max num of move you can make before a shoot action when you are in normal state
     */

    protected int maxMoveShoot;

    /* Methods */

    @Override
    public void moveAction() {

        //TODO implement

    }

   @Override
   public void moveAndGrab() {

        //TODO implement

   }

   @Override
    public void shoot() {

        //TODO implement
   }
}

