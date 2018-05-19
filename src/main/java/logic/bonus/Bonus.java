package main.java.logic.bonus;

import main.java.controller.EventVisitor;
import main.java.controller.Game;

/**
 * Interface that represents a bonus object. Also include the accept method used for
 * the Visitor Pattern
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
     * Trigger the specific action the bonus does.
     *
     */
    void trigger();

    /**
     * allow to differentiate between a bonus of points and a bonus of balls
     */
    boolean isBonusOfPoints();

    /**
     * gets the value of the bonus.
     * @return value of bonus (integer)
     */
    int getBonusValue();

    /**
     * this decide to accept the event visitor acording to the Visitor Pattern
     * @param v an instance of a Visitor.
     */
    void accept(EventVisitor v);
}
