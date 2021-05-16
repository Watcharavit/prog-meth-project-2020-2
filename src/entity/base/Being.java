package entity.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import application.GameSingleton;
import application.Tile;
import logic.Direction;

public abstract class Being extends Entity {
	protected Direction facing;
	protected double x, y, size;
	
	public Being(double x, double y) {
		this.x = x;
		this.y = y;
	}

	private Tile[] getCornerTiles(double x, double y) {
		double halfSize = size / 2;
		int l = (int) (x - halfSize);
		int r = (int) (x + halfSize);
		int u = (int) (y - halfSize);
		int d = (int) (y + halfSize);
		Tile lu = GameSingleton.world.getTile(l, u);
		Tile ld = GameSingleton.world.getTile(l, d);
		Tile ru = GameSingleton.world.getTile(r, u);
		Tile rd = GameSingleton.world.getTile(r, d);
		Tile[] s = {lu, ld, ru, rd};
		return s;
	}
	public boolean move(double dx, double dy) {
		double oldX = this.x;
		double oldY = this.y;
		double nX = oldX + dx;
		double nY = oldY + dy;
		Tile[] cornerTiles = getCornerTiles(nX, nY);
		HashSet<Tile> currentCornerTiles = new HashSet<Tile>(Arrays.asList(getCornerTiles(oldX, oldY)));
		boolean allPassable = true;
		for (Tile tile : cornerTiles) {
			if (!(tile.getObject() instanceof Passable) && !currentCornerTiles.contains(tile)) {
				allPassable = false;
				break;
			}
		}
		if (allPassable) {
			for (Tile tile : cornerTiles) {
				StillObject so = tile.getObject();
				if (so instanceof Passable) ((Passable) so).pass(this, tile);
			}
			this.x += dx;
			this.y += dy;
			GameSingleton.world.rerenderAround(oldX, oldY);
			GameSingleton.world.rerenderBeing(this);
			return true;
		}
		else {
			return false;
		}
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	
	public void setFacing(Direction facing) {
		this.facing = facing;
	}
	
}
