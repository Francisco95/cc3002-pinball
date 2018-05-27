package controller;

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
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 *
 * @see controller.EventAcceptor
 */
public interface EventVisitor extends Observer {

    void visitBonus(Bonus bonus);

    void visitBumper(Bumper bumper);

    void visitTarget(Target target);

    void visitTable(Table table);

    /**
     * update method from the Observer Pattern, in this case the implementation
     * will be always the same, responding to: if an observer observe any change in
     * an observable, then it will ask in this observable if accept a visit from him.
     * @param o the observable
     * @param arg any message sent by the observable.
     */
    @Override
    void update(Observable o, Object arg);
}
