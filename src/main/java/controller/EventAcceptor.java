package controller;

import logic.bonus.Bonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
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
 * @author Fancisco Muñoz Ponce. on date: 26-05-18
 *
 * @see controller.EventVisitor
 */
public interface EventAcceptor {
    void acceptFromGame(Game game);

    void acceptFromBumper(Bumper bumper);

    void acceptFromTarget(Target target);

    void acceptFromBonus(Bonus bonus);

    void acceptFromTable(Table table);

    /**
     * as part of Observer Pattern, this set the new observers that observe
     * this observable.
     *
     * @param observers instances of observers
     */
    void setObservers(Observer... observers);

    /**
     * as part of Observer Pattern, this method will delete all current observers from the list
     * of observers
     */
    void deleteAllObservers();
}
