package entity.base;

import application.GameSingleton;
import application.Tile;
import logic.Direction;

public abstract class Being extends Entity {
	protected Direction facing;
	protected double x, y, size;

	public void move(double dx, double dy) {
		double oldX = this.x;
		double oldY = this.y;
		double nX = oldX + dx;
		double nY = oldY + dy;
		double halfSize = size / 2;
		int l = (int) Math.floor(nX - halfSize);
		int r = (int) Math.floor(nX + halfSize);
		int u = (int) Math.floor(nY - halfSize);
		int d = (int) Math.floor(nY + halfSize);
		Tile lu = GameSingleton.world.getTile(l, u);
		Tile ld = GameSingleton.world.getTile(l, d);
		Tile ru = GameSingleton.world.getTile(r, u);
		Tile rd = GameSingleton.world.getTile(r, d);
		StillObject slu = lu.getObject();
		StillObject sld = ld.getObject();
		StillObject sru = ru.getObject();
		StillObject srd = rd.getObject();
		if (slu instanceof Passable && sld instanceof Passable && sru instanceof Passable && srd instanceof Passable) {
			((Passable) slu).pass(this, lu);
			((Passable) sld).pass(this, ld);
			((Passable) sru).pass(this, ru);
			((Passable) srd).pass(this, rd);
			this.x += dx;
			this.y += dy;
			GameSingleton.world.rerenderAround(oldX, oldY);
			GameSingleton.world.rerenderBeing(this);
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
