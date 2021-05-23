package application;

import game.GameSingleton;
import gui.StyledButton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This screen is to be shown when the game is paused. This screen contains
 * buttons for: Resume, Restart, How to Play, and Back to Main Menu.
 *
 */
class PauseScreen extends Screen {
	protected PauseScreen() {

		StackPane stack = new StackPane();
		Pane pauseOverlayBackground = new Pane();
		pauseOverlayBackground
				.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		pauseOverlayBackground.setOpacity(0.5);

		VBox pauseMenu = new VBox();
		pauseMenu.setAlignment(Pos.CENTER);
		Label pausedLabel = new Label("Game Paused");
		pausedLabel.setFont(new Font(64));
		pausedLabel.setTextFill(Color.WHITE);
		pauseMenu.setSpacing(24);

		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				AppSingleton.hidePause();
				GameSingleton.resume();
			}
		});

		Button resumeButton = new StyledButton("Resume (ESC)");
		resumeButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hidePause();
			GameSingleton.resume();
		});
		resumeButton.setPrefWidth(200);

		Button restartButton = new StyledButton("Restart");
		restartButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hidePause();
			GameSingleton.start(true);
		});
		restartButton.setPrefWidth(200);

		Button helpButton = new StyledButton("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			AppSingleton.showHelp();
		});
		helpButton.setPrefWidth(200);

		Button homeButton = new StyledButton("Back to Main Menu");
		homeButton.setOnAction((ActionEvent e) -> {
			AppSingleton.resetToHome();
			GameSingleton.start(false);
		});
		homeButton.setPrefWidth(200);

		pauseMenu.getChildren().addAll(pausedLabel, resumeButton, restartButton, helpButton, homeButton);

		stack.getChildren().addAll(pauseOverlayBackground, pauseMenu);
		this.getChildren().add(stack);
	}
}
