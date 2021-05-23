package entity.livings;

import entity.Being;
import game.GameSingleton;
import game.Tile;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;

/**
 * A being that requires death and respawn logic. Death and respawn has 2
 * phases: Dying phase: player just died, is frozen and rendered with
 * {@link #getDyingSprite()}. Spawning phase: player moved to spawn, rendered
 * with {@link #getAliveSprite()}, but still frozen.
 */
public abstract class LivingBeing extends Being implements Updatable {

	/** Time until this living being is respawned. */
	private double dyingTime = 0;

	/** Time until this living being gets its death lock removed. */
	private double spawningTime = 0;

	/**
	 * Whether or not this being is in its dying phase (frozen at the location of
	 * death and rendered with {@link #getDyingSprite()}
	 */
	private boolean isDying = false;

	/**
	 * Whether or not this being is in its spawning phase (frozen at the spawn
	 * location and rendered normally)
	 */
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
	 * Follow the game clock to time and progress the phases.
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
	 * Make this living being die. Set isDying true, calls {@link #onDeath()}, and
	 * start the dying phase that lasts 0.5 seconds.
	 */
	public void die() {
		if (!isDying && !isSpawning) {
			isDying = true;
			dyingTime = 30.0;
			this.onDeath();
		}
	}

	/**
	 * Respawn this being. Ends the dying phase and starts the respawning phase that
	 * lasts for {@link #getSpawnCooldown()}.
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
	 * Once fully alive (i.e. out of spawning phase), call {@link #onAlive()}.
	 */
	private void live() {
		isSpawning = false;
		this.onAlive();
	}

	/**
	 * To be called immediately upon death.
	 */
	protected abstract void onDeath();

	/**
	 * To be called upon spawn (that is when moving from dying phase to spawning
	 * phase).
	 */
	protected abstract void onSpawn();

	/**
	 * To be called when fully alive (out of spawning phase).
	 */
	protected abstract void onAlive();

	/**
	 * Select the sprite to render based on which phase this being is on
	 * ({@link #getDyingSprite()} if dying phase, {@link #getAliveSprite()}
	 * otherwise).
	 */
	@Override
	public Sprite getSprite() {
		if (isDying)
			return getDyingSprite();
		else
			return getAliveSprite();
	}

	/**
	 * Get the sprite to render when this being is alive.
	 * 
	 * @return The sprite to render when this being is alive.
	 */
	protected abstract Sprite getAliveSprite();

	/**
	 * Get the sprite to render when this being is dying.
	 * 
	 * @return The sprite to render when this being is dying.
	 */
	protected abstract Sprite getDyingSprite();

	/**
	 * Get the amount of time this being should stay frozen in spawning phase.
	 * Defaults to 0 but can be overriden by subclasses.
	 * 
	 * @return Cooldown time it takes to resapwn.
	 */
	protected double getSpawnCooldown() {
		return 0.0;
	}

	/**
	 * Check if it is dead or not. Being is considered dead if it is in either dying
	 * phase OR spawning phase.
	 * 
	 * @return true if it is dead. Otherwise, false.
	 */
	protected boolean isDead() {
		return this.isDying || this.isSpawning;
	}

}
