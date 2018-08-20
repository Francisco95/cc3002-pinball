package controller;

import interactions.AcceptObservation;
import interactions.DefaultInteractions;
import interactions.ReceiveChanges;
import logic.bonus.Bonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;

import java.util.Observable;

/**
 * Game logic controller class.
 * in order to communicate with all the other class implement the Observer pattern considering this as
 * the observer and the other classes as Observables, also mix this with a Visit Pattern in order to be able
 * to do the modifications on Game state in a cleaner way.
 *
 * @see ReceiveChanges
 * @see Bonus
 *
 * @author (template)Juan-Pablo Silva, (code)Francisco Muñoz P.
 */
public class Game extends DefaultInteractions {

    /**
     * the number of score in the game
     */
    private int score;

    /**
     * the number of balls in the game
     */
    private int balls;

    public Game(){
        this(3, 0);
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
     * also notify to observer that the number of balls has changed
     *
     * @param points the number of points to add to score
     */
    public void addToScore(int points){
        this.score += points;
        setChanged();
        notifyObservers("UpdateInfo");
    }

    /**
     * @return get the score value
     */
    public int getScore() {
        return score;
    }

    /**
     * set the score value to 'score'
     *
     * @param score the new score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * do an addition to the current balls number by an amount of 'balls'
     * also notify to observer that the number of balls has changed
     *
     * @param balls the number of balls to add to game
     */
    public void addToBalls(int balls){
        this.balls += balls;
        setChanged();
        notifyObservers("UpdateInfo");
    }

    /**
     * @return get the number of balls in game
     */
    public int getBalls() {
        return balls;
    }

    /**
     * set the number of balls in game to 'balls'
     *
     * @param balls the new number of balls
     */
    public void setBalls(int balls) {
        this.balls = balls;
    }

    /**
     * drop one ball decreasing the current number of balls by 1
     * also notify to observer that the number of balls has changed
     */
    public void dropBall(){
        if (balls > 0) {
            balls--;
            setChanged();
            notifyObservers("UpdateInfo");
        }
    }

    /**
     * check if the game is over, i.e., the number of balls is 0 (or less)
     *
     * @return True if the game is over ( number of balls == 0), or false if not
     */
    public boolean isGameOver(){
        return balls <= 0;
    }

    /**
     * When a ball hit a hittable it will add to the current game score
     * the corresponding amount of points.
     *
     * @param hittable instance of a Game element
     */
    public void hitHittable(Hittable hittable){
        addToScore(hittable.getScore());
    }

    /**
     * Use visitor patter with observe pattern to simulate a "multi-dispacth" interaction,
     * with this we only need to do one instanceof in order to check that the incoming observable
     * event is an instance of an acceptor of observation.
     *
     * @param o the observable, could be anything created as an acceptor of observation
     * @param arg the message sended by the observable
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof AcceptObservation) {
            ((AcceptObservation) o).acceptObservationFromGame(this);
        }
    }

    /**
     * if the bonus triggered is a bonus of balls, then increase the
     * number of balls by the corresponding amount, or else, the
     * bonus is of points and will increase the score.
     *
     * @param bonus Instance of Bonus an observable event)
     */
    @Override
    public void triggeredBonus(Bonus bonus) {
        if (bonus.isBonusOfBalls())
            addToBalls(bonus.getBonusValue());
        else
            addToScore(bonus.getBonusValue());
    }

    /**
     * when a ball hit a bumper it could generate an
     * increase in the game score
     *
     * @param bumper Instance of Bumper (an observable event)
     */
    @Override
    public void hitBumper(Bumper bumper) {
        hitHittable(bumper);
    }

    /**
     * when a ball hit a target it could generate an
     * increase in the game score
     *
     * @param target Instance of Target (an observable event)
     */
    @Override
    public void hitTarget(Target target) {
        if (!target.isActive()) {
            hitHittable(target);
        }
    }
}
