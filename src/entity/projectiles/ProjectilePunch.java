package entity.projectiles;

import entity.Being;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;
import resources.Sprite;
import resources.SpritesLibrary;

public class ProjectilePunch extends Projectile implements Updatable, Collidable {
	public static final double SIZE = 0.6;
	private final static double SPEED = 0.2;
	private double remainingMoveTicks;
	private final Player placer;
	private final Sprite sprite;

	public ProjectilePunch(double x, double y, Direction direction, Player placer) {
		super(x, y, SIZE, SPEED, direction);
		this.remainingMoveTicks = 3.0;
		this.placer = placer;
		switch (direction) {
		case DOWN:
			this.sprite = SpritesLibrary.PUNCH_DOWN;
			break;
		case UP:
			this.sprite = SpritesLibrary.PUNCH_UP;
			break;
		case LEFT:
			this.sprite = SpritesLibrary.PUNCH_LEFT;
			break;
		case RIGHT:
			this.sprite = SpritesLibrary.PUNCH_RIGHT;
			break;
		default:
			// Unreachable
			this.sprite = null;
		}
	}

	private void remove() {
		GameSingleton.removeBeing(this);
	}

	@Override
	public void collide(Being otherBeing) {
		if (otherBeing instanceof LivingBeing && otherBeing != placer) {
			((LivingBeing) otherBeing).die();
		}
	}

	@Override
	public boolean getCanPassThrough() {
		return true;
	}

	@Override
	public void update(double ticksPassed) {
		if (remainingMoveTicks > 0) {
			remainingMoveTicks -= ticksPassed;
			super.move(ticksPassed);
		} else {
			remove();
		}
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
