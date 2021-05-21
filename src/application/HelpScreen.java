package application;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

class HelpScreen extends StackPane {

	public HelpScreen() {

		VBox howToPlay = new VBox();
		howToPlay.setAlignment(Pos.TOP_CENTER);
		howToPlay.setPrefHeight(600);
		howToPlay.setPrefWidth(780);

		Label title = new Label("How to play");
		title.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
		title.setPadding(new Insets(100, 20, 50, 20));
		// add how to play
		String manualInformation = "manual";
		Label manual = new Label(manualInformation);
		manual.setPadding(new Insets(10, 20, 100, 20));

		HBox buttonPane = new HBox();
		buttonPane.setPrefHeight(150);
		buttonPane.setPrefWidth(780);

		Button backButton = new Button("Back");
		backButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		backButton.setPrefHeight(40);
		backButton.setPrefWidth(70);
		backButton.setOnAction((ActionEvent e) -> {
			AppSingleton.switchToHome();
		});

		Button playButton = new Button("Play");
		playButton.setOnAction((ActionEvent e) -> {
			AppSingleton.switchToGame();
		});
		playButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		playButton.setPrefHeight(40);
		playButton.setPrefWidth(70);

		buttonPane.getChildren().addAll(backButton, playButton);
		buttonPane.setAlignment(Pos.BOTTOM_CENTER);

		howToPlay.getChildren().addAll(title, manual, buttonPane);

		this.getChildren().addAll(howToPlay);
	}

	protected void onFocus() {

	}
}
