package application;

import java.util.HashSet;

import entity.base.Being;
import entity.base.StillObject;

public class Tile {
	private StillObject object;
	private HashSet<Being> beings;
	public final int x, y;
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.object = null;
		this.beings = new HashSet<Being>();
	}
	protected StillObject getObject() {
		return this.object;
	}
	protected HashSet<Being> getBeings() {
		return this.beings;
	}
	protected void setObject(StillObject object) {
		this.object = object;
		object.setTile(this);
	}
	protected void addBeing(Being being) {
		this.beings.add(being);
	}
	protected void removeBeing(Being being) {
		this.beings.remove(being);
	}
}
