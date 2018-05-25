package logic.table;

import controller.Game;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 24-05-18
 */
public class TableTest {
    Board table;
    ExtraBallBonus extraBallBonus;
    JackPotBonus jackPotBonus;
    DropTargetBonus dropTargetBonus;
    Game game;

    @Before
    public void setUp() throws Exception {
        table = new Board();
        extraBallBonus = ExtraBallBonus.getInstance();
        jackPotBonus = JackPotBonus.getInstance();
        dropTargetBonus = new DropTargetBonus(); // not implemented yet
        game = new Game(2, 0);
    }

    /**
     * test different ways of initialize the Table, there are 3 options:
     * - empty table with no name or lists of targets/bumpersa
     * - table without targets
     * - full table with name, targets and bumpers
     */
    @Test
    public void initializers(){
        // alreade initialized table as empty, this is not valid to start a game.
        assertEquals("", table.getTableName());
        assertNull(table.getBumpers());
        assertNull(table.getTargets());
        // this is equal to get method isPlayableTable() as false
        assertFalse(table.isPlayableTable());

        // now try the initialization without targets
        table = new Board("tableName", 5, 0.5);
        table.setBumpers(extraBallBonus, game);
        assertEquals("tableName", table.getTableName());
        assertNotNull(table.getBumpers());
        assertNull(table.getTargets());
        // this is equal to get method isPlayableTable() as true
        assertTrue(table.isPlayableTable());

        //  now try the initialization of full table
        table  = new Board("tableName", 5, 0.5,
                2, 4);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertEquals("tableName", table.getTableName());
        assertNotNull(table.getBumpers());
        assertNotNull(table.getTargets());
        // this is equal to get method isPlayableTable() as true
        assertTrue(table.isPlayableTable());
    }

    /**
     * test that set target actually set the expected number of targets,
     * and even if the table was initialized empty, can run this and create an empty list
     */
    @Test
    public void setTargets() {
        // table is already empty
        assertEquals("", table.getTableName());
        assertNull(table.getBumpers());
        assertNull(table.getTargets());
        // set the targets
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        // now se that the targets are 0
        assertEquals(0, table.getTargets().size());

        // lets try for a table initialized correctly
        table  = new Board("tableName", 5, 0.5,
                2, 4);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        // this will lead to a list of targets of size 4
        assertEquals(4, table.getTargets().size());

        // and even with this the table still is not 'playable'
        assertFalse(table.isPlayableTable());

        // its necessary to set the bumpers.
        table.setBumpers(extraBallBonus, game);

        // to get a 'playable' table
        assertTrue(table.isPlayableTable());
    }

    /**
     * test that set bumpers actually set the expected number of bumpers,
     * and even if the table was initialized empty, can run this and create an empty list.
     */
    @Test
    public void setBumpers() {
        // table is already empty
        assertEquals("", table.getTableName());
        assertNull(table.getBumpers());
        assertNull(table.getTargets());
        // set the bumpers
        table.setBumpers(extraBallBonus, game);
        // now se that the targets are 0
        assertEquals(0, table.getBumpers().size());

        // lets try for a table initialized correctly
        table  = new Board("tableName", 5, 0.5,
                2, 4);
        table.setBumpers(extraBallBonus, game);
        // this will lead to a list of targets of size 4
        assertEquals(5, table.getBumpers().size());
    }

    /**
     * test that this method actually add 1 to the counter of dropped dropTargets
     */
    @Test
    public void addDropedToCounter() {
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        table.addDropedToCounter();
        assertEquals(1, table.getCurrentlyDroppedDropTargets());
    }

    /**
     * test that setCounterOfDropped actually set the variable counterOfDropped to the value,
     * this variable is returned by the getCurrentlyDroppedDropTargets but if the
     * list of targets is not null, the method getCurrentlyDroppedTargets will return
     * allways the number of dropped dropTarget, no matter what value we set this before
     */
    @Test
    public void setCounterOfDropped() {
        assertNull(table.getTargets());
        int original = table.getCurrentlyDroppedDropTargets();
        table.setCounterOfDropped(-112);
        assertNotEquals(original, table.getCurrentlyDroppedDropTargets());
        assertEquals(-112, table.getCurrentlyDroppedDropTargets());

        // now try with a valid 'playable' table
        table  = new Board("tableName", 5, 0.5,
                2, 4);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertNotNull(table.getTargets());
        original = table.getCurrentlyDroppedDropTargets();
        table.setCounterOfDropped(-112);
        assertEquals(original, table.getCurrentlyDroppedDropTargets());

    }

    /**
     * test that this actually return the name of the table
     */
    @Test
    public void getTableName() {
        // at first the empty table has name ""
        assertEquals("", table.getTableName());

        // if we create a table with without targets

    }

    @Test
    public void getNumberOfDropTargets() {
    }

    @Test
    public void getCurrentlyDroppedDropTargets() {
    }

    @Test
    public void getBumpers() {
    }

    @Test
    public void getTargets() {
    }

    @Test
    public void resetDropTargets() {
    }

    @Test
    public void upgradeAllBumpers() {
    }

    @Test
    public void isPlayableTable() {
    }
}