package gui.handlers;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import facade.HomeworkTwoFacade;
import gui.GameFigures;
import gui.GameTypes;
import gui.components.TargetComponent;
import gui.events.BonusTriggerEvent;
import gui.events.GameEvent;
import gui.events.HitTargetEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.bonus.Bonus;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.GameElementType;

import static gui.Config.COLOR_DROP_TARGET_NON_ACTIVE;
import static gui.Config.COLOR_SPOT_TARGET_NON_ACTIVE;
import static gui.Config.TARGET_WIDTH;

public class BallTargetHandler extends CollisionHandler{

    private HomeworkTwoFacade facade;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BallTargetHandler() {
        super(GameTypes.BALL, GameTypes.TARGET);
    }

    @Override
    protected void onCollisionBegin(Entity ball, Entity target) {
        TargetComponent targetComponent = target.getComponent(TargetComponent.class);
        if (!targetComponent.isActive()){
            return;
        }

        int extraBallBonusCounter = ExtraBallBonus.getInstance().timesTriggered();
        int jackPotBonusCounter = JackPotBonus.getInstance().timesTriggered();
        int dropTargetBonusCounter = DropTargetBonus.getInstance().timesTriggered();
        targetComponent.hit();
        if ( targetComponent.targetType().equals(GameElementType.DROP_TARGET)) {
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_DROP_TARGET_NON_ACTIVE));
            if (extraBallBonusCounter < ExtraBallBonus.getInstance().timesTriggered()) {
                FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.EXTRA_BALL_BONUS));
            }
            if (dropTargetBonusCounter < DropTargetBonus.getInstance().timesTriggered()) {
                FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.DROP_TARGET_BONUS));
            }
        }
        else if (targetComponent.targetType().equals(GameElementType.SPOT_TARGET)) {
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_SPOT_TARGET_NON_ACTIVE));
            FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.JACK_POT_BONUS));
        }
    }

//    @Override
//    protected void onHitBoxTrigger(Entity ball, Entity target, HitBox boxBall, HitBox boxTarget) {
////        target.getComponent(TargetComponent.class).hit();
////        BallPhysics.ballReflectedWhenCollideToEntity(ball, target);
//    }
}
