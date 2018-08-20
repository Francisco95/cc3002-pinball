package logic.bonus;

import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import logic.table.GameTable;
import logic.table.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class ExtraBallBonusTest {
    private static Target target;
    private static Bumper bumper;
    private static ExtraBallBonus extraBallBonus;
    private static Game game;
    private static int score, balls;

    /**
     * since we test a class created with singleton Pattern,
     * is better use a setUp of before the class.
     */
    @BeforeClass
    public static void setUp() {
        score = 33;
        balls = 9;
        extraBallBonus = ExtraBallBonus.getInstance();
        game = new Game(balls, score);
        target = new DropTarget();
        bumper = new KickerBumper();

        // observers
        extraBallBonus.setObservers(game);
        target.setObservers(game, extraBallBonus);
        bumper.setObservers(game, extraBallBonus);
    }

    /**
     * test that this method actually return the instance of JackPotBonus
     * satisfying the Singleton Pattern
     */
    @Test
    public void getInstance() {
        assertEquals(extraBallBonus, ExtraBallBonus.getInstance());
        // if we repeat, still return the same
        assertEquals(extraBallBonus, ExtraBallBonus.getInstance());
    }

    /**
     * visit a DropTarget, this is used in visitTarget()
     */
    @Test
    public void visitADropTarget() {
        // tested in visitTarget()
    }

    /**
     * test that accept a visit from Bumper doesn't do anything,
     * tested in {@link DropTargetBonusTest} for kicker or
     * {@link JackPotBonusTest} for pop, acdeptFromBumper() its
     * common for all bonuses
     */
    @Test
    public void acceptFromBumper() {
        // tested im DropTargetBonusTest or JackPotBonusTest
    }

    /**
     * visit a bumper could trigger the bonus.
     */
    @Test
    public void visitBumper() {
        // look for a seed that doesn't trigger the bonus
        int seed = 0;
        ((KickerBumper)bumper).setSeed(seed);
        while(bumper.bonusTriggered()) {
            seed++;
            ((KickerBumper)bumper).setSeed(seed);
             bumper.bonusCouldBeTriggered();
        }
        int count = extraBallBonus.timesTriggered();
        extraBallBonus.hitBumper(bumper);
        assertEquals(count, extraBallBonus.timesTriggered());

        // look for a seed that trigger the bonus
        seed = 0;
        ((KickerBumper)bumper).setSeed(seed);
        while(!bumper.bonusTriggered()) {
            seed++;
            ((KickerBumper)bumper).setSeed(seed);
            bumper.bonusCouldBeTriggered();
        }
        extraBallBonus.hitBumper(bumper);
        assertEquals(count+1, extraBallBonus.timesTriggered());

    }

    /**
     * visit a target could trigger a bumper,
     * this trigger only can happen for a DropTarget
     */
    @Test
    public void visitTarget() {
        // look for a seed that doesn't trigger the bonus
        int seed = 0;
        ((DropTarget)target).setSeed(seed);
        while(target.bonusTriggered()) {
            seed++;
            ((DropTarget)target).setSeed(seed);
            ((DropTarget) target).setBonusIsTriggered();
        }
        int count = extraBallBonus.timesTriggered();
        extraBallBonus.hitTarget(target);
        assertEquals(count, extraBallBonus.timesTriggered());

        // look for a seed that trigger the bonus
        seed = 0;
        ((DropTarget)target).setSeed(seed);
        while(!target.bonusTriggered()) {
            seed++;
            ((DropTarget)target).setSeed(seed);
            ((DropTarget) target).setBonusIsTriggered();
        }
        extraBallBonus.hitTarget(target);
        assertEquals(count+1, extraBallBonus.timesTriggered());
    }

    /**
     * visit a table do nothing
     */
    @Test
    public void visitTable() {
        Table table = GameTable.getFullTable( "table", 9, 0.2,
                5, 8);
        table.setGameElementsObservers(game);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        int count = extraBallBonus.timesTriggered();
        extraBallBonus.changedStateOfTable(table);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals(count, extraBallBonus.timesTriggered());

    }

    /**
     * test that reset the counter of times triggers actually set the number of
     * triggers to 0.
     * Common behavior for all bonuses, to see test go to {@link DropTargetBonusTest}
     */
    @Test
    public void resetCounterTriggers() {
        // tested in DropTargetBonusTest
    }

    /**
     * test that set observers actually set the correct number of observers.
     * Common behavior for all bonuses, to see test go to {@link DropTargetBonusTest}
     */
    @Test
    public void setObservers() {
        // tested in DropTargetBoonusTest
    }

    /**
     * test that trigger() augment the timesTriggered by 1
     * and augment the score in game.
     */
    @Test
    public void trigger() {
        balls = game.getBalls();
        int count = extraBallBonus.timesTriggered() + 1;
        extraBallBonus.trigger();
        int jackScore = extraBallBonus.getBonusValue();
        assertEquals(count, extraBallBonus.timesTriggered());
        assertEquals(balls + jackScore, game.getBalls());
    }

    /**
     * plenty testes in interactions
     */
    @Test
    public void getBonusValue() {
        // go to test of bumpers, targets, table or game
    }

    /**
     * test that this method return True for
     * a ExtraBallBonus
     */
    @Test
    public void isBonusOfBalls() {
        assertTrue(extraBallBonus.isBonusOfBalls());
    }

    /**
     * interaction of the Observer,
     * plenty tested with interactions
     */
    @Test
    public void update() {
        // go to test of bumpers, targets, table or game
    }

    /**
     * test that accept a visit from Game will augment
     * the game balls. This is usually accessed trough a
     * trigger().
     */
    @Test
    public void acceptFromGame() {
        balls = game.getBalls();
        extraBallBonus.acceptObservationFromGame(game);
        assertEquals(balls + extraBallBonus.getBonusValue(), game.getBalls());
    }

    /**
     * test that accept a visit from another bonus
     * doesn't do anything.
     */
    @Test
    public void acceptFromBonus() {
        Bonus bonus = DropTargetBonus.getInstance();
        int countDrop = bonus.timesTriggered();
        int countExt = extraBallBonus.timesTriggered();
        extraBallBonus.acceptObservatiobFromBonus(bonus);
        assertEquals(countDrop, bonus.timesTriggered());
        assertEquals(countExt, extraBallBonus.timesTriggered());
    }

    /**
     * test that accept a visit from target doesn't do anything,
     * common behavior for al bonuses, to see test go to
     * {@link DropTargetBonusTest}
     */
    @Test
    public void acceptFromTarget() {
        // tested in DropTargetBonusTest
    }

    /**
     * test that accept a visit from table doesn't do anything,
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getTableWithoutTargets("table", 7, 0.3);
        table.setGameElementsObservers(game);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        int count = extraBallBonus.timesTriggered();
        extraBallBonus.acceptObservationFromTable(table);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals(count, extraBallBonus.timesTriggered());
    }

    /**
     * test that visit a Bonus doesn't do anything.
     */
    @Test
    public void visitBonus() {
        Bonus bonus = JackPotBonus.getInstance();
        assertFalse(bonus.isBonusOfBalls());
        int countExt = bonus.timesTriggered();
        int countDrop = extraBallBonus.timesTriggered();
        extraBallBonus.triggeredBonus(bonus);
        assertFalse(bonus.isBonusOfBalls());
        assertEquals(countDrop, extraBallBonus.timesTriggered());
        assertEquals(countExt, bonus.timesTriggered());
    }
}