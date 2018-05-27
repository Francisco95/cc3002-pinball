package logic.gameelements.bumper;

import controller.EventAcceptor;
import controller.Game;
import logic.bonus.Bonus;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Abstraction of some common behavior of any bumper,
 * since all the bumpers are used as observable,
 * we use that class here.
 * Also bumpers observe to {@link logic.bonus.DropTargetBonus}
 * waiting for the time to upgrade when the bonus is triggered.
 *
 * @author Fancisco Muñoz Ponce. on date: 23-05-18
 */
public abstract class AbstractBumper extends Observable implements Bumper {

    /**
     * keep count of the remaining hits the bumper has to receive to upgrade,
     * when is 0 the bumper is upgraded
     */
    private int remainingHits;

    /**
     * the points given by the bumper, at first it's same
     * points as basePoints, but when it is upgrades,
     * score will be equal to UpgradedPoints;
     */
    private int score;

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

    public AbstractBumper(int remainingHits, int score){
        this.score = score;
        this.remainingHits = remainingHits;
        randomProb = new Random();
    }

    @Override
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

    /**
     * set the seed value of the random probability, this is usefull for
     * testing purpose.
     * @param seed the seed to use
     */
    public void setSeed(int seed){
        this.seed = seed;
    }

    /**
     * set the remaining hits needed to upgrade
     * @param remainingHits the new remaining hits to upgrade
     */
    public void setRemainingHitsToUpgrade(int remainingHits){
        this.remainingHits = remainingHits;
    }

    /**
     * set the points given by the bumper
     * @param newScore new score for the bumper
     */
    public void setScore(int newScore){
        this.score = newScore;
    }

    public boolean compareScore(int score){
        return this.score == score;
    }

    public boolean compareRemainingHits(int remainingHits){
        return this.remainingHits == remainingHits;
    }

    @Override
    public int remainingHitsToUpgrade() {
        return remainingHits;
    }

    @Override
    public void hit() {
        this.remainingHits--;
        shouldUpgrade();
        setChanged();
        notifyObservers(score);
    }

    @Override
    public int getScore() {
        return score;
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

    @Override
    public void acceptFromGame(Game game) {
        game.visitBumper(this);
    }

    @Override
    public void acceptFromBonus(Bonus bonus) {
        bonus.visitBumper(this);
    }

    @Override
    public void acceptFromBumper(Bumper bumper) {
        // do nothing
    }

    @Override
    public void acceptFromTarget(Target target) {
        // do nothing
    }

    @Override
    public void acceptFromTable(Table table) {
        // do nothing
    }
}
