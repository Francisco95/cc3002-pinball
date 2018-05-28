package logic.table;

import controller.Game;
import javafx.scene.control.Tab;
import logic.bonus.Bonus;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 28-05-18
 */
public class GameTableTest {
    private Table emptyTable, noTargetTable, fullTable;
    private ExtraBallBonus extraBallBonus;
    private JackPotBonus jackPotBonus;
    private DropTargetBonus dropTargetBonus;
    private Game game;
    private int numOfTargets, numOfBumpers, numOfDropTargets;

    @Before
    public void setUp() throws Exception {
        extraBallBonus = ExtraBallBonus.getInstance();
        jackPotBonus = JackPotBonus.getInstance();
        dropTargetBonus = DropTargetBonus.getInstance();
        game = new Game(2, 0);

        numOfBumpers = 5;
        numOfTargets = 6;
        numOfDropTargets = 3;
        emptyTable = GameTable.getEmptyTable();
        noTargetTable = GameTable.getTableWithoutTargets(game, "noTargetTable", numOfBumpers, 0.5);
        fullTable = GameTable.getFullTable(game, "fullTable", numOfBumpers, 0.5,
                numOfDropTargets, numOfTargets);
        //set observers
        extraBallBonus.setObservers(game);
        jackPotBonus.setObservers(game);
        dropTargetBonus.setObservers(game);
    }

    /**
     * test that getEmptyTable() return an empty, non-playable table.
     */
    @Test
    public void getEmptyTable() {
        // already created in setUp
        assertNotNull(emptyTable);

    }

    /**
     * test that getTableWithoutTargets() return a playable Table with
     * bumpers but no targets.
     */
    @Test
    public void getTableWithoutTargets() {
        // already created in setUp
        assertNotNull(noTargetTable);
    }

    /**
     * test that getFullTable() return a playable Table with
     * bumpers and targets
     */
    @Test
    public void getFullTable() {
        // already created in setUp
        assertNotNull(fullTable);
    }

    /**
     * test that this actually return the name of the table
     */
    @Test
    public void getTableName() {
        // for empty table
        assertEquals("", emptyTable.getTableName());

        // for no target table
        assertEquals("noTargetTable", noTargetTable.getTableName());

        // for full table
        assertEquals("fullTable", fullTable.getTableName());
    }

    /**
     * test that this actually return the number of drop target created in the table
     */
    @Test
    public void getNumberOfDropTargets() {
        // for empty table
        assertEquals(0, emptyTable.getNumberOfDropTargets());

        // for no target table
        assertEquals(0, noTargetTable.getNumberOfDropTargets());

        // for full table
        assertEquals(numOfDropTargets, fullTable.getNumberOfDropTargets());
    }

    /**
     * test for the number of dropped dropTargets
     */
    @Test
    public void getCurrentlyDroppedDropTargets() {
        // empty table
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());

