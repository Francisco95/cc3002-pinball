package main.java.controller;

import main.java.logic.bonus.Bonus;
import main.java.logic.gameelements.Hittable;

/**
 * Interface that define Visitor Pattern, in particular this define how the
 * visitor visit methods from different class, this is done combined with
 * observer pattern, idea comes from: <a href="https://stackoverflow.com/a/6608600">http://google.com</a>.
 * Game it will be the visitor and visit a Bonus or Hittable means that game will increase his
 * score/balls by the corresponding amount.
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 */
public interface EventVisitor {
    /**
     * this decide to visit the event Bonus as part of Visit Pattern, this code will run only if the visited
     * class accept the visit.
     * @param bonus an instance of Bonus
     */
    void visitBonus(Bonus bonus);

    /**
     * this decide to visit the event Hittable as part of Visit Pattern, this code will run onyl if the visited
     * class accept the visit.
     * @param hittable an instance of hittable
     */
    void visitHittable(Hittable hittable);
}
