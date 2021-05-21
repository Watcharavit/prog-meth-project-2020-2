package entity.daemons;

import java.util.Formatter;

import application.AppSingleton;
import entity.PhantomEntity;
import entity.livings.Player;
import game.GameSingleton;
import gui.UnfocusableButton;
import interfaces.Updatable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTimerDaemon extends PhantomEntity implements Updatable {
	private double remainingTime;
	private double remainingTimeDebounced;
	private Label label;
	private Pane endPane;
	private Player[] players;

	public GameTimerDaemon(Player[] players, Pane timerUiPane, Pane endUiPane) {
		this.players = players;
		this.endPane = endUiPane;

		remainingTime = 60 * 60 * 5;

		label = new Label();
		label.setPadding(new Insets(24));
		timerUiPane.getChildren().add(label);
	}

	@Override
	public void update(double ticksPassed) {
		remainingTime -= ticksPassed;
		if (remainingTime < 0) {
			end();
		} else {
			double v = Math.floor(remainingTime / 60.0);
			if (remainingTimeDebounced != v) {
				remainingTimeDebounced = v;
				Formatter f = new Formatter();
				String r = f.format("Time Remaining: %3.0f", remainingTimeDebounced).toString();
				f.close();
				label.setText(r);
			}
		}
	}

	private void end() {
		GameSingleton.destroy();
		endPane.getChildren().add(new EndView(players));
	}

	class EndView extends Pane {
		protected EndView(Player[] player) {
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
			Label player1Score = new Label("Player 1 's score = ");
			Label player2Score = new Label("Player 2 's score = ");
			player1Score.setFont(new Font(35));
			player1Score.setTextFill(Color.WHITE);
			player2Score.setFont(new Font(35));
			player2Score.setTextFill(Color.WHITE);
			Label winnerName = new Label();
			winnerName.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
			winnerName.setTextFill(Color.WHITE);
			scoreBoard.setAlignment(Pos.CENTER);
			scoreBoard.setSpacing(20);
			scoreBoard.getChildren().addAll(player1Score, player2Score, winnerName);

			Button restartButton = new UnfocusableButton("Restart");
			restartButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start();
			});

			Button homeButton = new UnfocusableButton("Back to Main Menu");
			homeButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start();
				AppSingleton.resetToHome();
			});
			endGameMenu.getChildren().addAll(endLabel, scoreBoard, restartButton, homeButton);

			this.getChildren().addAll(endGameMenu);
		}
	}
}