        // no target table
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());

        // full table, for this manually call hit() for targets
        // first for only one target that we know is a dropTarget
        fullTable.getTargets().get(0).hit();
        assertEquals(1, fullTable.getCurrentlyDroppedDropTargets());
        fullTable.getTargets().get(0).reset();
        // here should use method resetDropTarget from table to reset the counter of dropped
        // bu we dont want to test that here so the counter will remain with the 1
        assertEquals(1, fullTable.getCurrentlyDroppedDropTargets());
        // then for all the targets
        for (Target target : fullTable.getTargets()){
            target.hit();
        }
        assertEquals(1 + numOfDropTargets, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * test that getBumpers() actually return the {@link List} of {@link Bumper}s
     * with the corresponding number of bumpers
     */
    @Test
    public void getBumpers() {
        // empty table
        assertNull(emptyTable.getBumpers());

        // no target table
        assertNotNull(noTargetTable.getBumpers());
        assertEquals(numOfBumpers, noTargetTable.getBumpers().size());
        for (Bumper b : noTargetTable.getBumpers()){
            assertNotNull(b);
        }
        // same with full table
    }

    /**
     * test that getTargets() actually return the {@link List} of {@link Target}s
     */
    @Test
    public void getTargets() {
        // empty table
        assertNull(emptyTable.getBumpers());

        // same with no target table

        // full table
        assertNotNull(fullTable.getTargets());
        assertEquals(numOfTargets, fullTable.getTargets().size());
        for (Target t : fullTable.getTargets()){
            assertNotNull(t);
        }
    }

    /**
     * test that resetDropTargets() actually reset the state of all {@link Target}s
     */
    @Test
    public void resetDropTargets() {
        // empty table
        assertNull(emptyTable.getTargets());
        emptyTable.resetDropTargets();
        assertNull(emptyTable.getTargets());

        // no targets table, same result.
        // full table
        assertNotNull(fullTable.getTargets());
        // we are going to hit() all the targets.
        for (Target t: fullTable.getTargets()){
            t.hit();
        }
        // then reset the dropTargets
        fullTable.resetDropTargets();
        // the fisrt 'numberOfDropTargets' elements of the list are dropTargets
        List<Target> targets = fullTable.getTargets();
        int i = 0;
        while (i < numOfDropTargets){
            assertTrue(targets.get(i).isActive());
            i++;
        }
        while (i < numOfTargets){
            assertFalse(targets.get(i).isActive());
            i++;
        }
    }

    /**
     * test that this method actually upgrade all the bumpers
     */
    @Test
    public void upgradeAllBumpers() {
        // empty table
        assertNull(emptyTable.getBumpers());
        emptyTable.upgradeAllBumpers();
        assertNull(emptyTable.getBumpers());

        // no targets table
        // check that all bumper are not upgraded
        for (Bumper b: noTargetTable.getBumpers()){
            assertFalse(b.isUpgraded());
        }
        // then upgrade and check
        noTargetTable.upgradeAllBumpers();
        for (Bumper b: noTargetTable.getBumpers()){
            assertTrue(b.isUpgraded());
        }

        // full table
        // check that all bumper are not upgraded
        for (Bumper b: fullTable.getBumpers()){
            assertFalse(b.isUpgraded());
        }
        // then upgrade and check
        fullTable.upgradeAllBumpers();
        for (Bumper b: fullTable.getBumpers()){
            assertTrue(b.isUpgraded());
        }
    }

    /**
     * test that isPlayableTable() return True only when a table is actually playable, this means,
     * that the table has a valid name (not only ""), and also has at least a valid list of bumpers (not null)
     */
    @Test
    public void isPlayableTable() {
        // empty table
        assertEquals("", emptyTable.getTableName());
        assertNull(emptyTable.getBumpers());
        assertFalse(emptyTable.isPlayableTable());

        // no target table
        assertEquals("noTargetTable", noTargetTable.getTableName());
        assertNotNull(noTargetTable.getBumpers());
        assertTrue(noTargetTable.isPlayableTable());

        // full table
        assertEquals("fullTable", fullTable.getTableName());
        assertNotNull(noTargetTable.getBumpers());
        assertTrue(noTargetTable.isPlayableTable());
    }

    /**
     * accept a visit from Game doesn't do anything
     */
    @Test
    public void acceptFromGame() {
        int score = game.getScore();
        int balls = game.getBalls();
        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.acceptFromGame(game);
        noTargetTable.acceptFromGame(game);
        fullTable.acceptFromGame(game);

        assertEquals(score, game.getScore());
        assertEquals(balls, game.getBalls());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * accept a visit from Game doesn't do anything.
     */
    @Test
    public void acceptFromBumper() {
        Bumper bumper = new KickerBumper();
        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.acceptFromBumper(bumper);
        noTargetTable.acceptFromBumper(bumper);
        fullTable.acceptFromBumper(bumper);

        assertFalse(bumper.isUpgraded());
        assertEquals(5, bumper.remainingHitsToUpgrade());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * accept a visit fron target doesn't do anything
     */
    @Test
    public void acceptFromTarget() {
        Target target = new DropTarget();
        assertTrue(target.isActive());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.acceptFromTarget(target);
        noTargetTable.acceptFromTarget(target);
        fullTable.acceptFromTarget(target);

        assertTrue(target.isActive());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * accept a visit from bonus could trigger a
     * upgradeAllBumpers() if the bonus is a DropTargetBonus
     * and it was triggered.
     */
    @Test
    public void acceptFromBonus() {
        // to trigger the bonus we need al the DropTargets in the
        // table non-active, this means that the only Table that
        // do anything here is the full table.
        for (Target t: fullTable.getTargets()){
            t.hit();
        }
        for (Bumper b: fullTable.getBumpers()){
            assertFalse(b.isUpgraded());
        }
        for (Bumper b: noTargetTable.getBumpers()){
            assertFalse(b.isUpgraded());
        }
        assertNull(emptyTable.getBumpers());

        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(numOfDropTargets, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.acceptFromBonus(dropTargetBonus);
        noTargetTable.acceptFromBonus(dropTargetBonus);
        fullTable.acceptFromBonus(dropTargetBonus);

        for (Bumper b: fullTable.getBumpers()){
            assertTrue(b.isUpgraded());
        }
        for (Bumper b: noTargetTable.getBumpers()){
            assertTrue(b.isUpgraded());
        }
        assertNull(emptyTable.getBumpers());
    }

    /**
     * accept a visit from another table doesnt do anything.
     */
    @Test
    public void acceptFromTable() {
        Table table = GameTable.getFullTable(game, "table", 11, 0.7,
                13, 17);
        assertTrue(table.isPlayableTable());
        assertEquals("table", table.getTableName());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.acceptFromTable(table);
        noTargetTable.acceptFromTable(table);
        fullTable.acceptFromTable(table);

        assertTrue(table.isPlayableTable());
        assertEquals("table", table.getTableName());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * test that this method actually set the corresponding number of observers to Table
     */
    @Test
    public void setObservers() {
        assertEquals(1, ((GameTable) emptyTable).countObservers());
        assertEquals(1, ((GameTable) noTargetTable).countObservers());
        assertEquals(1, ((GameTable) fullTable).countObservers());
    }

    /**
     * visit a bonus doesnt do anything
     */
    @Test
    public void visitBonus() {
        Bonus bonus1 = DropTargetBonus.getInstance();
        Bonus bonus2 = ExtraBallBonus.getInstance();
        Bonus bonus3 = JackPotBonus.getInstance();

        int count1 = bonus1.timesTriggered();
        int count2 = bonus2.timesTriggered();
        int count3 = bonus3.timesTriggered();

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.visitBonus(bonus1);
        emptyTable.visitBonus(bonus2);
        emptyTable.visitBonus(bonus3);

        noTargetTable.visitBonus(bonus1);
        noTargetTable.visitBonus(bonus2);
        noTargetTable.visitBonus(bonus3);

        fullTable.visitBonus(bonus1);
        fullTable.visitBonus(bonus2);
        fullTable.visitBonus(bonus3);

        assertEquals(count1, bonus1.timesTriggered());
        assertEquals(count2, bonus2.timesTriggered());
        assertEquals(count3, bonus3.timesTriggered());
        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * visit a bumper doesn't d anything.
     */
    @Test
    public void visitBumper() {
        Bumper bumper1 = new KickerBumper();
        Bumper bumper2 = new PopBumper();

        assertFalse(bumper1.isUpgraded());
        assertEquals(5, bumper1.remainingHitsToUpgrade());
        assertFalse(bumper2.isUpgraded());
        assertEquals(3, bumper2.remainingHitsToUpgrade());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.visitBumper(bumper1);
        emptyTable.visitBumper(bumper2);

        noTargetTable.visitBumper(bumper1);
        noTargetTable.visitBumper(bumper2);

        fullTable.visitBumper(bumper1);
        fullTable.visitBumper(bumper2);

        assertFalse(bumper1.isUpgraded());
        assertEquals(5, bumper1.remainingHitsToUpgrade());
        assertFalse(bumper2.isUpgraded());
        assertEquals(3, bumper2.remainingHitsToUpgrade());

        assertFalse(emptyTable.isPlayableTable());
        assertTrue(noTargetTable.isPlayableTable());
        assertTrue(fullTable.isPlayableTable());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * Visit a target trigger an addition in the counter of
     * dropped DropTarget of the target is a DropTarget non-active
     */
    @Test
    public void visitTarget() {
        Target target1 = new DropTarget();
        Target target2 = new SpotTarget();

        // targets active
        assertTrue(target1.isActive());
        assertTrue(target2.isActive());

        // doesn't change the currentlyDroppedDropTarget
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.visitTarget(target1);
        emptyTable.visitTarget(target2);

        noTargetTable.visitTarget(target1);
        noTargetTable.visitTarget(target2);

        fullTable.visitTarget(target1);
        fullTable.visitTarget(target2);

        assertTrue(target1.isActive());
        assertTrue(target2.isActive());

        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        // targets non-active
        ((DropTarget) target1).setActive(false);
        ((SpotTarget) target2).setActive(false);
        assertFalse(target1.isActive());
        assertFalse(target2.isActive());

        // change the counter of dropped dropTarget for full table
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.visitTarget(target1);
        emptyTable.visitTarget(target2);

        noTargetTable.visitTarget(target1);
        noTargetTable.visitTarget(target2);

        fullTable.visitTarget(target1);
        fullTable.visitTarget(target2);

        assertFalse(target1.isActive());
        assertFalse(target2.isActive());

        assertEquals(1, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(1, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(1, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * visit a table doesn't change anything
     */
    @Test
    public void visitTable() {
        Table table = GameTable.getFullTable(game, "table", 3, 0.05,
                6, 7);

        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());

        emptyTable.visitTable(table);
        noTargetTable.visitTable(table);
        fullTable.visitTable(table);

        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertEquals(0, emptyTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, noTargetTable.getCurrentlyDroppedDropTargets());
        assertEquals(0, fullTable.getCurrentlyDroppedDropTargets());
    }

    /**
     * update, this method is the base of all the interactions,
     * so its tested in every interaction.
     * The only thing that this method do is to activate the visitor
     * pattern.
     */
    @Test
    public void update() {
        // tested in every interaction. if all the interactions works, then this works.
    }
}