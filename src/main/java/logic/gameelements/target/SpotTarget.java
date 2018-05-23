package logic.gameelements.target;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define behavior of a spotTarget.
 * Use singleton pattern to guarantee only one initialization per game.
 * Use Observer Pattern to notify observer that win X points(Game) or
 * to notify a change in active (JackPotBonus).
 * @see controller.Game
 * @see logic.bonus.JackPotBonus
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class SpotTarget extends Observable implements Target{
    /**
     * the current state of the spotTarget, could be active (True) or inactive (False)
     */
    private boolean active;
    /**
     * the number of points given by the spotTarget
     */
    private final int pointsGiven = 0;
    /**
     * the instance of SpotTarget, this is part of Singleton Pattern
     */
    private static SpotTarget instance = null;

    /**
     * constructor declared as private for Singleton Pattern
     * by default set active to True.
     */
    private SpotTarget() {
        active = true;
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of SpotTarget only if there is no previous instance.
     * @return instance of SpotTarget
     */
    public static SpotTarget getInstance(){
        if (instance == null){
            instance = new SpotTarget();
        }
        return instance;
    }

    /**
     * as part of Observer Pattern, this set the new observers that observe this observable.
     * @param observers instances of observers
     */
    public void setObservers(Observer...observers){
        for (Observer o : observers)
            addObserver(o);
    }

    /**
     * set the Active parameter to 'value'
     * @param value the new value of 'active', could be true or false
     */
    public void setActive(boolean value){
        this.active = value;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * in this case reset will change active to true and clear all the observer
     */
    @Override
    public void reset() {
        setActive(true);
        deleteObservers();
    }

    @Override
    public void hit() {
        if (isActive()) {
            setActive(false);
            setChanged();
            notifyObservers(pointsGiven);
        }
    }

    @Override
    public int getScore() {
        return pointsGiven;
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
