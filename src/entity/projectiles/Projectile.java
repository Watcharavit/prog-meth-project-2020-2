package entity.projectiles;

import entity.Being;
import game.GameSingleton;
import logic.Direction;

/**
 * Very simple being that only moves in one direction and has short lifetime.
 *
 */
abstract class Projectile extends Being {

	/**
	 * Delta of its movement.
	 */
	private final double moveDeltaX, moveDeltaY;

	/**
	 * Constructor for the projectile.
	 * 
	 * @param x         Initial X position.
	 * @param y         Initial Y position.
	 * @param size      Size.
	 * @param speed     Movement speed.
	 * @param direction Movement direction.
	 */
	public Projectile(double x, double y, double size, double speed, Direction direction) {
		super(x, y, size);
		this.moveDeltaX = direction.getDeltaX() * speed;
		this.moveDeltaY = direction.getDeltaY() * speed;
	}

	/**
	 * On every frame, move the projectile in its direction.
	 * 
	 * @param ticksPassed Amount of time since last frame.
	 * @return Method to move @see
	 *         {@link game.GameSingleton#moveBeing(Being, double, double)}
	 */
	public boolean move(double ticksPassed) {
		return GameSingleton.moveBeing(this, moveDeltaX * ticksPassed, moveDeltaY * ticksPassed);
	}

}
