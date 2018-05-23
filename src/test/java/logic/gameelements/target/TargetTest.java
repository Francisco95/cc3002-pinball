package logic.gameelements.target;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
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
    public void setUp() throws Exception {
        balls = 1;
        score = 0;
        game = new Game(balls, score);
        jackPot = JackPotBonus.getInstance();
        extraBall = ExtraBallBonus.getInstance();
        spotTarget = SpotTarget.getInstance();
        dropTarget = DropTarget.getInstance();
        // then add the observerx
        spotTarget.setObservers(game, jackPot);
        dropTarget.setObservers(game, extraBall);
        jackPot.setObservers(game);
        extraBall.setObservers(game);
    }

    @After
    public void resetAll(){
        spotTarget.reset();
        dropTarget.reset();
        balls = 1;
        score = 0;
        game.setScore(score);
        game.setBalls(balls);
        spotTarget.setObservers(game, jackPot);
        dropTarget.setObservers(game, extraBall);
        jackPot.setCounterTriggers(0);
        extraBall.setCounterTriggers(0);
    }

    /**
     * test that method isActive() return as expected.
     * Also check that method hit() set Active to false.
     */
    @Test
    public void isActive() {
        // at first should be true
        boolean expected = true;
        assertEquals(expected, spotTarget.isActive());
        assertEquals(expected, dropTarget.isActive());
        //then set to false and check this
        expected = false;
        spotTarget.hit();
        dropTarget.hit();
        assertEquals(expected, spotTarget.isActive());
        assertEquals(expected, dropTarget.isActive());
    }

    /**
     * check that after set active to False, cannot instantiate again to re-activate the spot
     */
    @Test
    public void getInstance() {
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());
        spotTarget.hit();
        dropTarget.hit();
        assertFalse(spotTarget.isActive());
        assertFalse(dropTarget.isActive());
        spotTarget = SpotTarget.getInstance();
        dropTarget = DropTarget.getInstance();
        assertFalse(spotTarget.isActive());
        assertFalse(dropTarget.isActive());
    }

    /**
     * test that the only valid way to change the state of SpotTarget/dropTarget (means re-activate) is by reset.
     * This consider that setActive is created just to test and internal use.
     * also reset  do a clear of the list of observables.
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

        // and since reset erase the observers, use hit() dosent change Game
        spotTarget.hit();
        dropTarget.hit();
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
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
     * test that it actually change the score/balls on Game instance and also after hit() is not active anymore
     */
    @Test
    public void hit() {
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        assertTrue(spotTarget.isActive());
        assertTrue(dropTarget.isActive());

        score = game.getScore() + jackPot.getBonusValue() + dropTarget.getScore() + spotTarget.getScore();
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
     * check that do hit() just after a reset without add the jackPot/extraBall
     * observer doesn't produce change in game score/balls
     */
    @Test
    public void hitAfterReset(){
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        spotTarget.reset();
        dropTarget.reset();
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        spotTarget.hit();
        dropTarget.hit();
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }
}