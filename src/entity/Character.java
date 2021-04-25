package entity;

import entity.base.Entity;
import logic.Direction;

public abstract class Character extends Entity {
	protected boolean isAlive;
	protected Direction direction;

	
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
	
	
}
