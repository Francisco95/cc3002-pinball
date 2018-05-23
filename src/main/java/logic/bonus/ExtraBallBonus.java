package logic.bonus;

import controller.EventVisitor;
import logic.gameelements.Hittable;

import java.util.Observable;
import java.util.Observer;

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
public class ExtraBallBonus extends Observable implements Observer, Bonus {

    /**
     * count the number of triggers done so far now.
     */
    private int counterTriggers;

    /**
     * value of the bonus, in this case is 1 extra ball
     */
    private final int bonusValue = 1;
    /**
     * the instance of JackpotBonus, this is part of Singleton Pattern
     */
    private static ExtraBallBonus instance = null;

    /**
     * tell if this bonus give points or not (the other case is when give extra balls)
     */
    private boolean bonusOfPoints = false;

    /**
     * constructor declared as private for Singleton Pattern
     */
    private ExtraBallBonus() {
        this.counterTriggers = 0;
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

    public void setCounterTriggers(int value){
        this.counterTriggers = value;
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
        return false;
    }

    @Override
    public int getBonusValue() {
        return bonusValue;
    }

    @Override
    public void accept(EventVisitor v) {
        v.visitBonusOfBalls(bonusValue);
    }

    /**
     * Method update from Observer pattern.
     * there is no need for know what the message is.
     * In this case receive a Hittable object which could be a DropTarget
     * or a type of Bumper, then check method bonusTriggered() to know if this
     * bonus should be triggered or not.
     * this only can receive a Hittable class.
     * @param o Observable object, should be a Hittable instance
     * @param arg the message
     * @see logic.gameelements.Hittable
     */
    @Override
    public void update(Observable o, Object arg) {
        try {
            if (((Hittable) o).bonusTriggered()) {
                trigger();
            }
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
