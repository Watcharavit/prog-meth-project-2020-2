package entity.livings;

import game.GameSingleton;
import game.Tile;
import logic.Direction;

abstract class Monster extends LivingBeing {

	/** speed of this monster */
	private final double speed;

	/** create monster with given parameter */
	protected Monster(Tile spawnTile, double size, double speed, Direction direction) {
		super(spawnTile, size, direction);
		this.speed = speed;
	}

	/**
	 * move monster to the same direction as the constructor
	 * 
	 * @param ticksPassed
	 * @return move monster and change its position
	 */
	protected boolean move(double ticksPassed) {
		Direction facing = super.getFacing();
		double du = speed * ticksPassed;
		double dx = facing.getDeltaX() * du;
		double dy = facing.getDeltaY() * du;
		return GameSingleton.moveBeing(this, dx, dy);
	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onDeath() {
	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onSpawn() {

	}

}
