package logic.gameelements.target;

import controller.Game;
import logic.bonus.Bonus;
import logic.gameelements.GameElementType;
import logic.gameelements.bumper.Bumper;

/**
 * Class that define behavior of a spotTarget.
 * Use Observer Pattern to notify observer that win X points(Game) or
 * to notify a change in active (JackPotBonus).
 * @see controller.Game
 * @see logic.bonus.JackPotBonus
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class SpotTarget extends AbstractTarget{

    public SpotTarget() {
        super(true, 0, null, GameElementType.SPOT_TARGET);
    }

    /**
     * in this case is always true and trigger a JackPotBonus
     * @return always True; always trigger a JackPotBonus
     */
    @Override
    public boolean bonusTriggered() {
        return true;
    }
}
