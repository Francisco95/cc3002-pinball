package gui.handlers;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import gui.GameTypes;
import gui.components.BumperComponent;
import gui.events.GameEvent;
import gui.events.UpgradeBumperEvent;
import javafx.scene.shape.Circle;
import logic.bonus.ExtraBallBonus;
import logic.gameelements.GameElementType;

import static gui.Config.*;

/**
 * class that define the interaction ball hit a bumper
 *
 * @author Francisco Munoz Ponce
 */
public class BallBumperHandler extends CollisionHandler {

    public BallBumperHandler() {
        super(GameTypes.BALL, GameTypes.BUMPER);
    }

    @Override
    protected void onCollisionBegin(Entity ball, Entity bumper) {
//        BallPhysics.ballReflectedWhenCollideToCircularEntity(ball, bumper, Config.BUMPER_ELASTICITY);
        BumperComponent bumperComponent = bumper.getComponent(BumperComponent.class);
        boolean beforeHit = bumperComponent.isUpgraded();
        int extraBallBonusCounter = ExtraBallBonus.getInstance().timesTriggered();
        System.out.println("before hit counter is" + extraBallBonusCounter);
        bumperComponent.hit();
        if (!beforeHit && bumperComponent.isUpgraded()) {
            System.out.println("upgrade bumper");
//            upgradeBumper(bumper);
            FXGL.getEventBus().fireEvent(new UpgradeBumperEvent(UpgradeBumperEvent.UPGRADE_BUMPER, bumper));
            System.out.println("before hit counter is" + extraBallBonusCounter);
            System.out.println("after hit counter is" + ExtraBallBonus.getInstance().timesTriggered());
            if (extraBallBonusCounter < ExtraBallBonus.getInstance().timesTriggered()){
                System.out.println("bonus");
                FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.EXTRA_BALL_BONUS));
            }
        }
        System.out.println("  ");
    }

    public static void upgradeBumper(Entity bumper){
        BumperComponent bumperComponent = bumper.getComponent(BumperComponent.class);
        if (bumperComponent.bumperType().equals(GameElementType.KICKER_BUMPER)) {
            bumper.setView(new Circle(BUMPER_RADIUS, COLOR_KICKER_BUMPER_UPGRADED));
        }
        else if (bumperComponent.bumperType().equals(GameElementType.POP_BUMPER)) {
            bumper.setView(new Circle(BUMPER_RADIUS, COLOR_POP_BUMPER_UPGRADED));
        }
    }

    public static void downgradeBumper(Entity bumper){
        BumperComponent bumperComponent = bumper.getComponent(BumperComponent.class);
        if (bumperComponent.bumperType().equals(GameElementType.KICKER_BUMPER)) {
            bumper.setView(new Circle(BUMPER_RADIUS, COLOR_KICKER_BUMPER_BASE));
        }
        else if (bumperComponent.bumperType().equals(GameElementType.POP_BUMPER)) {
            bumper.setView(new Circle(BUMPER_RADIUS, COLOR_POP_BUMPER_BASE));
        }
    }
}
