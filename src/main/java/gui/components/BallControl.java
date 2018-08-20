package gui.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.Config;
import javafx.geometry.Point2D;

/**
 * Class where the controll of the ball is defined, in specific the gravity
 *
 * @author Francisco Munoz Ponce
 */
public class BallControl  extends Component {


    private PhysicsComponent physicsComponent;

    private Point2D velocity = new Point2D(0, -20);

    public BallControl() {
    }

    @Override
    public void onUpdate(double tpf) {
        getData();
        velocity = velocity.add(Config.GRAVITY);
        setData();
    }

    private void getData(){
        physicsComponent = entity.getComponent(PhysicsComponent.class);
        velocity = physicsComponent.getLinearVelocity();
    }

    private void setData(){
        physicsComponent.setLinearVelocity(velocity);
    }
}
