package logic.gameelements.bumper;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * tests for bumper implementatiosn, this include testing methods
 * and behavior from kicker and pop bumper
 * @author Fancisco Muñoz Ponce. on date: 23-05-18
 */
public class BumperTest {
    private KickerBumper kickerBumper;
    private PopBumper popBumper;
    private ExtraBallBonus extraBallBonus;
    private Game game;
    private int balls = 3;
    private int score = 0;


    @Before
    public void setUp() throws Exception {
        game = new Game(balls, score);
        kickerBumper = new KickerBumper();
        popBumper = new PopBumper();
        extraBallBonus = ExtraBallBonus.getInstance();

        // then add the observers
        kickerBumper.setObservers(game, extraBallBonus);
        popBumper.setObservers(game, extraBallBonus);
        extraBallBonus.setObservers(game);
    }

    @After
    public void tearDown() throws Exception {
        balls = 3;
        score = 0;
        game.setBalls(balls);
        game.setScore(score);
        kickerBumper.downgrade();
        popBumper.downgrade();
        extraBallBonus.resetCounterTriggers();
    }

    /**
     * check that this actually set the seed, for this we set a seed then get
     * many thimes the triggeredBonus() and check that always give the same result.
     */
    @Test
    public void setSeed() {
        kickerBumper.setSeed(12);
        popBumper.setSeed(12);
        // same seed for the two bumpers means same expected result
        boolean expected = kickerBumper.bonusTriggered();
        // arbitrary number of repetition
        assertEquals(expected, kickerBumper.bonusTriggered());
        assertEquals(expected, kickerBumper.bonusTriggered());
        assertEquals(expected, kickerBumper.bonusTriggered());
        assertEquals(expected, popBumper.bonusTriggered());
        assertEquals(expected, popBumper.bonusTriggered());
        assertEquals(expected, popBumper.bonusTriggered());
    }

