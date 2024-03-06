package invaders;

import invaders.facade.GameFacade;
import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Space Invaders Menu");
        VBox menu = new VBox(10);

        Button buttonEasy = new Button("Easy");
        buttonEasy.setOnAction(e -> {

            GameFacade startGame = new GameFacade(primaryStage);
            startGame.startGame("src/main/resources/config_easy.json");
        });
        menu.getChildren().add(buttonEasy);

        Button buttonMedium = new Button("Medium");
        buttonMedium.setOnAction(e -> {

            GameFacade startGame = new GameFacade(primaryStage);
            startGame.startGame("src/main/resources/config_medium.json");
        });
        menu.getChildren().add(buttonMedium);

        Button buttonHard = new Button("Hard");
        buttonHard.setOnAction(e -> {
            GameFacade startGame = new GameFacade(primaryStage);
            startGame.startGame("src/main/resources/config_hard.json");
        });
        menu.getChildren().add(buttonHard);

        Scene scene = new Scene(menu, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}