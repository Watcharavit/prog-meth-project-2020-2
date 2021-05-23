package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entrypoint of the application. It extends JavaFX's Application class.
 *
 */

public class Main extends Application {

	/**
	 * @see AppSingleton#start(Stage)
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("King of Bombs");
		AppSingleton.start(stage);
	}
}
