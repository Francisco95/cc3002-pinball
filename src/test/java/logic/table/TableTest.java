package logic.table;

import controller.Game;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.target.Target;
import logic.gameelements.bumper.Bumper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 24-05-18
 */
public class TableTest {
    private Board table;
    private ExtraBallBonus extraBallBonus;
    private JackPotBonus jackPotBonus;
    private DropTargetBonus dropTargetBonus;
    private Game game;

    @Before
    public void setUp() throws Exception {
        table = new Board();
        extraBallBonus = ExtraBallBonus.getInstance();
        jackPotBonus = JackPotBonus.getInstance();
        dropTargetBonus = new DropTargetBonus(); // not implemented yet
        game = new Game(2, 0);

        //set observers
        extraBallBonus.setObservers(game);
        jackPotBonus.setObservers(game);
        //dropTargetBonus.setObservers(game); // not implemented yet
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

        // if we create a table without targets defining a name
        table = new Board("tableWithoutTargets", 5, 0.5);
        table.setBumpers(extraBallBonus, game);
        assertEquals("tableWithoutTargets", table.getTableName());

        // finally try with a table with targets and bumpers
        table  = new Board("tableFull", 5, 0.5,
                2, 4);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertEquals("tableFull", table.getTableName());

    }

    /**
     * test that this actually return the number of drop target created in the table
     */
    @Test
    public void getNumberOfDropTargets() {
        // originally we have an empty table with 0 dropTargets
        assertEquals(0, table.getNumberOfDropTargets());

        // try with a table created with targets
        int numDropTargets = 2;
        table  = new Board("tableFull", 5, 0.5,
                numDropTargets, 4);
        // its necessary to set the bumper and targets every time that a new table is created
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertEquals(numDropTargets, table.getNumberOfDropTargets());
    }

    /**
     * test for the number of dropped dropTargets, for this we create a 'playable' table,
     * set some targets and then call hit to the targets. this will mean that the score on
     * game will change and also the counter on the bonus
     */
    @Test
    public void getCurrentlyDroppedDropTargets() {
        int numDropTargets = 4;
        table  = new Board("tableFull", 5, 0.5,
                numDropTargets, numDropTargets + 2);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        // the score should be += 2 * jackpotBonus + 100 * numOfDropTargets + dropTargetBonus
        // but the dropTargetBonus interaction is not implemented yet
        int score = game.getScore() + jackPotBonus.getBonusValue() * 2 +
                100 * numDropTargets;
        int balls = game.getBalls();
        int cJPB = jackPotBonus.timesTriggered() + 2;
        int cEBB = extraBallBonus.timesTriggered();
        int cDTB = dropTargetBonus.timesTriggered() + 1;

        //at first the game score is 0
        assertEquals(0, game.getScore());
        for (Target t : table.getTargets()){
            t.hit();
        }
        // all the bumpers should be upgraded, this is not implemented yet
        // test the other results
        assertEquals(cJPB, jackPotBonus.timesTriggered());
        assertTrue(cEBB <= extraBallBonus.timesTriggered());
        assertTrue(balls <= game.getBalls());
//        assertEquals(cDTB, dropTargetBonus.timesTriggered());
        assertEquals(score, game.getScore());

    }

    /**
     * test that getBumpers() actually return the {@link List} of {@link Bumper}s
     */
    @Test
    public void getBumpers() {
        // fo the empty table this will return a null list
        assertNull(table.getBumpers());

        int numberOfBumpers = 5;
        // for a table without targets and table full will return the actual list
        table  = new Board("tableFull", numberOfBumpers, 0.5);
        table.setBumpers(extraBallBonus, game);
        assertNotNull(table.getBumpers());
        assertEquals(numberOfBumpers, table.getBumpers().size());
        for (Bumper b : table.getBumpers()){
            assertNotNull(b);
        }

        table  = new Board("tableFull", numberOfBumpers, 0.5,
                4, 6);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertNotNull(table.getBumpers());
        assertEquals(numberOfBumpers, table.getBumpers().size());
        for (Bumper b : table.getBumpers()){
            assertNotNull(b);
        }
    }

