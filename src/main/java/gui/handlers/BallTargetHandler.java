package gui.handlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import gui.GameApp;
import gui.GameTypes;
import gui.components.TargetComponent;
import logic.gameelements.target.Target;

public class BallTargetHandler extends CollisionHandler{

    private GameApp app;

    /**
     * The order of types determines the order of entities in callbacks.
     *
     */
    public BallTargetHandler() {
        super(GameTypes.BALL, GameTypes.TARGET);
    }

    @Override
    protected void onCollisionBegin(Entity ball, Entity target) {
        target.getComponent(TargetComponent.class).hit();
        ball.getPositionComponent();
    }
}
