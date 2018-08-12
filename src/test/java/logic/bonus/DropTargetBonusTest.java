package logic.bonus;

import controller.Game;
import logic.gameelements.GameElementType;
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
 * test behavior of {@link DropTargetBonus} that doesn't
 * could be tested by interactions with {@link Game},
 * {@link Bumper}, {@link Target} or {@link logic.table.Table}.
 *
 * @see logic.gameelements.bumper.KickerBumperTest
 * @see logic.gameelements.bumper.PopBumperTest
 * @see logic.gameelements.target.SpotTargetTest
 * @see logic.gameelements.target.DropTargetTest
 * @see logic.table.GameTableTest
 *
 * @author Fancisco Muñoz Ponce. on date: 27-05-18
 */
public class DropTargetBonusTest {
    private static Target target;
    private static DropTargetBonus dropTargetBonus;
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
        dropTargetBonus = DropTargetBonus.getInstance();
        game = new Game(balls, score);
        target = new DropTarget();

        // observers
        dropTargetBonus.setObservers(game);
        target.setObservers(game, dropTargetBonus);
    }

    /**
     * test that this method actually return the instance of DropTargetBonus
     * satisfying the Singleton Pattern
     */
    @Test
    public void getInstance() {
        assertEquals(dropTargetBonus, DropTargetBonus.getInstance());
        // if we repeat, still return the same
        assertEquals(dropTargetBonus, DropTargetBonus.getInstance());
    }

    /**
     * test that visit a bumper doesn't do anything
     */
    @Test
    public void visitBumper() {
        Bumper bumper = new KickerBumper();
        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());
        assertEquals(500, bumper.getScore());
        assertFalse(dropTargetBonus.isBonusOfBalls());
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.hitBumper(bumper);
        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());
        assertEquals(500, bumper.getScore());
        assertEquals(count, dropTargetBonus.timesTriggered());
        assertFalse(dropTargetBonus.isBonusOfBalls());
    }

    /**
     * test that visit a target doesn't do anything
     */
    @Test
    public void visitTarget() {
        Target target = new DropTarget();
        assertEquals(100, target.getScore());
        assertTrue(target.isActive());
        assertEquals(target.getType(), GameElementType.DROP_TARGET);
        assertFalse(dropTargetBonus.isBonusOfBalls());
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.hitTarget(target);
        assertEquals(100, target.getScore());
        assertTrue(target.isActive());
        assertEquals(target.getType(), GameElementType.DROP_TARGET);
        assertEquals(count, dropTargetBonus.timesTriggered());
        assertFalse(dropTargetBonus.isBonusOfBalls());

    }

    /**
     * visit a playable table should trigger the bonus only
     * if the table has all of the dropTargets non-active.
     * Tested in {@link logic.table.GameTableTest}
     */
    @Test
    public void visitTable() {
        Table table = GameTable.getFullTable("table", 4, 0.1,
                7, 11);
        table.setGameElementsObservers(game);

        // first for the case when the table doesnt have all the dropTargets non-active,
        // set one dropTarget to false
        score = game.getScore();
        table.getTargets().get(0).hit();
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.changedStateOfTable(table);
        assertEquals(score + 100, game.getScore());
        assertEquals(count, dropTargetBonus.timesTriggered());

        // then for a table will all dropTargets non-active
        for (Target t: table.getTargets()){
            t.hit();
        }
        score = score + dropTargetBonus.getBonusValue() + table.getNumberOfDropTargets() * 100;
//        dropTargetBonus.changedStateOfTable(table);
        assertEquals(score, game.getScore());
        assertEquals(count+1, dropTargetBonus.timesTriggered());
    }

    /**
     * test that accept a visit from Bumper doesn't do anything
     */
    @Test
    public void acceptFromBumper() {
        Bumper bumper = new KickerBumper();
        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());
        assertEquals(500, bumper.getScore());
        assertFalse(dropTargetBonus.isBonusOfBalls());
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.acceptObservationFromBumper(bumper);
        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());
        assertEquals(500, bumper.getScore());
        assertEquals(count, dropTargetBonus.timesTriggered());
        assertFalse(dropTargetBonus.isBonusOfBalls());
    }

    /**
     * test that reset the counter of times triggers actually set the number of
     * triggers to 0
     */
    @Test
    public void resetCounterTriggers() {
        int count  = dropTargetBonus.timesTriggered();
        dropTargetBonus.trigger();
        assertEquals(count + 1, dropTargetBonus.timesTriggered());
        dropTargetBonus.resetCounterTriggers();
        assertEquals(0, dropTargetBonus.timesTriggered());
    }

    /**
     * test that set observers actually set the correct number of observers
     */
    @Test
    public void setObservers() {
        // already using 1 observer
        assertEquals(1, dropTargetBonus.countObservers());
    }

    /**
     * plenty tested in interactions
     */
    @Test
    public void timesTriggered() {
        // go to test of bumpers, targets, table or game
    }

    /**
     * test that trigger() augment the timesTriggered by 1
     * and augment the score in game.
     * Also should trigger an upgrade of all bumpers,
     * this is tested in {@link logic.table.GameTableTest}
     */
    @Test
    public void trigger() {
        score = game.getScore();
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.trigger();
        assertEquals(count +1, dropTargetBonus.timesTriggered());
        assertEquals(score + dropTargetBonus.getBonusValue(), game.getScore());

    }

    /**
     * plenty testes in interactions
     */
    @Test
    public void getBonusValue() {
        // go to test of bumpers, targets, table or game
    }

    /**
     * test that this method return False for
     * a DropTargetBonus
     */
    @Test
    public void isBonusOfBalls() {
        assertFalse(dropTargetBonus.isBonusOfBalls());
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
     * the game score. This is usually accessed trough a
     * trigger().
     */
    @Test
    public void acceptFromGame() {
        score = game.getScore();
        dropTargetBonus.acceptObservationFromGame(game);
        assertEquals(score + dropTargetBonus.getBonusValue(), game.getScore());
    }

    /**
     * test that accept a visit from another bonus
     * doesn't do anything.
     */
    @Test
    public void acceptFromBonus() {
        Bonus bonus = JackPotBonus.getInstance();
        int countJacPot = bonus.timesTriggered();
        int countDrop = dropTargetBonus.timesTriggered();
        dropTargetBonus.acceptObservatiobFromBonus(bonus);
        assertEquals(countJacPot, bonus.timesTriggered());
        assertEquals(countDrop, dropTargetBonus.timesTriggered());
    }

    /**
     * test that accept a visit from target,
     * doesn't do anything
     */
    @Test
    public void acceptFromTarget() {
        Target target = new DropTarget();
        assertTrue(target.isActive());
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.acceptObservationFromTarget(target);
        assertTrue(target.isActive());
        assertEquals(count, dropTargetBonus.timesTriggered());
    }

    /**
     * test that accept a visit from Table,
     * desn't do anything
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getTableWithoutTargets("table", 4, 0.3);
        table.setGameElementsObservers(game);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        int count = dropTargetBonus.timesTriggered();
        dropTargetBonus.acceptObservationFromTable(table);
        assertTrue(table.isPlayableTable());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals(count, dropTargetBonus.timesTriggered());
    }

    /**
     * test that visit a Bonus doesn't do anything.
     */
    @Test
    public void visitBonus() {
        Bonus bonus = ExtraBallBonus.getInstance();
        assertTrue(bonus.isBonusOfBalls());
        int countExt = bonus.timesTriggered();
        int countDrop = dropTargetBonus.timesTriggered();
        dropTargetBonus.triggeredBonus(bonus);
        assertTrue(bonus.isBonusOfBalls());
        assertEquals(countDrop, dropTargetBonus.timesTriggered());
        assertEquals(countExt, bonus.timesTriggered());
    }
}