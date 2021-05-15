package application;

import gui.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
	protected final static int WIDTH = 40;
	protected final static int HEIGHT = 40;
	protected final static int TILE_SIZE = 24;
	
	@Override
	public void start(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		Tile[][] tiles = new Tile[WIDTH][HEIGHT];
		MapGenerator.generateMap(tiles);
		World world = new World(tiles, TILE_SIZE);
		root.getChildren().add(world);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
