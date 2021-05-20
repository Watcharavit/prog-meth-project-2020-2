package application;

import gui.UnfocusableButton;
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

class PauseOverlay extends StackPane {
	protected PauseOverlay(GameScreen screenInstance) {
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
				screenInstance.resume();
			}
		});

		Button resumeButton = new UnfocusableButton("Resume (ESC)");
		resumeButton.setOnAction((ActionEvent e) -> {
			screenInstance.resume();
		});

		Button restartButton = new UnfocusableButton("Restart");
		restartButton.setOnAction((ActionEvent e) -> {
			screenInstance.restart();
			screenInstance.resume();
		});

		Button helpButton = new UnfocusableButton("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			screenInstance.switchToHelp();
		});

		Button homeButton = new UnfocusableButton("Back to Main Menu");
		homeButton.setOnAction((ActionEvent e) -> {
			screenInstance.switchToHome();
		});
		pauseMenu.getChildren().addAll(pausedLabel, resumeButton, restartButton, helpButton, homeButton);

		this.getChildren().addAll(pauseOverlayBackground, pauseMenu);
	}
}
