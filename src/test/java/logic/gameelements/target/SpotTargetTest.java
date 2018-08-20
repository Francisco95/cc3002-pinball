package logic.gameelements.target;

import controller.Game;
import logic.bonus.JackPotBonus;
import logic.gameelements.GameElementType;
import logic.table.GameTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class SpotTargetTest {
    private Game game;
    private JackPotBonus jackPot;
    private SpotTarget spotTarget;
    private int score;

    @Before
    public void setUp() {
        score = 73;
        game = new Game(1, score);
        jackPot = JackPotBonus.getInstance();
        spotTarget = new SpotTarget();
        // then add the observers
        spotTarget.setObservers(game, jackPot);
        jackPot.setObservers(game);
    }

    /**
     * for this the bonus is always triggered
     */
    @Test
    public void bonusTriggered() {
        spotTarget.setBonusIsTriggered();
        assertTrue(spotTarget.bonusTriggered());
        spotTarget.setBonusIsTriggered();
        assertTrue(spotTarget.bonusTriggered());
        spotTarget.setBonusIsTriggered();
        assertTrue(spotTarget.bonusTriggered());
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void setObservers() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void setActive() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void setSeed() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void setSeedToRandomProb() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void getRandomProb() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void isActive() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void reset() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * test that it actually change the score/balls on Game instance and also after hit()
     * is not active anymore
     */
    @Test
    public void hit() {
        assertEquals(score, game.getScore());
        assertTrue(spotTarget.isActive());

        score = game.getScore() + jackPot.getBonusValue() + spotTarget.getScore();
        spotTarget.hit();
        assertFalse(spotTarget.isActive());
        assertEquals(score, game.getScore());
    }

    /**
     * check that do hit() without add the jackPot/extraBall
     * observer doesn't produce change in game score/balls
     */
    @Test
    public void hitAfterReset(){
        assertEquals(score, game.getScore());
        spotTarget.reset();
        spotTarget.deleteObservers();
        assertEquals(score, game.getScore());
        spotTarget.hit();
        assertEquals(score, game.getScore());

    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void getScore() {
        // tested in hit() and hitAfterReset()
    }

    /**
     * test that return false if is not a dropTarget
     */
    @Test
    public void checkGetType() {
        assertEquals(spotTarget.getType(), GameElementType.SPOT_TARGET);
    }

    /**
     * test that accept a visit from game means
     * that a game augment his score by the corresponding
     * amount of points, this case, by 0
     */
    @Test
    public void acceptFromGame() {
        spotTarget.acceptObservationFromGame(game);
        assertEquals(score, game.getScore());
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void acceptFromBumper() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * go to {@link DropTargetTest}
     */
    @Test
    public void acceptFromTarget() {
        // tested in tests for dropTarget, common behavior
    }


    /**
     * accept a visit from a Table shouldn't
     * change anything in the Table or the SpotTarget
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getEmptyTable();
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertFalse(table.isPlayableTable());
        assertEquals(0, spotTarget.getScore());
        assertTrue(spotTarget.isActive());

        spotTarget.acceptObservationFromTable(table);

        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertFalse(table.isPlayableTable());
        assertEquals(0, spotTarget.getScore());
        assertTrue(spotTarget.isActive());
    }

    /**
     * test that accept visit from bonus (which should be a JackPotBonus)
     * triggers this bonus only if it was called by hit().
     */
    @Test
    public void acceptFromBonus() {
        score = game.getScore();
        int counterTrigger = jackPot.timesTriggered();

        spotTarget.acceptObservatiobFromBonus(jackPot);
        assertEquals(score, game.getScore());
        assertEquals(counterTrigger, jackPot.timesTriggered());

        spotTarget.hit();

        assertEquals(score + jackPot.getBonusValue(), game.getScore());
        assertEquals(counterTrigger+1, jackPot.timesTriggered());
    }
}