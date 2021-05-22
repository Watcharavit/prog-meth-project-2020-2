package game;

import java.util.HashSet;

import entity.Being;
import entity.PhantomEntity;
import entity.StillObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A singleton class to manage the game. This class is the only way to access
 * the member class of {@link game} from outside.
 *
 */
public class GameSingleton {

	/** The width (x axis) of the game map. */
	public final static int WIDTH = 25;
	/** The height (y axis) of the game map. */
	public final static int HEIGHT = 25;
	/** The size of each tile (in pixels). */
	protected final static int TILE_SIZE = 24;

	/** The Pane that contains everything in the game. */
	private static Pane rootPane;

	/** All tiles in the game world. */
	protected static Tile[][] tiles;
	/** All beings in the game world. */
	protected static HashSet<Being> allBeings;
	/** The {@link InputManager} instance */
	private static InputManager inputManager;
	/** The {@link BeingsManager} instance */
	private static BeingsManager beingsManager;
	/** The {@link GameController} instance */
	private static GameController controller;

	/**
	 * Add a being to the game. Calls
	 * {@link GameController#addEntity(entity.Entity)} and
	 * {@link BeingsManager#addBeing(Being)}.
	 * 
	 * @param being The being to add.
	 */
	public static void addBeing(Being being) {
		controller.addEntity(being);
		beingsManager.addBeing(being);
	}

	/**
	 * Remove a being from the game. Calls
	 * {@link GameController#removeEntity(entity.Entity)} and
	 * {@link BeingsManager#removeBeing(Being)}.
	 * 
	 * @param being The being to remove.
	 */
	public static void removeBeing(Being being) {
		controller.removeEntity(being);
		beingsManager.removeBeing(being);
	}

	/**
	 * Get the object at the specified tile position.
	 * 
	 * @param x The x position of the tile.
	 * @param y THe y position of the tile.
	 * @return The object at the tile.
	 */
	public static StillObject getTileObject(int x, int y) {
		return tiles[x][y].getObject();
	}

	/**
	 * Set the object at the specified tile position. This also calls
	 * {@link GameController#queueTileRender(Tile)} and
	 * {@link BeingsManager#updateBeingsAroundTile(Tile)}.
	 * 
	 * @param x      The x position of the tile.
	 * @param y      THe y position of the tile.
	 * @param object The object to put on the tile.
	 */
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

	/**
	 * Add a phantom entity (an unrendered background entity). Uses
	 * {@link GameController#addEntity(entity.Entity)}.
	 * 
	 * @param entity The entity to add.
	 */
	public static void addPhantomEntity(PhantomEntity entity) {
		controller.addEntity(entity);
	}

	/**
	 * Remove a phantom entity. Uses
	 * {@link GameController#removeEntity(entity.Entity)}.
	 * 
	 * @param entity The entity to remove.
	 */
	public static void removePhantomEntity(PhantomEntity entity) {
		controller.removeEntity(entity);
	}

	/**
	 * Get the set of currently pressed keyboard keys. Used for key polling.
	 * 
	 * @see entity.livings.Player#update(double)
	 * @return The set of currently pressed keyboard keys
	 */
	public static HashSet<KeyCode> getActiveKeys() {
		return inputManager.getActiveKeys();
	}

	/**
	 * Move a being. Uses {@link BeingsManager#moveBeing(Being, double, double)}.
	 * 
	 * @param being The being to move.
	 * @param dx    The x axis position difference.
	 * @param dy    The y axis position difference.
	 * @return Whether or not the move was successful.
	 */
	public static boolean moveBeing(Being being, double dx, double dy) {
		return beingsManager.moveBeing(being, dx, dy);
	}

	/**
	 * Prepare the GameSingleton.
	 * 
	 * @param pane The Pane to use for game UIs and canvases.
	 */
	public static void initialize(Pane pane) {
		rootPane = pane;
	}

	/**
	 * Start or restart the game. Build UIs and canvas layers. Initialize all fields
	 * except {@link #rootPane}.
	 * 
	 * @param createGameplayManager Whether or not to set up with
	 *                              {@link GameplayManager}. True for actual games.
	 *                              False if you just want a map with monsters
	 *                              moving (see {@link application.HomeScreen}).
	 */
	public static void start(boolean createGameplayManager) {
		destroy();
		rootPane.getChildren().clear();

		StackPane rootStack = new StackPane();

		HBox container = new HBox();

		VBox leftPart = new VBox();
		Pane gameUiPane = new Pane();
		Pane endOverlayPane = new Pane();
		leftPart.getChildren().add(gameUiPane);
		container.getChildren().add(leftPart);
		gameUiPane.setMinWidth(180);
		gameUiPane.setMaxWidth(180);

		StackPane layers = new StackPane();
		Canvas objectsCanvas = new Canvas();
		Canvas beingsCanvas = new Canvas();
		objectsCanvas.setHeight(HEIGHT * TILE_SIZE);
		objectsCanvas.setWidth(WIDTH * TILE_SIZE);
		beingsCanvas.setHeight(HEIGHT * TILE_SIZE);
		beingsCanvas.setWidth(WIDTH * TILE_SIZE);
		layers.getChildren().add(objectsCanvas);
		layers.getChildren().add(beingsCanvas);
		container.getChildren().add(layers);

		rootStack.getChildren().addAll(container, endOverlayPane);
		rootPane.getChildren().add(rootStack);

		allBeings = new HashSet<Being>();
		tiles = new Tile[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		inputManager = new InputManager(rootPane);
		controller = new GameController(objectsCanvas, beingsCanvas);
		beingsManager = new BeingsManager();
		MapGenerator.generateMap();
		MonsterGenerator.generateMonsters();
		if (createGameplayManager)
			GameplayManager.setupGameplay(gameUiPane, endOverlayPane);
		controller.start();
	}

	/**
	 * End the game. Uses {@link GameController#stop()}.
	 */
	public static void destroy() {
		if (controller != null && controller.isActive())
			controller.stop();
	}

	/**
	 * Pause the game. Uses {@link GameController#pause()}.
	 */
	public static void pause() {
		controller.pause();
	}

	/**
	 * Resume the game. Uses {@link GameController#resume()}.
	 */
	public static void resume() {
		controller.resume();
	}

}
