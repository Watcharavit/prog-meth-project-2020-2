package application;

import gui.StyledButton;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The How to Play screen. It consists mostly of Labels explaining the game.
 *
 */
class HelpScreen extends Screen {

	public HelpScreen() {
		StackPane stack = new StackPane();

		VBox howToPlay = new VBox();
		howToPlay.setAlignment(Pos.TOP_CENTER);
		howToPlay.setPrefHeight(600);
		howToPlay.setPrefWidth(780);

		ImageView title = new ImageView(new Image("images/how_to_play.png"));
		title.setFitHeight(120);

		/* @formatter:off */
		String manualInformation =
				  "Player 1 control: W A S D = move, Spacebar = place bomb, C = use equipment\n"
				+ "Player 2 control: Arrow keys = move, Enter = place bomb,  L = use equipment\n"
				+ "\n"
				+ "Equipments are given by the players' scores:\n"
				+ "   Top player: Bombkicker = can kick bombs.\n"
				+ "   Bottom player: Puncher = can punch other players and ghosts.\n"
				+ "\n"
				+ "Items appear randomly when you break walls. There are 2 items:\n"
				+ "   One increases the number of active bombs you can have by 1.\n"
				+ "   The other increases your bomb radius by 1 tile.\n"
				+ "\n"
				+ "Rules:\n"
				+ "1. The tile in the middle of the map is the King Floor.\n"
				+ "    You get score by standing on it.\n"
				+ "2. Game's time is 5 minutes.\n"
				+ "    When the game ends, the player with highest score wins.\n"
				+ "4. If you die, your bomb radius and bombs number will be reduced by 3.\n"
				+ "    But the minimum is 1, so don't worry about not being able to place bombs.\n"
				+ "5. You can kill ghosts and other players by boming or punching them,\n"
				+ "    but you cannot do anything to the thorn monsters.";
		
		/* @formatter:on */
		Label manual = new Label(manualInformation);
		manual.setPadding(new Insets(10, 20, 10, 20));
		manual.setFont(Font.font("Arial", FontWeight.BOLD, 15));

		HBox buttonPane = new HBox();
		buttonPane.setPrefHeight(40);
		buttonPane.setPrefWidth(780);

		Button backButton = new StyledButton("Back");
		backButton.setPrefWidth(70);
		backButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hideHelp();
		});

		buttonPane.getChildren().addAll(backButton);
		buttonPane.setAlignment(Pos.BOTTOM_CENTER);

		howToPlay.getChildren().addAll(title, manual, buttonPane);

		ImageView crown = new ImageView(new Image("images/crown.png"));
		crown.setOpacity(0.25);

		stack.getChildren().addAll(crown, howToPlay);
		stack.setBackground(
				new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

		this.getChildren().add(stack);
	}
}
