package logic.gameelements.target;

/**
 * Class that define behavior of a spotTarget.
 * Use singleton pattern to guarantee only one initialization per game.
 * Use Observer Pattern to notify observer that win X points(Game) or
 * to notify a change in active (JackPotBonus).
 * @see controller.Game
 * @see logic.bonus.JackPotBonus
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class SpotTarget extends AbstractTarget{

    /**
     * the instance of SpotTarget, this is part of Singleton Pattern
     */
    private static SpotTarget instance = null;

    /**
     * constructor declared as private for Singleton Pattern
     * by default set active to True.
     */
    private SpotTarget() {
        super(true, 0, null);
    }

    /**
     * the generator of instance for Singleton Pattern, this will generate
     * a new instance of SpotTarget only if there is no previous instance.
     * @return instance of SpotTarget
     */
    public static SpotTarget getInstance(){
        if (instance == null){
            instance = new SpotTarget();
        }
        return instance;
    }

    /**
     * in this case is always true and trigger a JackPotBonus
     * @return always True; always trigger a JackPotBonus
     */
    @Override
    public boolean bonusTriggered() {
        return true;
    }
}
