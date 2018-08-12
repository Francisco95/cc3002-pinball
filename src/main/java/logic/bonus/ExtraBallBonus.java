package logic.bonus;

import logic.gameelements.GameElementType;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;

/**
 * Class that define extra ball bonus.
 * use Singleton Pattern to guarantee only one initialize per game.
 * Use Observable Pattern to receive the message from DropTarget/Bumper to trigger,
 * Also use Observable Pattern mixed with Visit Pattern to solve the problem of
 * bonus that give points or balls.
 *
 * @see logic.bonus.Bonus
 * @see controller.Game
 * @see logic.gameelements.target.DropTarget
 * @see logic.gameelements.bumper.Bumper
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class ExtraBallBonus extends AbstractBonus {

    /**
     * the instance of JackpotBonus, this is part of Singleton Pattern
     */
    private static ExtraBallBonus instance = null;


    /**
     * constructor declared as private for Singleton Pattern
     */
    private ExtraBallBonus() {
        super(1, true);
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of ExtraBallBonus only if there is no previous instance.
     * @return an instance of ExtraBallBonus
     */
    public static ExtraBallBonus getInstance(){
        if (instance == null){
            instance = new ExtraBallBonus();
        }
        return instance;
    }

    public void visitADropTarget(DropTarget dropTarget){
        if (dropTarget.bonusTriggered()){
            trigger();
        }
    }

    @Override
    public void hitBumper(Bumper bumper) {
        if (bumper.bonusTriggered()){
            trigger();
        }
    }

    @Override
    public void hitTarget(Target target) {
        if (target.getType().equals(GameElementType.DROP_TARGET)){
            visitADropTarget((DropTarget) target);
        }
    }
}
