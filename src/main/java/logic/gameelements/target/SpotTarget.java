package main.java.logic.gameelements.target;

import main.java.controller.EventVisitor;
import main.java.controller.Game;
import main.java.logic.EventAcceptor;
import main.java.logic.bonus.JackPotBonus;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define behavior of a spotTarget.
 * Use singleton pattern to guarantee only one initialization per game.
 * Use Observer Pattern to notify observer that win X points.
 * @see Target
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class SpotTarget extends Observable implements EventAcceptor, Target{
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

    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * set variable 'active' to True
     */
    public void setActiveTrue(){
        this.active = true;
    }

    /**
     * set variable 'active' to False
     */
    public void setActiveFalse(){
        this.active = false;
    }

    /**
     * in this case reset will change active to true and clear all the observer
     */
    @Override
    public void reset() {
        setActiveTrue();
        deleteObservers();
    }

    @Override
    public int hit() {
        if (isActive()) {
            setActiveFalse();
            setChanged();
            notifyObservers();
            return pointsGiven;
        }
        return 0;
    }

    @Override
    public int getScore() {
        return pointsGiven;
    }

    @Override
    public void accept(EventVisitor v) {
        v.visitHittable(this);
    }
}
