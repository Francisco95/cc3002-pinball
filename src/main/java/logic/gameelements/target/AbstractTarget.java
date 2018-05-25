package logic.gameelements.target;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * @author Fancisco Muñoz Ponce. on date: 23-05-18
 */
public abstract class AbstractTarget  extends Observable implements Target {
    /**
     * the current state of the spotTarget, could be active (True) or inactive (False)
     */
    private boolean active;
    /**
     * the number of points given by the spotTarget
     */
    private int score;

    /**
     * the random probability of trigger a {@link logic.bonus.Bonus}
     */
    private Random randomProb;

    /**
     * the seed for the random number, by default is declared as -1 which means that there
     * is no seed set, if seed > -1 then set the seed
     */
    private int seed;

    public AbstractTarget(boolean active, int score, Random randomProb){
        this.active = active;
        this.score = score;
        this.seed = -1;
        this.randomProb = randomProb;
    }

    @Override
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

    /**
     * set the seed value of the random probability, this is usefull for
     * testing purpose.
     * @param seed the seed to use
     */
    public void setSeed(int seed){
        this.seed = seed;
    }

    /**
     * get the seed value of the random probability.
     * @return the seed value
     */
    public int getSeed(){
        return seed;
    }

    /**
     * set the seed to the instance of Random()
     */
    public void setSeedToRandomProb(){
        if (getSeed() > -1 && randomProb != null) {
            randomProb.setSeed(seed);
        }
    }

    /**
     * @return get the randomProb variable. ( instance of Random)
     */
    public Random getRandomProb(){
        return randomProb;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void reset() {
        setActive(true);
        setSeed(-1);
    }

    @Override
    public void hit() {
        if (isActive()){
            setActive(false);
            setChanged();
            notifyObservers(score);
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
