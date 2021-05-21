package application;

import game.GameSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class HomeScreen extends StackPane {

	private final Button gameButton, helpButton;

	protected HomeScreen() {
		this.setPadding(new Insets(0, 480, 0, 0));
		this.setPrefWidth(300);
		VBox home = new VBox();
		home.setAlignment(Pos.TOP_CENTER);
		home.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		Label title = new Label("King of Bomb");
		title.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
		title.setPadding(new Insets(80, 0, 80, 0));

		gameButton = new Button("Start Game");
		gameButton.setOnAction((ActionEvent e) -> {
			this.switchToGame();
		});
		gameButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		gameButton.setPrefHeight(40);
		gameButton.setPrefWidth(150);

		helpButton = new Button("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			this.switchToHelp();
		});
		helpButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		helpButton.setPrefHeight(40);
		helpButton.setPrefWidth(150);

		home.getChildren().addAll(title, gameButton, helpButton);

		this.getChildren().add(home);

	}

	private void switchToGame() {
		AppSingleton.switchToGame();
	}

	private void switchToHelp() {
		AppSingleton.switchToHelp();
	}

	protected void onFocus() {
		GameSingleton.start();
	}

}
