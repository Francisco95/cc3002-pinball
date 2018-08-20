package logic.gameelements.bumper;

import controller.Game;
import logic.bonus.ExtraBallBonus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class PopBumperTest {
    private PopBumper popBumper;
    private ExtraBallBonus extraBallBonus;
    private Game game;
    private int balls, score;

    @Before
    public void setUp() {
        balls = 5;
        score = 47;
        game = new Game(balls, score);
        popBumper = new PopBumper();
        extraBallBonus = ExtraBallBonus.getInstance();

        // then add the observers
        popBumper.setObservers(game, extraBallBonus);
        extraBallBonus.setObservers(game);
    }

    /**
     * check that actually return true if the bumper is upgraded
     */
    @Test
    public void isUpgraded() {
        // at first is not upgraded
        assertFalse(popBumper.isUpgraded());
        // then upgrade the bumper
        popBumper.setRemainingHitsToUpgrade(0);
        popBumper.upgrade(); // set the pointsGiven to the upgraded case
        assertEquals(popBumper.remainingHitsToUpgrade() == 0 && popBumper.getScore() == 300,
                popBumper.isUpgraded());

    }

    /**
     * check that upgrade actually change the points given by the bumper
     * and also set the remainingHits to 0
     */
    @Test
    public void upgrade() {
        assertEquals(100, popBumper.getScore());
        assertNotEquals(0, popBumper.remainingHitsToUpgrade());
        popBumper.upgrade();
        assertEquals(300, popBumper.getScore());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
    }

    /**
     * check that downgrade actually change the points given by the bumper
     * and also reset the remainingHits to 5
     */
    @Test
    public void downgrade() {
        popBumper.upgrade();
        assertEquals(300, popBumper.getScore());
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        popBumper.downgrade();
        assertEquals(100, popBumper.getScore());
        assertEquals(3, popBumper.remainingHitsToUpgrade());
    }

    /**
     * go to {@link KickerBumperTest}
     */
    @Test
    public void setObservers() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link KickerBumperTest}
     */
    @Test
    public void shouldUpgrade() {
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * go to {@link KickerBumperTest}
     */
    @Test
    public void setSeed() {
        // tested in tests for KickerBumper, common behavior
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
     * go to {@link KickerBumperTest} test name setScore()
     */
    @Test
    public void setScore() {
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * go to {@link KickerBumperTest} test name compareScore()
     */
    @Test
    public void compareScore() {
        // used in method isUpgraded(), go to that test
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * go to {@link KickerBumperTest} test name compareRemainingHits()
     */
    @Test
    public void compareRemainingHits() {
        // used in method isUpgraded(), go to that test
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * check that this method return the expected value
     */
    @Test
    public void remainingHitsToUpgrade() {
        // at first should be 5/3
        assertEquals(3, popBumper.remainingHitsToUpgrade());
        // if change to 0
        popBumper.setRemainingHitsToUpgrade(0);
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        // or to <0
        popBumper.setRemainingHitsToUpgrade(-1);
        assertEquals(-1, popBumper.remainingHitsToUpgrade());
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
        assertEquals(3, popBumper.remainingHitsToUpgrade());
        assertEquals(100, popBumper.getScore());
        assertFalse(popBumper.isUpgraded());
        int count = extraBallBonus.timesTriggered();

        // then look for a seed that actually trigger the extraballbonus
        int seed = 0;
        popBumper.setSeed(seed);
        while(!popBumper.bonusTriggered()) {
            seed++;
            popBumper.setSeed(seed);
            popBumper.bonusCouldBeTriggered();
        }
        // in the first hit the game score should augment by:
        score = game.getScore() + popBumper.getScore();
        // and the balls should:
        balls = game.getBalls() + extraBallBonus.getBonusValue();
        popBumper.hit();
        assertEquals(2, popBumper.remainingHitsToUpgrade());
        assertFalse(popBumper.isUpgraded());

        // the set the bumpers to 1 remainin hit and call hit again
        popBumper.setRemainingHitsToUpgrade(1);
        score = game.getScore() + 300;
        balls = game.getBalls() + extraBallBonus.getBonusValue();
        popBumper.hit();
        assertEquals(0, popBumper.remainingHitsToUpgrade());
        assertEquals(300, popBumper.getScore());
        assertTrue(popBumper.isUpgraded());
        assertEquals(count + 1, extraBallBonus.timesTriggered());
        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

    }

    /**
     * test that getScore return the points that the bumper give
     */
    @Test
    public void getScore() {
        assertEquals(100, popBumper.getScore());
        popBumper.upgrade();
        assertEquals(300, popBumper.getScore());
    }

    /**
     * go to {@link KickerBumperTest}
     */
    @Test
    public void bonusTriggered() {
        // tested in tests for KickerBumper, the probability of
        // trigger a bonus is the same for both bumpers
    }

    /**
     * test that accept a visit from Game produce an addition
     * to the game score the score of the bumper
     */
    @Test
    public void acceptFromGame() {
        assertEquals(score, game.getScore());
        popBumper.acceptObservationFromGame(game);
        assertEquals(score + popBumper.getScore(), game.getScore());
    }

    /**
     * test that accept a visit from Bonus could produce
     * a trigger of the bonus. Same behavior for all bumpers,
     * got o {@link KickerBumperTest}
     */
    @Test
    public void acceptFromBonus() {
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * test that accept from bumper doesn't change anything.
     * To see test go to {@link KickerBumperTest}
     */
    @Test
    public void acceptFromBumper() {
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * test that accept a visit from a Target
     * doesn't do anything.
     * To see test go to {@link KickerBumperTest}
     */
    @Test
    public void acceptFromTarget() {
        // tested in tests for KickerBumper, common behavior
    }

    /**
     * test that accept a visit from Table,
     * doesn't do anything.
     * To see test go to {@link KickerBumperTest}
     */
    @Test
    public void acceptFromTable() {
        // tested in tests for KickerBumper, common behavior
    }
}