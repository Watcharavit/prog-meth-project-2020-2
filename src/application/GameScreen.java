package application;

import game.GameSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class GameScreen extends StackPane {
	private final Pane gamePane;

	protected GameScreen() {
		StackPane container = new StackPane();
		container.setAlignment(Pos.BOTTOM_LEFT);

		gamePane = new Pane();
		GameSingleton.initialize(gamePane);
		container.getChildren().add(gamePane);

		Button pauseButton = new Button("Pause (ESC)");
		pauseButton.setFocusTraversable(false);
		pauseButton.setOnAction((ActionEvent e) -> {
			GameSingleton.pause();
			AppSingleton.showPause();
		});
		container.getChildren().add(pauseButton);

		this.getChildren().add(container);

		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				GameSingleton.pause();
				AppSingleton.showPause();
			}
		});

		GameSingleton.start(false);
	}

}
