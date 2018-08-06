package logic.gameelements;

/**
 * Interface that represents a hittable object.
 *
 * <p>Objects that are game elements should implement this interface.</p>
 *
 * @author (template)Juan-Pablo Silva, (code)Francisco Muñoz
 * @see logic.gameelements.bumper.Bumper
 * @see logic.gameelements.target.Target
 */
public interface Hittable {
    /**
     * Defines that an object have been hit.
     * Implementations should consider the events that a hit to an object can trigger.
     * If implemented as Observer Pattern, here should be the notifyObservers() because of
     * change on state
     */
    int hit();

    /**
     * Defines that a hittable object has to have a score when it is hit.
     *
     * @return the current score of the object when hit
     */
    int getScore();

    /**
     * return true if a bonus is triggered by this game element, false if not
     */
    boolean bonusTriggered();
}
