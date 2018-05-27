package controller;

import logic.bonus.Bonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
import java.util.Observer;

/**
 * Game logic controller class.
 * in order to communicate with all the other class implement the Observer pattern considering this as
 * the observer and the other classes as Observables, also mix this with a Visit Pattern in order to be able
 * to do the modifications on Game state in a cleaner way, this idea comes from a thread on StackOverflow,
 * which link is: <a href="https://stackoverflow.com/a/6608600">http://google.com</a> and is principally apply
 * to Game as the visitor and the Bonus classes as the visited because they can change score or balls.
 * @see EventVisitor
 * @see Bonus
 *
 * @author (template)Juan-Pablo Silva, (code)Francisco Muñoz P.
 */
public class Game implements Observer, EventVisitor {

    /**
     * the number of score in the game
     */
    private int score;
    /**
     * the number of balls in the game
     */
    private int balls;

    public Game(){
        this(1, 0);
    }
    public Game(int initialBalls) {
        this(initialBalls, 0);
    }

    public Game(int initialBalls, int initialSocore){
        this.score = initialSocore;
        this.balls = initialBalls;
    }

    /**
     * do an addition to the current score points by an amount of 'points'
     * @param points the number of points to add to score
     */
    public void addToScore(int points){
        this.score += points;
    }

    /**
     * @return get the score value
     */
    public int getScore() {
        return score;
    }

    /**
     * set the score value to 'score'
     * @param score the new score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * do an addition to the current balls number by an amount of 'balls'
     * @param balls the number of balls to add to game
     */
    public void addToBalls(int balls){
        this.balls += balls;
    }

    /**
     * @return get the number of balls in game
     */
    public int getBalls() {
        return balls;
    }

    /**
     * set the number of balls in game to 'balls'
     * @param balls the new number of balls
     */
    public void setBalls(int balls) {
        this.balls = balls;
    }

    public void visitHittable(Hittable hittable){
        addToScore(hittable.getScore());
    }

    /**
     * if the message 'arg' is null then apply visitor pattern, or else,
     * augment the score by the amount of 'arg'
     * Here arg could only be an integer or null and if it is null que observable
     * can only be an instance of Bonus.
     * @param o the observable, could be a Bonus, a Bumper, a Target or a Table
     * @param arg the message sended by the observable
     */
    @Override
    public void update(Observable o, Object arg) {
        ((EventAcceptor) o).acceptFromGame(this);
    }

    @Override
    public void visitBonus(Bonus bonus) {
        if (bonus.isBonusOfBalls())
            addToBalls(bonus.getBonusValue());
        else
            addToScore(bonus.getBonusValue());
    }

    @Override
    public void visitBumper(Bumper bumper) {
        visitHittable(bumper);
    }

    @Override
    public void visitTarget(Target target) {
        visitHittable(target);
    }

    @Override
    public void visitTable(Table table) {

    }

}