    /**
     * test that getTargets() actually return the {@link List} of {@link Target}s
     */
    @Test
    public void getTargets() {
        // fo the empty table this will return a null list
        assertNull(table.getTargets());

        // for a table without targets will return also a null list
        table  = new Board("tableFull", 5, 0.5);
        table.setBumpers(extraBallBonus, game);
        assertNull(table.getTargets());

        // for a table full will return the actual list
        int numberOfTargets= 5;
        table  = new Board("tableFull", 5, 0.5,
                numberOfTargets - 2, numberOfTargets);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertNotNull(table.getTargets());
        assertEquals(numberOfTargets, table.getTargets().size());
        for (Target t : table.getTargets()){
            assertNotNull(t);
        }
    }

    /**
     * test that resetDropTargets() actually reset the state of all {@link Target}s
     */
    @Test
    public void resetDropTargets() {
        // in an empty table this will still run but doesn't change anything
        assertNull(table.getTargets());
        table.resetDropTargets();
        assertNull(table.getTargets());
        // this is the same for a table without targets


        // for a full table with targets and bumpers
        int numberOfDropTargets = 6;
        int numberOfTargets = numberOfDropTargets + 3;
        // create a full table with targets
        table  = new Board("tableFull", 5, 0.5,
                numberOfDropTargets, numberOfTargets);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertNotNull(table.getTargets());
        // we are going to hit() all the targets.
        for (Target t: table.getTargets()){
            t.hit();
        }
        // then reset the dropTargets
        table.resetDropTargets();

        // the fisrt 'numberOfDropTargets' elements of the list are dropTargets
        List<Target> targets = table.getTargets();
        int i = 0;
        while (i < numberOfDropTargets){
            assertTrue(targets.get(i).isActive());
            i++;
        }
        while (i < numberOfTargets){
            assertFalse(targets.get(i).isActive());
            i++;
        }
    }

    /**
     * test that this method actually upgrade all the bumpers
     */
    @Test
    public void upgradeAllBumpers() {
        // in an empty table this will still run but doesn't change anything
        assertNull(table.getBumpers());
        table.upgradeAllBumpers();
        assertNull(table.getBumpers());

        // for a table without targets we get:
        table  = new Board("tableWithoutTargets", 5, 0.5);
        table.setBumpers(extraBallBonus, game);
        // check that all bumper are not upgraded
        for (Bumper b: table.getBumpers()){
            assertFalse(b.isUpgraded());
        }
        // the upgrade and check
        table.upgradeAllBumpers();
        for (Bumper b: table.getBumpers()){
            assertTrue(b.isUpgraded());
        }
        // this will be the same result for a table with targets and bumpers
    }

    /**
     * test that isPlayableTable() return True only when a table is actually playable, this means,
     * that the table has a valid name (not only ""), and also has at least a valid list of bumpers (not null)
     */
    @Test
    public void isPlayableTable() {
        // with an empty table this will be false because the name is "" and the bumper list is null
        assertEquals("", table.getTableName());
        assertNull(table.getBumpers());
        assertFalse(table.isPlayableTable());
        // also the targets are null
        assertNull(table.getTargets());

        // with a table without target it will be a playable table
        table  = new Board("tableWithoutTargets", 5, 0.5);
        table.setBumpers(extraBallBonus, game);
        assertEquals("tableWithoutTargets", table.getTableName());
        assertNotNull(table.getBumpers());
        assertNull(table.getTargets());
        assertTrue(table.isPlayableTable());

        // for a full table we wet true from isPlayableTable()
        table  = new Board("tableFull", 5, 0.5,
                4, 4);
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertEquals("tableFull", table.getTableName());
        assertNotNull(table.getBumpers());
        assertNotNull(table.getTargets());
        assertTrue(table.isPlayableTable());

        // one important thing is that for an empty table we can generate
        // the lists of bumpers and targets as void but the name cannot
        // be changed after the instance was created so the table will still
        // be not playable. Name marks the difference.
        table = new Board();
        table.setBumpers(extraBallBonus, game);
        table.setTargets(jackPotBonus, extraBallBonus, dropTargetBonus, game);
        assertEquals("", table.getTableName());
        assertNotNull(table.getBumpers());
        assertNotNull(table.getTargets());
        assertFalse(table.isPlayableTable());
    }
}