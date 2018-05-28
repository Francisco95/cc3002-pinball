package logic.gameelements.target;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.table.GameTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class DropTargetTest {
    private Game game;
    private ExtraBallBonus extraBall;
    private DropTarget dropTarget;
    private int score, balls;

    @Before
    public void setUp() {
        balls = 3;
        score = 43;
        game = new Game(balls, score);
        extraBall = ExtraBallBonus.getInstance();
        dropTarget = new DropTarget();
        // then add the observers
        dropTarget.setObservers(game, extraBall);
        extraBall.setObservers(game);
    }

    /**
     * test behavior of trigger a bonus given a random probability
     */
    @Test
    public void bonusTriggered() {
        // tested in setSeed()
    }

    /**
     * test that this actually set the observers
     */
    @Test
    public void setObservers() {
        // already setted, check that the number of observers its correct
        assertEquals(2, dropTarget.countObservers());
    }

    /**
     * test that this change the value of 'Active'
     */
    @Test
    public void setActive() {
        // tested in isActive();
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
    }

    /**
     * test that that actually set the ranodm seed in order to get always the same
     * result, this is used in bonusTriggered()
     */
    @Test
    public void setSeedToRandomProb() {
        //tested in setSeed()

    }

    /**
     * test that return the correct instance of Random
     */
    @Test
    public void getRandomProb() {
        // used in bonusTriggered, tested in setSeed()

    }

    /**
     * test that method isActive() return as expected.
     * Also check that method hit() set Active to false.
     */
    @Test
    public void isActive() {
        // at first should be true
        assertTrue(dropTarget.isActive());
        //then set to false and check this
        dropTarget.hit();
        assertFalse(dropTarget.isActive());
        // and set to active again
        dropTarget.setActive(true);
        assertTrue(dropTarget.isActive());
    }

    /**
     * test that the only valid way to change the state of SpotTarget/dropTarget
     * (means re-activate) is by reset.
     * This consider that setActive is created just to test and internal use.
     * Also reset could be more generic and reset whatever we want.
     */
    @Test
    public void reset() {
        assertTrue(dropTarget.isActive());
        dropTarget.setActive(false);
        assertFalse(dropTarget.isActive());
        dropTarget.reset();
        assertTrue(dropTarget.isActive());
    }

    /**
     * test that it actually change the score/balls on Game instance and also after hit()
     * is not active anymore
     */
    @Test
    public void hit() {
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        assertTrue(dropTarget.isActive());

        score = game.getScore() +  dropTarget.getScore();
        balls = game.getBalls() + extraBall.getBonusValue();
        // set the seed in a way to get a triggered
        int seed = 0;
        dropTarget.setSeed(seed);
        while(!dropTarget.bonusTriggered()) {
            seed++;
            dropTarget.setSeed(seed);
        }
        dropTarget.hit();
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
        dropTarget.reset();
        dropTarget.deleteObservers();

        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());
        dropTarget.hit();
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }

    /**
     * test that return the correct score
     */
    @Test
    public void getScore() {
        // tested in hit() and hitAfterReset()
    }

    /**
     * test that return true if it is a dropTarget object
     */
    @Test
    public void isADropTarget() {
        assertTrue(dropTarget.isADropTarget());
    }

    /**
     * test that accept a visit from game means
     * that a game augmente his score by the corresponding
     * amount,
     */
    @Test
    public void acceptFromGame() {
        dropTarget.acceptFromGame(game);
        assertEquals(score + dropTarget.getScore(), game.getScore());
    }

    /**
     * test that accept a visit from bumper
     * dosent do anything to the state of the bumper
     * or the state of the target
     */
    @Test
    public void acceptFromBumper() {
        Bumper kickerBumper = new KickerBumper();
        // previous state
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        assertEquals(500, kickerBumper.getScore());
        assertTrue(dropTarget.isActive());
        assertEquals(100, dropTarget.getScore());

        dropTarget.acceptFromBumper(kickerBumper);

        // state after accept visit
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        assertEquals(500, kickerBumper.getScore());
        assertTrue(dropTarget.isActive());
        assertEquals(100, dropTarget.getScore());
    }

    /**
     * test that accept visit from another target
     * dosent do anything to the state of targets
     */
    @Test
    public void acceptFromTarget() {
        Target target = new SpotTarget();
        // state before
        assertTrue(target.isActive());
        assertEquals(0, target.getScore());
        assertTrue(dropTarget.isActive());
        assertEquals(100, dropTarget.getScore());

        dropTarget.acceptFromTarget(target);

        // state after
        assertTrue(target.isActive());
        assertEquals(0, target.getScore());
        assertTrue(dropTarget.isActive());
        assertEquals(100, dropTarget.getScore());
    }

    /**
     * test that accept visit from table could trigger a
     * an addition in the counter of dropped dropTargets
     * if this target is non-active
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getEmptyTable();
        // is active, doesn't change
        int counter = table.getCurrentlyDroppedDropTargets();
        dropTarget.acceptFromTable(table);
        assertEquals(counter, table.getCurrentlyDroppedDropTargets());

        // is non-active, change by +1
        dropTarget.setActive(false);
        dropTarget.acceptFromTable(table);
        assertEquals(counter + 1, table.getCurrentlyDroppedDropTargets());
    }

    /**
     * test that accept visit from bonus could trigger
     * this bonus if the randomProb is good
     */
    @Test
    public void acceptFromBonus() {
        // look for a seed that doesn't trigger the bonus
        int seed = 0;
        dropTarget.setSeed(seed);
        while(dropTarget.bonusTriggered()) {
            seed++;
            dropTarget.setSeed(seed);
        }
        dropTarget.setSeed(seed);
        int counterTrigger = extraBall.timesTriggered();
        balls = game.getBalls();
        dropTarget.acceptFromBonus(extraBall);
        assertEquals(counterTrigger, extraBall.timesTriggered());
        assertEquals(balls, game.getBalls());

        // look for a seed that trigger the bonus
        seed = 0;
        dropTarget.setSeed(seed);
        while(!dropTarget.bonusTriggered()) {
            seed++;
            dropTarget.setSeed(seed);
        }
        dropTarget.setSeed(seed);
        dropTarget.acceptFromBonus(extraBall);
        assertEquals(counterTrigger+1, extraBall.timesTriggered());
        assertEquals(balls+1, game.getBalls());

    }
}