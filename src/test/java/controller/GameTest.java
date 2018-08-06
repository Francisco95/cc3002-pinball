package controller;

import logic.bonus.Bonus;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import logic.table.GameTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 01-07-18
 */
public class GameTest {
    private Game game;
    private Table table;
    private int score;
    private int balls;

    @Before
    public void setUp() throws Exception {
        score = 7;
        balls = 3;
        game = new Game(balls, score);
        table = GameTable.getFullTable("testTable", 10,
                0.4, 6, 12);

        // set observers
        JackPotBonus.getInstance().setObservers(game);
        DropTargetBonus.getInstance().setObservers(game);
        ExtraBallBonus.getInstance().setObservers(game);
        table.setGameElementsObservers(game);
    }

    @Test
    public void addToScore() {
        score = game.getScore();
        game.addToScore(347);
        assertEquals(score + 347, game.getScore());
    }

    @Test
    public void getScore() {
        assertEquals(score, game.getScore());
    }

    @Test
    public void setScore() {
        score = 100234;
        game.setScore(score);
        assertEquals(score, game.getScore());
    }

    @Test
    public void addToBalls() {
        balls = game.getBalls();
        game.addToBalls(666);
        assertEquals(balls + 666, game.getBalls());
    }

    @Test
    public void getBalls() {
        assertEquals(balls, game.getBalls());
    }

    @Test
    public void setBalls() {
        balls = 56784;
        game.setBalls(balls);
        assertEquals(balls, game.getBalls());
    }

    @Test
    public void dropBall() {
        balls = game.getBalls();
        game.dropBall();
        assertEquals(balls - 1, game.getBalls());
        game.dropBall();
        assertEquals(balls - 2, game.getBalls());
    }

    @Test
    public void isGameOver() {
        assertFalse(game.isGameOver());
        game.setBalls(0);
        assertTrue(game.isGameOver());
        game.setBalls(-123342);
        assertTrue(game.isGameOver());
    }

    @Test
    public void constructions() {
        // default construction
        game = new Game();
        assertEquals(3, game.getBalls());
        assertEquals(0, game.getScore());

        // construction only declaring the initial number of balls
        game = new Game(4);
        assertEquals(4, game.getBalls());
        assertEquals(0, game.getScore());
    }

    @Test
    public void triggeredBonus() {
        // a bonus was triggered, then...
        Bonus bonus = JackPotBonus.getInstance();
        assertEquals(score, game.getScore());
        game.triggeredBonus(bonus);
        assertEquals(score + bonus.getBonusValue(), game.getScore());

        bonus = ExtraBallBonus.getInstance();
        assertEquals(balls, game.getBalls());
        game.triggeredBonus(bonus);
        assertEquals(balls + bonus.getBonusValue(), game.getBalls());
    }

    @Test
    public void hitBumper() {
        Bumper bumper = new KickerBumper();
        assertEquals(score, game.getScore());
        game.hitBumper(bumper);
        assertEquals(score + bumper.getScore(), game.getScore());
    }

    @Test
    public void hitTarget() {
        Target target = new DropTarget();
        assertEquals(score, game.getScore());
        target.setActive(false);
        game.hitTarget(target);
        assertEquals(score + target.getScore(), game.getScore());
    }
}