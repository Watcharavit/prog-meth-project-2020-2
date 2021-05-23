package entity.projectiles;

import entity.Being;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

public class ProjectilePunch extends Projectile implements Updatable, Collidable {

	/**
	 * Punch's size.
	 */
	public static final double SIZE = 0.6;

	/**
	 * Punch's speed.
	 */
	private final static double SPEED = 0.2;

	/**
	 * Time left before this object is gone from screen.
	 */
	private double remainingMoveTicks;

	/**
	 * Player who uses this skill.
	 */
	private final Player placer;

	/**
	 * Sprite to be rendered for this entity.
	 */
	private final Sprite sprite;

	/**
	 * Initialize all field and set remaining move time. Then get sprite according
	 * to the direction it is facing.
	 * 
	 * @param x         Its X position.
	 * @param y         Its Y position.
	 * @param direction The direction it is going.
	 * @param placer    Player who uses this skill.
	 */
	public ProjectilePunch(double x, double y, Direction direction, Player placer) {
		super(x, y, SIZE, SPEED, direction);
		this.remainingMoveTicks = 3.0;
		this.placer = placer;
		switch (direction) {
		case DOWN:
			this.sprite = SpritesLibrary.PUNCH_DOWN;
			break;
		case UP:
			this.sprite = SpritesLibrary.PUNCH_UP;
			break;
		case LEFT:
			this.sprite = SpritesLibrary.PUNCH_LEFT;
			break;
		case RIGHT:
			this.sprite = SpritesLibrary.PUNCH_RIGHT;
			break;
		default:
			// Unreachable
			this.sprite = null;
		}
	}

	/**
	 * Remove this object from screen {@link game.GameSingleton#removeBeing(Being)}
	 */
	private void remove() {
		GameSingleton.removeBeing(this);
	}

	/**
	 * If punch hits other beings, then kill them.
	 */
	@Override
	public void collide(Being otherBeing) {
		if (otherBeing instanceof LivingBeing && otherBeing != placer) {
			((LivingBeing) otherBeing).die();
		}
	}

	/**
	 * Check if this entity can be pass through.
	 * 
	 * @return Always return true.
	 */
	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	/**
	 * Update every 1/60 second. If remaining move time hasn't run out yet, then
	 * move it. Otherwise, remove it.
	 */
	@Override
	public void update(double ticksPassed) {
		if (remainingMoveTicks > 0) {
			remainingMoveTicks -= ticksPassed;
			super.move(ticksPassed);
		} else {
			remove();
		}
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
