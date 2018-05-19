package main.java.logic.bonus;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTargetBonus implements Bonus {
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
}
