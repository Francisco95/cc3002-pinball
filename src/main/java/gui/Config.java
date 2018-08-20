package gui;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * Class that define those variables that can affect the entire game, these are "constants" used
 * in different classes of the gui package.
 *
 * @author Francisco Munoz Ponce
 */
public class Config {

    private Config(){}

    // basic stage variables (dimensions, background and gravity)
    public static final double WIDTH = 500;
    public static final double HEIGHT = 900;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Point2D GRAVITY = new Point2D(0, 5);
    public static final Color COLOR_WALL = Color.WHITESMOKE;


    // game elements variables (dimensions and colors)
    public static final double BALL_RADIUS = 10;
    public static final Color COLOR_BALL = Color.SILVER;

    public static final double BUMPER_RADIUS = BALL_RADIUS * 1.6;
    public static final Color COLOR_KICKER_BUMPER_BASE = Color.YELLOW;
    public static final Color COLOR_KICKER_BUMPER_UPGRADED = Color.RED;
    public static final Color COLOR_POP_BUMPER_BASE = Color.LIGHTBLUE;
    public static final Color COLOR_POP_BUMPER_UPGRADED = Color.BLUE;

    public static final double TARGET_WIDTH = BALL_RADIUS * 1.5;
    public static final Color COLOR_SPOT_TARGET_ACTIVE = Color.ORANGERED;
    public static final Color COLOR_SPOT_TARGET_NON_ACTIVE = Color.DARKRED;
    public static final Color COLOR_DROP_TARGET_ACTIVE = Color.LIME;
    public static final Color COLOR_DROP_TARGET_NON_ACTIVE = Color.FORESTGREEN;
    
    
    // flipper variables (dimensions, rotation limits and position)
    public static final double FLIPPER_WIDTH = 160;
    public static final double FLIPPER_HEIGHT = 10;
    public static final Color COLOR_FLIPPER = Color.BLUEVIOLET;
    public static final double FLIPPER_MAX_ROTATION_ANGLE = 70;
    public static final double FLIPPER_RIGHT_BASIC_ANGLE = -30;
    public static final double FLIPPER_LEFT_BASIC_ANGLE = 30;
    public static final double FLIPPER_SPEED = 20;
    public static final double FLIPPER_RIGHT_POSITION =  WIDTH - FLIPPER_WIDTH - 75;
    public static final double FLIPPER_LEFT_POSITION = 75;
    public static final double FLIPPER_VERTICAL_POSITION = HEIGHT - 110;


    // timer times for automatic reset of game elements
    public static final int N1 = 10;
    public static final int N2 = 20;
    public static final int N3 = 120;


    /**
     * class for define variables on the assets
     */
    public static final class Asset{
        public static final String FXML_MAIN_UI = "main.fxml";
    }


}
