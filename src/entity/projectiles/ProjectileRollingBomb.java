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

public class ProjectileRollingBomb extends Projectile implements Updatable, Collidable {
	public final static double SIZE = 0.6;
	private final static double SPEED = 0.3;
	private final Player kicker;
	private final int radius;
	private boolean exploded;

	public ProjectileRollingBomb(double x, double y, Direction direction, Player kicker, int radius) {
		super(x, y, SIZE, SPEED, direction);
		this.kicker = kicker;
		this.radius = radius;
		this.exploded = false;
	}

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

	@Override
	public void update(double ticksPassed) {
		// Try to move. Move fail = stuck -> explode.
		if (!super.move(ticksPassed)) {
			this.explode();
		}
	}

	@Override
	public void collide(Being otherBeing) {

	}

	@Override
	public boolean getCanPassThrough() {
		return false;
	}

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.ROLLING_BOMB;
	}
}
