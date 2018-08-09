package gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import javax.swing.text.html.parser.Entity;

/**
 *
 * Class for definition of all the entities that are going to be used in the game,
 * particularly here we define the visual characteristics of WALLS, BALL, BACKGROUND,
 * FLIPPERS and gameeleemnts such as BUMPERS and TARGETS.
 *
 * @author Francisco Munoz Ponce
 */
public class GameFactory {

    /**
     * Creation of a Ball, should be a circle that can collide with every elements
     * in the scene, this is the main object of the game.
     *
     * Should be declared as DYNAMIC type but due to problems with FXGL library it will
     * be KINETIC type and the physical movement will be defined in {@link GamePhysics}
     * where all the reaction to collisions will be defined
     *
     * @param x X position at start
     * @param y Y position at start
     * @return the BALL entity
     */
    public static Entity ball(double x, double y){return null;}

    /**
     *
     * Creation of the four walls, these are just objects that can collide with others
     *
     * @return the WALL entity
     */
    public static Entity walls(){return null;}

    /**
     * Definition of the background of the platform (table), for
     * simplicity will be just a black background
     *
     * @return the background entity
     */
    public static Entity background(){return null;}

    /**
     * Creation of the two flippers, both are going to be
     * just rectangular figures which can rotate, color will
     * be a type of gray and the position will be fixed
     *
     * @param x X position of the two flippers
     * @param y Y position of the two flippers
     * @return the FLIPPER entity
     */
    public static Entity flippers(double x, double y){return null;}

    /**
     * Creation of the bumpers, with characteristics:
     *
     * BUMPER --------- SHAPE --------- COLOR (ground level / level-up)
     *
     * pop    --------- circle --------
     * kicker --------- triangle ------
     *
     * @param x                                 X position of the Bumper
     * @param y                                 Y position of the Bumper
     * @param color                             color of the bumper (on ground level)
     * @param shape                             Shape of the bumper
     * @return                                  the entity BUMPER
     */
    public static Entity bumper(double x, double y, Color color, Shape shape){return null;}

    /**
     * Creation of the targets, with characteristics:
     *
     * TARGET -------- SHAPE -------------- COLOR (active / non-active)
     *
     * spot   -------- triangle (small) ---
     * drop   -------- square (small) -----
     *
     * @param x                                 X position of the target
     * @param y                                 Y position of the target
     * @param color                             color of the target (on active mode)
     * @param shape                             shape of the target
     * @return                                  the entity TARGET
     */
    public static Entity target(double x, double y, Color color, Shape shape){return null;}


    /**
     * Additional creation of rectangles that could be useful
     *
     * @param x X position
     * @param y Y position
     * @param width width of the rectangle
     * @param length length of the rectangle
     * @return the entity rectangle
     */
    public static Entity rectangle(double x, double y, double width, double length){return null;}

    /**
     * Additional creation of lines that could be useful
     *
     * @param x X position
     * @param y Y position
     * @param length length of the line
     * @return the entity line
     */
    public static Entity line(double x, double y, double length){return null;}


}
