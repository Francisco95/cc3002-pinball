package gui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 *
 * Class for definition of all the entities that are going to be used in the game,
 * particularly here we define the visual characteristics of WALLS, BALL, BACKGROUND,
 * FLIPPERS and gameeleemnts such as BUMPERS and TARGETS.
 *
 * @author Francisco Munoz Ponce
 */
public class GameFactory {

    private static double ballRadius = 10;

    /**
     * Creation of a Ball, should be a circle that can collide with every elements
     * in the scene, this is the main object of the game.
     *
     * Should be declared as DYNAMIC type but due to problems with FXGL library it will
     * be KINETIC type and the physical movement will be defined in {@link BallPhysics}
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
     * @param thickness define the hit box of the walls
     * @return the WALL entity
     */
    public static Entity walls(int thickness){
        Entity walls = Entities.makeScreenBounds(thickness);
        walls.setType(GameTypes.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    /**
     * Definition of the background of the platform (table), for
     * simplicity will be just a black background
     *
     * @param width of the background
     * @param height of the background
     * @return the background entity
     */
    public static Entity background(int width, int height){
        return Entities.builder()
                .viewFromNode(new Rectangle(width, height, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

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
     * kicker --------- circle ------
     *
     * @param x                                 X position of the Bumper
     * @param y                                 Y position of the Bumper
     * @param color                             color of the bumper (on ground level)
     * @param shape                             Shape of the bumper
     * @return                                  the entity BUMPER
     */
    public static Entity bumperShape(double x, double y, Color color, Shape shape){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(
                new FixtureDef().density(0.4f).restitution(1.3f));
        return boxBuilder(x, y, 0, ballRadius * 0.9, ballRadius * 0.9, "Bumper", color)
                .with(physics, new CollidableComponent(true))
                .build();
    }

    /**
     * Creation of the targets, with characteristics:
     *
     * TARGET -------- SHAPE -------------- COLOR (active / non-active)
     *
     * spot   -------- square (small) ---
     * drop   -------- square (small) -----
     *
     * @param x                                 X position of the target
     * @param y                                 Y position of the target
     * @param color                             color of the target (on active mode)
     * @param shape                             shape of the target
     * @return                                  the entity TARGET
     */
    public static Entity targetShape(double x, double y, Color color, Shape shape){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(
                new FixtureDef().density(0.4f).restitution(0.8f));
        return boxBuilder(x, y, 0, ballRadius * 0.9, ballRadius * 0.9, "Target", color)
                .with(physics, new CollidableComponent(true))
                .build();
    }


    /**
     * Additional creation of lines that could be useful, defined as statics
     *
     * @param x X position
     * @param y Y position
     * @param initAngle rotation of the object
     * @param length length of the line
     * @param color the color of the figure
     * @return the entity line
     */
    public static Entity line(double x, double y, double initAngle, double length, Color color){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(
                new FixtureDef().density(1f).restitution(0.8f));
        return boxBuilder(x, y, initAngle, length, 1, "line", color)
                .with(physics, new CollidableComponent(true))
                .build();
    }

    /**
     * Additional creation of box that could be useful, defined as statics
     *
     * @param x X position
     * @param y Y position
     * @param angle rotation of the object
     * @param width width of the rectangle
     * @param height length of the rectangle
     * @param color the color of the figure
     * @return the entity rectangle
     */
    public static Entities.EntityBuilder boxBuilder(double x, double y, double angle, double width,
                                                    double height, String name, Color color){
        return Entities.builder()
                .at(x, y)
                .rotate(angle)
                .bbox(new HitBox(name, BoundingShape.box(width, height)))
                .viewFromNode(new Rectangle(width, height, color));
    }

    public static Entities.EntityBuilder circleBuilder(double x, double y, double rad, Color color, String name){
        return Entities.builder()
                .at(x, y)
                .bbox(new HitBox(name, BoundingShape.circle(rad)))
                .viewFromNode(new Circle(rad, color));
    }


}
