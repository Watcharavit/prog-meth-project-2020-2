package entity.base;

import gui.Tile;

public interface Passable {
	public void pass(entity.Character character, Tile currentTile);
	// Do something when character steps on. 
	//E.g. BombFlame = kill, Drop = collect
}
