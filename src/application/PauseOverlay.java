package application;

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
	StackPane pauseOverlay;
	protected PauseOverlay(Runnable resume) {
		Pane pauseOverlayBackground = new Pane();
		pauseOverlayBackground.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		pauseOverlayBackground.setOpacity(0.5);
		
		VBox pauseMenu = new VBox();
		pauseMenu.setAlignment(Pos.CENTER);
		Label pausedLabel = new Label("Game Paused");
		pausedLabel.setFont(new Font(64));
		pausedLabel.setTextFill(Color.WHITE);
		Button resumeButton = new Button("Resume");
		this.setOnKeyPressed((KeyEvent e) -> {
			KeyCode code = e.getCode();
			if (code == KeyCode.ESCAPE) {
				resume.run();
			}
		});
		resumeButton.setOnAction((ActionEvent e) -> {
			resume.run();
		});
		pauseMenu.getChildren().addAll(pausedLabel, resumeButton);
		
		this.getChildren().addAll(pauseOverlayBackground, pauseMenu);
	}
}
