package entity.livings;

import entity.Being;
import game.Tile;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * This class provides information about Ghost Monster. Unlike other living
 * beings, the ghost monster disappears for a duration in spawning phase (see
 * {@link LivingBeing}). We achieve this by returning an empty sprite for some
 * time after spawn.
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
	 * Update on every frame: Handle movement and the disappear-before-spawn logic.
	 * If monster is dead, reduce {@link #hiddenTime}. If monster is alive, try to
	 * move. If move fail, turn to a random direction.
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
	 * Upon collision with a player, kill the player.
	 */
	@Override
	public void collide(Being otherCharacter) {
		if (super.isDead())
			return;
		if (otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		}
	}

	/**
	 * Ghosts are pass-through. Other beings can walk through them.
	 */
	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	/**
	 * 5 seconds spawn cooldown (during 4 of which this thing will be invisible),
	 */
	@Override
	protected double getSpawnCooldown() {
		return 300.0;
	}

	@Override
	protected void onDeath() {

	}

	/**
	 * Upon entering spawning phase, start a 4 seconds hidden duration.
	 */
	@Override
	protected void onSpawn() {
		this.hiddenTime = 240.0;
	}

	@Override
	protected void onAlive() {

	}

	/**
	 * @return return Transparent sprite if hidden, normal sprite otherwise.
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
