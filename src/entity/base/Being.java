package entity.base;

import application.GameSingleton;
import logic.Direction;

public abstract class Being extends Entity {
	protected Direction facing;
	private double x, y;
	
	public Being(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(double dx, double dy) {
		double oldX = this.x;
		double oldY = this.y;
		this.x += dx;
		this.y += dy;
		GameSingleton.world.rerenderAround(oldX, oldY);
		GameSingleton.world.rerenderBeing(this);
	};
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
}
