package entity.daemons;

import java.util.Formatter;

import entity.PhantomEntity;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Updatable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameTimerDaemon extends PhantomEntity implements Updatable {
	private double remainingTime;
	private double remainingTimeDebounced;
	private Label label;
	public GameTimerDaemon(Player[] players, Pane uiPane) {
		remainingTime = 60 * 60 * 5;
		
		label = new Label();
		label.setPadding(new Insets(24));
		uiPane.getChildren().add(label);
	}

	@Override
	public void update(double ticksPassed) {
		remainingTime -= ticksPassed;
		if (remainingTime < 0) {
			GameSingleton.destroy();
		}
		else {
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
}
