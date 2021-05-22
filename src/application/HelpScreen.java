package application;

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

class HelpScreen extends StackPane {

	public HelpScreen() {
		VBox howToPlay = new VBox();
		howToPlay.setAlignment(Pos.TOP_CENTER);
		howToPlay.setPrefHeight(600);
		howToPlay.setPrefWidth(780);

		Label title = new Label("How to play");
		title.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
		title.setTextFill(Color.WHITE);
		title.setPadding(new Insets(35, 20, 25, 20));
		title.setBackground(
				new Background(new BackgroundFill(Color.DARKORANGE, CornerRadii.EMPTY, new Insets(7, 10, 5, 10))));

		String manualInformation = "Player 1 control: A, S, D, W, Spacebar = placing bomb , C = using equipment\r\n"
				+ "Player2 control: Left, Right, Up, Down, Enter = placing bomb,  L = using equipment\r\n" + "\r\n"
				+ "Equipment: get it according to the score \r\n" + "	Bombkicker = can kick a bomb\r\n"
				+ "	mitt = can kill everything except thorn monster\r\n" + "\r\n"
				+ "Item: Item drops randomly when you break the wall\r\n" + "	Item to increase bomb radius by 1\r\n"
				+ "	Item to increase bomb�s number you can place by 1\r\n" + "\r\n" + "Rules \r\n"
				+ "1. There is King Floor in the middle of the map, you will get a score \r\n"
				+ "   by standing on that floor.\r\n"
				+ "2. Player who has lower score gets Puncher and higher score gets Bombkicker.\r\n"
				+ "3. Game�s time is 5 minutes,then the game ends.Player with higher score wins.\r\n"
				+ "4. If you die, your bomb radius and bomb�s number get reduce by 3. \r\n"
				+ "   The lowest you can have is 1 so do not worry about not being able \r\n"
				+ "   to place a bomb.\r\n"
				+ "5. You can kill a ghost by using a bomb or a mitt, but you cannot do anything \r\n"
				+ "   against thorn monster.\r\n" + "";
		Label manual = new Label(manualInformation);
		manual.setPadding(new Insets(10, 20, 10, 20));
		manual.setFont(Font.font("Courier New", FontWeight.BOLD, 15));
//		manual.setBackground(new Background(new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY)));

		HBox buttonPane = new HBox();
		buttonPane.setPrefHeight(40);
		buttonPane.setPrefWidth(780);

		Button backButton = new Button("Back");
		backButton.setFont(Font.font("Courier New", FontWeight.NORMAL, 15));
		backButton.setPrefHeight(40);
		backButton.setPrefWidth(70);
		backButton.setOnAction((ActionEvent e) -> {
			AppSingleton.hideHelp();
		});

		buttonPane.getChildren().addAll(backButton);
		buttonPane.setAlignment(Pos.BOTTOM_CENTER);

		howToPlay.getChildren().addAll(title, manual, buttonPane);

		ImageView crown = new ImageView(new Image("sprites/crown.png"));
		crown.setOpacity(0.25);

		this.getChildren().add(howToPlay);
		this.getChildren().add(crown);
		this.setBackground(
				new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
