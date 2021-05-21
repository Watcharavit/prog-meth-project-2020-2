package application;

import game.GameSingleton;
import gui.UnfocusableButton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class GameScreen extends StackPane {
	private boolean isPaused = false;
	private final StackPane pauseOverlay;
	private final Pane gamePane;

	protected GameScreen() {

		StackPane container = new StackPane();
		container.setAlignment(Pos.BOTTOM_LEFT);

		gamePane = new Pane();
		GameSingleton.initialize(gamePane);
		container.getChildren().add(gamePane);

		Button pauseButton = new UnfocusableButton("Pause (ESC)");
		pauseButton.setOnAction((ActionEvent e) -> {
			this.pause();
		});
		container.getChildren().add(pauseButton);

		this.getChildren().add(container);

		pauseOverlay = new PauseOverlay(this);

		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				if (isPaused)
					this.resume();
				else
					this.pause();
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
		GameSingleton.start();
	}

	protected void onFocus() {
		restart();
	}
}
