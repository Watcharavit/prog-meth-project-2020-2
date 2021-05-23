package application;

import javafx.scene.layout.StackPane;

/**
 * Abstract class extended by all screens.
 *
 */
abstract class Screen extends StackPane {
	/**
	 * To be called when the screen is focused.
	 */
	protected void onFocus() {

	}
}
