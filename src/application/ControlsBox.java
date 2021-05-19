package application;

import gui.UnfocusableButton;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

class ControlsBox extends HBox {
	protected ControlsBox(GameScreen screenInstance) {
		super.setAlignment(Pos.CENTER);
		Button pauseButton = new UnfocusableButton("Pause (ESC)");
		pauseButton.setOnAction((ActionEvent e) -> {
			screenInstance.pause();
		});
		this.getChildren().add(pauseButton);
	}
}
