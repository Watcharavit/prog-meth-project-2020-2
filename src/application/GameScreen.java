package application;

import game.GameSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import resources.MusicsLibrary;

/**
 * The screen containing the game itself. This screen also serves as background
 * for {@link HomeScreen}. This class passes a large Pane to
 * {@link game.GameSingleton} to draw the game content. This class creates a
 * floating Pause button on the bottom left of the screen.
 *
 */
class GameScreen extends Screen {
	protected GameScreen() {
		StackPane container = new StackPane();
		container.setAlignment(Pos.BOTTOM_LEFT);

		Pane gamePane = new Pane();
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

	/**
	 * Upon focus, play the screen's music.
	 */
	@Override
	public void onFocus() {
		MusicsLibrary.playMusic(MusicsLibrary.GAME_PLAY);
	}
}
