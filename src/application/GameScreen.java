package application;

import game.GameSingleton;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

class GameScreen extends StackPane {
	boolean isPaused = false;
	StackPane pauseOverlay;
	protected GameScreen() {
		
		Canvas gameCanvas = new Canvas();
		GameSingleton.initialize(gameCanvas);
		this.getChildren().add(gameCanvas);
		
		pauseOverlay = new PauseOverlay(() -> {
			this.resume();
		});
		
		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				if (isPaused) this.resume();
				else this.pause();
			}
		});
	}
	private void pause() {
		isPaused = true;
		this.getChildren().add(pauseOverlay);
		GameSingleton.pause();
	}
	private void resume() {
		isPaused = false;
		this.getChildren().remove(pauseOverlay);
		GameSingleton.resume();
	}
}
