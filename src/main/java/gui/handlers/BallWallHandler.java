package gui.handlers;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import gui.GameTypes;
import gui.events.DropBallEvent;

/**
 * Class that define interaction of hit wall, in
 * specific, we define when the ball hit the bottom wall and is lost.
 */
public class BallWallHandler extends CollisionHandler {

    public BallWallHandler() {
        super(GameTypes.BALL, GameTypes.WALL);
    }

    @Override
    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
        if (boxWall.getName().equals("BOT")){
            FXGL.getEventBus().fireEvent(new DropBallEvent(DropBallEvent.DROP_BALL, ball));
        }
    }

}
