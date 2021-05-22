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
			endGameMenu.setSpacing(24);

			Label endLabel = new Label("Game Ended");
			endLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 60));
			endLabel.setTextFill(Color.BLACK);
			endLabel.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			endLabel.setOpacity(0.85);

			VBox scoreBoard = new VBox();
			Player winnerPlayer = player[0];
			String winnerName = "player 1";
			for (int i = 0; i < player.length; i++) {
				Label playerScore = new Label(player[i].getName() + "'s score = "
						+ String.format("%3.1f", Math.floor(player[i].getKingTime()) / 60));
				playerScore.setFont(new Font(25));
				playerScore.setTextFill(Color.BLACK);
				scoreBoard.getChildren().add(playerScore);
				if (winnerPlayer.getKingTime() < player[i].getKingTime()) {
					winnerPlayer = player[i];
				}
			}

			if (winnerPlayer.getKingTime() == 0) {
				winnerName = "Draw!";
			} else {
				winnerName = winnerPlayer.getName() + " Wins!";
			}

			scoreBoard.setAlignment(Pos.CENTER);
			scoreBoard.setSpacing(7);
			scoreBoard.setBackground(
					new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			scoreBoard.setOpacity(0.85);

			Label winner = new Label(winnerName);
			winner.setFont(Font.font("Courier New", FontWeight.BOLD, 40));
			winner.setTextFill(Color.BLACK);
			winner.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			winner.setOpacity(0.85);

			Button restartButton = new UnfocusableButton("Restart");
			restartButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start();
			});

			Button homeButton = new UnfocusableButton("Back to Main Menu");
			homeButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start();
				AppSingleton.resetToHome();
			});
			endGameMenu.getChildren().addAll(endLabel, scoreBoard, winner, restartButton, homeButton);
			endGameMenu.setPadding(new Insets(72, 0, 0, 300));
			this.getChildren().addAll(endGameMenu);
		}
	}
}
