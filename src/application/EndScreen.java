package application;

import gui.UnfocusableButton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EndScreen extends StackPane {
	private String winnerName;
	
	protected EndScreen(GameScreen screenInstance) {
		onEnd();
		
		Pane EndScreenBackground = new Pane();
		EndScreenBackground
				.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		EndScreenBackground.setOpacity(0.7);

		VBox endGameMenu = new VBox();
		endGameMenu.setAlignment(Pos.CENTER);
		Label endLabel = new Label("Game Ended");
		endLabel.setFont(new Font(64));
		endLabel.setTextFill(Color.AZURE);
		endGameMenu.setSpacing(24);
		
		VBox scoreBoard = new VBox();
		Label player1Score = new Label("Player 1 's score = "+ getPlayer1Score());
		Label player2Score = new Label("Player 2 's score = "+ getPlayer2Score());
		player1Score.setFont(new Font(35));
		player1Score.setTextFill(Color.WHITE);
		player2Score.setFont(new Font(35));
		player2Score.setTextFill(Color.WHITE);
		Label winnerName = new Label(this.winnerName);
		winnerName.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
		winnerName.setTextFill(Color.WHITE);
		scoreBoard.setAlignment(Pos.CENTER);
		scoreBoard.setSpacing(20);
		scoreBoard.getChildren().addAll(player1Score, player2Score, winnerName);

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
		endGameMenu.getChildren().addAll(endLabel, scoreBoard, restartButton, helpButton, homeButton);

		this.getChildren().addAll(endGameMenu);
		
	}
	
	public void onEnd() {
		// if the score doesn't show all of it, there is a problem ex. 12.021 shows 12.02 and 12.023 shows 12.02 ?
		if (getPlayer1Score()>getPlayer2Score()) {
			this.winnerName = "Player 1 Wins !!";
		}else if (getPlayer1Score()<getPlayer2Score()){ 
			this.winnerName = "Player 2 Wins !!";
		}else {
			this.winnerName = "Draw";
		}
	}
	
	// implement these following classes
	private double getPlayer1Score() {
		return 0;
	}
	private double getPlayer2Score() {
		return 0;
	}
}
