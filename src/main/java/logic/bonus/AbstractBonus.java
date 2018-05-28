package logic.bonus;

import controller.EventAcceptor;
import controller.EventVisitor;
import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Fancisco Muñoz Ponce. on date: 26-05-18
 */
public abstract class AbstractBonus extends Observable implements Bonus{
    /**
     * count the number of triggers done so far now.
     */
    private int counterTriggers;

    /**
     * value of the bonus, could be points or balls
     */
    private int bonusValue;

    /**
     * this says if a bonus give balls or points
     */
    private boolean bonusOfBalls;

    /**
     * constructor declared in abstract class to generalize
     * some common variable constructions.
     * @param bonusValue the amount of bonus (points or balls) that give
     * @param bonusOfBalls true if this bonus give balls, false if give points
     */
    public AbstractBonus(int bonusValue, boolean bonusOfBalls){
        this.counterTriggers = 0;
        this.bonusValue = bonusValue;
        this.bonusOfBalls = bonusOfBalls;
    }

    public void resetCounterTriggers(){
        this.counterTriggers = 0;
    }

    @Override
    public void setObservers(Observer...observers){
        for (Observer o : observers)
            addObserver(o);
    }

    @Override
    public int timesTriggered() {
        return counterTriggers;
    }

    @Override
    public void trigger() {
        counterTriggers++;
        setChanged();
        notifyObservers();
    }

    @Override
    public int getBonusValue() {
        return bonusValue;
    }

    @Override
    public boolean isBonusOfBalls() {
        return bonusOfBalls;
    }

    @Override
    public void update(Observable o, Object arg) {
        ((EventAcceptor) o).acceptFromBonus(this);
    }

    @Override
    public void acceptFromGame(Game game) {
        game.visitBonus(this);
    }

    @Override
    public void acceptFromBonus(Bonus bonus) {
        // do nothing
    }

    @Override
    public void acceptFromBumper(Bumper bumper) {
        // do nothing
    }

    @Override
    public void acceptFromTarget(Target target) {
        // do nothing
    }

    @Override
    public void acceptFromTable(Table table) {
        // do nothing
    }

    @Override
    public void visitBonus(Bonus bonus) {
        // do nothing
    }
}
