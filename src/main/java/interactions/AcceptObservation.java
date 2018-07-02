package interactions;

import controller.Game;
import logic.bonus.Bonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observer;

/**
 * This interface represent an Event Acceptor object,
 * this is part of implementation for Observable Pattern
 * paired together with Visitor Patter, using the idea that
 * comes from: <a href="https://stackoverflow.com/a/6608600">stackoverflow</a>.
 *
 * This implementation is made for cases of many observables and many observers.
 *
 * The generalization is, if an object accept a visitor, then that object
 * is also an observable. If an object visit an acceptor, then that object
 * is also an observer.
 *
 * This implementantion also generalize the interactions, creating method that
 * allow to every class interact with every class, including interaction of
 * two same classes with different instanciation. At this time not all classes
 * interact with all other but in the future it is possible and it will be "easy"
 * to implement.
 *
 * @author Fancisco Muñoz Ponce. on date: 26-05-18
 *
 * @see ReceiveChanges
 */
public interface AcceptObservation {
    /**
     * method that define if the current Observable event will accept or not
     * an observation from a Game instance
     *
     * @param game Instance of Game
     */
    void acceptObservationFromGame(Game game);

    /**
     * method that define if the current Observable event will accept or not
     * an observation from a Bumper instance
     *
     * @param bumper Instance of Bumper
     */
    void acceptObservationFromBumper(Bumper bumper);

    /**
     * method that define if the current Observable event will accept or not
     * an observation from a Target instance
     *
     * @param target Instance of Target
     */
    void acceptObservationFromTarget(Target target);

    /**
     * method that define if the current Observable event will accept or not
     * an observation from a Bonus instance
     *
     * @param bonus Instance of Bonus
     */
    void acceptObservatiobFromBonus(Bonus bonus);

    /**
     * method that define if the current Observable event will accept or not
     * an observation from a Table instance
     *
     * @param table Instance of Table
     */
    void acceptObservationFromTable(Table table);

    /**
     * as part of Observer Pattern, this set the new observers that observe
     * this observable.
     *
     * @param observers instances of observers
     */
    void setObservers(Observer... observers);

    /**
     * as part of Observer Pattern, this method will delete all current
     * observers from the list of observers
     */
    void deleteAllObservers();
}
