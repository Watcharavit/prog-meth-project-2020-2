package entity.projectiles;

import entity.Being;
import game.GameSingleton;
import logic.Direction;

abstract class Projectile extends Being {

	/**
	 * Distance that it moves.
	 */
	private final double moveDeltaX, moveDeltaY;

	/**
	 * Constructor for skill after using the equipment.
	 * 
	 * @param x         Its X position.
	 * @param y         Its Y position.
	 * @param size      Its size.
	 * @param speed     Its speed.
	 * @param direction The direction it is going.
	 */
	public Projectile(double x, double y, double size, double speed, Direction direction) {
		super(x, y, size);
		this.moveDeltaX = direction.getDeltaX() * speed;
		this.moveDeltaY = direction.getDeltaY() * speed;
	}

	/**
	 * 
	 * @param ticksPassed Time rate of game rendering.
	 * @return Method to move @see
	 *         {@link game.GameSingleton#moveBeing(Being, double, double)}
	 */
	public boolean move(double ticksPassed) {
		return GameSingleton.moveBeing(this, moveDeltaX * ticksPassed, moveDeltaY * ticksPassed);
	}

}
