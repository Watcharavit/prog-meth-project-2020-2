package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.GameSingleton;
import graphics.Sprite;
import logic.Direction;

public class Punch extends Being implements Updatable,Collidable {
	public static final double SIZE = 0.6;
	private final static double SPEED = 0.2;
	final double moveDeltaX, moveDeltaY;
	private double remainingMoveTicks;
	final Player placer;
	public Punch(double x, double y, Direction direction, Player placer) {
		super(x, y, SIZE);
		super.facing = direction;
		this.moveDeltaX = direction.getDeltaX() * SPEED;
		this.moveDeltaY = direction.getDeltaY() * SPEED;
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
			GameSingleton.moveBeing(this, moveDeltaX * ticksPassed, moveDeltaY * ticksPassed);
		}
		else {
			remove();
		}
	}
	private static final Sprite sprite = new Sprite(0);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
