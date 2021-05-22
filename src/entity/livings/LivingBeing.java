package entity.livings;

import entity.Being;
import game.GameSingleton;
import game.Tile;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;

public abstract class LivingBeing extends Being implements Updatable {

	/** Time it takes before that living being completely gone from the game. */
	private double dyingTime = 0;

	/** Time it takes before re-spawning. */
	private double spawningTime = 0;

	/** Check if it should be dying soon. */
	private boolean isDying = false;

	/** Check if it should be re-spawn soon. */
	private boolean isSpawning = false;

	/** Position that living being spawns. */
	private final Tile spawnTile;

	/** Direction that this living being is going. */
	private Direction facing;

	/**
	 * Set spawn tile, size, and direction of this being.
	 * 
	 * @param spawnTile Position that living being spawns.
	 * @param size      Size of this being.
	 * @param direction Direction that this living being is going.
	 */
	protected LivingBeing(Tile spawnTile, double size, Direction direction) {
		super(spawnTile.x + 0.5, spawnTile.y + 0.5, size);
		this.spawnTile = spawnTile;
		this.facing = direction;
	}

	/**
	 * 
	 * @return Direction that this living being is going.
	 */
	public Direction getFacing() {
		return this.facing;
	}

	/**
	 * Set direction that this living being is going.
	 * 
	 * @param facing Direction that this living being is going.
	 */
	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	/**
	 * Update every 1/60 second If this living being is dying soon, then re-spawn
	 * it. Otherwise, do nothing.
	 */
	@Override
	public void update(double ticksPassed) {
		if (isDying) {
			dyingTime -= ticksPassed;
			if (dyingTime <= 0) {
				respawn();
			}
		} else if (isSpawning) {
			spawningTime -= ticksPassed;
			if (spawningTime <= 0) {
				live();
			}
		}
	}

	/**
	 * Make this living being die. Set isDying true.
	 */
	public void die() {
		if (!isDying && !isSpawning) {
			isDying = true;
			dyingTime = 30.0;
			this.onDeath();
		}
	}

	/**
	 * Respawn this living being at the spawn tile and set isDying false, isSpawning
	 * true.
	 */
	private void respawn() {
		isDying = false;
		isSpawning = true;
		spawningTime = getSpawnCooldown();

		GameSingleton.removeBeing(this);
		this.setX(spawnTile.x + 0.5);
		this.setY(spawnTile.y + 0.5);
		GameSingleton.addBeing(this);
		this.onSpawn();
	}

	/**
	 * When alive, do something. Override in sub class. Set isSpawning false.
	 */
	private void live() {
		isSpawning = false;
		this.onAlive();
	}

	/**
	 * Override in sub class.
	 */
	protected abstract void onDeath();

	/**
	 * Override in sub class.
	 */
	protected abstract void onSpawn();

	/**
	 * Override in sub class.
	 */
	protected abstract void onAlive();

	/**
	 * If this living being is alive, then return normal image. Otherwise, return
	 * Dying Image.
	 */
	@Override
	public Sprite getSprite() {
		if (isDying)
			return getDyingSprite();
		else
			return getAliveSprite();
	}

	/**
	 * Override in sub class.
	 */
	protected abstract Sprite getAliveSprite();

	/**
	 * Override in sub class.
	 */
	protected abstract Sprite getDyingSprite();

	/**
	 * Override in sub class.
	 */
	protected double getSpawnCooldown() {
		return 0.0;
	}

	/**
	 * Check if it is dead or not.
	 * 
	 * @return true if it is dead. Otherwise, false.
	 */
	protected boolean isDead() {
		return this.isDying || this.isSpawning;
	}

}
