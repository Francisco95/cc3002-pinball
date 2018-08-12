package gui.table;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import gui.Config;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.gameelements.GameElementType;

import java.io.IOError;
import java.util.*;

import static com.almasb.fxgl.app.DSLKt.spawn;

public abstract class GameTable {

    private List<Entity> targets = new ArrayList<>();
    private List<Entity> bumpers = new ArrayList<>();
    private List<Entity> gameElement = new ArrayList<>();
    private Pane storyPane = new Pane();
    private Pane rootPane;

    protected List<Coordinates> positions = Coordinates.arrayOfPositions(Config.WIDTH,
            Config.HEIGHT - 200, 2.3 * Config.BALL_RADIUS);
    protected final Random random = FXGLMath.getRandom();

    public GameTable(){
        System.out.println("hola");
        System.out.println(positions.size());
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

    public abstract void init();

    void addTarget(Entity entity) {
        targets.add(entity);
    }

    void addBumper(Entity entity) {
        bumpers.add(entity);
    }

    void addGameElement(Entity entity){
        gameElement.add(entity);
    }

    Entity spawnGameElement(double x, double y, GameElementType type){
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

}
