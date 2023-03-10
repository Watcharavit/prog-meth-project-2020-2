package entity.bomb;

import entity.Being;
import entity.StillObject;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * The blast of the bomb. Kills things that step on it.
 * 
 */
public class BombFlame extends Being implements Updatable, Collidable {

	/**
	 * Size of bomb flame being. The value is 1.0, meaning it occupies the entire
	 * tile. Any being overlapping with the tile it is on will trigger
	 * {@link #collide(Being)}.
	 */
	private static final double SIZE = 1.0;

	/**
	 * Player who place this bomb. This is currently unused. We may add a mechanic
	 * to increase the placer's score when this flame kills other players.
	 */
	//private final Player placer;

	/**
	 * Time left until bomb flame is gone.
	 */
	private double remainingTicks;

	/**
	 * Object that will be there instead of bomb flame after it is gone.
	 */
	private final StillObject afterBomb;

	/**
	 * Bomb flame image.
	 */
	private final Sprite sprite;

	/**
	 * Create bomb flame.
	 * 
	 * @param x         Its position.
	 * @param y         Its position.
	 * @param placer    Player who place this bomb.
	 * @param lifetime  Time left until bomb flame is gone..
	 * @param afterBomb Object that will be there instead of bomb flame after it is
	 *                  gone.
	 * @param sprite    Bomb flame image.
	 */
	public BombFlame(int x, int y, Player placer, double lifetime, StillObject afterBomb, Sprite sprite) {
		super(x + 0.5, y + 0.5, SIZE);
		//this.placer = placer;
		this.remainingTicks = lifetime;
		this.afterBomb = afterBomb;
		this.sprite = sprite;
	}

	/**
	 * Kill living being if they collides.
	 */
	@Override
	public void collide(Being character) {
		if (character instanceof LivingBeing) {
			((LivingBeing) character).die();
		}
	}

	/**
	 * Check if bomb flame can be pass through.
	 * 
	 * @return Always return true.
	 */
	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	/**
	 * Get sprite for a flame tile.
	 * 
	 * @param direction The direction of the flame tile.
	 * @param end       Whether or not the flame tile is at the farthest reach of
	 *                  the explosion.
	 * @return Sprite for each position.
	 */
	protected static Sprite getSpriteFor(Direction direction, boolean end) {
		if (end) {
			switch (direction) {
			case UP:
				return SpritesLibrary.BLAST_TOP;
			case DOWN:
				return SpritesLibrary.BLAST_BOTTOM;
			case LEFT:
				return SpritesLibrary.BLAST_LEFT;
			case RIGHT:
				return SpritesLibrary.BLAST_RIGHT;
			}
		} else {
			switch (direction) {
			case UP:
				return SpritesLibrary.BLAST_VERTICAL;
			case DOWN:
				return SpritesLibrary.BLAST_VERTICAL;
			case LEFT:
				return SpritesLibrary.BLAST_HORIZONTAL;
			case RIGHT:
				return SpritesLibrary.BLAST_HORIZONTAL;
			}
		}

		// Unreachable
		return null;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Update on every frame: Check if this bomb flame should be gone. If yes, then
	 * remove it and set tile to an specific object after it is gone.
	 */
	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		int x = (int) super.getX();
		int y = (int) super.getY();
		if (this.remainingTicks <= 0) {
			GameSingleton.removeBeing(this);
			GameSingleton.setTileObject(x, y, afterBomb);
		}
	}
}
