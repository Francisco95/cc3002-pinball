package logic.bonus;

import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * test interactions of many targets/bumpers with the bonuses
 * to see more specific tests for interactions go
 * to {@link logic.gameelements.target.DropTargetTest} or
 * {@link logic.gameelements.bumper.KickerBumperTest}.
 * Also test some specific behavior of the bonuses.
 *
 * @author Fancisco Muñoz Ponce. on date: 26-05-18
 */
public class BonusTest {
    private ArrayList<Target> targets;
    private ArrayList<Bumper> bumpers;
    private DropTargetBonus dropTargetBonus;
    private ExtraBallBonus extraBallBonus;
    private JackPotBonus jackPotBonus;
    private Game game;

    @Before
    public void setUp() {
        targets = new ArrayList<>();
        bumpers = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            targets.add(new DropTarget());
        }
        for (int i = 0; i < 2; i++){
            targets.add(new SpotTarget());
        }
        for (int i = 0; i < 3; i++){
            bumpers.add(new KickerBumper());
        }
        for (int i = 0; i < 1; i++){
            bumpers.add(new PopBumper());
        }
        game = new Game();
        dropTargetBonus = DropTargetBonus.getInstance();
        extraBallBonus = ExtraBallBonus.getInstance();
        jackPotBonus = JackPotBonus.getInstance();
    }

    @Test
    public void getInstance() {
    }

    @Test
    public void setObservers() {
    }

    @Test
    public void setCounterTriggers() {
    }

    @Test
    public void timesTriggered() {
    }

    @Test
    public void trigger() {
    }

    @Test
    public void isBonusOfPoints() {
    }

    @Test
    public void getBonusValue() {
    }

    @Test
    public void update() {
    }

    @Test
    public void accept() {
    }
}