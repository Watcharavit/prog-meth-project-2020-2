package application;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resources.MusicsLibrary;

public class AppSingleton {
	private static GameScreen gameScreen;
	private static HomeScreen homeScreen;
	private static HelpScreen helpScreen;
	private static StackPane rootStack;
	private static Scene rootScene;

	public static void start(Stage primaryStage) {

		rootStack = new StackPane();
		gameScreen = new GameScreen();
		homeScreen = new HomeScreen();
		rootScene = new Scene(rootStack);

		helpScreen = new HelpScreen();

		resetToHome();
		primaryStage.setScene(rootScene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void resetToHome() {
		switchToHome();
	}

	protected static void switchToGame() {
		rootStack.getChildren().clear();
		rootStack.getChildren().add(gameScreen);
		MusicsLibrary.playMusic(MusicsLibrary.GAME_PLAY);
	}

	protected static void switchToHome() {
		rootStack.getChildren().clear();
		rootStack.getChildren().add(gameScreen);
		rootStack.getChildren().add(homeScreen);
		MusicsLibrary.playMusic(MusicsLibrary.MAIN_MENU);
	}

	protected static void showHelp() {
		rootStack.getChildren().add(helpScreen);
	}

	protected static void hideHelp() {
		rootStack.getChildren().remove(helpScreen);
	}

}
