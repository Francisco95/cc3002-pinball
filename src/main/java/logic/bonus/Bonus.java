package logic.bonus;

import controller.EventAcceptor;
import controller.EventVisitor;
import controller.Game;

/**
 * Interface that represents a bonus object. Also include the accept method used for
 * the Visitor Pattern
 *
 * @author Juan-Pablo Silva
 * @see ExtraBallBonus
 * @see JackPotBonus
 * @see DropTargetBonus
 */
public interface Bonus extends EventAcceptor, EventVisitor {
    /**
     * Gets the number of times the bonus has been triggered.
     *
     * @return number of times the bonus has been triggered
     */
    int timesTriggered();

    /**
     * Trigger the specific action the bonus does.
     *
     */
    void trigger();

    /**
     * gets the value of the bonus.
     * @return value of bonus (integer)
     */
    int getBonusValue();

    boolean isBonusOfBalls();

}
