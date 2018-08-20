package gui.table;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import gui.spawndata.CustomSpawnData;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.gameelements.GameElementType;

import java.util.*;

import static com.almasb.fxgl.app.DSLKt.spawn;
import static gui.Config.*;

/**
 * Class tat define the structure of a game table, here we define some utility walls
 * and the flippers but no the game elements.
 *
 * @author Francisco Munoz Ponce
 */
public abstract class GameTable {

    private List<Entity> targets = new ArrayList<>();
    private List<Entity> bumpers = new ArrayList<>();
    private List<Entity> gameElement = new ArrayList<>();
    private Pane storyPane = new Pane();
    private Pane rootPane;

    protected List<Coordinates> positions = Coordinates.arrayOfPositions(WIDTH,
            HEIGHT - 250, 4 * BALL_RADIUS);
    protected final Random random = FXGLMath.getRandom();

    public GameTable(){
        Rectangle bg = new Rectangle(FXGL.getAppWidth() - 20, 200, Color.color(0, 0, 0, 0.6));
        bg.setArcWidth(25);
        bg.setArcHeight(25);
        bg.setStroke(Color.color(0.1, 0.2, 0.86, 0.76));
        bg.setStrokeWidth(3);

        storyPane.setTranslateX(10);
        storyPane.setTranslateY(25);

        rootPane = new Pane(bg, storyPane);
        rootPane.setTranslateX(10);
        rootPane.setTranslateY(FXGL.getAppHeight() - 200);
    }

    public abstract void setTableElements();
    public void resetTableElements(GameApplication gameApp){
        for (Entity target : targets){
            target.removeFromWorld();
        }

        for (Entity bumper : bumpers){
            Body body = bumper.getComponent(PhysicsComponent.class).getBody();
            bumper.removeComponent(PhysicsComponent.class);

            gameApp.getMasterTimer().runOnceAfter(() -> gameApp.getPhysicsWorld().getJBox2DWorld().destroyBody(body), Duration.seconds(0.1));

            gameApp.getGameWorld().removeEntity(bumper);
        }

        targets.clear();
        bumpers.clear();
    }

    public void initEmptyStage(){
        spawnFlippers();
        spawnFlipperSideWalls();
    }

    public Entity spawnGameElement(double x, double y, GameElementType type){
        String entityName;
        if (type.equals(GameElementType.POP_BUMPER)){
            entityName = "PopBumper";
        }
        else if (type.equals(GameElementType.KICKER_BUMPER)){
            entityName = "KickerBumper";
        }
        else if (type.equals(GameElementType.DROP_TARGET)){
            entityName = "DropTarget";
        }
        else if (type.equals(GameElementType.SPOT_TARGET)){
            entityName = "SpotTarget";
        }
        else{
            entityName = null;
            System.out.println("invalid game type");
            System.out.println(type);
        }

        return spawn(entityName, x, y);
    }

    public void spawnFlippers(){
        spawn("LeftFlipper", FLIPPER_RIGHT_POSITION, FLIPPER_VERTICAL_POSITION);
        spawn("RightFlipper", FLIPPER_LEFT_POSITION, FLIPPER_VERTICAL_POSITION);
    }

    private CustomSpawnData.Builder dataBuilder;
    private CustomSpawnData data;

    private void spawnDiagonalNormalFlipperWalls(){
        dataBuilder = new CustomSpawnData.Builder()
                .from(0, FLIPPER_VERTICAL_POSITION - 30)
                .shape(FLIPPER_WIDTH/2 + 30, FLIPPER_HEIGHT + 3);

        data = dataBuilder.traslateToX(FLIPPER_RIGHT_POSITION + 75)
                .rotate(FLIPPER_RIGHT_BASIC_ANGLE)
                .build();
        spawn("CustomWall", data);
        data = dataBuilder.traslateToX(FLIPPER_LEFT_POSITION - 25)
                .rotate(FLIPPER_LEFT_BASIC_ANGLE)
                .build();
        spawn("CustomWall", data);
    }

    private void spawnVerticalNormalFlipperWalls(){
        dataBuilder = new CustomSpawnData.Builder()
                .from(0, FLIPPER_VERTICAL_POSITION - 105)
                .shape(FLIPPER_WIDTH/2 + 30, FLIPPER_HEIGHT + 3);

        data = dataBuilder.traslateToX(FLIPPER_RIGHT_POSITION + FLIPPER_WIDTH - 40)
                .rotate(-90)
                .build();
        spawn("CustomWall", data);
        data = dataBuilder.traslateToX(FLIPPER_LEFT_POSITION - 75)
                .rotate(90)
                .build();
        spawn("CustomWall", data);
    }

    private void spawnDiagonalElasticFlipperWalls(){
        dataBuilder = new CustomSpawnData.Builder()
                .from(0, FLIPPER_VERTICAL_POSITION - 125)
                .shape(FLIPPER_WIDTH/2, FLIPPER_HEIGHT - 2)
                .withRestitution(1.5f)
                .paint(Color.LIGHTSKYBLUE);
        data = dataBuilder.traslateToX(FLIPPER_LEFT_POSITION + 25)
                .rotate(50)
                .build();
        spawn("CustomWall", data);
        data = dataBuilder.traslateToX(FLIPPER_RIGHT_POSITION + 25 + 30)
                .rotate(-50)
                .build();
        spawn("CustomWall", data);
    }

    private void spawnSpikyWalls(){
        dataBuilder = new CustomSpawnData.Builder()
                .from(0, FLIPPER_VERTICAL_POSITION - 280)
                .shape(FLIPPER_WIDTH * 0.6, FLIPPER_WIDTH * 0.6)
                .rotate(45)
                .paint(Color.ROYALBLUE);
        data = dataBuilder.traslateToX(-55).build();
        spawn("CustomWall", data);
        data = dataBuilder.traslateToX(WIDTH - 45).build();
        spawn("CustomWall", data);
    }

    public void spawnFlipperSideWalls(){
        spawnSpikyWalls();

        // normal diagonal walls, should follow the line of the flippers
        spawnDiagonalNormalFlipperWalls();

        // normal vertical walls, must match with the normal diagonal walls
        spawnVerticalNormalFlipperWalls();

        // elastic diagonal walls
        spawnDiagonalElasticFlipperWalls();

    }

    public void addGameElement(Entity entity) {
        this.gameElement.add(entity);
    }

    public void addTarget(Entity target){
        this.targets.add(target);
    }

    public void addBumper(Entity bumper){
        this.bumpers.add(bumper);
    }
}
