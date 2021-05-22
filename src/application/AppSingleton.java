package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is a singleton class that handles navigation between screens in the
 * application. It works as a stack navigator: screens are pushed on top and
 * popped of.
 * 
 * A trick we use here is to make the {@link HomeScreen} transparent, so we can
 * have the {@link GameScreen} below as moving background.
 *
 */
public class AppSingleton {
	/** One screen in the navigator */
	private static Screen homeScreen, gameScreen, pauseScreen, helpScreen;

	/** The StackPane containing the screens */
	private static StackPane screensStack;

	/**
	 * Initialize the StackPane and the screens. Put everything on the given stage.
	 * 
	 * @param primaryStage The stage on which we build this navigator.
	 */
	public static void start(Stage primaryStage) {
		screensStack = new StackPane();

		gameScreen = new GameScreen();
		homeScreen = new HomeScreen();
		pauseScreen = new PauseScreen();
		helpScreen = new HelpScreen();
		Scene rootScene = new Scene(screensStack);

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

	/**
	 * Push a screen onto the stack, disable the the screen below, and call
	 * {@link Screen#onFocus()}.
	 * 
	 * @param node The screen to show.
	 */
	private static void showScreen(Screen node) {
		ObservableList<Node> children = screensStack.getChildren();
		if (children.size() >= 1) {
			Node lastChild = children.get(children.size() - 1);
			lastChild.setDisable(true);
		}

		children.add(node);

		node.onFocus();
	}

	/**
	 * Remove a screen from the stack and enable and focus the new visible screen.
	 * 
	 * @param node The screen to remove.
	 */
	private static void hideScreen(Screen node) {
		ObservableList<Node> children = screensStack.getChildren();
		children.remove(node);

		if (children.size() >= 1) {
			Node lastChild = children.get(children.size() - 1);
			lastChild.setDisable(false);
			((Screen) lastChild).onFocus();
		}
	}

	/**
	 * Show home screen.
	 * 
	 * @see HomeScreen
	 */
	protected static void showHome() {
		showScreen(homeScreen);
	}

	/**
	 * Hide home screen.
	 */
	protected static void hideHome() {
		hideScreen(homeScreen);
	}

	/**
	 * Show pause screen.
	 * 
	 * @see PauseScreen
	 */
	protected static void showPause() {
		showScreen(pauseScreen);
	}

	/**
	 * Hide pause screen.
	 */
	protected static void hidePause() {
		hideScreen(pauseScreen);
	}

	/**
	 * Show help screen.
	 * 
	 * @see HelpScreen
	 */
	protected static void showHelp() {
		showScreen(helpScreen);
	}

	/**
	 * Hide help screen.
	 */
	protected static void hideHelp() {
		hideScreen(helpScreen);
	}

}
