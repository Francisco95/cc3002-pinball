package logic.gameelements.bumper;

import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.List;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class KickerBumper implements Table {
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
}
