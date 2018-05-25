package logic.table;

import controller.EventVisitor;
import controller.Game;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;

import java.util.*;

/**
 * class that define the behavior of a instance of Table.
 * Here use an Observer Pattern to communicate with the
 * bumpers, targets and game.
 * this is going to be an observable.
 * Also use Double Dispatch to simplify actions of Board,
 * Targets and Bumpers.
 * @see logic.gameelements.target.Target
 * @see logic.gameelements.bumper.Bumper
 * @see controller.Game
 * @see facade.HomeworkTwoFacade
 *
 * @author Fancisco Muñoz Ponce. on date: 19-05-18
 */
public class Board extends Observable implements Table {

    private String tableName;
    private List<Target> targets;
    private List<Bumper> bumpers;
    private int numberOfBumpers;
    private int numberOfDropTargets;
    private int numberOfTargets;
    private int counterOfDroped;
    private double prob;

    public Board(String tableName, int numberOfBumpers, double prob,
                 int numberOfDropTargets, int numberOfTargets){
        this.tableName = tableName;
        this.numberOfBumpers = numberOfBumpers;
        this.numberOfDropTargets = numberOfDropTargets;
        this.numberOfTargets = numberOfTargets;
        this.counterOfDroped = 0;
        this.prob = prob;
    }

    public Board(String tableName, int numberOfBumpers, double prob){
        this(tableName, numberOfBumpers, prob, 0, 0);
    }

    public Board(){
        this("", 0, 0, 0, 0);
    }

    @Override
    public void setTargets(JackPotBonus jackPotBonus, ExtraBallBonus extraBallBonus,
                           DropTargetBonus dropTargetBonus, Game game){
        if (targets != null){
            return;
        }
        targets = new ArrayList<>();
        Target auxTarget;
        int i = 0;
        while (i < numberOfDropTargets) {
            auxTarget = new DropTarget();
            auxTarget.setObservers(game, dropTargetBonus, extraBallBonus);
            targets.add(auxTarget);
            i++;
        }
        while (i < numberOfTargets) {
            auxTarget = new SpotTarget();
            auxTarget.setObservers(game, jackPotBonus);
            targets.add(auxTarget);
            i++;
        }
    }

    @Override
    public void setBumpers(ExtraBallBonus extraBallBonus, Game game){
        if (bumpers != null)
                return;
        Random randomProb = new Random();
        bumpers = new ArrayList<>();
        Bumper auxBumper;
        for (int i = 0; i < numberOfBumpers; i++){
            auxBumper = newBumper(randomProb);
            auxBumper.setObservers(extraBallBonus, game);
            bumpers.add(auxBumper);
        }
    }

    /**
     * return a new bumper instance, using the 'randomProb' to decide
     * if this new bumper is a popBumper or KickerBumper
     * @param randomProb the random probability, its a value between [0, 1].
     * @return the instance of a bumper.
     */
    private Bumper newBumper(Random randomProb){
        /*if the random probability is less than 'prob' then
        * generate a popBumper, or else generate a kicker*/
        if (randomProb.nextDouble() <= prob)
            return new PopBumper();
        else
            return new KickerBumper();
    }

    public void addDropedToCounter(){
        this.counterOfDroped++;
    }

    public void setCounterOfDropped(int value){
        this.counterOfDroped = value;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public int getNumberOfDropTargets() {
        return numberOfDropTargets;
    }

    @Override
    public int getCurrentlyDroppedDropTargets() {
        if (targets == null || targets.size() == 0){
            return counterOfDroped;
        }
        setCounterOfDropped(0);
        for (Target t: targets){
            if (t.getScore() == 100 && !t.isActive()) {
                addDropedToCounter();
            }
        }
        return counterOfDroped;
    }

    @Override
    public List<Bumper> getBumpers() {
        return bumpers;
    }

    @Override
    public List<Target> getTargets() {
        return targets;
    }

    @Override
    public void resetDropTargets() {
        for (Target t : targets){
            t.reset();
        }
    }

    @Override
    public void upgradeAllBumpers() {
        for (Bumper b: bumpers){
            b.upgrade();
        }
    }

    @Override
    public boolean isPlayableTable() {
        return !tableName.equals("") && bumpers != null;
    }
}
