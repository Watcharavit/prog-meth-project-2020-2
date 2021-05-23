package entity.livings;

import entity.Being;
import game.Tile;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * This class provides information about Wall Thorn Monster. The wall thorn
 * monster cannot be killed by anything and only moves in one axis.
 */
public class MonsterWallThorn extends Monster implements Updatable, Collidable {
	/**
	 * Size of Wall Thorn Monster.
	 */
	public static final double SIZE = 0.8;

	/**
	 * Speed of Wall Thorn Monster.
	 */
	public static final double SPEED = 0.05;

	/**
	 * create Wall Thorn Monster with given spawn tile and direction
	 * 
	 * @param spawnTile Position that Wall Thorn Monster spawns.
	 * @param direction Direction that this living being is going.
	 */
	public MonsterWallThorn(Tile spawnTile, Direction direction) {
		super(spawnTile, SIZE, SPEED, direction);
	}

	/**
	 * Update on every frame: Move the monster and flip direction if stuck.
	 */
	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead())
			return;
		boolean didMove = super.move(ticksPassed);
		if (!didMove) {
			super.setFacing(super.getFacing().flip());
		}
	}

	/**
	 * If this monster collide with another living being, kill the other being.
	 */
	@Override
	public void collide(Being otherCharacter) {
		if (super.isDead())
			return;
		if (otherCharacter instanceof LivingBeing && !(otherCharacter instanceof MonsterWallThorn)) {
			((LivingBeing) otherCharacter).die();
		}

	}

	/**
	 * This monster cannot die, so this method is overridden to do nothing.
	 */
	@Override
	public void die() {
		// This thing can't die.
	}

	/**
	 * Wall thorns are not pass-through: they can collide with each other,
	 * {@link entity.projectiles.ProjectileRollingBomb} can be stopped by the, etc.
	 * 
	 * @return Always return false.
	 */
	@Override
	public boolean getCanPassThrough() {
		return false;
	}

	/**
	 * As this being never dies, this function is actually not needed.
	 * 
	 * @return Always return 0.0
	 */
	@Override
	protected double getSpawnCooldown() {
		return 0.0;
	}

	/**
	 * Never dies, so do nothing on death.
	 */
	@Override
	protected void onDeath() {

	}

	/**
	 * Never dies, so do nothing on spawn.
	 */
	@Override
	protected void onSpawn() {

	}

	/**
	 * Never dies, so do nothing on alive.
	 */
	@Override
	protected void onAlive() {

	}

	/**
	 * @return return Wall Thorn monster sprite.
	 */
	@Override
	protected Sprite getAliveSprite() {
		return SpritesLibrary.THORN;
	}

	/**
	 * Never dies, so return nothing.
	 * 
	 * @return return transparent sprite.
	 */
	@Override
	protected Sprite getDyingSprite() {
		return SpritesLibrary.TRANSPARENT;
	}

}
