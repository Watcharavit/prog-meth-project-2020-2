package entity.projectiles;

import entity.Being;
import entity.StillObject;
import entity.bomb.BombPhantom;
import entity.livings.Player;
import game.GameSingleton;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;

public class ProjectileRollingBomb extends Projectile implements Updatable, Collidable {
	public final static double SIZE = 0.6;
	private final static double SPEED = 0.3;
	final Player kicker;
	final int radius;

	public ProjectileRollingBomb(double x, double y, Direction direction, Player kicker, int radius) {
		super(x, y, SIZE, SPEED, direction);
		this.kicker = kicker;
		this.radius = radius;
	}

	private void explode() {
		int tileX = (int) super.getX();
		int tileY = (int) super.getY();
		GameSingleton.removeBeing(this);
		StillObject oldObject = GameSingleton.getTileObject(tileX, tileY);
		BombPhantom phantom = new BombPhantom(kicker, radius, tileX, tileY);
		phantom.startExplosion();
	}

	@Override
	public void update(double ticksPassed) {
		// Try to move. Move fail = stuck -> explode.
		if (super.move(ticksPassed)) {
			this.explode();
		}
	}

	@Override
	public void collide(Being otherBeing) {
		this.explode();
	}

	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	private final static Sprite sprite = new Sprite(0);

	@Override
	public Sprite getSprite() {
		return sprite;
	}
}
