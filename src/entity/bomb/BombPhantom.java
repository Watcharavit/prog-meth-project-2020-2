package entity.bomb;

import entity.PhantomEntity;
import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import graphics.SpritesLibrary;
import interfaces.Bombable;
import interfaces.Updatable;
import logic.Direction;

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
		GameSingleton.addPhantomEntity(this);
		explodeHere();
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

	private void explodeHere() {
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			BombFlame flame = new BombFlame(x, y, placer, phantomLifetime, casted, SpritesLibrary.BLAST_CENTER);
			GameSingleton.addBeing(flame);
			casted.bombed();
		}
	}

	private boolean explodeAt(Direction direction, int radius, double lifetime) {
		int x = this.x + direction.getDeltaX() * radius;
		int y = this.y + direction.getDeltaY() * radius;
		if (x < 0 || x >= GameSingleton.WIDTH || y < 0 || y >= GameSingleton.HEIGHT)
			return true;
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			boolean stopBlast = casted.getCanStopBlast();
			BombFlame flame = new BombFlame(x, y, placer, lifetime, casted,
					BombFlame.getSpriteFor(direction, stopBlast || radius == this.radius));
			GameSingleton.addBeing(flame);
			casted.bombed();

			if (!stopBlast)
				return false;
		}
		return true;
	}

	private void explodeForRadius(int radius) {
		double lifetime = DEFAULT_FLAME_LIFETIME + (this.radius - radius) * TICK_PER_RADIUS;
		if (!lStop)
			lStop = this.explodeAt(Direction.LEFT, radius, lifetime);
		if (!rStop)
			rStop = this.explodeAt(Direction.RIGHT, radius, lifetime);
		if (!uStop)
			uStop = this.explodeAt(Direction.UP, radius, lifetime);
		if (!dStop)
			dStop = this.explodeAt(Direction.DOWN, radius, lifetime);
	}
}
