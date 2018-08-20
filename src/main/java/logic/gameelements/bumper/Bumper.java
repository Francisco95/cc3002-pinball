package logic.gameelements.bumper;

import interactions.AcceptObservation;
import logic.gameelements.Hittable;

/**
 * Interface that represents operations related to a bumper behavior.
 *
 * @author Juan-Pablo Silva
 * @see Hittable
 */
public interface Bumper extends Hittable, AcceptObservation {

    /**
     * Gets the remaining hits the bumper has to receive to upgrade.
     *
     * @return the remaining hits to upgrade the bumper
     */
    int remainingHitsToUpgrade();

    /**
     * Gets whether the bumper is currently upgraded or not.
     *
     * @return true if the bumper is upgraded, false otherwise
     */
    boolean isUpgraded();

    /**
     * Upgrades a bumper making {@link #isUpgraded()} return true.
     */
    void upgrade();

    /**
     * Downgrades a bumper making {@link #isUpgraded()} return false.
     */
    void downgrade();

    /**
     * set the bonusIsTriggered variable to true if the bonus is triggered
     */
    void bonusCouldBeTriggered();

}
