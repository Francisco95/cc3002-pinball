package logic.gameelements.target;

import controller.Game;
import interactions.AcceptObservation;
import interactions.DefaultInteractions;
import logic.bonus.Bonus;
import logic.table.Table;

import java.util.Observable;
import java.util.Random;

/**
 * @author Fancisco Muñoz Ponce. on date: 23-05-18
 */
public abstract class AbstractTarget  extends DefaultInteractions implements Target {
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

    /**
     * this says if the target is a dropTarget or not
     */
    private boolean aDropTarget;

    public AbstractTarget(boolean active, int score, Random randomProb, boolean aDropTarget){
        this.active = active;
        this.score = score;
        this.seed = -1;
        this.randomProb = randomProb;
        this.aDropTarget = aDropTarget;
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
     * set the seed to the instance of Random()
     */
    public void setSeedToRandomProb(){
        if (seed > -1 && randomProb != null) {
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
    public int hit() {
        if (isActive()){
            setActive(false);
            setChanged();
            notifyObservers(score);
            return score;
        }
        return 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof AcceptObservation) {
            ((AcceptObservation) o).acceptObservationFromTarget(this);
        }
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public boolean isADropTarget() {
        return aDropTarget;
    }

    @Override
    public void acceptObservationFromGame(Game game) {
        game.hitTarget(this);
    }

    @Override
    public void acceptObservationFromTable(Table table) {
        table.hitTarget(this);
    }

    @Override
    public void acceptObservatiobFromBonus(Bonus bonus) {
        bonus.hitTarget(this);
    }
}
