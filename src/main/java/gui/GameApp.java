package gui;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.event.Handles;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.UI;
import controller.Game;
import facade.HomeworkTwoFacade;
import gui.components.*;
import gui.events.DeactivateTarget;
import gui.events.DropBallEvent;
import gui.events.GameEvent;
import gui.events.UpgradeBumperEvent;
import gui.handlers.*;
import gui.table.TableRandomPositions;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.gameelements.GameElementType;
import logic.table.Table;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.IntStream;

import static com.almasb.fxgl.app.DSLKt.*;
import static gui.Config.*;

/**
 *
 * Main class for the implementation of all the graphic part, run main method
 * will run the game
 *
 * @author Francisco Munoz Ponce
 *
 */
public class GameApp extends GameApplication implements Observer {
    private HomeworkTwoFacade facade = new HomeworkTwoFacade(new Game());
    private GameController uiController;
    private boolean isABallInGame;
    private TableRandomPositions gameTable;

    private void setNewPlayableTable(Table newTable){
        gameTable.resetTableElements(this);
        facade.setGameTable(newTable);
        gameTable.setTableElements();
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth((int) WIDTH);
        settings.setHeight((int) HEIGHT);
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

    private Entity getFlipper(Class<? extends Component> entityControllerClassName){
        return getGameWorld().getEntitiesByComponent(entityControllerClassName).get(0);
    }

    private FlipperController getFlipperControllerInstance(Class<? extends Component> entityConctrollerClassName){
        return (FlipperController) getFlipper(entityConctrollerClassName).getComponent(entityConctrollerClassName);
    }


    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("PushRightFlipper") {
            @Override
            protected void onActionBegin() {
                getFlipperControllerInstance(LeftFlipperControl.class).setPushed(true);
            }

            @Override
            protected void onActionEnd() {
                getFlipperControllerInstance(LeftFlipperControl.class).setPushed(false);
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("PushLeftFlipper") {
            @Override
            protected void onActionBegin() {
                getFlipperControllerInstance(RightFlipperControl.class).setPushed(true);
            }

            @Override
            protected void onActionEnd() {
                getFlipperControllerInstance(RightFlipperControl.class).setPushed(false);
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("NewBall") {
            @Override
            protected void onActionBegin() {
                if (!isABallInGame && !facade.gameOver()){
                    spawnBall();
                }
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("SetNewTable") {
            @Override
            protected void onActionBegin() {
                setNewPlayableTable(facade.newFullPlayableTable("DefaultFullTable", 7, 0.3, 2, 4));
            }
        }, KeyCode.N);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0, 0); // gravity motor didn't works well, using a self defined instead.
        getPhysicsWorld().addCollisionHandler(new BallTargetHandler());
        getPhysicsWorld().addCollisionHandler(new BallBumperHandler());
        getPhysicsWorld().addCollisionHandler(new BallWallHandler());
    }

    private void initStage(){
        facade.addObserver(this);
        gameTable = new TableRandomPositions(facade);
        gameTable.initEmptyStage();
        spawn("Background");
        spawn("TableLimits");
    }

    @Override
    protected void initUI() {
        uiController = new GameController(getGameScene());

        UI ui = getAssetLoader().loadUI(Asset.FXML_MAIN_UI, uiController);
        uiController.getLabelScore().textProperty().bind(getip("Score").asString("Score: %d"));

        // this option can be used to see that the balls displaying are correct
//        uiController.getLabelBalls().textProperty().bind(getip("Balls").asString("Balls: %d"));

        IntStream.range(0, geti("Balls"))
                .forEach(i -> uiController.addBall());
        getGameScene().addUI(ui);
    }

    private void spawnBall(){
        spawn("Ball", Config.WIDTH - Config.BALL_RADIUS, Config.HEIGHT/2);
        uiController.useBall();
        isABallInGame = true;
    }

    @Handles(eventType = "EXTRA_BALL_BONUS")
    public void onExtraBallBonus(GameEvent event){
        uiController.addBall();
    }

    @Handles(eventType = "DROP_TARGET_BONUS")
    public void onDropTargetBonus(GameEvent event){
        List<Entity> targets = getGameWorld().getEntitiesByType(GameTypes.TARGET);
        TargetComponent targetComponent;
        for (Entity target : targets){
            targetComponent = target.getComponent(TargetComponent.class);
            if (targetComponent.targetType().equals(GameElementType.DROP_TARGET)){
                FXGL.getEventBus().fireEvent(new DeactivateTarget(DeactivateTarget.DEACTIVATE_TARGET, target));
            }
        }
        List<Entity> bumpers = getGameWorld().getEntitiesByType(GameTypes.BUMPER);
        for (Entity bumper : bumpers){
            FXGL.getEventBus().fireEvent(new UpgradeBumperEvent(UpgradeBumperEvent.UPGRADE_BUMPER, bumper));
        }
    }

    @Handles(eventType = "JACK_POT_BONUS")
    public void onJackPotBonus(GameEvent event){
        // do nothing
    }

    @Handles(eventType = "UPGRADE_BUMPER")
    public void onUpgradeBumper(UpgradeBumperEvent event){
        Entity bumper = event.getBumper();
        BallBumperHandler.upgradeBumper(bumper);
        getMasterTimer().runOnceAfter(
                () -> {
                    bumper.getComponent(BumperComponent.class).reset();
                    BallBumperHandler.downgradeBumper(bumper);
                }, Duration.seconds(Config.N1));
    }

    @Handles(eventType = "DEACTIVATE_TARGET")
    public void onDeactivateTarget(DeactivateTarget event){
        Entity target = event.getTargetGame();
        BallTargetHandler.deactivateTarget(target);
        TargetComponent targetComponent = target.getComponent(TargetComponent.class);
        int time;
        if (targetComponent.targetType().equals(GameElementType.DROP_TARGET)){
            time = Config.N3;
        }
        else{
            time = Config.N2;
        }
        getMasterTimer().runOnceAfter(
                () -> {
                    target.getComponent(TargetComponent.class).reset();
                    BallTargetHandler.activateTarget(target);
                }, Duration.seconds(time));
    }

    @Handles(eventType = "DROP_BALL")
    public void onDropBall(DropBallEvent event){
        Entity ball = event.getBall();
        Body body = ball.getComponent(PhysicsComponent.class).getBody();
        ball.removeComponent(PhysicsComponent.class);

        getMasterTimer().runOnceAfter(() -> getPhysicsWorld().getJBox2DWorld().destroyBody(body), Duration.seconds(0.1));

        getGameWorld().removeEntity(ball);
        isABallInGame = false;
        if (facade.getAvailableBalls() == 1){ // is game over
            showGameOver();
        }
        else { // drop a ball
            facade.dropBall();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof HomeworkTwoFacade){
            set("Score", facade.getCurrentScore());
            set("Balls", facade.getAvailableBalls());
        }
    }

    private void showGameOver(){
        getDisplay().showConfirmationBox("Demo Over. Play Again?", yes -> {
            if (yes) {
                getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
                startNewGame();
                facade.resetGame();
            } else {
                exit();
            }
        });
    }

    public static void main(String[] args){
        launch(args);
    }
}
