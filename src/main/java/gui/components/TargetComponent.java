package gui.components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.GameElementType;
import logic.gameelements.target.Target;

import java.util.Observable;
import java.util.Observer;

/**
 * Class that define the  Target component on the Entity component which stores an instance
 * of {@link Target}
 *
 * @author Francisco Munoz Ponce
 */
public class TargetComponent extends Component{

    private Target target;

    public TargetComponent(Target target){
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
    }

    public void hit(){
        target.hit();
    }

    public GameElementType targetType(){
        return target.getType();
    }

    public boolean isActive(){
        return target.isActive();
    }

    public void reset(){
        target.reset();
    }



}
