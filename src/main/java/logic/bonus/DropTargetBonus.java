package logic.bonus;

import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import logic.table.GameTable;
import logic.table.Table;

/**
 * Class that define drop target bonus.
 * use Singleton Pattern to guarantee only one initialize per game.
 * Use Observable Pattern to receive the message from Table to trigger when al dropTarget are off,
 * Also use Observable Pattern mixed with Visit Pattern to solve the problem of
 * bonus that give points or balls.
 *
 * @see Bonus
 * @see controller.Game
 * @see GameTable
 * @see logic.gameelements.bumper.Bumper
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTargetBonus extends AbstractBonus{

    /**
     * count the number of drop targets dropped
     */
    private int counterOfDroppedDropTargets;

    /**
     * the instance of DropTargetBonus, this is part of Singleton Pattern
     */
    private static DropTargetBonus instance = null;

    private DropTargetBonus(){
        super(1000000, false);
        this.counterOfDroppedDropTargets = 0;
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of DropTargetBonus only if there is no previous instance.
     *
     * @return an instance of DropTargetBonus
     */
    public static DropTargetBonus getInstance(){
        if (instance == null){
            instance = new DropTargetBonus();
        }
        return instance;
    }

    private void visitADropTarget(DropTarget dropTarget){
        if (!dropTarget.isActive()){
            counterOfDroppedDropTargets++;
        }
        if (counterOfDroppedDropTargets == countObservers()){
            trigger();
        }
    }

    @Override
    public void visitBumper(Bumper bumper) {
        // do nothing
    }

    @Override
    public void visitTarget(Target target) {
        if (target.isADropTarget()){
            visitADropTarget((DropTarget) target);
        }
    }

    @Override
    public void visitTable(Table table) {
        if (table.getCurrentlyDroppedDropTargets() == table.getNumberOfDropTargets()){
            trigger();
        }
    }

    @Override
    public void acceptFromBumper(Bumper bumper) {
        // do nothing
    }
}
