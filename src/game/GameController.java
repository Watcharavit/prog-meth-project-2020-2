package game;

import java.util.HashSet;
import java.util.Set;

import entity.base.Being;
import entity.base.Entity;
import entity.base.StillObject;
import entity.base.Updatable;
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
	private void updateUpdatables(double ticksPassed) {
		Set<Updatable> updatableCloned = new HashSet<Updatable>();
		updatableCloned.addAll(updatableEntities);
		for (Updatable updatable : updatableCloned) {
			updatable.update(ticksPassed);
		}
	}
	private void render() {
		for (Tile tile : renderQueue) {
			int x = tile.x;
			int y = tile.y;
			StillObject object = tile.getObject();
			object.getSprite().drawTopLeft(objectsGc, x*GameSingleton.TILE_SIZE, y*GameSingleton.TILE_SIZE);
		}
		beingsGc.clearRect(0, 0, GameSingleton.WIDTH*GameSingleton.TILE_SIZE, GameSingleton.HEIGHT*GameSingleton.TILE_SIZE);
		for (Being being : allBeings) {
			being.getSprite().drawCenter(beingsGc, being.getX()*GameSingleton.TILE_SIZE, being.getY()*GameSingleton.TILE_SIZE);
		}
		renderQueue = new HashSet<Tile>();
	}
	protected void queueTileRender(Tile tile) {
		renderQueue.add(tile);
	}
	private void onTick(double ticksPassed) {
		if (ticksPassed > 1.1) System.out.println("Slow Update: " + ticksPassed);
		updateUpdatables(ticksPassed);
		render();
	}
	protected void initializeLoop() {
		timer = new GameLoopTimer() {
			@Override
			protected void tick(long frameTimeNano) {
				double ticksPassed = frameTimeNano * 0.6e-7; // 60fps = 16.67ms = 1.00
				onTick(ticksPassed);
			  }
		};
		timer.start();
	}
	protected void pause() {
		timer.pause();
	}
	protected void resume() {
		timer.resume();
	}
}