package logic.gameelements.target;

import interactions.AcceptObservation;
import logic.gameelements.Hittable;

import java.util.Observer;

/**
 * Interface that represents operations related to a target behavior.
 *
 * @author Juan-Pablo Silva
 * @see Hittable
 */
public interface Target extends Hittable, AcceptObservation {
    /**
     * Gets whether the target is currently active or not.
     *
     * @return true if the target is active, false otherwise
     */
    boolean isActive();

    /**
     * set the Active parameter to 'value'
     * @param value the new value of 'active', could be true or false
     */
    void setActive(boolean value);

    /**
     * Resets the state of a target making it active again.
     */
    void reset();

    /**
     * as part of Observer Pattern, this set the new observers that observe this observable.
     * @param observers instances of observers
     */
    void setObservers(Observer... observers);

    boolean isADropTarget();
}
