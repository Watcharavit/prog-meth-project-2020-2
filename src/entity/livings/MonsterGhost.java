package entity.livings;

import entity.Being;
import game.Tile;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * This class provides information about Ghost Monster.
 *
 */
public class MonsterGhost extends Monster implements Collidable, Updatable {

	/**
	 * Size of Ghost Monster.
	 */
	public static final double SIZE = 0.8;

	/**
	 * Movement speed of Ghost Monster.
	 */
	public static final double SPEED = 0.1;

	/**
	 * Time it takes before Ghost Monster respawn.
	 */
	private double hiddenTime = 0;

	/**
	 * create Ghost Monster with given spawn tile, direction.
	 * 
	 * @param spawnTile Position that living being spawns.
	 */
	public MonsterGhost(Tile spawnTile) {
		super(spawnTile, SIZE, SPEED, Direction.random());
	}

	/**
	 * Update every 1/60 second Check if this monster is dead or not. If it is
	 * alive, then move it to random direction. Otherwise respawn it.
	 */
	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead()) {
			this.hiddenTime -= ticksPassed;
			return;
		}

		boolean didMove = super.move(ticksPassed);
		if (!didMove) {
			super.setFacing(Direction.random());
		}
	}

	/**
	 * If it collides with player, kill the player.
	 */
	@Override
	public void collide(Being otherCharacter) {
		if (super.isDead())
			return;
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		}
	}

	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	@Override
	protected double getSpawnCooldown() {
		return 240.0;
	}

	@Override
	protected void onDeath() {

	}

	@Override
	protected void onSpawn() {
		this.hiddenTime = 300.0;
	}

	@Override
	protected void onAlive() {

	}

	/**
	 * @return return Transparent sprite if this entity is waiting for respawns.
	 *         Otherwise, return living sprite.
	 */
	@Override
	protected Sprite getAliveSprite() {
		if (super.isDead() && this.hiddenTime > 0)
			return SpritesLibrary.TRANSPARENT;
		else
			return SpritesLibrary.GHOST;
	}

	@Override
	protected Sprite getDyingSprite() {
		return SpritesLibrary.GHOST_DYING;
	}

}
