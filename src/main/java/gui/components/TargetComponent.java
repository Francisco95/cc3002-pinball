package gui.components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.GameElementType;
import logic.gameelements.target.Target;

import java.util.Observable;
import java.util.Observer;

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



}
