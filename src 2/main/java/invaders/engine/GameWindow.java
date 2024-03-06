package invaders.engine;
import invaders.observer.*;

import java.util.List;
import java.util.ArrayList;

import invaders.ConfigReader;
import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.scene.control.Alert;
import javafx.util.Duration;


import java.util.concurrent.atomic.AtomicInteger;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import invaders.gameobject.Enemy;
import invaders.entities.Player;
import invaders.gameobject.GameObject;

public class GameWindow {
	private final int width;
    private final int height;
	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews =  new ArrayList<EntityView>();
    private Renderable background;
    private Timeline timeline1;
    Label timeLabel = new Label("  Time: 0:00");
    Label pointLabel = new Label("  Point: 0");


    //    private ArrayList<observer> timeObserver = new ArrayList<observer>();
    ConcreteTime time = new ConcreteTime();
    TimeObserver timeObserver = new TimeObserver(0);

    ConcreteScore score = new ConcreteScore();
    ScoreObserver scoreObserver = new ScoreObserver();
    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    // private static final double VIEWPORT_MARGIN = 280.0;

	public GameWindow(GameEngine model){
        this.model = model;
		this.width =  model.getGameWidth();
        this.height = model.getGameHeight();

        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

    }

	public void run() {
        timeLabel.setFont(Font.font("Arial", 23)); // Set the font size
        timeLabel.setTextFill(Color.BLUE); // Set the text color
        timeLabel.setLayoutX(0);
        timeLabel.setLayoutY(0);

        pointLabel.setFont(Font.font("Arial", 23)); // Set the font size
        pointLabel.setTextFill(Color.BLUE); // Set the text color
        pointLabel.setLayoutX(250);
        pointLabel.setLayoutY(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        this.pane.getChildren().add(timeLabel);
        this.pane.getChildren().add(pointLabel);

        time.addObserver(timeObserver);
        score.addObserver(scoreObserver);


    }

    private void draw(){
        model.update();

        time.update(17);
        if (time.getObserverList().size()>0){
            timeLabel.setText(time.getObserverList().get(0).toString());
        }

        for (Renderable renPoint: model.getPendingPoints()){
            if (renPoint.getRenderableObjectName().equals("EnemyProjectile")) {
                if (renPoint.getStrat().getName().equals("fast")){
                    score.getObserverList().get(0).update(2);
                }
                if (renPoint.getStrat().getName().equals("slow")){
                    score.getObserverList().get(0).update(1);
                }
            }
            if (renPoint.getRenderableObjectName().equals("Enemy")) {
                if (renPoint.getStrat().getName().equals("fast")){
                    score.getObserverList().get(0).update(4);
                }
                if (renPoint.getStrat().getName().equals("slow")){
                    score.getObserverList().get(0).update(3);
                }
            }
        }
        pointLabel.setText(score.getObserverList().get(0).toString());


        List<Renderable> renderables = model.getRenderables();
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (Renderable entity : renderables){
            if (!entity.isAlive()){
                for (EntityView entityView : entityViews){
                    if (entityView.matchesEntity(entity)){
                        entityView.markForDelete();
                    }
                }
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }


        model.getGameObjects().removeAll(model.getPendingToRemoveGameObject());
        model.getGameObjects().addAll(model.getPendingToAddGameObject());
        model.getRenderables().removeAll(model.getPendingToRemoveRenderable());
        model.getRenderables().addAll(model.getPendingToAddRenderable());


        //condition to stop
        if (model.getPlayer().getHealth() ==0){
            time.removeObserver(timeObserver);

        }

        boolean found = false;
        List<Renderable> gameObjects = model.getRenderables(); // Your list of game objects
        for (Renderable gameObject :gameObjects ){
            if (gameObject.isAlive()&&gameObject.getRenderableObjectName().equals("Enemy")){
                found = true;
            }
        }
        if(!found){
            time.removeObserver(timeObserver);
        }
        model.getPendingToAddGameObject().clear();
        model.getPendingToRemoveGameObject().clear();
        model.getPendingToAddRenderable().clear();
        model.getPendingToRemoveRenderable().clear();
        model.getPendingPoints().clear();

        entityViews.removeIf(EntityView::isMarkedForDelete);

    }

	public Scene getScene() {
        return scene;
    }
}
