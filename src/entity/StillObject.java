package entity;

import game.Tile;

public abstract class StillObject extends PhysicalEntity {
	private Tile tile;
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
}
