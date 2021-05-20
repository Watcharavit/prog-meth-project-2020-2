package application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

class HomeScreen extends VBox {
	private final Main mainInstance;
	
	private final Button gameButton, helpButton;
	protected HomeScreen(Main mainInstance) {
		this.mainInstance = mainInstance;
		
		this.setAlignment(Pos.CENTER);
		
		gameButton = new Button("Start Game");
		gameButton.setOnAction((ActionEvent e) -> {
			this.switchToGame();
		});
		helpButton = new Button("How to Play");
		helpButton.setOnAction((ActionEvent e) -> {
			this.switchToHelp();
		});
		
		this.getChildren().addAll(gameButton, helpButton);
	}
	
	private void switchToGame() {
		mainInstance.switchToGame();
		gameButton.setText("Back to Game");
	}
	
	private void switchToHelp() {
		mainInstance.switchToHelp();
	}
	
	
}
