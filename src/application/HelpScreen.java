package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

class HelpScreen extends VBox {
	public HelpScreen(Main mainInstance) {
		this.setAlignment(Pos.TOP_CENTER);
		Label title = new Label("Help");
		
		Button backButton = new Button("Back");
		backButton.setOnAction((ActionEvent e) -> {
			mainInstance.switchToHome();
		});
		
		this.getChildren().addAll(title, backButton);
	}
}
