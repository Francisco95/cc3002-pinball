package logic.table;

import controller.EventAcceptor;
import controller.Game;
import logic.bonus.Bonus;
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
 * Use of Observer pattern with Visitor Pattern.
 *
 * @see controller.EventVisitor
 * @see EventAcceptor
 * @see Game
 * @see facade.HomeworkTwoFacade
 *
 * @author Fancisco Muñoz Ponce.
 */
public class GameTable extends Observable implements Table {

    private String tableName;
    private List<Target> targets;
    private List<Bumper> bumpers;
    private int numberOfDropTargets;
    private int counterOfDroppedDropTarget;

    private GameTable(String tableName, List<Bumper> bumpers, List<Target> targets,
                      int numberOfDropTargets) {
        this.tableName = tableName;
        this.targets = targets;
        this.bumpers = bumpers;
        this.numberOfDropTargets = numberOfDropTargets;

        // add to targets this as observer only if targets is not null
        if (this.targets != null) {
            for (Target t : this.targets) {
                t.setObservers(this);
            }
        }

        // add the DropTargetBonus observer
        setObservers(DropTargetBonus.getInstance());
    }

    /**
     * Create a new Empty {@link Table} with no {@link Bumper}s or {@link Target}s,
     * this will be a Table created without a 'TableName'.
     *
     * @return                          the instance of {@link Table}
     */
    public static Table getEmptyTable(){
        return new GameTable("", null, null, 0);
    }

    /**
     * Create a new {@link Table} with {@link Bumper}s and without {@link Target}s,
     * this will be a Table called 'TableName'.
     *
     * @param game                      instance of {@link Game}
     * @param tableName                 the name that {@link Table} will have.
     * @param numberOfBumpers           the number of {@link Bumper}s to create
     *                                  in the {@link Table}.
     * @param prob                      the probability that a {@link Bumper}
     *                                  is {@link PopBumper}
     *
     * @return                          the instance of {@link Table}
     */
    public static Table getTableWithoutTargets(Game game, String tableName, int numberOfBumpers, double prob){
        List<Bumper> bumpers = createBumpers(game, numberOfBumpers, prob);
        return new GameTable(tableName, bumpers, null, 0);
    }

    /**
     * Create a new Full {@link Table} with {@link Bumper}s and {@link Target}s,
     * this will be a Table called 'TableName'.
     *
     * @param game                      instance of {@link Game}
     * @param tableName                 the name that {@link Table} will have.
     * @param numberOfBumpers           the number of {@link Bumper}s to create
     *                                  in the {@link Table}.
     * @param prob                      the probability that a {@link Bumper}
     *                                  is {@link PopBumper}
     * @param numberOfDropTargets       the number of {@link DropTarget}s to create
     *                                  in the {@link Table} casted as {@link Target}s
     * @param numberOfTargets           the number of {@link Target}s to create in
     *                                  the {@link Table}, this consider {@link DropTarget}s
     *                                  and {@link SpotTarget}s
     *
     * @return                          the instance of {@link Table}
     */
    public static Table getFullTable(Game game, String tableName, int numberOfBumpers, double prob,
                                     int numberOfDropTargets, int numberOfTargets){
        List<Target> targets = createTargets(game, numberOfDropTargets, numberOfTargets);
        List<Bumper> bumpers = createBumpers(game, numberOfBumpers, prob);
        return new GameTable(tableName, bumpers, targets, numberOfDropTargets);
    }

    /**
     * action of add 1 to the current counter of dropped {@link DropTarget}
     */
    private void addDropedToCounter(){
        this.counterOfDroppedDropTarget++;
    }


    /**
     * Create a new {@link Bumper} instance, using the instance of
     * {@link Random}, called 'randomProb', to decide if this new bumper
     * it will be a {@link PopBumper} or {@link KickerBumper}
     *
     * @param randomProb                instance of {@link Random}
     * @param prob                      the probability of create a {@link PopBumper}
     *
     * @return                          an instance of {@link Bumper}
     */
    private static Bumper newBumper(Random randomProb, double prob){
        /*if the random probability is less than 'prob' then
         * generate a popBumper, or else generate a kicker*/
        if (randomProb.nextDouble() <= prob)
            return new PopBumper();
        else
            return new KickerBumper();
    }

