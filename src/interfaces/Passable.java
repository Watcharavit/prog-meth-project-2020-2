package interfaces;

import entity.Being;

public interface Passable {
	public void pass(Being character);
	// Do something when character steps on.
	// E.g. BombFlame = kill, Drop = collect
}