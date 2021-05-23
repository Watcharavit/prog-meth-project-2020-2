package application;

import game.GameSingleton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.MusicsLibrary;
import gui.StyledButton;
/**
 * The Home Screen displays a large logo, a Start Game button, and a How to Play
 * button. This screen uses {@link GameScreen} as animated background.
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
		
		left.setBackground(new Background(new BackgroundImage(new Image(ClassLoader.getSystemResource("images/wall_background.png").toString()),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, new BackgroundPosition(Side.LEFT, 1, true, Side.TOP, 1, true),
		          BackgroundSize.DEFAULT)));
		
		right.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);

		ImageView title = new ImageView(new Image("images/game_title.png"));
		title.setOpacity(0.95);

		right.getChildren().add(title);
		right.setAlignment(Pos.CENTER);

		gameButton = new StyledButton("Start Game");
		gameButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hideHome();
			GameSingleton.start(true);
		});
		gameButton.setPrefHeight(40);
		gameButton.setPrefWidth(150);

		helpButton = new StyledButton("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			AppSingleton.showHelp();
		});
		helpButton.setPrefHeight(40);
		helpButton.setPrefWidth(150);

		left.getChildren().addAll(gameButton, helpButton);
		
		VBox.setMargin(gameButton, new Insets(24));
		VBox.setMargin(helpButton, new Insets(24));

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
