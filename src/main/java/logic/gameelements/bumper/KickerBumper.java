package logic.gameelements.bumper;


/**
 * Class that define behavior of a kicker bumper
 * Use of Observer Pattern to notify observers that win X points (Game)
 * or to notify a trigger of bonus (ExtraBallBonus)
 * all this behavior is defined in {@link logic.gameelements.bumper.AbstractBumper}.
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class KickerBumper extends AbstractBumper {

    /**
     * the number of points given by the bumper not upgraded
     */
    private int baseScore;

    /**
     * the number of points given by the bumper when it's upgraded
     */
    protected int upgradedScore;

    /**
     * the number of hits needed to make an upgrade
     */
    protected int hitsNeededToUpgrade;

    public KickerBumper() {
        super(5, 500);
        this.hitsNeededToUpgrade = 5;
        this.baseScore = 500;
        this.upgradedScore = 1000;
    }

    @Override
    public boolean isUpgraded() {
        return compareRemainingHits(0) && compareScore(upgradedScore);
    }

    @Override
    public void upgrade() {
        setRemainingHitsToUpgrade(0);
        setScore(upgradedScore);
    }

    @Override
    public void downgrade() {
        setRemainingHitsToUpgrade(hitsNeededToUpgrade);
        setScore(baseScore);
    }
}
