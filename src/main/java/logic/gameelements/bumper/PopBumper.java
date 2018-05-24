package logic.gameelements.bumper;

/**
 * @author Fancisco Muñoz Ponce. on date: 17-05-18
 */
public class PopBumper extends AbstractBumper{
    /**
     * the number of points given by the bumper not upgraded
     */
    private int baseScore;

    /**
     * the number of points given by the bumper when it's upgraded
     */
    private int upgradedScore;

    /**
     * the number of hits needed to make an upgrade
     */
    private int hitsNeededToUpgrade;

    public PopBumper(){
        super(3, 100);
        this.hitsNeededToUpgrade = 3;
        this.baseScore = 100;
        this.upgradedScore = 300;
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
