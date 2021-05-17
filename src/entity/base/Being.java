package entity.base;

import logic.Direction;

public abstract class Being extends Entity {
	protected Direction facing;
	protected double x, y;
	public final double size;
	
	public Being(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public void changeX(double dx) {
		this.x += dx;
	}
	public void changeY(double dy) {
		this.y += dy;
	}
	
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	
}
