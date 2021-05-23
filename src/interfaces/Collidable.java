package interfaces;

import entity.Being;

public interface Collidable {
	// Do something to the other character upon collision
	/**
	 * 
	 * @param otherBeing 
	 */
	public abstract void collide(Being otherBeing);
	
	/**
	 * Check if the entity can be pass through.
	 * 
	 * @return Always return true.
	 */
	default boolean getCanPassThrough() {
		return false;
	}
}
