package it.polimi.sw2019;

import it.polimi.sw2019.model.MoveEffect;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMoveEffect {

    @Test
    public void iCanChooseWhenMoveTargetTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);

        assertTrue(moveEffect.iCanChooseWhenMoveTarget());

        moveEffect.setMoveTargetAfter(true);

        assertFalse(moveEffect.iCanChooseWhenMoveTarget());
    }

    @Test
    public void iCanChooseWhenMoveTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);

        assertTrue(moveEffect.iCanChooseWhenMove());

        moveEffect.setMoveYouBefore(true);

        assertFalse(moveEffect.iCanChooseWhenMove());
    }

    @Test
    public void iCanMoveBeforeTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);

        assertTrue(moveEffect.iCanMoveBefore());

        moveEffect.setMoveYouAfter(true);

        assertFalse(moveEffect.iCanMoveBefore());
    }

    @Test
    public void iCanMoveTargetBeforeTest() {

        MoveEffect moveEffect = new MoveEffect();

        moveEffect.setMoveTargetBefore(false);
        moveEffect.setMoveTargetAfter(false);
        moveEffect.setMoveYouAfter(false);
        moveEffect.setMoveYouSameDirection(true);
        moveEffect.setMoveYouBefore(false);

        assertTrue(moveEffect.iCanMoveTargetBefore());

        moveEffect.setMoveTargetAfter(true);

        assertFalse(moveEffect.iCanMoveTargetBefore());
    }

}
