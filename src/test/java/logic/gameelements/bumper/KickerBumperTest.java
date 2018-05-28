package logic.gameelements.bumper;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import logic.table.GameTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * tests for KickerBumper, here we don test interaction
 * of many Bumpers with table, for that go to {@link logic.table.TableTest}
 *
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class KickerBumperTest {
    private KickerBumper kickerBumper;
    private ExtraBallBonus extraBallBonus;
    private Game game;
    private int balls, score;

    @Before
    public void setUp() {
        balls = 3;
        score = 99;
        game = new Game(balls, score);
        kickerBumper = new KickerBumper();
        extraBallBonus = ExtraBallBonus.getInstance();

        // then add the observers
        kickerBumper.setObservers(game, extraBallBonus);
        extraBallBonus.setObservers(game);
    }

    /**
     * check that actually return true if the bumper is upgraded
     */
    @Test
    public void isUpgraded() {
        // at first is not upgraded
        assertFalse(kickerBumper.isUpgraded());
        // then upgrade the bumper
        kickerBumper.setRemainingHitsToUpgrade(0);
        kickerBumper.upgrade(); // set the pointsGiven to the upgraded case
        assertEquals(kickerBumper.remainingHitsToUpgrade() == 0 && kickerBumper.getScore() == 1000,
                kickerBumper.isUpgraded());

    }

    /**
     * check that upgrade actually change the points given by the bumper
     * and also set the remainingHits to 0
     */
    @Test
    public void upgrade() {
        assertEquals(500, kickerBumper.getScore());
        assertNotEquals(0, kickerBumper.remainingHitsToUpgrade());
        kickerBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
    }

    /**
     * check that downgrade actually change the points given by the bumper
     * and also reset the remainingHits to 5
     */
    @Test
    public void downgrade() {
        kickerBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        kickerBumper.downgrade();
        assertEquals(500, kickerBumper.getScore());
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
    }

    /**
     * test that this actually set the observers
     */
    @Test
    public void setObservers() {
        assertEquals(2, kickerBumper.countObservers());
    }

    /**
     * check that when the bumper should be upgraded this method trigger
     * the upgrade() method
     */
    @Test
    public void shouldUpgrade() {
        // at fitst the bumper is not upgraded
        assertFalse(kickerBumper.isUpgraded());
        // then without a hit(), shouldUpgrade() shouldn't trigger an upgrade
        kickerBumper.shouldUpgrade();
        assertFalse(kickerBumper.isUpgraded());

        // for last, if we set the remainingHits to 0 then shouldUpgrade() trigger an upgrade
        kickerBumper.setRemainingHitsToUpgrade(0);
        kickerBumper.shouldUpgrade();
        assertTrue(kickerBumper.isUpgraded());

        // and if remaininHits go bellow 0 (if receive a hit() after an upgrade) then
        // shouldUpgrade() set the counter to 0 again
        kickerBumper.setRemainingHitsToUpgrade(-1);
        assertEquals(-1, kickerBumper.remainingHitsToUpgrade());
        kickerBumper.shouldUpgrade();
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
    }

    /**
     * check that this actually set the seed, for this we set a seed and then get
     * the triggeredBonus() and check that always give the same result.
     */
    @Test
    public void setSeed() {
        kickerBumper.setSeed(12);
        boolean expected = kickerBumper.bonusTriggered();
        // arbitrary number of repetition
        assertEquals(expected, kickerBumper.bonusTriggered());
        assertEquals(expected, kickerBumper.bonusTriggered());
        assertEquals(expected, kickerBumper.bonusTriggered());
    }

    /**
     * test that this method actually change the value of
     * 'remainingHitsToUpgrade' to the specified value.
     */
    @Test
    public void setRemainingHitsToUpgrade() {
        // used/tested in remainingHitsToUpgrade()
    }

    /**
     * test that setScore() actually change the score by the given amount
     */
    @Test
    public void setScore() {
        // tested in upgrade() and downgrade() since an upgrade/downgrade
        // means set the score to a new value.
    }

    /**
     * test that compareScore() actually compare the current score
     * to some value.
     */
    @Test
    public void compareScore() {
        // used in method isUpgraded(), go to that test
        int aScore = kickerBumper.getScore();
        assertTrue(kickerBumper.compareScore(aScore));
        aScore = -1235;
        assertFalse(kickerBumper.compareScore(aScore));
    }

    /**
     * test that compareRemainingHits() actually compare the
     * remaining hits necessaries to upgrade to some value.
     */
    @Test
    public void compareRemainingHits() {
        // used in method isUpgraded(), go to that test
        int aNum = kickerBumper.remainingHitsToUpgrade();
        assertTrue(kickerBumper.compareRemainingHits(aNum));
        aNum = -58657;
        assertFalse(kickerBumper.compareRemainingHits(aNum));
    }

    /**
     * check that this method return the expected value
     */
    @Test
    public void remainingHitsToUpgrade() {
        // at first should be 5/3
        assertEquals(5, kickerBumper.remainingHitsToUpgrade());
        // if change to 0
        kickerBumper.setRemainingHitsToUpgrade(0);
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        // or to <0
        kickerBumper.setRemainingHitsToUpgrade(-1);
        assertEquals(-1, kickerBumper.remainingHitsToUpgrade());
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
        assertEquals(500, kickerBumper.getScore());
        assertFalse(kickerBumper.isUpgraded());
        int count = extraBallBonus.timesTriggered();

        // then look for a seed that actually trigger the extraballbonus
        int seed = 0;
        kickerBumper.setSeed(seed);
        while(!kickerBumper.bonusTriggered()) {
            seed++;
            kickerBumper.setSeed(seed);
        }
        // in the first hit the game score should augment by:
        score = game.getScore() + kickerBumper.getScore();
        // and the balls should:
        balls = game.getBalls() + extraBallBonus.getBonusValue();
        kickerBumper.hit();
        assertEquals(4, kickerBumper.remainingHitsToUpgrade());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(count + 1, extraBallBonus.timesTriggered());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

        // the set the bumpers to 1 remainin hit and call hit again
        kickerBumper.setRemainingHitsToUpgrade(1);
        score = game.getScore() + 1000;
        balls = game.getBalls() + extraBallBonus.getBonusValue();
        kickerBumper.hit();
        assertEquals(0, kickerBumper.remainingHitsToUpgrade());
        assertEquals(1000, kickerBumper.getScore());
        assertTrue(kickerBumper.isUpgraded());
        assertEquals(count + 2, extraBallBonus.timesTriggered());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }

    /**
     * test that getScore return the points that the bumper give
     */
    @Test
    public void getScore() {
        assertEquals(500, kickerBumper.getScore());
        kickerBumper.upgrade();
        assertEquals(1000, kickerBumper.getScore());
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
        assertEquals(rand.nextInt(10) == 0, kickerBumper.bonusTriggered());
        rand.setSeed(seed);

        rand.setSeed(seed);
        kickerBumper.setSeed(seed);
        while(rand.nextInt(10) != 0){
            assertFalse(kickerBumper.bonusTriggered());
            seed++;
            rand.setSeed(seed);
            kickerBumper.setSeed(seed);
        }
        rand.setSeed(seed);
        assertEquals(0, rand.nextInt(10));
        assertTrue(kickerBumper.bonusTriggered());

    }

    /**
     * test that accept a visit from Game produce an addition
     * to the game score the score of the bumper
     */
    @Test
    public void acceptFromGame() {
        assertEquals(score, game.getScore());
        kickerBumper.acceptFromGame(game);
        assertEquals(score + kickerBumper.getScore(), game.getScore());
    }

    /**
     * test that accept a visit from Bonus could produce
     * a trigger of the bonus.
     */
    @Test
    public void acceptFromBonus() {
        int counterTrigger = extraBallBonus.timesTriggered();
        // look for a seed that doesn't trigger the bonus
        int seed = 0;
        kickerBumper.setSeed(seed);
        while(kickerBumper.bonusTriggered()) {
            seed++;
            kickerBumper.setSeed(seed);
        }
        assertEquals(balls, game.getBalls());
        kickerBumper.acceptFromBonus(extraBallBonus);
        assertEquals(counterTrigger, extraBallBonus.timesTriggered());
        assertEquals(balls, game.getBalls());

        // look for a seed that trigger the bonus
        seed = 0;
        kickerBumper.setSeed(seed);
        while(!kickerBumper.bonusTriggered()) {
            seed++;
            kickerBumper.setSeed(seed);
        }
        kickerBumper.acceptFromBonus(extraBallBonus);
        assertEquals(counterTrigger+1, extraBallBonus.timesTriggered());
        assertEquals(balls + extraBallBonus.getBonusValue(), game.getBalls());
    }

    /**
     * test that accept a visit from another bumper
     * doesn't do anything
     */
    @Test
    public void acceptFromBumper() {
        Bumper bumper = new KickerBumper();
        // state before
        assertFalse(bumper.isUpgraded());
        assertEquals(500, bumper.getScore());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());

        kickerBumper.acceptFromBumper(bumper);
        assertFalse(bumper.isUpgraded());
        assertEquals(500, bumper.getScore());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());

    }

    /**
     * test that accept a visit from a Target
     * doesn't do anything.
     */
    @Test
    public void acceptFromTarget() {
        Target target = new SpotTarget();
        // state before
        assertTrue(target.isActive());
        assertEquals(0, target.getScore());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());

        kickerBumper.acceptFromTarget(target);
        assertTrue(target.isActive());
        assertEquals(0, target.getScore());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());
    }

    /**
     * test that accept a visit from Table,
     * doesn't do anything
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getEmptyTable();
        assertFalse(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals("", table.getTableName());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());

        kickerBumper.acceptFromTable(table);
        assertFalse(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals("", table.getTableName());
        assertFalse(kickerBumper.isUpgraded());
        assertEquals(500, kickerBumper.getScore());
    }
}