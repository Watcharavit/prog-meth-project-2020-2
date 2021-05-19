package entity.livings;

import entity.Being;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Updatable;

public abstract class LivingBeing extends Being implements Updatable {
	private double timeUntilSpawn = 0;
	private boolean dead = false;
	private final Tile spawnTile;

	protected LivingBeing(Tile spawnTile, double size) {
		super(spawnTile.x + 0.5, spawnTile.y + 0.5, size);
		this.spawnTile = spawnTile;
	}

	@Override
	public void update(double ticksPassed) {
		if (dead) {
			timeUntilSpawn -= ticksPassed;
			if (timeUntilSpawn <= 0) {
				dead = false;
				respawn();
			}
		}
	}

	public void die() {
		if (!dead) {
			dead = true;
			timeUntilSpawn = getSpawnCooldown();
		}
	}

	private void respawn() {
		GameSingleton.removeBeing(this);
		this.setX(spawnTile.x + 0.5);
		this.setY(spawnTile.y + 0.5);
		GameSingleton.addBeing(this);
	}

	protected double getSpawnCooldown() {
		return 0.0;
	}

	protected boolean isDead() {
		return this.dead;
	}

}
