package main.java.logic.gameelements.target;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTarget implements Target {
    private Random randomProb;


    public void setSeed(int seed){
        randomProb.setSeed(seed);
    }
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public int hit() {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

}
