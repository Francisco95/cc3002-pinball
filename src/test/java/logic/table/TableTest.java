package logic.table;

import controller.Game;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import logic.gameelements.bumper.Bumper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 24-05-18
 */
public class TableTest {
    private Table emptyTable, noTargetTable, fullTable;
    private ExtraBallBonus extraBallBonus;
    private JackPotBonus jackPotBonus;
    private DropTargetBonus dropTargetBonus;
    private Game game;
    private int numOfTargets, numOfBumpers, numOfDropTargets;

    @Before
    public void setUp() {
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
     * test different ways of initialize the Table, there are 3 options:
     * - empty table without name and lists of targets/bumpersa
     * - table without targets
     * - full table with name, targets and bumpers
     */
    @Test
    public void initializers(){
        // for the empty table
        // this is equal to get method isPlayableTable() as false
        assertFalse(emptyTable.isPlayableTable());

        // for the table without targets
        // this is equal to get method isPlayableTable() as true
        assertTrue(noTargetTable.isPlayableTable());

        // for the full table
        // this is equal to get method isPlayableTable() as true
        assertTrue(fullTable.isPlayableTable());
    }

    /**
     * test that when create every type of table automatically create a list of targets or not
     */
    @Test
    public void createTargets() {
        // for the empty table there are no targets created
        assertNull(emptyTable.getTargets());

        // same for the table without targets
        assertNull(noTargetTable.getTargets());

        // for the full table
        assertNotNull(fullTable.getTargets());
    }

    /**
     * test that when create every type of table automatically create a list of bumpers or not
     */
    @Test
    public void createBumpers() {
        // for the empty table there are no bumpers created
        assertNull(emptyTable.getBumpers());

        // for the table without targets sould be bumpers created
        assertNotNull(noTargetTable.getBumpers());

        // same for the full table
        assertNotNull(fullTable.getBumpers());
    }

    /**
     * test that this method actually add 1 to the counter of dropped dropTargets,
     * this method is used when visit a target that is a dropTarget not active.
     * Isolating the call to this method should work for every type of table.
     */
    @Test
    public void addDropedToCounter() {
        DropTarget dropTarget = new DropTarget();
        dropTarget.setActive(false);

        // for the empty table
        int initialCounter = emptyTable.getCurrentlyDroppedDropTargets();
        emptyTable.visitTarget(dropTarget);
        assertEquals(initialCounter + 1, emptyTable.getCurrentlyDroppedDropTargets());

        // for the no target table
        initialCounter = noTargetTable.getCurrentlyDroppedDropTargets();
        noTargetTable.visitTarget(dropTarget);
        assertEquals(initialCounter + 1, noTargetTable.getCurrentlyDroppedDropTargets());

        // for the full table
        initialCounter = fullTable.getCurrentlyDroppedDropTargets();
        fullTable.visitTarget(dropTarget);
        assertEquals(initialCounter + 1, fullTable.getCurrentlyDroppedDropTargets());
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
}