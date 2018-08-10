package gui.components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.target.Target;

public class TargetComponent extends Component {

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




}
