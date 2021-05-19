package entity.projectiles;

import entity.Being;
import entity.livings.LivingBeing;
import entity.livings.Player;
import game.GameSingleton;
import graphics.Sprite;
import interfaces.Collidable;
import interfaces.Updatable;
import logic.Direction;

public class ProjectilePunch extends Projectile implements Updatable, Collidable {
	public static final double SIZE = 0.6;
	private final static double SPEED = 0.2;
	private double remainingMoveTicks;
	final Player placer;

	public ProjectilePunch(double x, double y, Direction direction, Player placer) {
		super(x, y, SIZE, SPEED, direction);
		this.remainingMoveTicks = 3.0;
		this.placer = placer;
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

	private static final Sprite sprite = new Sprite(0);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
