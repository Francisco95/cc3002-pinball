package gui.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.Config;
import javafx.geometry.Point2D;

public class BallControl  extends Component {


    private PhysicsComponent physicsComponent;

    private Point2D velocity = new Point2D(0, -20);

    public BallControl() {
    }

    @Override
    public void onUpdate(double tpf) {
        getData();
        System.out.println(velocity);
        velocity = velocity.add(Config.GRAVITY);
        System.out.println("new velocity");
        System.out.println(velocity);
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
