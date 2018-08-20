package gui;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.scene.Viewport;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.UIController;
import facade.HomeworkTwoFacade;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Definition of the UI information, here we define how are displayed the score and the number of balls.
 * We use texture of small balls to show the number of balls.
 * @author Francisco Munoz Ponce
 */
public class GameController implements UIController {

    @FXML
    private Label labelScore;

    @FXML
    private Label labelBalls;

    @FXML
    private double ballsPosX;

    @FXML
    private double ballsPosY;

    private List<Texture> balls = new ArrayList<>();

    private GameScene gameScene;

    public GameController(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void init() {
        labelScore.setFont(FXGL.getUIFactory().newFont(18));
        labelBalls.setFont(FXGL.getUIFactory().newFont(18));
    }

    public Label getLabelScore(){
        return labelScore;
    }

    public Label getLabelBalls(){
        return labelBalls;
    }

    public void useBall(){
        Texture t = balls.get(balls.size() - 1);

        balls.remove(balls.size() - 1);
        gameScene.removeUINode(t);
    }

    public void addBall(){
        int numBalls = balls.size();

        Texture texture = FXGL.getAssetLoader().loadTexture("ball2.png", 16, 16);
        texture.setTranslateX(ballsPosX - 20 * numBalls);
        texture.setTranslateY(ballsPosY);

        balls.add(texture);
        gameScene.addUINode(texture);
    }
}
