package main.java.logic.bonus;

import main.java.controller.EventVisitor;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class ExtraBallBonus implements Bonus {
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
    public void accept(EventVisitor v) {

    }
}
