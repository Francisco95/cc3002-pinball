package logic.gameelements.target;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test the interactions of one single DropTarget and SpotTarget.
 * to see tests of interaction of many targets and bumpers, specially
 * the interaction with the DropTargetBonus go to {@link logic.table.TableTest}
 * or {@link logic.bonus.BonusTest}.
 * @author Fancisco Muñoz Ponce. on date: 18-05-18
 */
public class TargetTest {
    private Game game;
    private JackPotBonus jackPot;
    private ExtraBallBonus extraBall;
    private SpotTarget spotTarget;
    private DropTarget dropTarget;
    private int score, balls;

    @Before
    public void setUp() {
        balls = 1;
        score = 0;
        game = new Game(balls, score);
        jackPot = JackPotBonus.getInstance();
        extraBall = ExtraBallBonus.getInstance();
        spotTarget = new SpotTarget();
        dropTarget = new DropTarget();
        // then add the observers
        spotTarget.setObservers(game, jackPot);
        dropTarget.setObservers(game, extraBall);
        jackPot.setObservers(game);
        extraBall.setObservers(game);
    }

    /**
     * test that method isActive() return as expected.
     * Also check that method hit() set Active to false.
     */
    @Test
    public void isActive() {
        // at first should be true
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());
        //then set to false and check this
        spotTarget.hit();
        dropTarget.hit();
        assertFalse(spotTarget.isActive());
        assertFalse(dropTarget.isActive());
    }

    /**
     * test that the only valid way to change the state of SpotTarget/dropTarget
     * (means re-activate) is by reset.
     * This consider that setActive is created just to test and internal use.
     */
    @Test
    public void reset() {
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());
        spotTarget.setActive(false);
        dropTarget.setActive(false);
        assertFalse(spotTarget.isActive());
        assertFalse(dropTarget.isActive());
        spotTarget.reset();
        dropTarget.reset();
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());
    }

    /**
     * check that set seed actually give same values in the random probability
     */
    @Test
    public void setSeed(){
        dropTarget.setSeed(12);
        boolean expected = dropTarget.bonusTriggered();
        // arbitrary number of repetition, here i choose 4, all must be the same result
        assertEquals(expected, dropTarget.bonusTriggered());
        assertEquals(expected, dropTarget.bonusTriggered());
        assertEquals(expected, dropTarget.bonusTriggered());
        assertEquals(expected, dropTarget.bonusTriggered());

        // for spotTarget the bonus is alwasy triggered
        assertTrue(spotTarget.bonusTriggered());
        assertTrue(spotTarget.bonusTriggered());
        assertTrue(spotTarget.bonusTriggered());
    }
    /**
     * test that it actually change the score/balls on Game instance and also after hit()
     * is not active anymore
     */
    @Test
    public void hit() {
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());

        score = game.getScore() + jackPot.getBonusValue() +
                dropTarget.getScore() + spotTarget.getScore();
        balls = game.getBalls() + extraBall.getBonusValue();
        spotTarget.hit();
        // set the seed in a way to get a triggered
        int seed = 0;
        dropTarget.setSeed(seed);
        while(!dropTarget.bonusTriggered()) {
            seed++;
            dropTarget.setSeed(seed);
        }
        dropTarget.hit();
        assertFalse(spotTarget.isActive());
        assertFalse(dropTarget.isActive());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
    }

    /**
     * check that do hit() without add the jackPot/extraBall
     * observer doesn't produce change in game score/balls
     */
    @Test
    public void hitAfterReset(){
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        spotTarget.reset();
        dropTarget.reset();
        spotTarget.deleteObservers();
        dropTarget.deleteObservers();

        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        spotTarget.hit();
        dropTarget.hit();
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }
}