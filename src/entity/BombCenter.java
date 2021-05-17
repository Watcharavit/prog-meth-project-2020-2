package entity;

import application.GameSingleton;
import application.Tile;
import entity.base.Bombable;
import entity.base.Entity;
import entity.base.StillObject;
import entity.base.Updatable;
import gui.Sprite;

public class BombCenter extends BombFlame implements Updatable {
	int explodedRadius = 0;
	double explodedTicks = 0;
	final int radius;
	boolean explosionFinished = false;
	
	public BombCenter(Player placer, int radius) {
		super(placer, false);
		this.radius = radius;
	}
	
	@Override
	public void update(double frameTimeRatio) {
		super.update(frameTimeRatio);
		this.explodedTicks += frameTimeRatio;
		this.explodedRadius = (int) (this.explodedTicks);
		if (!explosionFinished) {
			super.remainingFrame = BombFlame.DEFAULT_REMAINING_FRAME;
			this.explode(Math.min(radius, explodedRadius));
			if (radius <= explodedRadius) {
				explosionFinished = true;
			}
		}
	}
	
	private boolean explodeAt(int x, int y) {
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			boolean stopBlast = casted.getCanStopBlast();
			if (!(tileObject instanceof BombCenter)) {
				BombFlame flame = new BombFlame(placer, stopBlast);
				GameSingleton.setTileObject(x, y, flame);
			}
			casted.bombed();
			if (!stopBlast) return false;
		}
		return true;
	}
	
	private void explode(int radius) {
		Tile containingTile = super.getTile();
		int x = containingTile.x;
		int y = containingTile.y;
		for (int i = x + 1; i <= x + radius; i++) {
			if (this.explodeAt(i, y)) break;
		}
		for (int i = x - 1; i >= x - radius; i--) {
			if (this.explodeAt(i, y)) break;
		}
		for (int j = y + 1; j <= y + radius; j++) {
			if (this.explodeAt(x, j)) break;
		}
		for (int j = y - 1; j >= y - radius; j--) {
			if (this.explodeAt(x, j)) break;
		}
	}
}
