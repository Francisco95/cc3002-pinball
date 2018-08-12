package gui;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.event.Handles;
import com.almasb.fxgl.settings.GameSettings;
import controller.Game;
import facade.HomeworkTwoFacade;
import gui.components.BallControl;
import gui.components.TargetComponent;
import gui.events.GameEvent;
import gui.events.HitBumperEvent;
import gui.handlers.BallBumperHandler;
import gui.handlers.BallTargetHandler;
import gui.handlers.BallWallHandler;
import gui.table.Table1;
import logic.gameelements.GameElementType;
import logic.table.Table;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.app.DSLKt.spawn;
import static gui.GameFactory.*;

/**
 *
 * Main class for the implementation of all the graphic part, run main method
 * will run the game
 *
 * @author Francisco Munoz Ponce
 *
 */
public class GameApp extends GameApplication {
    private HomeworkTwoFacade facade = new HomeworkTwoFacade(new Game());

    private void initPlayableTable(Table newTable){
        facade.setGameTable(newTable);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth((int)Config.WIDTH);
        settings.setHeight((int)Config.HEIGHT);
        settings.setTitle("Pinball (pre alpha)");
        settings.setVersion("0.1");
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Score", facade.getCurrentScore());
        vars.put("Balls", facade.getAvailableBalls());
    }

    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new GameFactory());

        initStage();
    }

    @Override
    protected void initInput(){}

    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new BallTargetHandler());
        getPhysicsWorld().addCollisionHandler(new BallWallHandler());
        getPhysicsWorld().addCollisionHandler(new BallBumperHandler());
    }

    private void initStage(){
        initPlayableTable(facade.newFullPlayableTable("example", 2, 0.3, 7, 10));
//        initPlayableTable(facade.newPlayableTableWithNoTargets("example2",5, 0.5));
        System.out.println(facade.getTargets().size());
        new Table1(facade).init();
        spawn("Background");
//        spawn("TableLimits");
        spawnWalls();
        spawnBall();
    }

    private void spawnWalls(){
        Entity entity = spawn("HorizontalWall", 0, 0);
        spawn("HorizontalWall", 0, Config.HEIGHT - 20);
        spawn("VerticalWall", 0, 0);
        spawn("VerticalWall", Config.WIDTH - 20, 0);
    }

    private void spawnBall(){
        Entity ball = spawn("Ball", 50, 50);
    }

    @Handles(eventType = "EXTRA_BALL_BONUS")
    public void onExtraBallBonus(GameEvent event){
        // do nothing
    }

    @Handles(eventType = "DROP_TARGET_BONUS")
    public void onDropTargetBonus(GameEvent event){
//        List<Entity> targets = getGameWorld().getEntitiesByType(GameTypes.TARGET);
//        TargetComponent targetComponent;
//        for (Entity target : targets){
//            targetComponent = target.getComponent(TargetComponent.class);
//            if (targetComponent.targetType().equals(GameElementType.DROP_TARGET)){
//                if (!targetComponent.isActive()){
//                    target.setView(GameFigures.dropTargetActiveFigure);
//                }
//            }
//        }
//        List<Entity> bumpers = getGameWorld().getEntitiesByType(GameTypes.BUMPER);
//        for (Entity bumper : bumpers){
//            GameFigures.upgradeBumper(bumper);
//        }
    }

    @Handles(eventType = "JACK_POT_BONUS")
    public void onJackPotBonus(GameEvent event){
        // do nothing
    }

    public static void main(String[] args){
        launch(args);
    }
}
