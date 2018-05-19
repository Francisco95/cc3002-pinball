package main.java.controller;

/**
 * Interface that define Visitor Pattern, in particular this define how the
 * visitor visit methods from different class, this is done combined with
 * observer pattern, idea comes from: <a href="https://stackoverflow.com/a/6608600">http://google.com</a>.
 * Game it will be the visitor and visit a Bonus increase his
 * score/balls by the corresponding amount.
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 */
public interface EventVisitor {
    /**
     * this decide to visit the event Bonus that give points as part of Visit Pattern,
     * this code will run only if the visited class accept the visit.
     * @param pointsBonus the number of points of the bonus
     */
    void visitBonusOfPoints(int pointsBonus);

    /**
     * this decide to visit the event Bonus that give balls as part of Visit Pattern,
     * this code will run only if the visited class accept the visit.
     * @param ballsBonus the number of balls of the bonus
     */
    void visitBonusOfBalls(int ballsBonus);
}
