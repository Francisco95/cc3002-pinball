package test.java.logic.gameelements;

import main.java.controller.Game;
import main.java.logic.bonus.JackPotBonus;
import main.java.logic.gameelements.target.SpotTarget;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Observer;

import static org.junit.Assert.*;

/**
 * @author Fancisco Muñoz Ponce. on date: 18-05-18
 */
public class TargetTest {
    private Game game;
    private JackPotBonus jackPot;
    private SpotTarget spotTarget;

    @Before
    public void setUp() throws Exception {
        game = new Game(1);
        jackPot = JackPotBonus.getInstance();
        spotTarget = SpotTarget.getInstance();
        // then add the observerx
        spotTarget.setObservers(game, jackPot);
        jackPot.setObservers(game);
    }

    @After
    public void resetAll(){
        spotTarget.reset();
        game.setScore(0);
        spotTarget.setObservers(game, jackPot);
        jackPot.setCounterTriggers(0);
    }

    /**
     * test that method isActive() return as expected.
     * Also check that method hit() set Active to false.
     */
    @Test
    public void isActive() {
        // at first should be true
        boolean expected = true;
        assertEquals(expected, spotTarget.isActive());
        //then set to false and check this
        expected = false;
        spotTarget.hit();
        assertEquals(expected, spotTarget.isActive());
    }

    /**
     * check that after set active to False, cannot instantiate again to re-activate the spot
     */
    @Test
    public void getInstance() {
        assertTrue(spotTarget.isActive());
        spotTarget.hit();
        assertFalse(spotTarget.isActive());
        spotTarget = SpotTarget.getInstance();
        assertFalse(spotTarget.isActive());
    }

    /**
     * test that the only valid way to change the state of SpotTarget (means re-activate) is by reset.
     * This consider that methods setActiveTrue and setActiveFalse are created just to test and internal use.
     * also reset  do a clear of the list of observables.
     * this test run just after the test of getInstance() so the state of the instance will be not active
     */
    @Test
    public void reset() {
        assertTrue(spotTarget.isActive());
        spotTarget.reset();
        assertTrue(spotTarget.isActive());
    }

    /**
     * test that it actually change the score on Game instance and also after hit() is not active anymore
     */
    @Test
    public void hit() {
        int scoreExpected = 0;
        assertEquals(scoreExpected, game.getScore());
        assertTrue(spotTarget.isActive());
        spotTarget.hit();
        scoreExpected = jackPot.getJackPotPoints();
        assertFalse(spotTarget.isActive());
        assertEquals(scoreExpected, game.getScore());
    }

    /**
     * check that do hit() just after a reset without add the jackPot observer doesnt rpoduce change in game score
     */
    @Test
    public void hitAfterReset(){
        spotTarget.reset();
        spotTarget.hit();
        int scoreExpected = 0;
        assertEquals(scoreExpected, game.getScore());

    }
}