package entity.base;

import application.Tile;

public abstract class StillObject extends Entity {
	private Tile tile;
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
}
