package application;

import entity.base.StillObject;

public class Tile {
	StillObject object;
	public Tile(StillObject object) {
		this.object = object;
	}
	public StillObject getObject() {
		return this.object;
	}
}
