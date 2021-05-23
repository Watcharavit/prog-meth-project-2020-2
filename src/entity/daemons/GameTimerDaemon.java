package entity.daemons;

import java.util.Formatter;

import application.AppSingleton;
import entity.PhantomEntity;
import entity.livings.Player;
import game.GameSingleton;
import gui.StyledButton;
import interfaces.Updatable;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.MusicsLibrary;

/**
 * This is a class used to show attributes of players and time left before the
 * game end.
 *
 */
public class GameTimerDaemon extends PhantomEntity implements Updatable {

	/**
	 * Time left before the game ends.
	 */
	private double remainingTime;

	/**
	 * Time showed on screen before the game ends.
	 */
	private double remainingTimeDebounced;

	/**
	 * Label of remaining time shows on screen.
	 */
	private Label label;

	/**
	 * End screen that shows players score and winner.
	 */
	private Pane endPane;

	/**
	 * Array list of players in the game.
	 */
	private Player[] players;

	/**
	 * Initialize all fields. Set time limit of the game depends on you.
	 * 
	 * @param players     Array list of players in the game.
	 * @param timerUiPane Time screen.
	 * @param endUiPane   End screen.
	 */
	public GameTimerDaemon(Player[] players, Pane timerUiPane, Pane endUiPane) {
		this.players = players;
		this.endPane = endUiPane;

		remainingTime = 60 * 60 * 5;

		label = new Label();
		label.setPadding(new Insets(24));
		timerUiPane.getChildren().add(label);
	}

	/**
	 * Update the game. If remaining time run out, then end the game. If not, update
	 * time showed on screen to be real remaining time left.
	 */
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

	/**
	 * End this game {@link game.GameSingleton#destroy()}. Play soundtrack music of
	 * the end scene.
	 */
	private void end() {
		GameSingleton.destroy();
		endPane.getChildren().add(new EndView(players));
		MusicsLibrary.playMusic(MusicsLibrary.GAME_END);
	}

	/**
	 * Create end screen and score board.
	 *
	 */
	class EndView extends StackPane {

		/**
		 * Create end screen and score board. Showing score of each player, the result
		 * of the game. And choice if they want to play again or go back to main menu.
		 * 
		 * @param player Players in the game.
		 */
		protected EndView(Player[] player) {
			StackPane stack = new StackPane();
			Pane endScreenBackground = new Pane();
			endScreenBackground
					.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
			endScreenBackground.setOpacity(0.7);

			VBox endGameMenu = new VBox();
			endGameMenu.setAlignment(Pos.CENTER);
			endGameMenu.setSpacing(24);

			ImageView endLabel = new ImageView(new Image("images/game_ended.png"));
			endLabel.setOpacity(0.95);

			VBox scoreBoard = new VBox();
			Player winnerPlayer = player[0];
			String winnerName = "player 1";
			for (int i = 0; i < player.length; i++) {
				Label playerScore = new Label(player[i].getName() + "'s score = "
						+ String.format("%3.0f", Math.floor(player[i].getKingTime()) / 60));
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
			winner.setOpacity(0.95);

			Button restartButton = new StyledButton("Restart");
			restartButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start(true);
				MusicsLibrary.playMusic(MusicsLibrary.GAME_PLAY);
			});

			Button homeButton = new StyledButton("Back to Main Menu");
			homeButton.setOnAction((ActionEvent e) -> {
				GameSingleton.start(false);
				AppSingleton.resetToHome();
			});
			endGameMenu.getChildren().addAll(endLabel, scoreBoard, winner, restartButton, homeButton);
			// endGameMenu.setPadding(new Insets(72, 0, 0, 95));
			stack.getChildren().addAll(endScreenBackground, endGameMenu);
			this.getChildren().addAll(stack);
		}
	}
}
