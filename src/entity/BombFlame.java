package entity;

import entity.base.Being;
import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;

public class BombFlame extends StillObject implements Updatable, Passable, Bombable {
	final Player placer;
	protected double remainingTicks;
	final boolean spawnDrop;

	public BombFlame(Player placer, double lifetime, boolean spawnDrop) {
		this.placer = placer;
		this.remainingTicks = lifetime;
		this.spawnDrop = spawnDrop;
	}

	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			// player respawn
			((Player) character).respawn();

			((Player) character).recievePenalty();
		} else if (character instanceof Ghost) {
			// remove ghost and create a new one after 5second
		}

	}

	private static final Sprite sprite = new Sprite(1);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		if (this.remainingTicks <= 0) {
			if (spawnDrop) { // we can add more condition
				spawnItemDrop(x, y);
			} else {
				this.remove(x, y);
			}
		}

	}

	private void remove(int x, int y) {
		GameSingleton.setTileObject(x, y, new Floor());
	}

	private void spawnItemDrop(int x, int y) {
		double random = Math.random();
		if (random > 0.8) {
			GameSingleton.setTileObject(x, y, new BombFlameUpgrade());
		} else if (random < 0.2) {
			GameSingleton.setTileObject(x, y, new BombQuantityUpgrade());
		} else {
			this.remove(x, y);
		}
	}
}
