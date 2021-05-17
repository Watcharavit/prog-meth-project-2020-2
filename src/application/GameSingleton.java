package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import entity.Player;
import entity.base.Being;
import entity.base.Entity;
import entity.base.StillObject;
import entity.base.Updatable;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.PlayerControl;

public class GameSingleton {
	
	
	protected final static int WIDTH = 25;
	protected final static int HEIGHT = 25;
	protected final static int TILE_SIZE = 24;
	
	
	private static Tile[][] tiles;
	private static InputManager inputManager;
	private static PlayersManager playersManager;
	private static BeingsManager beingsManager;
	private static GameController controller;
	
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
		controller.queueTileRender(tile);
	}
	public static void addGhostEntity(Entity entity) {
		controller.addEntity(entity);
	}
	public static void removeGhostEntity(Entity entity) {
		controller.removeEntity(entity);
	}
	public static HashSet<KeyCode> getActiveKeys() {
		return inputManager.getActiveKeys();
	}
	public static boolean moveBeing(Being being, double dx, double dy) {
		boolean r = beingsManager.moveBeing(being, dx, dy);
		if (r) controller.queueTileRender(tiles[(int) being.getX()][(int) being.getY()]);
		return r;
	}
	
	
	protected static void initialize(Canvas canvas) {
		canvas.setHeight(HEIGHT*TILE_SIZE);
		canvas.setWidth(WIDTH*TILE_SIZE);
		tiles = new Tile[WIDTH][HEIGHT];
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		inputManager = new InputManager(canvas);
		controller = new GameController(canvas, tiles);
		MapGenerator.generateMap(tiles);
		beingsManager = new BeingsManager(tiles);
		playersManager = new PlayersManager();
		controller.initializeLoop();
		
	}
	
}
