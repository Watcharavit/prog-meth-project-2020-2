package interfaces;

import entity.Being;

public interface Passable {

	/**
	 * Do something when Being steps on.
	 * 
	 * @param character Being that pass this entity.
	 */
	public void pass(Being character);
	// Do something when character steps on.
	// E.g. BombFlame = kill, Drop = collect
}
