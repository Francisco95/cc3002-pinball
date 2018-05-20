package logic.bonus;

import controller.EventVisitor;
import controller.Game;
import logic.gameelements.Hittable;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define jackpot bonus.
 * use Singleton Pattern to guarantee only one initialize per game.
 * Use Observable Pattern to receive the message from spotTarget to trigger,
 * Also use Observable Pattern mixed with Visit Pattern to solve the problem of
 * bonus that give points or balls.
 *
 * @see Bonus
 * @see Game
 * @see logic.gameelements.target.SpotTarget
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class JackPotBonus extends Observable implements Observer, Bonus {
    /**
     * count the number of triggers done so far now.
     */
    private int counterTriggers;

    /**
     * the points given by a JackPot Bonus
     */
    private final int jackPotPoints = 100000;
    /**
     * the instance of JackpotBonus, this is part of Singleton Pattern
     */
    private static JackPotBonus instance = null;

    /**
     * tell if this bonus give points or not (the other case is when give extra balls)
     */
    private boolean bonusOfPoints = true;

    /**
     * constructor declared as private for Singleton Pattern
     */
    private JackPotBonus() {
        this.counterTriggers = 0;
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of JackPotBonus only if there is no previous instance.
     *
     * @return an instance of JackPotBonus
     */
    public static JackPotBonus getInstance(){
        if (instance == null){
            instance = new JackPotBonus();
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
        return bonusOfPoints;
    }

    @Override
    public int getBonusValue() {
        return jackPotPoints;
    }

    /**
     * Method update from Observer pattern.
     * there is no need for know what the message is. Since the state
     * off {@link logic.gameelements.target.SpotTarget} only change
     * from active to not active once per game.
     * this only can receive a Hittable (SpotTarget) instance.
     * @param o the observable, in this case, is a SpotTarget
     * @param arg the message that send, in this case isn't necessary.
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

    @Override
    public void accept(EventVisitor v) {
        v.visitBonusOfPoints(jackPotPoints);
    }
}
