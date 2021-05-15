package entity.base;

import logic.Direction;

public abstract class Being extends Entity {
	protected boolean isAlive;
	protected Direction direction;
	private double x, y;

	
	// not sure
	/*public void Character() {
		direction = Direction.NONE;
	}*/
	public abstract boolean move(Direction dir); 
	// move player the same direction as input and move balloon/wall monster randomly


	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
}
