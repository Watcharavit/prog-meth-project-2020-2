package game;

import java.util.HashSet;

import entity.Being;
import entity.Entity;
import entity.StillObject;
import interfaces.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * The 'Engine' of the game.
 * Controls the clock, updates {@link interfaces.Updatable}, and renders {@link entity.PhysicalEntity}s.
 *
 * For rendering, we have two canvas layers: one for {@link entity.Being}s and one for {@link entity.StillObject}.
 * We re-render every {@link entity.Being} on every frame.
 * But we only re-render a {@link Tile}'s {@link entity.StillObject} when requested. 
 */
class GameController {
	/** A set of all {@link interfaces.Updatable} in the game. */
	private HashSet<Updatable> updatableEntities;
	/** A set of {@link Tile} whose {@link entity.StillObject} needs to be re-rendered in the next frame. */
	private HashSet<Tile> renderQueue;
	/** The GraphicsContext of one of a layer. */
	private GraphicsContext objectsGc, beingsGc;
	/** The clock that time our updating and rendering */
	private GameLoopTimer timer;
	/** All beings in the game. Reference to {@link GameSingleton#allBeings}. */
	HashSet<Being> allBeings;

	/**
	 * Prepare the controller.
	 * @param objectsCanvas The canvas for the objects layer. Below.
	 * @param beingsCanvas The canvas for the beings layer. Above.
	 */
	protected GameController(Canvas objectsCanvas, Canvas beingsCanvas) {
		updatableEntities = new HashSet<Updatable>();
		renderQueue = new HashSet<Tile>();
		this.objectsGc = objectsCanvas.getGraphicsContext2D();
		this.beingsGc = beingsCanvas.getGraphicsContext2D();
		this.allBeings = GameSingleton.allBeings;
		timer = new GameLoopTimer() {
			@Override
			protected void tick(long frameTimeNano) {
				double ticksPassed = frameTimeNano * 0.6e-7; // 60fps = 16.67ms = 1.00
				onTick(ticksPassed);
			}
		};
	}

	/**
	 * If the given entity is {@link interfaces.Updatable}, add it to {@link #updatableEntities};
	 * @param entity The entity to be added.
	 */
	protected void addEntity(Entity entity) {
		if (entity instanceof Updatable) {
			this.updatableEntities.add((Updatable) entity);
		}
	}

	/**
	 * If the given entity is {@link interfaces.Updatable}, remove it from {@link #updatableEntities};
	 * @param entity The entity to be removed.
	 */
	protected void removeEntity(Entity entity) {
		if (entity instanceof Updatable) {
			this.updatableEntities.remove((Updatable) entity);
		}
	}

	/** A simple array for specifying type to set.toArray(). */
	private static final Updatable[] emptyUpdatablesArray = {};

	/**
	 * Run {@link interfaces.Updatable#update(double)} on each entity in {@link #updatableEntities}.
	 * @param ticksPassed The amount of ticks that passed since last frame. 1 tick always equal 16.67ms (even in non-60fps scenarios).
	 */
	private void updateUpdatables(double ticksPassed) {
		Updatable[] updatableCloned = updatableEntities.toArray(emptyUpdatablesArray);
		for (Updatable updatable : updatableCloned) {
			updatable.update(ticksPassed);
		}
	}

	/**
	 * Render a frame.
	 * <ul>
	 * <li>Redraw the object in each tile in the {@link #renderQueue}.</li>
	 * <li>Redraw every being in {@link #allBeings}</li>
	 * </ul>
	 * 
	 */
	private void render() {
		for (Tile tile : renderQueue) {
			int x = tile.x;
			int y = tile.y;
			StillObject object = tile.getObject();
			object.getSprite().drawTopLeft(objectsGc, x * GameSingleton.TILE_SIZE, y * GameSingleton.TILE_SIZE);
		}
		beingsGc.clearRect(0, 0, GameSingleton.WIDTH * GameSingleton.TILE_SIZE,
				GameSingleton.HEIGHT * GameSingleton.TILE_SIZE);
		for (Being being : allBeings) {
			double x = being.getX();
			double y = being.getY();
			being.getSprite().drawCenter(beingsGc, x * GameSingleton.TILE_SIZE, y * GameSingleton.TILE_SIZE);
		}
		renderQueue = new HashSet<Tile>();
	}
	
	/**
	 * Add tile to the {@link #renderQueue}.
	 * @param tile The tile whose object should be re-rendered.
	 */
	protected void queueTileRender(Tile tile) {
		renderQueue.add(tile);
	}

	/**
	 * Run {@link #updateUpdatables(double)} and {@link #render()} for a frame.
	 * @param ticksPassed The amount of ticks that passed since last frame. 1 tick always equal 16.67ms (even in non-60fps scenarios).
	 */
	private void onTick(double ticksPassed) {
		updateUpdatables(ticksPassed);
		render();
	}

	/**
	 * Start the timer.
	 */
	protected void start() {
		timer.start();
	}

	/**
	 * Destroy the timer.
	 */
	protected void stop() {
		timer.stop();
	}

	/**
	 * Pause the timer.
	 */
	protected void pause() {
		timer.pause();
	}

	/**
	 * Resume the timer.
	 */
	protected void resume() {
		timer.resume();
	}

	/**
	 * Check if the timer is still alive.
	 * Returns false if {@link #stop()} has been called. 
	 * @return Whether or not the timer has been destroyed.
	 */
	protected boolean isActive() {
		return timer.isActive();
	}
}
