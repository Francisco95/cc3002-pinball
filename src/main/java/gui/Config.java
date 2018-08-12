package gui;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Config {

    private Config(){}


    public static final double WIDTH = 500;
    public static final double HEIGHT = 800;

    public static final double FLIPPER_WIDTH = 30;
    public static final double FLIPPER_HEIGHT = 10;
    public static final Color COLOR_FLIPPER = Color.IVORY;
    public static final double FLIPPER_MAX_ROTATION_ANGLE = 50;
    public static final double FLIPPER_LEFT_BASIC_ANGLE = -30;
    public static final double FLIPPER_RIGHT_BASIC_ANGLE = 30;


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


    public static final Color COLOR_WALL = Color.WHITESMOKE;
    public static final Color COLOR_BACKGROUND = Color.BLACK;

    public static final Point2D GRAVITY = new Point2D(5, 5);



}
