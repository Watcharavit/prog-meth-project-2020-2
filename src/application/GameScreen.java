package application;

import game.GameSingleton;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

class GameScreen extends StackPane {
	boolean isPaused = false;
	StackPane pauseOverlay;
	Pane gamePane;
	protected GameScreen() {
		VBox container = new VBox();
		
		ControlsBox controlsPane = new ControlsBox(this);
		container.getChildren().add(controlsPane);
		
		gamePane = new Pane();
		GameSingleton.initialize(gamePane);
		container.getChildren().add(gamePane);
		this.getChildren().add(container);
		
		pauseOverlay = new PauseOverlay(this);
		
		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				if (isPaused) this.resume();
				else this.pause();
			}
		});
	}
	protected void pause() {
		isPaused = true;
		this.getChildren().add(pauseOverlay);
		GameSingleton.pause();
	}
	protected void resume() {
		isPaused = false;
		this.getChildren().remove(pauseOverlay);
		GameSingleton.resume();
	}
	protected void restart() {
		GameSingleton.destroy();
		GameSingleton.initialize(gamePane);
	}
}
