package entity.livings;

import entity.Being;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Updatable;
import logic.Direction;

public abstract class LivingBeing extends Being implements Updatable {
	private double dyingTime = 0;
	private double spawningTime = 0;
	private boolean isDying = false;
	private boolean isSpawning = false;
	private final Tile spawnTile;

	protected LivingBeing(Tile spawnTile, double size, Direction direction) {
		super(spawnTile.x + 0.5, spawnTile.y + 0.5, size, direction);
		this.spawnTile = spawnTile;
	}

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

	public void die() {
		if (!isDying && !isSpawning) {
			isDying = true;
			dyingTime = 30.0;
			this.onDeath();
		}
	}

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

	private void live() {
		isSpawning = false;
		this.onAlive();
	}

	protected abstract void onDeath();

	protected abstract void onSpawn();

	protected abstract void onAlive();

	@Override
	public Sprite getSprite() {
		if (isDying)
			return getDyingSprite();
		else
			return getAliveSprite();
	}

	protected abstract Sprite getAliveSprite();

	protected abstract Sprite getDyingSprite();

	protected double getSpawnCooldown() {
		return 0.0;
	}

	protected boolean isDead() {
		return this.isDying || this.isSpawning;
	}

}
