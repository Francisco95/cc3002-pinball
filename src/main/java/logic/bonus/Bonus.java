package logic.bonus;

import interactions.AcceptObservation;
import interactions.ReceiveChanges;

/**
 * Interface that represents a bonus object. Also include the accept methods used for
 * the Visitor Pattern
 *
 * @author (template)Juan-Pablo Silva, (code)Francisco Muñoz
 *
 * @see ExtraBallBonus
 * @see JackPotBonus
 * @see DropTargetBonus
 */
public interface Bonus extends ReceiveChanges {
    /**
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
     *
     * @return value of bonus (integer)
     */
    int getBonusValue();

    /**
     * @return return true if the bonus is a bonus of balls
     */
    boolean isBonusOfBalls();

}
