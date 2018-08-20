package gui;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import gui.components.BallControl;
import gui.components.LeftFlipperControl;
import gui.components.RightFlipperControl;
import gui.spawndata.CustomSpawnData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Random;

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
        Random random = new Random();
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().restitution(0.35f).density(10f).friction(1f));
        physics.setOnPhysicsInitialized(
                () -> physics.setLinearVelocity(-100*(random.nextFloat()*2 + 1), -100*(random.nextFloat()*3 + 3))
        );

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
        walls.getComponent(PhysicsComponent.class).setFixtureDef(
                new FixtureDef().restitution(0.4f).density(0f).friction(0.4f)
        );
        return walls;
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
    @Spawns("LeftFlipper")
    public Entity newLeftFlipper(SpawnData data){
        return newFlipper(data, Config.FLIPPER_RIGHT_BASIC_ANGLE)
                .with(new LeftFlipperControl())
                .build();
    }

    @Spawns("RightFlipper")
    public Entity newRightFlipper(SpawnData data){
        return newFlipper(data, Config.FLIPPER_LEFT_BASIC_ANGLE)
                .with(new RightFlipperControl())
                .build();
    }

    private Entities.EntityBuilder newFlipper(SpawnData data, double angle){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        physics.setFixtureDef(new FixtureDef().restitution(0.6f).density(0.2f).friction(0f));

        return boxBuilder(angle, FLIPPER_WIDTH, FLIPPER_HEIGHT, COLOR_FLIPPER, "Flipper")
                .from(data)
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.FLIPPER);
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

    @Spawns("CustomWall")
    public Entity newCustomWall(CustomSpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(new FixtureDef().restitution(data.getRestitution()).density(0.1f).friction(0f));

        return boxBuilder(data.getAngle(), data.getLength(), data.getHeight(), data.getColor(), "CustomWall")
                .with(physics, new CollidableComponent(true))
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
     * @param name the name of the hit box
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
        physics.setFixtureDef(
                new FixtureDef().density(0.3f).restitution(1.5f));
        return circleBuilder(BUMPER_RADIUS, bumperColor, "Bumper")
                .with(physics, new CollidableComponent(true))
                .type(GameTypes.BUMPER);
    }

    private static Entities.EntityBuilder newTarget(Color targetColor){
        return squareBoxBuilder(0, TARGET_WIDTH, targetColor, "Target")
                .with(new CollidableComponent(true))
                .type(GameTypes.TARGET);
    }

}
