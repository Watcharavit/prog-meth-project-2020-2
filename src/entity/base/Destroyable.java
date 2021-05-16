package entity.base;

import application.Tile;

public interface Destroyable {
	public abstract void bombed(Tile currentTile);
	// Do something when bombed
}
