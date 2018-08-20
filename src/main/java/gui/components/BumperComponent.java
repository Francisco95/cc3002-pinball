package gui.components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.GameElementType;
import logic.gameelements.bumper.Bumper;

/**
 * Class that define the bumper component on a bumper entity, here we store the {@link Bumper} instance
 *
 * @author Francisco Munoz Ponce
 */
public class BumperComponent extends Component {

    private Bumper bumper;

    public BumperComponent(Bumper bumper){
        this.bumper = bumper;
    }

    public void hit(){
        bumper.hit();
    }

    public GameElementType bumperType(){
        return bumper.getType();
    }

    public boolean isUpgraded(){
        return bumper.isUpgraded();
    }

    public void reset(){
        bumper.downgrade();
    }

    public Bumper getBumper(){
        return bumper;
    }
}
