package interactions;

import controller.Game;
import logic.bonus.Bonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
import java.util.Observer;

/**
 * Default class that define almost all methods declared in {@link ReceiveChanges}
 * and {@link AcceptObservation} as empty methods, this is because we will have
 * potentially a lot of empty declarations. This is some kind of adaptation of the interface.
 *
 * With this we just will need to override the methods that we are interested in.
 *
 * An empty Receive method here means that observe changes in the input Instance doesn't
 * change anything in the current Instance
 *
 * An empty Accept method here means that being observed by the input Instance doesn't have
 * any interaction defined.
 *
 * @see ReceiveChanges
 * @see AcceptObservation
 *
 * @author Fancisco Muñoz Ponce. on date: 01-07-18
 */
public abstract class DefaultInteractions extends Observable implements AcceptObservation, ReceiveChanges {
    @Override
    public void acceptObservationFromGame(Game game) {

    }

    @Override
    public void acceptObservationFromBumper(Bumper bumper) {

    }

    @Override
    public void acceptObservationFromTarget(Target target) {

    }

    @Override
    public void acceptObservatiobFromBonus(Bonus bonus) {

    }

    @Override
    public void acceptObservationFromTable(Table table) {

    }

    @Override
    public void setObservers(Observer... observers) {
        deleteAllObservers();
        for (Observer o : observers)
            addObserver(o);
    }

    @Override
    public void deleteAllObservers() {
        deleteObservers();
    }

    @Override
    public void triggeredBonus(Bonus bonus) {

    }

    @Override
    public void hitBumper(Bumper bumper) {

    }

    @Override
    public void hitTarget(Target target) {

    }

    @Override
    public void changedStateOfTable(Table table) {

    }

//    @Override
//    public void update(Observable o, Object arg) {
//
//    }
}
