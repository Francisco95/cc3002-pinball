package gui.components;

import gui.Config;
import gui.GameTypes;

import static java.lang.Math.abs;

/**
 * Definition of the movement of the left flipper, it an extension of the abstract class {@link FlipperController}
 *
 * @author Francisco Munoz Ponce
 */
public class LeftFlipperControl extends FlipperController {

    public LeftFlipperControl(){
        type = GameTypes.LEFT_FLIPPER;
    }

    @Override
    public boolean angleOverUpperLimit(double offset) {
        return offsetSpeed() > offset;
    }

    @Override
    public boolean angleUnderLowerLimit(double offset) {
        return offset < Config.FLIPPER_RIGHT_BASIC_ANGLE - angle;
    }

    private double offsetSpeed(){
        return basicOffsetSpeed() - Config.FLIPPER_MAX_ROTATION_ANGLE;
    }

    private double basicOffsetSpeed(){
        return angle - Config.FLIPPER_RIGHT_BASIC_ANGLE;
    }
    @Override
    public void push(){
        if (offsetSpeed() < 0){
            angularVelocity = Config.FLIPPER_SPEED;
        }
        else {
            angularVelocity = abs(offsetSpeed()) > 0.01 ? -offsetSpeed() / 4 : 0;
        }
    }

    @Override
    public void pull(){
        if (basicOffsetSpeed() > 0){
                angularVelocity = -Config.FLIPPER_SPEED/2;
        }
        else {
            angularVelocity = abs(basicOffsetSpeed()) > 0.01 ? -basicOffsetSpeed()/4 : 0;
        }
    }
}
