package logic.gameelements.target;

import controller.Game;
import logic.bonus.JackPotBonus;
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
        assertTrue(spotTarget.bonusTriggered());
        assertTrue(spotTarget.bonusTriggered());
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
     * go to {@link DropTargetTest}
     */
    @Test
    public void getScore() {
        // tested in tests for dropTarget, common behavior
    }

    /**
     * test that return false if is not a dropTarget
     */
    @Test
    public void isADropTarget() {
        assertFalse(spotTarget.isADropTarget());
    }

    /**
     * test that accept a visit from game means
     * that a game augment his score by the corresponding
     * amount of points, this case, by 0
     */
    @Test
    public void acceptFromGame() {
        spotTarget.acceptFromGame(game);
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

        spotTarget.acceptFromTable(table);

        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertFalse(table.isPlayableTable());
        assertEquals(0, spotTarget.getScore());
        assertTrue(spotTarget.isActive());
    }

    /**
     * test that accept visit from bonus (which should be a JackPotBonus)
     * always trigger this bonus.
     */
    @Test
    public void acceptFromBonus() {
        score = game.getScore();
        int counterTrigger = jackPot.timesTriggered();

        spotTarget.acceptFromBonus(jackPot);
        assertEquals(score + jackPot.getBonusValue(), game.getScore());
        assertEquals(counterTrigger+1, jackPot.timesTriggered());
    }
}