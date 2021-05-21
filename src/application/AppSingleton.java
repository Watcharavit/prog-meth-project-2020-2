package application;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import resources.MusicsLibrary;

public class AppSingleton {
	private static GameScreen gameScreen;
	private static HomeScreen homeScreen;
	private static HelpScreen helpScreen;
	private static Stage primaryStage;
	private static StackPane homeGameStack;
	private static Scene homeGameScene, helpScene;

	public static void start(Stage primaryStage) {
		AppSingleton.primaryStage = primaryStage;

		homeGameStack = new StackPane();
		gameScreen = new GameScreen();
		homeGameStack.getChildren().add(gameScreen);
		homeScreen = new HomeScreen();
		homeGameScene = new Scene(homeGameStack);

		helpScreen = new HelpScreen();
		helpScene = new Scene(helpScreen);

		resetToHome();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void resetToHome() {
		switchToHome();
	}

	protected static void switchToGame() {
		gameScreen.onFocus();
		primaryStage.setScene(homeGameScene);
		homeGameStack.getChildren().remove(homeScreen);
		MusicsLibrary.playMusic(MusicsLibrary.GAME_PLAY);
	}

	protected static void switchToHome() {
		homeScreen.onFocus();
		primaryStage.setScene(homeGameScene);
		homeGameStack.getChildren().add(homeScreen);
		MusicsLibrary.playMusic(MusicsLibrary.MAIN_MENU);
	}

	protected static void switchToHelp() {
		helpScreen.onFocus();
		primaryStage.setScene(helpScene);
		MusicsLibrary.playMusic(null);
	}

}
