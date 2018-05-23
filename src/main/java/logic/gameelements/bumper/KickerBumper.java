package logic.gameelements.bumper;

import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Class that define behavior of a kicker bumper
 * Use of Observer Pattern to notify observers that win X points (Game)
 * or to notify a trigger of bonus (ExtraBallBonus)
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class KickerBumper extends Observable implements Bumper{

    /**
     * keep count of the remaining hits the bumper has to receive to upgrade,
     * when is 0 the bumper is upgraded
     */
    private int remainingHits;

    /**
     * the number of points given by the kickerbumper
     */
    private int pointsGiven;

    /**
     * the random probability of trigger an {@link logic.bonus.ExtraBallBonus},
     * this will be a random number between [0, 9] both included, since there is 10% of probability
     * of trigger, the number should be 0.
     */
    private Random randomProb;

    /**
     * the seed for the random number, by default is declared as -1 which means that there
     * is no seed set, if seed > -1 then set the seed
     */
    private int seed = -1;

    public KickerBumper(){
        this.remainingHits = 5;
        this.pointsGiven = 500;
        randomProb = new Random();
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
     * check after a hit if the kicker bumper should be upgraded.
     */
    public void shouldUpgrade(){
        if (remainingHits <= 0){
            upgrade();
        }
    }

    public void setRemainingHitsToUpgrade(int remainingHits){
        this.remainingHits = remainingHits;
    }
    @Override
    public int remainingHitsToUpgrade() {
        return remainingHits;
    }

    @Override
    public boolean isUpgraded() {
        return remainingHits == 0 && pointsGiven == 1000;
    }

    @Override
    public void upgrade() {
        remainingHits = 0;
        this.pointsGiven = 1000;
    }

    @Override
    public void downgrade() {
        this.pointsGiven = 500;
        this.remainingHits = 5;
    }

    @Override
    public void hit() {
        this.remainingHits--;
        shouldUpgrade();
        setChanged();
        notifyObservers(pointsGiven);

    }

    @Override
    public int getScore() {
        return pointsGiven;
    }

    /**
     * for this case there is a probability of 1/10 of trigger an ExtraBallBonus when hit() is called.
     * @return return True if the probability is satisfied.
     */
    @Override
    public boolean bonusTriggered() {
        if (seed > -1) {
            randomProb.setSeed(seed);
        }
        return randomProb.nextInt(10) == 0;
    }
}
