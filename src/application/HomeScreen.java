package application;

import game.GameSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.MusicsLibrary;

/**
 * The Home Screen displays a large logo, a Start Game button, and a How to Play button.
 * This screen uses {@link GameScreen} as animated background.
 *
 */
class HomeScreen extends Screen {

	private final Button gameButton, helpButton;

	protected HomeScreen() {
		HBox container = new HBox();
		VBox right = new VBox();
		right.setPrefWidth(600);
		VBox left = new VBox();
		left.setPrefWidth(180);
		right.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);
		left.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		ImageView title = new ImageView(new Image("sprites/game_title.png"));
		title.setOpacity(0.95);

		right.getChildren().add(title);
		right.setAlignment(Pos.CENTER);

		gameButton = new Button("Start Game");
		gameButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hideHome();
			GameSingleton.start(true);
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

		container.getChildren().addAll(left, right);
		
		this.getChildren().add(container);
	}

	/**
	 * Upon focus, play the screen's music.
	 */
	@Override
	public void onFocus() {
		MusicsLibrary.playMusic(MusicsLibrary.MAIN_MENU);
	}
}
