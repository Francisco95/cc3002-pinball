package logic.bonus;

import interactions.AcceptObservation;
import controller.Game;
import interactions.DefaultInteractions;
import java.util.Observable;

/**
 * Abstract class used to compact all the common behavior of all type of bonus,
 *
 * @author Fancisco Muñoz Ponce. on date: 26-05-18
 */
public abstract class AbstractBonus extends DefaultInteractions implements Bonus{

    /**
     * count the number of triggers done so far now.
     */
    private int counterTriggers;

    /**
     * value of the bonus, could be points or balls
     */
    private int bonusValue;

    /**
     * this says if a bonus give balls or points
     */
    private boolean bonusOfBalls;

    /**
     * constructor declared in abstract class to generalize
     * some common variable constructions.
     *
     * @param bonusValue the amount of bonus (points or balls) that give
     * @param bonusOfBalls true if this bonus give balls, false if give points
     */
    public AbstractBonus(int bonusValue, boolean bonusOfBalls){
        this.counterTriggers = 0;
        this.bonusValue = bonusValue;
        this.bonusOfBalls = bonusOfBalls;
    }

    /**
     * method that reset the counter of triggers, principally used for
     * testing purpose but can be helpful in the case of reset a game
     * and need to reset the counter of triggers of bonuses (since
     * their instanciation is with Singleton Pattern).
     *
     */
    public void resetCounterTriggers(){
        this.counterTriggers = 0;
    }

    /**
     * @return number of times the bonus has been triggered
     */
    @Override
    public int timesTriggered() {
        return counterTriggers;
    }

    /**
     * trigger a bonus means add 1 to the counter of triggers
     * and trigger the notification to his observers.
     */
    @Override
    public void trigger() {
        counterTriggers++;
        setChanged();
        notifyObservers();
    }

    /**
     * @return value of bonus (integer)
     */
    @Override
    public int getBonusValue() {
        return bonusValue;
    }

    /**
     * @return return true if the bonus is a bonus of balls
     */
    @Override
    public boolean isBonusOfBalls() {
        return bonusOfBalls;
    }

    /**
     * Use visitor patter with observe pattern to simulate a "multi-dispacth" interaction,
     * with this we only need to do one instanceof in order to check that the incoming observable
     * event is an instance of an acceptor of observation.
     *
     * @param o   the observable
     * @param arg any message sent by the observable.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof AcceptObservation) {
            ((AcceptObservation) o).acceptObservatiobFromBonus(this);
        }
    }

    /**
     * if this bonus is observed by a Game instance, it will trigger
     * a change in that Game instance.
     *
     * @param game Instance of Game
     */
    @Override
    public void acceptObservationFromGame(Game game) {
        game.triggeredBonus(this);
    }

}
