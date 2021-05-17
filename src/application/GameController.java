package application;

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
	private HashSet<Tile> renderQueueStage;
	private GraphicsContext gc;
	private final Tile[][] tiles;
	private final int width, height;
	
	protected GameController(Canvas canvas, Tile[][] tiles) {
		updatableEntities = new HashSet<Updatable>();
		renderQueueStage = new HashSet<Tile>();
		this.gc = canvas.getGraphicsContext2D();
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
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
	private void updateUpdatables(double frameTimeRatio) {
		Set<Updatable> updatableCloned = new HashSet<Updatable>();
		updatableCloned.addAll(updatableEntities);
		for (Updatable updatable : updatableCloned) {
			updatable.update(frameTimeRatio);
		}
	}
	private void propagateTileRender(Tile tile) {
		if (renderQueue.add(tile)) {
			int x = tile.x;
			int y = tile.y;
			boolean hasBeing = tile.getBeings().size() > 0;
			for (int i = Math.max(0, x-1); i <= Math.min(x+1, width-1); i++) {
				for (int j = Math.max(0, y-1); j <= Math.min(y+1, height-1); j++) {
					Tile otherTile = tiles[i][j];
					if (hasBeing || otherTile.getBeings().size() > 0) propagateTileRender(tiles[i][j]);
				}
			}
		}
	}
	private void render() {
		renderQueue = new HashSet<Tile>();
		for (Tile tile : renderQueueStage) {
			propagateTileRender(tile);
		}
		for (Tile tile : renderQueue) {
			int x = tile.x;
			int y = tile.y;
			StillObject object = tile.getObject();
			object.getSprite().drawTopLeft(gc, x*GameSingleton.TILE_SIZE, y*GameSingleton.TILE_SIZE);
		}
		for (Tile tile : renderQueue) {
			HashSet<Being> beings = tile.getBeings();
			for (Being being : beings) {
				being.getSprite().drawCenter(gc, being.getX()*GameSingleton.TILE_SIZE, being.getY()*GameSingleton.TILE_SIZE);
			}
		}
		renderQueueStage = new HashSet<Tile>();
	}
	protected void queueTileRender(Tile tile) {
		renderQueueStage.add(tile);
	}
	private void onTick(double frameTimeRatio) {
		updateUpdatables(frameTimeRatio);
		render();
	}
	protected void initializeLoop() {
		GameLoopTimer timer = new GameLoopTimer() {
			@Override
			protected void tick(long frameTimeNano) {
				double frameTimeRatio = frameTimeNano * 0.6e-7; // 60fps = 16.67ms = 1.00
				onTick(frameTimeRatio);
			}
		};
		timer.start();
	}
}
