package game;

import java.util.HashSet;

import entity.Being;
import entity.StillObject;

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

	protected boolean removeBeing(Being being) {
		return this.beings.remove(being);
	}
}
