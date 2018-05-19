package main.java.logic.table;

import main.java.controller.EventVisitor;
import main.java.logic.bonus.EventAcceptor;
import main.java.logic.gameelements.bumper.Bumper;
import main.java.logic.gameelements.target.Target;

import java.util.List;
import java.util.Observable;

/**
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 */
public class Board extends Observable implements EventAcceptor, Table {
    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public int getNumberOfDropTargets() {
        return 0;
    }

    @Override
    public int getCurrentlyDroppedDropTargets() {
        return 0;
    }

    @Override
    public List<Bumper> getBumpers() {
        return null;
    }

    @Override
    public List<Target> getTargets() {
        return null;
    }

    @Override
    public void resetDropTargets() {

    }

    @Override
    public void upgradeAllBumpers() {

    }

    @Override
    public boolean isPlayableTable() {
        return false;
    }

    @Override
    public void accept(EventVisitor v) {

    }
}
