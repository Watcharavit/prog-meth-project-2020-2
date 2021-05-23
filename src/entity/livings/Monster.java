package entity.livings;

import game.GameSingleton;
import game.Tile;
import logic.Direction;

/**
 * This class is super class for all monster beings.
 *
 */
abstract class Monster extends LivingBeing {

	/** MOvement speed of this monster */
	private final double SPEED;

	/**
	 * Create a monster.
	 * 
	 * @param spawnTile The tile at which this monster should spawn and respawn.
	 * @param size      The body size of this monster. See
	 *                  {@link entity.Being#halfSize}.
	 * @param speed     The movement speed of this monster in tiles per tick.
	 * @param direction The initial movement direction of this monster.
	 */
	protected Monster(Tile spawnTile, double size, double speed, Direction direction) {
		super(spawnTile, size, direction);
		this.SPEED = speed;
	}

	/**
	 * Move the monster in the direction it is facing.
	 * 
	 * @param ticksPassed The amount of time that has passed since last move.
	 * @return Whether or not the move was successful.
	 */
	protected boolean move(double ticksPassed) {
		Direction facing = super.getFacing();
		double du = SPEED * ticksPassed;
		double dx = facing.getDeltaX() * du;
		double dy = facing.getDeltaY() * du;
		return GameSingleton.moveBeing(this, dx, dy);
	}

	@Override
	protected void onDeath() {
	}

	@Override
	protected void onSpawn() {

	}

}
