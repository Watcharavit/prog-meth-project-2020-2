package interfaces;

import entity.StillObject;

public interface Bombable {
	public default void bombed() {

	}

	public default boolean getCanStopBlast() {
		return false;
	}
	
	public StillObject getAfterBombed();
}
