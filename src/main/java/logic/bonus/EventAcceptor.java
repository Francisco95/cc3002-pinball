package main.java.logic.bonus;

import main.java.controller.EventVisitor;

/**
 * Interface that define Visitor Pattern, in particular this define how the
 * acceptor is visited from the main class, this is done combined with
 * observer pattern, idea comes from: <a href="https://stackoverflow.com/a/6608600">http://google.com</a>
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 */
public interface EventAcceptor {
    /**
     * this decide to accept the event visitor acording to the Visitor Pattern
     * @param v an instance of a Visitor.
     */
    void accept(EventVisitor v);
}
