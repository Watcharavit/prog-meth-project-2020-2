package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas();
		GameSingleton.initialize(canvas);
		root.getChildren().add(canvas);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
