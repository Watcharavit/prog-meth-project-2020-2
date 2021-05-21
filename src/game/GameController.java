package game;

import java.util.HashSet;

import entity.Being;
import entity.Entity;
import entity.StillObject;
import interfaces.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class GameController {
	private HashSet<Updatable> updatableEntities;
	private HashSet<Tile> renderQueue;
	private GraphicsContext objectsGc, beingsGc;
	private GameLoopTimer timer;
	HashSet<Being> allBeings;

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

	protected void addEntity(Entity entity) {
		if (entity instanceof Updatable) {
			this.updatableEntities.add((Updatable) entity);
		}
	}

	protected void removeEntity(Entity entity) {
		if (entity instanceof Updatable) {
			this.updatableEntities.remove((Updatable) entity);
		}
	}

	// Java is dumb so we need to provide this to set.toArray() to get the correct
	// type.
	private static final Updatable[] emptyUpdatablesArray = {};

	private void updateUpdatables(double ticksPassed) {
		Updatable[] updatableCloned = updatableEntities.toArray(emptyUpdatablesArray);
		for (Updatable updatable : updatableCloned) {
			updatable.update(ticksPassed);
		}
	}

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

	protected void queueTileRender(Tile tile) {
		renderQueue.add(tile);
	}

	private void onTick(double ticksPassed) {
		updateUpdatables(ticksPassed);
		render();
	}

	protected void start() {
		timer.start();
	}

	protected void stop() {
		timer.stop();
	}

	protected void pause() {
		timer.pause();
	}

	protected void resume() {
		timer.resume();
	}

	protected boolean isActive() {
		return timer.isActive();
	}
}
