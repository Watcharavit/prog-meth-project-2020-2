package entity;

import entity.base.Bombable;
import entity.base.Entity;
import entity.base.StillObject;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;

public class BombCenter extends BombFlame implements Updatable {
	static final int DEFAULT_FLAME_LIFETIME = 60;
	static final int TICK_PER_RADIUS = 1;
	int explodeRadius = 0;
	double explodedTicks = 0;
	final int radius;
	boolean explosionFinished = false;

	boolean lStop = false;
	boolean rStop = false;
	boolean uStop = false;
	boolean dStop = false;

	public BombCenter(Player placer, int radius) {
		super(placer, DEFAULT_FLAME_LIFETIME + radius * TICK_PER_RADIUS, false);
		this.radius = radius;
	}

	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		this.explodedTicks += ticksPassed;
		int newExplodeRadius = Math.min(radius, (int) (this.explodedTicks / TICK_PER_RADIUS));
		if (newExplodeRadius >= 1 && newExplodeRadius > explodeRadius) {
			for (int i = explodeRadius + 1; i <= newExplodeRadius; i++)
				this.explodeForRadius(i);
			explodeRadius = newExplodeRadius;
		}
	}

	private boolean explodeAt(int x, int y, double lifetime) {
		if (x < 0 || x >= GameSingleton.WIDTH || y < 0 || y >= GameSingleton.HEIGHT)
			return true;
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			boolean stopBlast = casted.getCanStopBlast();
			if (tileObject instanceof BombCenter) {
				((BombCenter) tileObject).setLifetime(lifetime);
			} else {
				BombFlame flame = new BombFlame(placer, lifetime, casted.getShouldSpawnDrop());
				GameSingleton.setTileObject(x, y, flame);
			}
			casted.bombed();

			if (!stopBlast)
				return false;
		}
		return true;
	}

	private void explodeForRadius(int radius) {
		Tile containingTile = super.getTile();
		int x = containingTile.x;
		int y = containingTile.y;
		double lifetime = DEFAULT_FLAME_LIFETIME + (this.radius - radius) * TICK_PER_RADIUS;
		if (!lStop)
			lStop = this.explodeAt(x - radius, y, lifetime);
		if (!rStop)
			rStop = this.explodeAt(x + radius, y, lifetime);
		if (!uStop)
			uStop = this.explodeAt(x, y - radius, lifetime);
		if (!dStop)
			dStop = this.explodeAt(x, y + radius, lifetime);
	}

	private void setLifetime(double lifetime) {
		super.remainingTicks = lifetime;
	}
}
