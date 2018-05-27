package logic.bonus;

import controller.EventVisitor;
import logic.gameelements.target.DropTarget;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define drop target bonus.
 * use Singleton Pattern to guarantee only one initialize per game.
 * Use Observable Pattern to receive the message from Table to trigger when al dropTarget are off,
 * Also use Observable Pattern mixed with Visit Pattern to solve the problem of
 * bonus that give points or balls.
 *
 * @see Bonus
 * @see controller.Game
 * @see logic.table.Board
 * @see logic.gameelements.bumper.Bumper
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTargetBonus extends Observable implements Observer, Bonus {

    /**
     * count the number of triggers done so far now.
     */
    private int counterTriggers;

    /**
     * count the number of drop targets dropped
     */
    private int counterOfDroppedDropTargets;

    /**
     * value of the bonus, in this case is 1000000 points
     */
    private final int bonusValue = 1000000;
    /**
     * the instance of DropTargetBonus, this is part of Singleton Pattern
     */
    private static DropTargetBonus instance = null;

    private DropTargetBonus(){
        this.counterTriggers = 0;
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

    /**
     * as part of Observer Pattern, this set the new observers that observe
     * this observable.
     *
     * @param observers instances of observers
     */
    public void setObservers(Observer...observers){
        for (Observer o : observers)
            addObserver(o);
    }

    /**
     * set the counter of triggers, this could be used to reset the Game but
     * it was created for testing purpose.
     *
     * @param value the new value to set.
     */
    public void setCounterTriggers(int value){
        this.counterTriggers = value;
    }

    private void upgradeCounterOfDroppedDropTargets(DropTarget dropTarget){
        if (!dropTarget.isActive())
            counterOfDroppedDropTargets++;
    }

    @Override
    public int timesTriggered() {
        return counterTriggers;
    }

    @Override
    public void trigger() {
        counterTriggers++;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean isBonusOfPoints() {
        return true;
    }

    @Override
    public int getBonusValue() {
        return bonusValue;
    }

    /**
     * for now there are only one type of observable and it
     * is a {@link logic.gameelements.target.DropTarget},
     * if another observable is set, then te code will fail.
     * receive a message of change from this observer will mean that it was hit()
     * and then changed to non-active, then add this to a counter.
     * When this counter reach the number of observables then trigger the bonus
     *
     * @param o the observable
     * @param arg the message from the observable
     */
    @Override
    public void update(Observable o, Object arg) {
        try{
            upgradeCounterOfDroppedDropTargets((DropTarget)o);
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
        finally {
            if (counterOfDroppedDropTargets == countObservers()){
                trigger();
            }
        }
    }

    @Override
    public void accept(EventVisitor v) {
        v.visitBonusOfPoints(bonusValue);
    }
}
