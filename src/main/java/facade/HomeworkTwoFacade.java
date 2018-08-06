package facade;

import controller.Game;
import logic.bonus.Bonus;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.GameTable;
import logic.table.Table;

import java.util.List;

/**
 * Facade class to expose the logic of the game to a GUI in the upcoming homework.
 *
 * @author Juan-Pablo Silva
 */
public class HomeworkTwoFacade {
    /**
     * Instance of the game controller.
     *
     * @see Game
     */
    private Game game;

    /**
     * Instance of the table where to play
     *
     * @see Table
     */
    private Table table;

    /**
     * General constructor, it receive an Instance of Game
     * and create a default non-playable Table instance.
     *
     * @param game Instance of Game
     */
    public HomeworkTwoFacade(Game game) {
        this.game = game;
        this.table = GameTable.getEmptyTable();

        // create the instances of bonuses, just to be sure
        // that this Singleton pattern classes where initialized,
        // and set the game as the observer of bonuses
        JackPotBonus.getInstance().setObservers(game);
        ExtraBallBonus.getInstance().setObservers(game);
        DropTargetBonus.getInstance().setObservers(game);
    }

    /**
     * Default constructor, create both instances by default.
     */
    public HomeworkTwoFacade(){
        this(new Game());
    }

    /**
     * Gets whether the current table is playable or not.
     *
     * @return true if the current table is playable, false otherwise
     */
    public boolean isPlayableTable() {
        return table.isPlayableTable();
    }

    /**
     * Gets the instance of {@link logic.bonus.DropTargetBonus} currently in the game.
     * Since this Instance is created by Singleton pattern, we just need to do getInstance()
     *
     * @return the DropTargetBonus instance
     */
    public Bonus getDropTargetBonus() {
        return DropTargetBonus.getInstance();
    }

    /**
     * Gets the instance of {@link logic.bonus.ExtraBallBonus} currently in the game.
     * Since this Instance is created by Singleton pattern, we just need to do getInstance()
     *
     * @return the ExtraBallBonus instance
     */
    public Bonus getExtraBallBonus() {
        return ExtraBallBonus.getInstance();
    }

    /**
     * Gets the instance of {@link logic.bonus.JackPotBonus} currently in the game.
     * Since this Instance is created by Singleton pattern, we just need to do getInstance()
     *
     * @return the JackPotBonus instance
     */
    public Bonus getJackPotBonus() {
        return JackPotBonus.getInstance();
    }

    /**
     * Creates a new table with the given parameters with no targets.
     *
     * @param name            the name of the table
     * @param numberOfBumpers the number of bumpers in the table
     * @param prob            the probability a {@link logic.gameelements.bumper.PopBumper}
     * @return a new table determined by the parameters
     */
    public Table newPlayableTableWithNoTargets(String name, int numberOfBumpers, double prob) {
        Table tmpTable =  GameTable.getTableWithoutTargets(name, numberOfBumpers, prob);
        tmpTable.setGameElementsObservers(game);
        return tmpTable;
    }

    /**
     * Creates a new table with the given parameters.
     *
     * @param name                the name of the table
     * @param numberOfBumpers     the number of bumpers in the table
     * @param prob                the probability a {@link logic.gameelements.bumper.PopBumper}
     * @param numberOfTargets     the number of {@link logic.gameelements.target.SpotTarget}
     * @param numberOfDropTargets the number of {@link logic.gameelements.target.DropTarget}
     * @return a new table determined by the parameters
     */
    public Table newFullPlayableTable(String name, int numberOfBumpers, double prob, int numberOfTargets,
                                      int numberOfDropTargets) {
        Table tmpTable = GameTable.getFullTable(name, numberOfBumpers, prob, numberOfDropTargets, numberOfTargets);
        tmpTable.setGameElementsObservers(game);
        return tmpTable;
    }

    /**
     * Gets the list of bumpers in the current table.
     *
     * @return the list of bumpers
     * @see Bumper
     */
    public List<Bumper> getBumpers() {
        return table.getBumpers();
    }

    /**
     * Gets the list of targets in the current table.
     *
     * @return the list of targets
     * @see Target
     */
    public List<Target> getTargets() {
        return table.getTargets();
    }

    /**
     * Gets the name of the current table.
     *
     * @return the name of the current table
     */
    public String getTableName() {
        return table.getTableName();
    }

    /**
     * Gets the current number of available balls to play.
     *
     * @return the number of available balls
     */
    public int getAvailableBalls() {
        return game.getBalls();
    }

    /**
     * Gets the points earned so far.
     *
     * @return the earned score
     */
    public int getCurrentScore() {
        return game.getScore();
    }

    /**
     * Gets the current table.
     *
     * @return the current table
     * @see Table
     */
    public Table getCurrentTable() {
        return table;
    }

    /**
     * Sets a new table to play.
     *
     * @param newTable the new table
     */
    public void setGameTable(Table newTable) {
        DropTargetBonus.getInstance().resetCounterTriggers();
        JackPotBonus.getInstance().resetCounterTriggers();
        ExtraBallBonus.getInstance().resetCounterTriggers();
        table = newTable;
        table.setGameElementsObservers(game);
    }

    /**
     * Reduces the number of available balls by 1 and returns the new number.
     *
     * @return the new number of available balls
     */
    public int dropBall() {
        game.dropBall();
        return game.getBalls();
    }

    /**
     * Checks whether the game is over or not. A game is over when the number of available balls are 0.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean gameOver() {
        return game.isGameOver();
    }
}
