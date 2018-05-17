package main.java.logic.bonus;

import main.java.controller.Game;

/**
 * Interface that represents a bonus object.
 *
 * @author Juan-Pablo Silva
 * @see ExtraBallBonus
 * @see JackPotBonus
 * @see DropTargetBonus
 */
public interface Bonus {
    /**
     * Gets the number of times the bonus has been triggered.
     *
     * @return number of times the bonus has been triggered
     */
    int timesTriggered();

    /**
     * Trigger the specific action the bonus does and applies it to the {@link Game} object.
     *
     * @param game the game controller object
     */
    void trigger(Game game);
}
