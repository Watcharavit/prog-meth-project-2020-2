package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import entity.Player;
import entity.WallThornMonster;
import entity.base.Being;
import entity.base.PhysicalEntity;
import entity.base.PhantomEntity;
import entity.base.StillObject;
import entity.base.Updatable;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.PlayerControl;

public class GameSingleton {

	public final static int WIDTH = 25;
	public final static int HEIGHT = 25;
	protected final static int TILE_SIZE = 24;

	protected static Tile[][] tiles;
	protected static HashSet<Being> allBeings;
	private static InputManager inputManager;
	private static PlayersManager playersManager;
	private static BeingsManager beingsManager;
	private static GameController controller;
	private static MonsterGenerator monsterGenerator;
	
	public static void addBeing(Being being) {
		controller.addEntity(being);
		beingsManager.addBeing(being);
	}
	public static void removeBeing(Being being) {
		controller.removeEntity(being);
		beingsManager.removeBeing(being);
	}

	public static StillObject getTileObject(int x, int y) {
		return tiles[x][y].getObject();
	}
	public static void setTileObject(int x, int y, StillObject object) {
		Tile tile = tiles[x][y];
		StillObject existing = tile.getObject();
		if (existing != null) {
			controller.removeEntity(existing);
		}
		controller.addEntity(object);
		tile.setObject(object);
		beingsManager.updateBeingsAroundTile(tile);
		controller.queueTileRender(tile);
	}
	
	public static void addPhantomEntity(PhantomEntity entity) {
		controller.addEntity(entity);
	}
	public static void removePhantomEntity(PhantomEntity entity) {
		controller.removeEntity(entity);
	}

	public static HashSet<KeyCode> getActiveKeys() {
		return inputManager.getActiveKeys();
	}

	public static boolean moveBeing(Being being, double dx, double dy) {
		return beingsManager.moveBeing(being, dx, dy);
	}

	public static void initialize(Pane root) {
		HBox container = new HBox();
		Pane playersUiPane = new Pane();
		StackPane layers = new StackPane();
		Canvas objectsCanvas = new Canvas();
		Canvas beingsCanvas = new Canvas();
		objectsCanvas.setHeight(HEIGHT * TILE_SIZE);
		objectsCanvas.setWidth(WIDTH * TILE_SIZE);
		beingsCanvas.setHeight(HEIGHT * TILE_SIZE);
		beingsCanvas.setWidth(WIDTH * TILE_SIZE);
		layers.getChildren().add(objectsCanvas);
		layers.getChildren().add(beingsCanvas);
		container.getChildren().add(playersUiPane);
		container.getChildren().add(layers);
		root.getChildren().add(container);
		allBeings = new HashSet<Being>();
		tiles = new Tile[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		inputManager = new InputManager(root);
		controller = new GameController(objectsCanvas, beingsCanvas);
		beingsManager = new BeingsManager();
		MapGenerator.generateMap();
		playersManager = new PlayersManager(playersUiPane);
		monsterGenerator = new MonsterGenerator();
		controller.start();
	}
	public static void destroy() {
		controller.stop();
	}

	public static void pause() {
		controller.pause();
	}

	public static void resume() {
		controller.resume();
	}

}
