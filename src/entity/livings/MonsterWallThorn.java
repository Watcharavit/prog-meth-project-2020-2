package entity.livings;

import entity.Being;
import game.Tile;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

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
	 * Update every 1/60 second Check if this monster is dead or not. If it is then
	 * do nothing. Otherwise set the direction it is going.
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
	 * If this monster collide with other living beings, then kill them and move
	 * pass them. Otherwise, move to the opposite direction.
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
	 * This monster cannot die, so it does't do anything.
	 */
	@Override
	public void die() {
		// This thing can't die.
	}

	/**
	 * Check if this monster can be pass through.
	 * 
	 * @return Always return false
	 */
	@Override
	public boolean getCanPassThrough() {
		return false;
	}

	/**
	 * Return spawn cooldown.
	 * 
	 * @return always return 0.0
	 */
	@Override
	protected double getSpawnCooldown() {
		return 0.0;
	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onDeath() {

	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onSpawn() {

	}

	/**
	 * Does't do anything.
	 */
	@Override
	protected void onAlive() {

	}

	/**
	 * @return return Wall Thorn monster image.
	 */
	@Override
	protected Sprite getAliveSprite() {
		return SpritesLibrary.THORN;
	}

	/**
	 * @return return Wall Thorn monster dying image.
	 */
	@Override
	protected Sprite getDyingSprite() {
		return SpritesLibrary.TRANSPARENT;
	}

}
