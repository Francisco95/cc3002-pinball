package logic.gameelements.target;

import java.util.Random;

/**
 * class that define the behavior of DropTarget.
 * Use of Singleton Pattern to guarantee only 1 instance per game.
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
     * the instance of DropTarget, this is part of Singleton Pattern
     */
    private static DropTarget instance = null;

    /**
     * constructor declared as private for singleton pattern
     * by default, set active to true and create instance of Random()
     */
    private DropTarget() {
        super(true, 100, new Random());
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of DropTarget only if there is no previous instance.
     * @return instance of SpotTarget
     */
    public static DropTarget getInstance(){
        if (instance == null){
            instance = new DropTarget();
        }
        return instance;
    }

    /**
     * for this case there is a probability of 3/10 of trigger an ExtraBallBonus when hit() is called.
     * @return return True if the probability is satisfied.
     */
    @Override
    public boolean bonusTriggered() {
        setSeedToRandomProb();
        return getRandomProb().nextInt(10) < 3;
    }
}
