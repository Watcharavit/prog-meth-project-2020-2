package application;

import javafx.scene.layout.StackPane;

/**
 * This is the abstract class extended by screens.
 * @author Wisha
 *
 */
abstract class Screen extends StackPane {
	/**
	 * To be called when the screen is focused.
	 */
	protected void onFocus() {
		
	}
}
