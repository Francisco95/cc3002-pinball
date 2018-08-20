package gui.handlers;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import facade.HomeworkTwoFacade;
import gui.GameTypes;
import gui.components.TargetComponent;
import gui.events.DeactivateTarget;
import gui.events.GameEvent;
import javafx.scene.shape.Rectangle;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.GameElementType;
import logic.gameelements.target.Target;

import static gui.Config.*;
import static logic.gameelements.GameElementType.DROP_TARGET;
import static logic.gameelements.GameElementType.SPOT_TARGET;

/**
 * Class that define the interaction ball hit target
 *
 * @author Francisco Munoz Ponce
 */
public class BallTargetHandler extends CollisionHandler{

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
        int dropTargetBonusCounter = DropTargetBonus.getInstance().timesTriggered();
        int jackPotBonusCounter = JackPotBonus.getInstance().timesTriggered();

        targetComponent.hit();
        FXGL.getEventBus().fireEvent(new DeactivateTarget(DeactivateTarget.DEACTIVATE_TARGET, target));
        checkBonusEvents(extraBallBonusCounter, dropTargetBonusCounter, jackPotBonusCounter);
    }


    public static void activateTarget(Entity target){
        TargetComponent targetComponent = target.getComponent(TargetComponent.class);
        if ( targetComponent.targetType().equals(DROP_TARGET)){
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_DROP_TARGET_ACTIVE));
        }
        else if (targetComponent.targetType().equals(SPOT_TARGET)){
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_SPOT_TARGET_ACTIVE));
        }
    }

    public static void deactivateTarget(Entity target){
        TargetComponent targetComponent = target.getComponent(TargetComponent.class);
        if ( targetComponent.targetType().equals(DROP_TARGET)){
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_DROP_TARGET_NON_ACTIVE));
        }
        else if (targetComponent.targetType().equals(SPOT_TARGET)){
            target.setView(new Rectangle(TARGET_WIDTH, TARGET_WIDTH, COLOR_SPOT_TARGET_NON_ACTIVE));
        }
    }

    public static void checkBonusEvents( int extraBallBonusCounter,int dropTargetBonusCounter, int jackPotBonusCounter){

        if (extraBallBonusCounter < ExtraBallBonus.getInstance().timesTriggered()) {
            FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.EXTRA_BALL_BONUS));
        }
        if (dropTargetBonusCounter < DropTargetBonus.getInstance().timesTriggered()) {
            FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.DROP_TARGET_BONUS));
        }
        if (jackPotBonusCounter < JackPotBonus.getInstance().timesTriggered()){
            FXGL.getEventBus().fireEvent(new GameEvent(GameEvent.JACK_POT_BONUS));
        }
    }

}
