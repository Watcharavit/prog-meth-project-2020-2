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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class HomeScreen extends HBox {

	private final Button gameButton, helpButton;

	protected HomeScreen() {
		VBox right = new VBox();
		right.setPrefWidth(600);
		VBox left = new VBox();
		left.setPrefWidth(180);
		right.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);
		left.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		Label title = new Label("King of Bomb");
		title.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 60));
		title.setTextFill(Color.BLACK);
		title.setPadding(new Insets(18, 3, 18, 3));
		title.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		title.setOpacity(0.85);

		right.getChildren().add(title);
		right.setAlignment(Pos.CENTER);

		gameButton = new Button("Start Game");
		gameButton.setOnAction((ActionEvent e) -> {
			GameSingleton.start();
			AppSingleton.switchToGame();
		});
		gameButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		gameButton.setPrefHeight(40);
		gameButton.setPrefWidth(150);

		helpButton = new Button("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			AppSingleton.showHelp();
		});
		helpButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		helpButton.setPrefHeight(40);
		helpButton.setPrefWidth(150);

		left.getChildren().addAll(gameButton, helpButton);

		this.getChildren().addAll(left, right);
	}

}
