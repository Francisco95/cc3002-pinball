package main.java.controller;

import main.java.logic.EventAcceptor;
import main.java.logic.bonus.Bonus;
import main.java.logic.gameelements.Hittable;

import java.util.Observable;
import java.util.Observer;

/**
 * Game logic controller class.
 * in order to communicate with all the other class implement the Observer pattern considering this as
 * the observer and the other classes as Observables, also mix this with a Visit Pattern in order to be able
 * to do the modifications on Game state in a cleaner way, this idea comes from a thread on StackOverflow,
 * which link is: <a href="https://stackoverflow.com/a/6608600">http://google.com</a>
 *
 * @author (template)Juan-Pablo Silva, (code)Francisco Muñoz P.
 */
public class Game implements Observer, EventVisitor{

    /**
     * the number of score in the game
     */
    private int score;
    /**
     * the number of balls in the game
     */
    private int balls;

    public Game(int initialNumOfBalls) {
        this.score = 0;
        this.balls = initialNumOfBalls;
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

    @Override
    public void update(Observable o, Object arg) {
        ((EventAcceptor) o).accept(this);
    }

    @Override
    public void visitBonus(Bonus bonus) {
        if (bonus.isBonusOfPoints()){
            addToScore(bonus.getBonusValue());
        }
        else{
            addToBalls(bonus.getBonusValue());
        }
    }

    @Override
    public void visitHittable(Hittable hittable) {
        addToScore(hittable.getScore());
    }

}
