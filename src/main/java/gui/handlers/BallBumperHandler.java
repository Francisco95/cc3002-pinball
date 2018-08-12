package gui.handlers;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import gui.BallPhysics;
import gui.GameTypes;
import gui.components.BumperComponent;
import gui.events.GameEvent;
import javafx.scene.shape.Circle;
import logic.bonus.ExtraBallBonus;

import static gui.Config.*;

public class BallBumperHandler extends CollisionHandler {
    public BallBumperHandler() {
        super(GameTypes.BALL, GameTypes.BUMPER);
    }

    @Override
    protected void onCollisionBegin(Entity ball, Entity bumper) {
        BallPhysics.ballReflectedWhenCollideToEntity(ball, bumper);
        BumperComponent bumperComponent = bumper.getComponent(BumperComponent.class);
        if (!bumperComponent.isUpgraded()){
            return;
        }

        int extraBallBonusCounter = ExtraBallBonus.getInstance().timesTriggered();
        bumperComponent.hit();
        upgradeBumper(bumper);
        if (extraBallBonusCounter < ExtraBallBonus.getInstance().timesTriggered()){
            FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.EXTRA_BALL_BONUS));
        }
    }

    private void upgradeBumper(Entity bumper){
        BumperComponent bumperComponent = bumper.getComponent(BumperComponent.class);
        switch (bumperComponent.bumperType()){
            case KICKER_BUMPER:
                bumper.setView(new Circle(BUMPER_RADIUS, COLOR_KICKER_BUMPER_UPGRADED));
            case POP_BUMPER:
                bumper.setView(new Circle(BUMPER_RADIUS, COLOR_POP_BUMPER_UPGRADED));
        }
    }
}
