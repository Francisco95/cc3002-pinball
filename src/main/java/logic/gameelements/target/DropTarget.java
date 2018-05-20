package logic.gameelements.target;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * class that define the behavior of DropTarget.
 * Use of Singleton Pattern to guarantee only 1 instance per game.
 * Use Observer Pattern mixed with visitor pattern to solve
 * problem of one observer(Game) and many observables(targets, bumpers and bonus).
 * @see logic.gameelements.target.Target
 * @see controller.Game
 * @see logic.bonus.DropTargetBonus
 * @see logic.bonus.ExtraBallBonus
 * @see
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTarget extends Observable implements Target {
    /**
     * the current state of the spotTarget, could be active (True) or inactive (False)
     */
    private boolean active;
    /**
     * the number of points given by the spotTarget
     */
    private final int pointsGiven = 100;
    /**
     * the random probability of trigger an {@link logic.bonus.ExtraBallBonus},
     * this will be a number between [0, 9] both included, since there is 30% of probability
     * of trigger, the number should be 0, 1 or 2. (< 3)
     */
    private Random randomProb;

    /**
     * the seed for the random number, by default is declared as -1 which means that there
     * is no seed set, if seed > -1 then set the seed
     */
    private int seed = -1;

    /**
     * the instance of DropTarget, this is part of Singleton Pattern
     */
    private static DropTarget instance = null;

    /**
     * constructor declared as private for singleton pattern
     * by default, set active to true and create instance of Random()
     */
    private DropTarget() {
        this.active = true;
        this.randomProb = new Random();
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
     * set the seed value of the random probability, this is usefull for
     * testing purpose.
     * @param seed the seed to use
     */
    public void setSeed(int seed){
        this.seed = seed;
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

    @Override
    public void reset() {
        setActive(true);
        randomProb = new Random(); // this is to reset the seed
        deleteObservers();
    }

    @Override
    public void hit() {
        if (isActive()){
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
     * for this case there is a probability of 3/10 of trigger an ExtraBallBonus when hit() is called.
     * @return return True if the probability is satisfied.
     */
    @Override
    public boolean bonusTriggered() {
        if (seed > -1) {
            randomProb.setSeed(seed);
        }
        return randomProb.nextInt(10) < 3;
    }
}
