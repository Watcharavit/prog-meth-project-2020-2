package entity.bomb;

import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import game.Tile;
import interfaces.Bombable;
import interfaces.Updatable;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * The bomb players place. It explodes after a specified amount of time, or when
 * hit by blast from another bomb's explosion. It spawns a {@link BombPhantom}
 * to create explosion.
 */
public class BombObject extends StillObject implements Updatable, Bombable {

	/**
	 * Player who placed this bomb.
	 */
	private final Player placer;

	/**
	 * The radius of this bomb.
	 */
	public final int radius;

	/**
	 * Time left until bomb explode.
	 */
	private double remainingTicks = 105;

	/**
	 * Lock to make sure a bomb explode only once.
	 */
	private boolean explosionLock = false;

	/**
	 * Object at the position before in replaces by a bomb.
	 */
	private final Bombable originalObject;

	/**
	 * Construct bomb.
	 * 
	 * @param placer         Player who place this bomb.
	 * @param radius         Bomb radius.
	 * @param originalObject Object at the position before in replaces by a bomb.
	 */
	public BombObject(Player placer, int radius, Bombable originalObject) {
		this.placer = placer;
		this.radius = radius;
		this.originalObject = originalObject;
	}

	/**
	 * Update on every frame: Check if this bomb should be explode or not. If yes,
	 * then explode.
	 */
	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		if (this.remainingTicks <= 0) {
			if (prepareForExplosion())
				startExplosion();
		}
	}

	/**
	 * Start the explosion of the bomb.
	 */
	private void startExplosion() {
		Tile tile = super.getTile();
		BombPhantom phantom = new BombPhantom(placer, radius, tile.x, tile.y);
		phantom.startExplosion();
	}

	/**
	 * If the bomb has not explode yet, then remove this object form the map and
	 * return the placed bomb quota (with {@link entity.livings.Player#returnBomb()}
	 * to the placer.
	 * 
	 * @return True if the bomb has not explode (so it is eligible for explosion).
	 *         Otherwise return false.
	 */
	public boolean prepareForExplosion() {
		if (!explosionLock) {
			explosionLock = true;
			placer.returnBomb();
			Tile tile = super.getTile();
			GameSingleton.setTileObject(tile.x, tile.y, originalObject.getAfterBombed());
			return true;
		} else
			return false;
	}

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB;
	}

	/**
	 * Called when a bomb explode.
	 */
	@Override
	public void bombed() {
		if (prepareForExplosion())
			startExplosion();
	}

	/**
	 * @return Object after the bomb explode and completely gone.
	 */
	@Override
	public StillObject getAfterBombed() {
		return originalObject.getAfterBombed();
	}

}
