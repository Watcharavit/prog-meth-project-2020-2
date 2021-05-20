package entity.livings;

import entity.Being;
import game.Tile;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;

public class MonsterWallThorn extends Monster implements Updatable, Collidable {	
	public static final double SIZE = 0.8, SPEED = 0.05;

	public MonsterWallThorn(Tile spawnTile, Direction direction) {
		super(spawnTile, SIZE, SPEED, direction);
	}

	@Override
	public void update(double ticksPassed) {
		super.update(ticksPassed);
		if (super.isDead()) return;
		boolean didMove = super.move(ticksPassed);
		if (!didMove) {
			super.setFacing(super.getFacing().flip());
		}
	}

	@Override
	public void collide(Being otherCharacter) {
		if (super.isDead()) return;
		if (otherCharacter instanceof LivingBeing && !(otherCharacter instanceof MonsterWallThorn)) {
			((LivingBeing) otherCharacter).die();
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

	private static final Sprite sprite = new Sprite(2);
	@Override
	protected Sprite getAliveSprite() {
		return sprite;
	}
	@Override
	protected Sprite getDyingSprite() {
		return sprite;
	}

}
