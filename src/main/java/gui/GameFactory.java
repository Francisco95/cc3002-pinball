package gui;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import gui.components.BallControl;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static gui.Config.*;


/**
 *
 * Class for definition of all the entities that are going to be used in the game,
 * particularly here we define the visual characteristics of WALLS, BALL, BACKGROUND,
 * FLIPPERS and gameeleemnts such as BUMPERS and TARGETS.
 *
 * @author Francisco Munoz Ponce
 */
public class GameFactory implements EntityFactory {




    /**
     * Creation of a Ball, should be a circle that can collide with every elements
     * in the scene, this is the main object of the game.
     *
     * Should be declared as DYNAMIC type but due to problems with FXGL library it will
     * be KINETIC type and the physical movement will be defined in {@link BallControl}
     * and in the handler of collisions.
     *
     * @param data position of the entity
     * @return the BALL entity
     */
    @Spawns("Ball")
    public Entity newBall(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);

        return circleBuilder(BALL_RADIUS, COLOR_BALL, "Box")
                .from(data)
                .type(GameTypes.BALL)
                .with(physics, new CollidableComponent(true))
                .with(new BallControl())
                .build();
    }

    /**
     * Creation of the four walls of the screen, these are limits on the movement of the ball
     *
     * @return the WALL entity
     */
    @Spawns("TableLimits")
    public Entity newTableLimits(SpawnData data){
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(GameTypes.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }

    @Spawns("HorizontalWall")
    public Entity newHorizontalWall(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return boxBuilder(0, WIDTH, 20, Config.COLOR_BACKGROUND, "HorizontalWall")
                .from(data)
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.WALL)
                .build();
    }

    @Spawns("VerticalWall")
    public Entity newVerticalWall(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return boxBuilder(0, 20, HEIGHT, Config.COLOR_BACKGROUND, "VerticalWall")
                .from(data)
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.WALL)
                .build();
    }

    /**
     * Definition of the background of the platform (table), for
     * simplicity will be just a black background
     *
     * @return the background entity
     */
    @Spawns("Background")
    public Entity newBackground(SpawnData data){
        return Entities.builder()
                .viewFromNode(new Rectangle(WIDTH, HEIGHT, COLOR_BACKGROUND))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    /**
     * Creation of the two flippers, both are going to be
     * just rectangular figures which can rotate, color will
     * be a type of gray and the position will be fixed
     *
     * @return the FLIPPER entity
     */
    @Spawns("Flipper")
    public Entity newFlipper(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);

        return boxBuilder(0, FLIPPER_WIDTH, FLIPPER_HEIGHT, COLOR_FLIPPER, "Flipper")
                .from(data)
                .with(physics, new CollidableComponent(true))
                .build();
    }

    /**
     * Creation of the bumpers, with characteristics:
     *
     * BUMPER --------- SHAPE --------- COLOR (ground level / level-up)
     *
     * pop    --------- circle --------
     * kicker --------- circle ------
     *
     * @return                                  the entity BUMPER
     */
    @Spawns("KickerBumper")
    public Entity newKickerBumper(SpawnData data){
        return newBumper(COLOR_KICKER_BUMPER_BASE)
                .from(data)
                .build();
    }

    @Spawns("PopBumper")
    public Entity newPopBumper(SpawnData data){
        return newBumper(COLOR_POP_BUMPER_BASE)
                .from(data)
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
     * @return                                  the entity TARGET
     */
    @Spawns("DropTarget")
    public Entity newDropTarget(SpawnData data){
        return newTarget(COLOR_DROP_TARGET_ACTIVE)
                .from(data)
                .build();
    }

    @Spawns("SpotTarget")
    public Entity newSpotTarget(SpawnData data){
        return newTarget(COLOR_SPOT_TARGET_ACTIVE)
                .from(data)
                .build();
    }

    @Spawns("SmallWall")
    public Entity newSmallWall(SpawnData data){
        return boxBuilder(0, WIDTH/4, 5, COLOR_WALL, "SmallWall")
                .with(new CollidableComponent(true))
                .from(data)
                .type(GameTypes.WALL)
                .build();
    }

    @Spawns("BigWall")
    public Entity newBigWall(SpawnData data){
        return boxBuilder(0, HEIGHT/4, WIDTH/4, COLOR_WALL, "BigWall")
                .with(new CollidableComponent(true))
                .from(data)
                .type(GameTypes.WALL)
                .build();
    }

    /**
     * Additional creation of box that could be useful, defined as statics
     *
     * @param angle rotation of the object
     * @param width width of the rectangle
     * @param height length of the rectangle
     * @param color the color of the figure
     * @param name
     * @return an EntityBuilder of a rectangle
     */
    private static Entities.EntityBuilder boxBuilder(double angle, double width,
                                                     double height, Color color, String name){
        return Entities.builder()
                .rotate(angle)
                .bbox(new HitBox(name, BoundingShape.box(width, height)))
                .viewFromNode(new Rectangle(width, height, color));
    }

    private static Entities.EntityBuilder squareBoxBuilder(double angle, double width, Color color, String name){
        return boxBuilder(angle, width, width, color, name);
    }

    private static Entities.EntityBuilder circleBuilder(double rad, Color color, String name){
        return Entities.builder()
                .bbox(new HitBox(name, BoundingShape.circle(rad)))
                .viewFromNode(new Circle(rad, color));
    }

    private static Entities.EntityBuilder newBumper(Color bumperColor){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
//        physics.setFixtureDef(
//                new FixtureDef().density(1f).restitution(1.3f));
        return circleBuilder(BUMPER_RADIUS, bumperColor, "Bumper")
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.BUMPER);
    }

    private static Entities.EntityBuilder newTarget(Color targetColor){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
//        physics.setFixtureDef(
//                new FixtureDef().density(1f).restitution(1.3f));
        return squareBoxBuilder(0, TARGET_WIDTH, targetColor, "Target")
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.TARGET);
    }


}
