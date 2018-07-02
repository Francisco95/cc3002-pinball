package interactions;

import interactions.AcceptObservation;
import logic.bonus.Bonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
import java.util.Observer;

/**
 * This interface represent an Event Visitor object,
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
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 *
 * @see AcceptObservation
 */
public interface ReceiveChanges extends Observer {

    /**
     * method that update the Observer if the observed event, in this
     * case the Bonus, it shows any change, in this case, the bonus is triggered.
     *
     * @param bonus Instance of Bonus an observable event)
     */
    void triggeredBonus(Bonus bonus);

    /**
     * method that update the Observer if the observed event, in this
     * case the Bumper, it shows any change, in this case, being hit.
     *
     * @param bumper Instance of Bumper (an observable event)
     */
    void hitBumper(Bumper bumper);

    /**
     * method that update the Observer if the observed event, in this
     * case the Target, it shows any change, in this case, being hit.
     *
     * @param target Instance of Target (an observable event)
     */
    void hitTarget(Target target);

    /**
     * method that update the Observer if the observed event, in this
     * case the Table, it shows any change, in this case, any change of state.
     *
     * @param table Instance of Table (an observable event)
     */
    void changedStateOfTable(Table table);

    /**
     * update method from the Observer Pattern, in this case the implementation
     * will be always the same, responding to: if an observer observe any change in
     * an observable, then it will ask to the Observable if he accept the observation.
     * @param o the observable
     * @param arg any message sent by the observable.
     */
    @Override
    void update(Observable o, Object arg);
}
