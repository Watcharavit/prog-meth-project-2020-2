package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is a singleton class that handles navigation between screens in the application.
 * It works as a stack navigator: screens are pushed on top and popped of.
 * @author Wisha
 *
 */
public class AppSingleton {
	private static GameScreen gameScreen;
	private static HomeScreen homeScreen;
	private static PauseScreen pauseScreen;
	private static HelpScreen helpScreen;
	private static StackPane screensStack;
	private static Scene rootScene;

	/**
	 * Initialize the StackPane and the screens. Put everything on the given stage.
	 * @param primaryStage the stage on which we build the navigator.
	 */
	public static void start(Stage primaryStage) {
		screensStack = new StackPane();
		gameScreen = new GameScreen();
		homeScreen = new HomeScreen();
		pauseScreen = new PauseScreen();
		helpScreen = new HelpScreen();
		rootScene = new Scene(screensStack);

		primaryStage.setScene(rootScene);

		resetToHome();

		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Reset the navigator to default state.
	 */
	public static void resetToHome() {
		screensStack.getChildren().clear();
		screensStack.getChildren().add(gameScreen);
		showHome();
	}

	private static void showScreen(Node node) {
		ObservableList<Node> children = screensStack.getChildren();
		if (children.size() >= 1) {
			Node lastChildren = children.get(children.size() - 1);
			lastChildren.setDisable(true);
		}

		children.add(node);

		if (node instanceof Focusable) {
			((Focusable) node).onFocus();
		}
	}

	private static void hideScreen(Node node) {
		ObservableList<Node> children = screensStack.getChildren();
		children.remove(node);

		if (children.size() >= 1) {
			Node lastChildren = children.get(children.size() - 1);
			lastChildren.setDisable(false);
			if (lastChildren instanceof Focusable) {
				((Focusable) lastChildren).onFocus();
			}
		}
	}

	/**
	 * @see HomeScreen
	 */
	protected static void showHome() {
		showScreen(homeScreen);
	}

	protected static void hideHome() {
		hideScreen(homeScreen);
	}

	/**
	 * @see PauseScreen
	 */
	protected static void showPause() {
		showScreen(pauseScreen);
	}

	protected static void hidePause() {
		hideScreen(pauseScreen);
	}

	/**
	 * @see HelpScreen
	 */
	protected static void showHelp() {
		showScreen(helpScreen);
	}

	protected static void hideHelp() {
		hideScreen(helpScreen);
	}

}
