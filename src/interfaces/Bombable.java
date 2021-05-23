package interfaces;

import entity.StillObject;

public interface Bombable {
	public default void bombed() {

	}

	/**
	 * Check if the entity can stop bomb flame.
	 * @return Default false.
	 */
	public default boolean getCanStopBlast() {
		return false;
	}

	/**
	 * 
	 * @return Object after the bomb flame is gone.
	 */
	public StillObject getAfterBombed();
}
