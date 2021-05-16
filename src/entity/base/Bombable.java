package entity.base;

public interface Bombable {
	public default void bombed() {
		
	}
	public default boolean getCanStopBlast() {
		return false;
	}
}
