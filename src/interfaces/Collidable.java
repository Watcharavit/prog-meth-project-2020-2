package interfaces;

import entity.Being;

public interface Collidable {
	// Do something to the other character upon collision
	public abstract void collide(Being otherBeing);

	default boolean getCanPassThrough() {
		return false;
	}
}