    /**
     * method that define the visito to a {@link DropTarget}, this
     * is an auxiliar method created for a particular behavior when
     * a visitor visit a {@link Target}.
     *
     * @param dropTarget                the {@link DropTarget} visited.
     *
     * @see controller.EventVisitor
     */
    private void visitADropTarget(DropTarget dropTarget){
        if (!dropTarget.isActive()){
            addDropedToCounter();
        }
    }

    /**
     * Create the {@link List} of {@link Target}
     * Method declared static to allow call before create the instance of Table.
     * Since the bonuses are generated with Singleton Pattern, calling the
     * static method getInstance() give the current created {@link ExtraBallBonus}
     * or create the corresponding instance.
     *
     * @param game                      instance of {@link Game}
     * @param numberOfDropTargets       number of {@link DropTarget}s to create
     * @param numberOfTargets           the number of {@link Target}s to create
     *
     * @return                          a {@link List} of {@link Target}s
     */
    private static List<Target> createTargets(Game game, int numberOfDropTargets, int numberOfTargets) {

        ArrayList<Target> targets = new ArrayList<>();
        JackPotBonus jackPotBonus = JackPotBonus.getInstance();
        ExtraBallBonus extraBallBonus = ExtraBallBonus.getInstance();
        DropTargetBonus dropTargetBonus = DropTargetBonus.getInstance();

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
        return targets;
    }

    /**
     * Create the {@link List} of {@link Bumper}.
     * Method declared static to allow call before create the instance of Table.
     * Since the bonuses are generated with Singleton Pattern, calling the
     * static method getInstance() give the current created {@link ExtraBallBonus}
     * or create the corresponding instance.
     *
     * @param game                      instance of the current {@link Game}
     * @param numberOfBumpers           number of {@link Bumper}s to create
     * @param prob                      the probability of create a {@link PopBumper}
     *
     * @return                          a {@link List} of {@link Bumper}s
     */
    private static List<Bumper> createBumpers(Game game, int numberOfBumpers, double prob) {
        ExtraBallBonus extraBallBonus = ExtraBallBonus.getInstance();
        Random randomProb = new Random();
        ArrayList<Bumper> bumpers = new ArrayList<>();
        Bumper auxBumper;
        for (int i = 0; i < numberOfBumpers; i++){
            auxBumper = newBumper(randomProb, prob);
            auxBumper.setObservers(extraBallBonus, game);
            bumpers.add(auxBumper);
        }
        return bumpers;
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
        return counterOfDroppedDropTarget;
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
        if (targets == null)
            return;

        for (int i = 0; i < numberOfDropTargets; i++){
            targets.get(i).reset();
        }
        // and reset the counter of dropped dropTargets
        this.counterOfDroppedDropTarget = 0;
    }

    @Override
    public void upgradeAllBumpers() {
        if (bumpers == null)
            return;
        for (Bumper bumper : bumpers){
            bumper.upgrade();
        }
    }

    @Override
    public boolean isPlayableTable() {
        return !tableName.equals("") && bumpers != null;
    }

    @Override
    public void acceptFromGame(Game game) {
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
    public void acceptFromBonus(Bonus bonus) {
        int previousCounterOfTriggers = bonus.timesTriggered();
        bonus.visitTable(this);
        if (previousCounterOfTriggers + 1 == bonus.timesTriggered()){
            upgradeAllBumpers();
        }
    }

    @Override
    public void acceptFromTable(Table table) {
        // do nothing
    }

    @Override
    public void setObservers(Observer... observers) {
        for (Observer o : observers)
            addObserver(o);
    }

    @Override
    public void visitBonus(Bonus bonus) {
        // do nothing
    }

    @Override
    public void visitBumper(Bumper bumper) {
        // do nothing
    }

    @Override
    public void visitTarget(Target target) {
        if (target.isADropTarget()){
            visitADropTarget((DropTarget) target);
        }
    }

    @Override
    public void visitTable(Table table) {
        // do nothing
    }

    @Override
    public void update(Observable o, Object arg) {
        ((EventAcceptor) o).acceptFromTable(this);
    }
}
