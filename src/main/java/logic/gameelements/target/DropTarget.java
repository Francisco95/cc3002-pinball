package main.java.logic.gameelements.target;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class DropTarget implements Target {
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
