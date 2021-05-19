package entity.projectiles;

import entity.Being;
import game.GameSingleton;
import graphics.Sprite;
import interfaces.Updatable;
import logic.Direction;

abstract class Projectile extends Being {
	private final double moveDeltaX, moveDeltaY;
	public Projectile(double x, double y, double size, double speed, Direction direction) {
		super(x, y, size);
		this.moveDeltaX = direction.getDeltaX() * speed;
		this.moveDeltaY = direction.getDeltaY() * speed;
		super.facing = direction;
	}

	public boolean move(double ticksPassed) {
		return GameSingleton.moveBeing(this, moveDeltaX * ticksPassed, moveDeltaY * ticksPassed);
	}



}