    /**
     * check that when the bumper should be upgraded this method trigger
     * the upgrade() method
     */
    @Test
    public void shouldUpgrade() {
        // at fitst the bumper is not upgraded
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());
        // then without a hit(), shouldUpgrade() shouldn't trigger an upgrade
        kickerBumper.shouldUpgrade();
        popBumper.shouldUpgrade();
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());

        // for last, if we set the remainingHits to 0 then shouldUpgrade() trigger an upgrade
        kickerBumper.setRemainingHitsToUpgrade(0);
        popBumper.setRemainingHitsToUpgrade(0);
        kickerBumper.shouldUpgrade();
        popBumper.shouldUpgrade();
        assertTrue(kickerBumper.isUpgraded());
        assertTrue(popBumper.isUpgraded());

        // and if remaininHits go bellow 0 (if receive a hit() after an upgrade) then
        // shouldUpgrade() set the counter to 0 again
        kickerBumper.setRemainingHitsToUpgrade(-1);
        popBumper.setRemainingHitsToUpgrade(-1);
        assertEquals(-1, kickerBumper.remainingHitsToUpgrade());
        assertEquals(-1, popBumper.remainingHitsToUpgrade());
        kickerBumper.shouldUpgrade();
        popBumper.shouldUpgrade();
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
    }

    /**
     * check that this method return the expected value
     */
    @Test
    public void remainingHitsToUpgrade() {
        // at first should be 5/3
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        assertEquals(3, popBumper.remainingHitsToUpgrade());
        // if change to 0
        kickerBumper.setRemainingHitsToUpgrade(0);
        popBumper.setRemainingHitsToUpgrade(0);
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        // or to <0
        kickerBumper.setRemainingHitsToUpgrade(-1);
        popBumper.setRemainingHitsToUpgrade(-1);
        assertEquals(-1, kickerBumper.remainingHitsToUpgrade());
        assertEquals(-1, popBumper.remainingHitsToUpgrade());
    }

    /**
     * check that actually return true if the bumper is upgraded
     */
    @Test
    public void isUpgraded() {
        // at first is not upgraded
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());
        // then upgrade the bumper
        kickerBumper.setRemainingHitsToUpgrade(0);
        popBumper.setRemainingHitsToUpgrade(0);
        popBumper.upgrade(); // set the pointsGiven to the upgraded case
        kickerBumper.upgrade(); // set the pointsGiven to the upgraded case
        // the kickerbumper is upgraded if remainingHits == 0 && pointsGiven==1000
        assertEquals(kickerBumper.remainingHitsToUpgrade() == 0 && kickerBumper.getScore() == 1000,
                kickerBumper.isUpgraded());
        assertEquals(popBumper.remainingHitsToUpgrade() == 0 && popBumper.getScore() == 300,
                popBumper.isUpgraded());

    }

    /**
     * check that upgrade actually change the points given by the bumper
     * and also set the remainingHits to 0
     */
    @Test
    public void upgrade() {
        assertEquals(500, kickerBumper.getScore());
        assertEquals(100, popBumper.getScore());
        assertNotEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertNotEquals(0, popBumper.remainingHitsToUpgrade());
        kickerBumper.upgrade();
        popBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(300, popBumper.getScore());
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
    }

    /**
     * check that downgrade actually change the points given by the bumper
     * and also reset the remainingHits to 5
     */
    @Test
    public void downgrade() {
        kickerBumper.upgrade();
        popBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(300, popBumper.getScore());
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        kickerBumper.downgrade();
        popBumper.downgrade();
        assertEquals(500, kickerBumper.getScore());
        assertEquals(100, popBumper.getScore());
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        assertEquals(3, popBumper.remainingHitsToUpgrade());
    }

    /**
     * check that hit() actually change the score in game and the state of bumper and bonus
     */
    @Test
    public void hit() {
        // at fist the game has:
        assertEquals(game.getBalls(), balls);
        assertEquals(game.getScore(), score);
        // and the bumper/bonus
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        assertEquals(3, popBumper.remainingHitsToUpgrade());
        assertEquals(500, kickerBumper.getScore());
        assertEquals(100, popBumper.getScore());
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());
        assertEquals(0, extraBallBonus.timesTriggered());

        // then look for a seed that actually trigger the extraballbonus
        int seed = 0;
        kickerBumper.setSeed(seed);
        while(!kickerBumper.bonusTriggered()) {
            seed++;
            kickerBumper.setSeed(seed);
        }
        popBumper.setSeed(seed);
        // in the first hit the game score should augment by:
        score = game.getScore() + 500 + 100;
        // and the balls should:
        balls = game.getBalls() + extraBallBonus.getBonusValue() * 2;
        kickerBumper.hit();
        popBumper.hit();
        assertEquals(4, kickerBumper.remainingHitsToUpgrade());
        assertEquals(2, popBumper.remainingHitsToUpgrade());
        assertFalse(kickerBumper.isUpgraded());
        assertFalse(popBumper.isUpgraded());
        assertEquals(2, extraBallBonus.timesTriggered());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

        // the set the bumpers to 1 remainin hit and call hit again
        kickerBumper.setRemainingHitsToUpgrade(1);
        popBumper.setRemainingHitsToUpgrade(1);
        score = game.getScore() + 1000 + 300;
        balls = game.getBalls() + extraBallBonus.getBonusValue() * 2;
        kickerBumper.hit();
        popBumper.hit();
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(300, popBumper.getScore());
        assertTrue(kickerBumper.isUpgraded());
        assertTrue(popBumper.isUpgraded());
        assertEquals(4, extraBallBonus.timesTriggered());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }

    /**
     * test that getScore return the points that the bumper give
     */
    @Test
    public void getScore() {
        assertEquals(500, kickerBumper.getScore());
        assertEquals(100, popBumper.getScore());
        kickerBumper.upgrade();
        popBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(300, popBumper.getScore());
    }

    /**
     * test behavior of bonusTriggered
     */
    @Test
    public void bonusTriggered() {
        // expected behavior, set the same seed for two random instances return same
        int seed = 12333;
        Random rand = new Random(seed);
        kickerBumper.setSeed(seed);
        popBumper.setSeed(seed);
        assertEquals(rand.nextInt(10) == 0, kickerBumper.bonusTriggered());
        rand.setSeed(seed);
        assertEquals(rand.nextInt(10) == 0, popBumper.bonusTriggered());

        rand.setSeed(seed);
        kickerBumper.setSeed(seed);
        popBumper.setSeed(seed);
        while(rand.nextInt(10) != 0){
            assertFalse(kickerBumper.bonusTriggered());
            seed++;
            rand.setSeed(seed);
            kickerBumper.setSeed(seed);
        }
        popBumper.setSeed(seed);
        rand.setSeed(seed);
        assertEquals(0, rand.nextInt(10));
        assertTrue(kickerBumper.bonusTriggered());
        assertTrue(popBumper.bonusTriggered());

    }
}