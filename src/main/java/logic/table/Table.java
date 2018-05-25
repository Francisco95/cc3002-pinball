package logic.table;

import controller.Game;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;

import java.util.List;

/**
 * Interface that represents the basics of a table to be played on.
 *
 * @author Juan-Pablo Silva
 */
public interface Table {
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
     * set the {@link List} of {@link logic.gameelements.target.Target} only if
     * this list wasn't defined before. This force to use this method only once
     * per new board.
     *
     * @param jackPotBonus                  instance of {@link JackPotBonus}
     * @param extraBallBonus                instance of {@link ExtraBallBonus}
     * @param dropTargetBonus               instance of {@link DropTargetBonus}
     * @param game                          instance of {@link Game}
     */
    void setTargets(JackPotBonus jackPotBonus, ExtraBallBonus extraBallBonus,
                    DropTargetBonus dropTargetBonus, Game game);

    /**
     * Gets the {@link List} of {@link Target}s in the table.
     *
     * @return the targets in the table
     */
    List<Target> getTargets();

    /**
     * set the {@link List} of {@link logic.gameelements.bumper.Bumper} only if
     * this list wasn't defined before. This force to use this method only once
     * per new board.
     *
     * @param extraBallBonus        instance of {@link ExtraBallBonus}
     * @param game                  instance of {@link Game}
     */
    void setBumpers(ExtraBallBonus extraBallBonus, Game game);

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
}
