package application;

import entity.base.StillObject;

public class Tile {
	StillObject object;
	private final int x, y;
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public StillObject getObject() {
		return this.object;
	}
	public void setObject(StillObject object) {
		if (this.object != null) this.object.destroy();
		this.object = object;
		object.setTile(this);
	}
}
