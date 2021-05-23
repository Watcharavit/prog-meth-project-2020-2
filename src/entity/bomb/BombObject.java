package entity.bomb;

import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import game.Tile;
import interfaces.Bombable;
import interfaces.Updatable;
import resources.Sprite;
import resources.SpritesLibrary;

public class BombObject extends StillObject implements Updatable, Bombable {

	/**
	 * Player who place this bomb.
	 */
	private final Player placer;

	/**
	 * Bomb radius.
	 */
	public final int radius;

	/**
	 * Time left until bomb explode.
	 */
	private double remainingTicks = 105;

	/**
	 * Boolean to make sure a bomb explode only once.
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
	 * Update every 1/60 second Check if this bomb should be explode or not. If yes,
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
	 * Start the explosion of a bomb.
	 */
	private void startExplosion() {
		Tile tile = super.getTile();
		BombPhantom phantom = new BombPhantom(placer, radius, tile.x, tile.y);
		phantom.startExplosion();
	}

	/**
	 * If the bomb has not explode yet, then reduce number of bombs player can
	 * place.
	 * 
	 * @return True if the bomb has not explode. Otherwise return false.
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
