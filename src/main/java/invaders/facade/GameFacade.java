package invaders.facade;
import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;

import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class GameFacade {
    private GameEngine gameEngine;
    private GameWindow gameWindow;
    private Stage primaryStage;

    public GameFacade(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startGame(String configPath) {
        gameEngine = new GameEngine(configPath);
        gameWindow = new GameWindow(gameEngine);
        gameWindow.run();
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(gameWindow.getScene());
        primaryStage.show();

    }

    public void stopGame() {
    }
}

