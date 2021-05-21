package application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class HomeScreen extends StackPane {
	private final Main mainInstance;

	private final Button gameButton, helpButton;
	
	protected HomeScreen(Main mainInstance) {
		this.mainInstance = mainInstance;
		//StackPane container = new StackPane();
		
		VBox home = new VBox();
		home.setPrefHeight(600);
		home.setPrefWidth(780);
		home.setAlignment(Pos.TOP_CENTER);
		
		Label title = new Label("King of Bomb");
		title.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
		title.setPadding(new Insets(200, 20, 120, 20));
		
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
		mainInstance.switchToGame();
		gameButton.setText("Back to Game");
	}

	private void switchToHelp() {
		mainInstance.switchToHelp();
	}

}
