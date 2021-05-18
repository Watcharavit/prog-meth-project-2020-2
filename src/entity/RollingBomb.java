package entity;

import entity.base.Being;
import entity.base.Bombable;
import entity.base.Collidable;
import entity.base.StillObject;
import entity.base.Updatable;
import game.GameSingleton;
import graphics.Sprite;
import logic.Direction;

public class RollingBomb extends Being implements Updatable,Collidable {
	public final static double SIZE = 0.6;
	private final static double SPEED = 0.3;
	final double moveDeltaX, moveDeltaY;
	final Player kicker;
	final int radius;
	public RollingBomb(double x, double y, Direction direction, Player kicker, int radius) {
		super(x, y, SIZE);
		super.facing = direction;
		this.moveDeltaX = direction.getDeltaX() * SPEED;
		this.moveDeltaY = direction.getDeltaY() * SPEED;
		this.kicker = kicker;
		this.radius = radius;
	}
	
	private void explode() {
		int tileX = (int) super.getX();
		int tileY = (int) super.getY();
		GameSingleton.removeBeing(this);
		StillObject oldObject = GameSingleton.getTileObject(tileX, tileY);
		GameSingleton.setTileObject(tileX, tileY, new BombCenter(kicker, radius));
		if (oldObject instanceof Bombable) {
			((Bombable) oldObject).bombed();
		}
	}
	@Override
	public void update(double ticksPassed) {
		// Try to move. Move fail = stuck -> explode.
		if (!GameSingleton.moveBeing(this, moveDeltaX, moveDeltaY)) {
			this.explode();
		}
	}
	@Override
	public void collide(Being otherBeing) {
		this.explode();
	}
	
	private final static Sprite sprite = new Sprite(0);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
}
