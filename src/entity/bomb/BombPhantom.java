package entity.bomb;

import entity.PhantomEntity;
import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Bombable;
import interfaces.Updatable;
import logic.Direction;
import resources.SpritesLibrary;

/**
 * Create bomb explosion. This entity is spawned by {@link BombObject} when its
 * time runs out. This entity will place {@link BombFlame} on tiles of the
 * explosion blast.
 *
 */
public class BombPhantom extends PhantomEntity implements Updatable {

	/**
	 * Life time of bomb flame.
	 */
	static final int DEFAULT_FLAME_LIFETIME = 60;

	/**
	 * Amount of tick to wait before expanding the bomb blast by one block.
	 */
	static final int TICK_PER_RADIUS = 1;

	/**
	 * Current radius of the explosion.
	 */
	private int explodeRadius = 0;

	/**
	 * Number of ticks passed since the start of the explosion.
	 */
	private double totalTicks = 0;

	/**
	 * The full radius of the bomb (maximum value of {@link #explodeRadius}).
	 */
	final int radius;

	/**
	 * Position of the explosion center.
	 */
	final int x, y;

	/**
	 * The player who placed this bomb.
	 */
	private final Player placer;

	/**
	 * The amount of time (in ticks) this phantom has live for to accomplish its
	 * work.
	 */
	private final double phantomLifetime;

	/**
	 * Whether the explosion has started. This is almost always true, as
	 * {@link #startExplosion()} should always be called immediately after the
	 * phantom was created.
	 */
	private boolean setOff = false;

	/**
	 * Whether the explosion was stopped (by
	 * {@link interfaces.Bombable#getCanStopBlast()} in this direction.
	 */
	boolean lStop = false;
	boolean rStop = false;
	boolean uStop = false;
	boolean dStop = false;

	/**
	 * Construct the phantom (explosion not started).
	 * 
	 * @param placer The player who placed the bomb. To be stored in
	 *               {@link #placer}.
	 * @param radius The radius of the bomb. To be stored in {@link #radius}.
	 * @param x      The x position of the bomb center.
	 * @param y      The y position of the bomb center.
	 */
	public BombPhantom(Player placer, int radius, int x, int y) {
		this.radius = radius;
		this.placer = placer;
		this.x = x;
		this.y = y;
		this.phantomLifetime = DEFAULT_FLAME_LIFETIME + radius * TICK_PER_RADIUS;
	}

	/**
	 * Start the explosion. This calls {@link #explodeHere()}, then uses
	 * {@link GameSingleton#addPhantomEntity(PhantomEntity)} to subscribe itself to
	 * the game clock in order to expand the explosion with {@link #update(double)}
	 * as time passes.
	 */
	public void startExplosion() {
		GameSingleton.addPhantomEntity(this);
		explodeHere();
		this.setOff = true;
	}

	/**
	 * Expand the explosion by using {@link #explodeAt(Direction, int, double)} on
	 * tiles around it. Use the {@link #TICK_PER_RADIUS} constant and the tickPassed
	 * parameter to compute how many tiles to expand the explosion. If the explosion
	 * is complete ({@link #radius} == {@link #explodeRadius}), remove self with
	 * {@link GameSingleton#removePhantomEntity(PhantomEntity)}.
	 * 
	 * @param ticksPassed The number of ticks passed since last frame.
	 */
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

	/**
	 * Replace the center tile object with a {@link BombFlame}. Also call
	 * {@link interfaces.Bombable#bombed()} on the replaced object.
	 */
	private void explodeHere() {
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			BombFlame flame = new BombFlame(x, y, placer, phantomLifetime, casted.getAfterBombed(),
					SpritesLibrary.BLAST_CENTER);
			GameSingleton.addBeing(flame);
			casted.bombed();
		}
	}

	/**
	 * Replace the tile object at a certain position with a {@link BombFlame}. Also
	 * call {@link interfaces.Bombable#bombed()} on the replaced object.
	 * 
	 * @param direction Direction of the blast. For computing target tile position
	 *                  and choosing the right sprite.
	 * @param radius    Distance of the target tile away from the bomb center.
	 * @param lifetime  Amount of time (in ticks) the flame should live for.
	 * @return
	 */
	private boolean explodeAt(Direction direction, int radius, double lifetime) {
		int x = this.x + direction.getDeltaX() * radius;
		int y = this.y + direction.getDeltaY() * radius;
		if (x < 0 || x >= GameSingleton.WIDTH || y < 0 || y >= GameSingleton.HEIGHT)
			return true;
		StillObject tileObject = GameSingleton.getTileObject(x, y);
		if (tileObject instanceof Bombable) {
			Bombable casted = ((Bombable) tileObject);
			boolean stopBlast = casted.getCanStopBlast();
			StillObject afterBomb = casted.getAfterBombed();
			GameSingleton.setTileObject(x, y, afterBomb);
			BombFlame flame = new BombFlame(x, y, placer, lifetime, afterBomb,
					BombFlame.getSpriteFor(direction, stopBlast || radius == this.radius));
			GameSingleton.addBeing(flame);
			casted.bombed();
			return stopBlast;
		}
		return true;
	}

	/**
	 * Call {@link #explodeAt(Direction, int, double)} in all directions for a given
	 * radius. This will skip directions that are stopped by {@link #lStop},
	 * {@link #rStop}, {@link #uStop}, or {@link #dStop}.
	 * 
	 * @param radius The radius.
	 */
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
