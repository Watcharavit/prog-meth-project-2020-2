package entity.livings;

import game.GameSingleton;
import game.Tile;
import logic.Direction;

abstract class Monster extends LivingBeing {
	private final double speed;
	protected Monster(Tile spawnTile, double size, double speed, Direction direction) {
		super(spawnTile, size, direction);
		this.speed = speed;
	}

	protected boolean move(double ticksPassed) {
		Direction facing = super.getFacing();
		double du = speed * ticksPassed;
		double dx = facing.getDeltaX() * du;
		double dy = facing.getDeltaY() * du;
		return GameSingleton.moveBeing(this, dx, dy);
	}

	@Override
	protected void onDeath() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onSpawn() {
		// TODO Auto-generated method stub

	}

}
