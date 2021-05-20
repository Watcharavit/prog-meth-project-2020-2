package entity;

import logic.Direction;

public abstract class Being extends PhysicalEntity {
	private double x, y;
	public final double halfSize;

	public Being(double x, double y, double size) {
		this.x = x;
		this.y = y;
		this.halfSize = size / 2;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}
