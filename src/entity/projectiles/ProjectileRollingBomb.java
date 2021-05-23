package entity.projectiles;

import entity.Being;
import entity.bomb.BombPhantom;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * Projectile spawned by {@link entity.equipments.EquipmentBombKicker}. Moves in
 * one direction until it hits something. Then explode.
 *
 */
public class ProjectileRollingBomb extends Projectile implements Updatable, Collidable {

	/**
	 * Its size.
	 */
	public final static double SIZE = 0.6;

	/**
	 * Its speed.
	 */
	private final static double SPEED = 0.3;

	/**
	 * Player who uses this skill.
	 */
	private final Player kicker;

	/**
	 * Bomb flame radius {@link entity.bomb.BombObject}.
	 */
	private final int radius;

	/**
	 * Check if the bomb exploded.
	 */
	private boolean exploded;

	/**
	 * 
	 * @param x         Its X position.
	 * @param y         Its Y position.
	 * @param direction The direction it is going.
	 * @param kicker    Player who uses this skill.
	 * @param radius    Bomb flame radius
	 */
	public ProjectileRollingBomb(double x, double y, Direction direction, Player kicker, int radius) {
		super(x, y, SIZE, SPEED, direction);
		this.kicker = kicker;
		this.radius = radius;
		this.exploded = false;
	}

	/**
	 * If the bomb exploded, do nothing. Otherwise, move it to the end of the
	 * direction, then wait for it to explode @see {@link entity.bomb.BombPhantom}
	 */
	private void explode() {
		if (this.exploded)
			return;
		this.exploded = true;
		int tileX = (int) super.getX();
		int tileY = (int) super.getY();
		GameSingleton.removeBeing(this);
		BombPhantom phantom = new BombPhantom(kicker, radius, tileX, tileY);
		phantom.startExplosion();
	}

	/**
	 * Update on every frame: Try to move. Move fail = stuck = run
	 * {@link #explode()}.
	 */
	@Override
	public void update(double ticksPassed) {
		if (!super.move(ticksPassed)) {
			this.explode();
		}
	}

	@Override
	public void collide(Being otherBeing) {

	}

	/**
	 * Check if this entity can be pass through.
	 * 
	 * @return Always return false.
	 */
	@Override
	public boolean getCanPassThrough() {
		return false;
	}

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.ROLLING_BOMB;
	}
}
