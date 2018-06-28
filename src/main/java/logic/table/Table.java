package logic.table;

import controller.EventAcceptor;
import controller.EventVisitor;
import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;

import java.util.List;
import java.util.Observer;

/**
 * Interface that represents the basics of a table to be played on.
 * This interface its extended from {@link EventAcceptor}
 * and {@link EventVisitor} to generalize interactions between different objects.
 *
 * @author Juan-Pablo Silva, modified by Francisco Muñoz.
 */
public interface Table extends EventAcceptor, EventVisitor {
    /**
     * Gets the table name.
     *
     * @return the table's name
     */
    String getTableName();

    /**
     * Gets the number of {@link logic.gameelements.target.DropTarget} in the table.
     *
     * @return the number of DropTargets in the table
     */
    int getNumberOfDropTargets();

    /**
     * Gets the number of {@link logic.gameelements.target.DropTarget} that are currently dropped or inactive.
     *
     * @return the number of DropTargets that are currently inactive
     */
    int getCurrentlyDroppedDropTargets();

    /**
     * Gets the {@link List} of {@link Bumper}s in the table.
     *
     * @return the bumpers in the table
     */
    List<Bumper> getBumpers();

    /**
     * Gets the {@link List} of {@link Target}s in the table.
     *
     * @return the targets in the table
     */
    List<Target> getTargets();

    /**
     * Resets all {@link logic.gameelements.target.DropTarget} in the table. Make them active.
     */
    void resetDropTargets();

    /**
     * Upgrade all {@link Bumper}s in the table.
     */
    void upgradeAllBumpers();

    /**
     * Gets whether the table is playable or not.
     *
     * @return true if the table is playable, false otherwise
     */
    boolean isPlayableTable();

    /**
     * Set (or reset) all possible Observers Instances of every {@link logic.gameelements}.
     * This include an Instance of Game and instances of all the three types
     * of game bonuses, which objects are obtained through getInstance()
     * of Singleton Pattern.
     *
     * This method should be ran every time an Instance of Table is created and every time
     * that you may want to change the Game instance by a new one.
     *
     * @param game Instance of Game
     */
    void setGameElementsObservers(Game game);

}
