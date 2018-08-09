package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

/**
 *
 * Main class for the implementation of all the graphic part, run main method
 * will run the game
 *
 * @author Francisco Munoz Ponce
 *
 */
public class GameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Pinball (pre alpha)");
        gameSettings.setVersion("0.0");
    }

    @Override
    protected void initGame(){}

    @Override
    protected void initInput(){}

    @Override
    protected void initPhysics(){
    }

    public static void main(String[] args){
        launch(args);
    }
}
