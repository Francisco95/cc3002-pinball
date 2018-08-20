package gui.components;

import gui.Config;
import gui.GameTypes;

import static java.lang.Math.abs;

/**
 * Class that define the movement of the right filler , this is an extension of {@link FlipperController}
 *
 * @author Francisco Munoz Ponce
 */
public class RightFlipperControl extends FlipperController {

    public RightFlipperControl(){
        type = GameTypes.RIGHT_FLIPPER;
    }


    @Override
    public boolean angleOverUpperLimit(double offset) {
        return offset < offsetSpeed();
    }

    @Override
    public boolean angleUnderLowerLimit(double offset) {
        return -basicOffsetSpeed() > offset;
    }

    private double offsetSpeed(){
        return basicOffsetSpeed() - Config.FLIPPER_MAX_ROTATION_ANGLE;
    }

    private double basicOffsetSpeed(){
        return Config.FLIPPER_LEFT_BASIC_ANGLE - angle;
    }
    @Override
    public void push() {
        if (offsetSpeed() < 0 ){
            angularVelocity = -Config.FLIPPER_SPEED;
        }
        else{
            angularVelocity = abs(offsetSpeed()) > 0.01 ? offsetSpeed()/4 : 0;
        }
    }

    @Override
    public void pull() {
        if (basicOffsetSpeed() > 0){
            angularVelocity = Config.FLIPPER_SPEED / 2;
        }
        else{
            angularVelocity = abs(basicOffsetSpeed()) > 0.01 ? basicOffsetSpeed()/4 : 0;
        }
    }
}
