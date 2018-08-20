package gui.components;


import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.GameTypes;

/**
 * definition of the movement of flippers, how it is controlled
 *
 * @author Francisco Munoz Ponce
 */
public abstract class FlipperController extends Component {
    protected double angle;
    protected boolean isPushed;
    protected GameTypes type;
    protected PhysicsComponent physics;
    protected double angularVelocity = 0;

    @Override
    public void onUpdate(double tpf) {
        angle = entity.getRotation() % 360;
        if (isPushed){
            push();
        }
        else
            pull();
        physics.setAngularVelocity(angularVelocity);
    }

    public void setPushed(boolean pushed) {
        isPushed = pushed;
    }

    public void nullVelocity(){
        entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
    }

    public abstract boolean angleOverUpperLimit(double offset);
    public abstract boolean angleUnderLowerLimit(double offset);
    public abstract void push();
    public abstract void pull();

}
