package entity.livings;

import entity.Being;
import game.Tile;
import graphics.Sprite;
import graphics.SpritesLibrary;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;

public class MonsterGhost extends Monster implements Collidable, Updatable {
	public static final double SIZE = 0.8, SPEED = 0.1;

	public MonsterGhost(Tile spawnTile) {
		super(spawnTile, SIZE, SPEED, Direction.random());
	}

	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead()) return;
		boolean didMove = super.move(ticksPassed);
		if (!didMove) {
			super.setFacing(Direction.random());
		}
	}

	@Override
	public void collide(Being otherCharacter) {
		if (super.isDead()) return;
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
		return 60.0;
	}
	@Override
	protected void onDeath() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onSpawn() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onAlive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Sprite getAliveSprite() {
		return SpritesLibrary.GHOST;
	}

	@Override
	protected Sprite getDyingSprite() {
		return SpritesLibrary.GHOST_DYING;
	}

}
