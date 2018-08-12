package gui.handlers;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import controller.Game;
import gui.GameTypes;
import javafx.geometry.Point2D;

public class BallWallHandler extends CollisionHandler {

    public BallWallHandler() {
        super(GameTypes.BALL, GameTypes.WALL);
    }

    @Override
    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxWall, HitBox boxBall) {
        PhysicsComponent physics = ball.getComponent(PhysicsComponent.class);
        if (boxWall.getName().contains("Horizontal")){
            Point2D vel = physics.getLinearVelocity();
            Point2D newVel = new Point2D(vel.getX(), - vel.getY());
            physics.setLinearVelocity(newVel);
        }
        else if (boxWall.getName().contains("Vertical")){
            Point2D vel = physics.getLinearVelocity();
            Point2D newVel = new Point2D(-vel.getX(), vel.getY());
            physics.setLinearVelocity(newVel);
        }
        else{
            System.out.println("Not defined");
        }
    }

}
