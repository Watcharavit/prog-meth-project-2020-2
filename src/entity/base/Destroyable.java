package entity.base;

import gui.Tile;

public interface Destroyable {
	public abstract void bombed(Tile currentTile);
	// Do something when bombed
}
