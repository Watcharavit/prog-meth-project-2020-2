package application;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import entity.Player;
import entity.base.Entity;
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
	
	private final static HashMap<PlayerControl, KeyCode> player1Control = new HashMap<PlayerControl, KeyCode>();
	private final static HashMap<PlayerControl, KeyCode> player2Control = new HashMap<PlayerControl, KeyCode>();
	
	private static Set<Entity> allEntities = new HashSet<Entity>();
	private static Set<Updatable> updatableEntities = new HashSet<Updatable>();
	
	private static HashSet<KeyCode> activeKeys = new HashSet<KeyCode>();
	static {
		player1Control.put(PlayerControl.MOVE_UP, KeyCode.W);
		player1Control.put(PlayerControl.MOVE_LEFT, KeyCode.A);
		player1Control.put(PlayerControl.MOVE_DOWN, KeyCode.S);
		player1Control.put(PlayerControl.MOVE_RIGHT, KeyCode.D);
		
		player2Control.put(PlayerControl.MOVE_UP, KeyCode.UP);
		player2Control.put(PlayerControl.MOVE_LEFT, KeyCode.LEFT);
		player2Control.put(PlayerControl.MOVE_DOWN, KeyCode.DOWN);
		player2Control.put(PlayerControl.MOVE_RIGHT, KeyCode.RIGHT);
		
	}
	
	private static Canvas canvas;
	public static World world;
	
	public static void addEntity(Entity entity) {
		GameSingleton.allEntities.add(entity);
		if (entity instanceof Updatable) {
			GameSingleton.updatableEntities.add((Updatable) entity);
		}
	}
	public static void removeEntity(Entity entity) {
		GameSingleton.allEntities.remove(entity);
		if (entity instanceof Updatable) {
			GameSingleton.updatableEntities.remove((Updatable) entity);
		}
	}
	
	public static void initialize(Canvas canvas) {
		GameSingleton.canvas = canvas;
		canvas.setFocusTraversable(true);
		canvas.setHeight(HEIGHT*TILE_SIZE);
		canvas.setWidth(WIDTH*TILE_SIZE);
		GameSingleton.world= new World(WIDTH, HEIGHT, TILE_SIZE, canvas);
		GameSingleton.initializePlayers();
		GameSingleton.world.rerenderAll();
		GameSingleton.initializeListeners();
		GameSingleton.initializeGameLoop();
	}
	private static void initializePlayers() {
		initializePlayer("Player 1", player1Control, 2.5, 2.5);
		initializePlayer("Player 2", player2Control, WIDTH - 2.5, HEIGHT- 2.5);
	}
	private static void initializePlayer(String name, Map<PlayerControl, KeyCode> keyMap, double posX, double posY) {
		Player player = new Player(name, posX, posY, keyMap);
		GameSingleton.world.addBeing(player);
	}
	private static void initializeListeners() {
		GameSingleton.canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				GameSingleton.activeKeys.add(code);
			}
		});
		GameSingleton.canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				KeyCode code = e.getCode();
				GameSingleton.activeKeys.remove(code);
			}
		});
	}
	private static void initializeGameLoop() {
		GameLoopTimer timer = new GameLoopTimer() {
			@Override
			public void tick(long frameTimeNano) {
				double frameTimeRatio = frameTimeNano * 3 / (5e7); // 60fps = 16.67ms = 1.00
				for (Updatable updatable : GameSingleton.updatableEntities) {
					updatable.update(frameTimeRatio);
				}
			}
		};
		timer.start();
	}
	public static HashSet<KeyCode> getActiveKeys() {
		return GameSingleton.activeKeys;
	}
	
}
