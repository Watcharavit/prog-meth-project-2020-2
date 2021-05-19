package entity.bomb;

import entity.PhantomEntity;
import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Bombable;
import interfaces.Updatable;

public class BombPhantom extends PhantomEntity implements Updatable {
	static final int DEFAULT_FLAME_LIFETIME = 60;
	static final int TICK_PER_RADIUS = 1;
	private int explodeRadius = 0;
	private double totalTicks = 0;
	final int radius, x, y;
	private final Player placer;
	private final double phantomLifetime;
	private boolean setOff = false;

	boolean lStop = false;
	boolean rStop = false;
	boolean uStop = false;
	boolean dStop = false;

	public BombPhantom(Player placer, int radius, int x, int y) {
		this.radius = radius;
		this.placer = placer;
		this.x = x;
		this.y = y;
		this.phantomLifetime = DEFAULT_FLAME_LIFETIME + radius * TICK_PER_RADIUS;

	}

	public void startExplosion() {
		explodeAt(x, y, phantomLifetime);
		GameSingleton.addPhantomEntity(this);
		this.setOff = true;
	}

	@Override
	public void update(double ticksPassed) {
		if (!setOff)
			return;
		this.totalTicks += ticksPassed;
		int newExplodeRadius = Math.min(radius, (int) (this.totalTicks / TICK_PER_RADIUS));
		if (newExplodeRadius >= 1 && newExplodeRadius > explodeRadius) {
			for (int i = explodeRadius + 1; i <= newExplodeRadius; i++)
				this.explodeForRadius(i);
			explodeRadius = newExplodeRadius;
		}
		if (this.totalTicks > this.phantomLifetime) {
			GameSingleton.removePhantomEntity(this);
		}
	}

	private boolean explodeAt(int x, int y, double lifetime) {
		if (x < 0 || x >= GameSingleton.WIDTH || y < 0 || y >= GameSingleton.HEIGHT)
			return true;
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			boolean stopBlast = casted.getCanStopBlast();
			BombFlame flame = new BombFlame(placer, lifetime, casted.getShouldSpawnDrop());
			GameSingleton.setTileObject(x, y, flame);
			casted.bombed();

			if (!stopBlast)
				return false;
		}
		return true;
	}

	private void explodeForRadius(int radius) {
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
}
