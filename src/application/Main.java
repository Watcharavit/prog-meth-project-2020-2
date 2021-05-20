package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Stage primaryStage;
	Scene gameScene, homeScene, helpScene;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		GameScreen gameScreen = new GameScreen(this);
		gameScene = new Scene(gameScreen);
		
		HomeScreen homeScreen = new HomeScreen(this);
		homeScene = new Scene(homeScreen);
		
		HelpScreen helpScreen = new HelpScreen(this);
		helpScene = new Scene(helpScreen);
		
		primaryStage.setScene(homeScene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	protected void switchToGame() {
		primaryStage.setScene(gameScene);
	}
	
	protected void switchToHome() {
		primaryStage.setScene(homeScene);
	}
	
	protected void switchToHelp() {
		primaryStage.setScene(helpScene);
	}

}
