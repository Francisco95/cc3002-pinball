package logic.gameelements.target;

import logic.gameelements.GameElementType;

import java.util.Random;

/**
 * class that define the behavior of DropTarget.
 * Use of Observer Pattern to notify observers that win X points (Game)
 * or to notify a trigger of bonus (ExtraBallBonus).
 * All of this behavior is defined in {@link logic.gameelements.target.AbstractTarget}.
 *
 * @see logic.gameelements.target.Target
 * @see controller.Game
 * @see logic.bonus.DropTargetBonus
 * @see logic.bonus.ExtraBallBonus
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTarget extends AbstractTarget {


    /**
     * constructor declared as private for singleton pattern
     * by default, set active to true and create instance of Random()
     */
    public DropTarget() {
        super(true, 100, new Random(), GameElementType.DROP_TARGET);
    }

    /**
     * for this case there is a probability of 3/10 of trigger an ExtraBallBonus when hit() is called.
     */
    @Override
    public void setBonusIsTriggered() {
        setSeedToRandomProb();
        bonusIsTriggered = getRandomProb().nextInt(10) < 3;
    }
}
