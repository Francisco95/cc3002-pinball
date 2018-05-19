package main.java.logic.bonus;

import main.java.controller.EventVisitor;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define droptarget bonus.
 * use Singleton Pattern to guarantee only one initialize per game.
 * Use Observable Pattern to receive the message from Table to trigger when al dropTarget are off,
 * Also use Observable Pattern mixed with Visit Pattern to solve the problem of
 * one observer(Game) and many observables(Bonus, Bumpers, Targets).
 * @see main.java.logic.bonus.Bonus
 * @see main.java.controller.Game
 * @see main.java.logic.table.Board
 * @see main.java.logic.gameelements.bumper.Bumper
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTargetBonus extends Observable implements Observer, Bonus {
    @Override
    public int timesTriggered() {
        return 0;
    }

    @Override
    public void trigger() {

    }

    @Override
    public boolean isBonusOfPoints() {
        return false;
    }

    @Override
    public int getBonusValue() {
        return 0;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void accept(EventVisitor v) {

    }
}
