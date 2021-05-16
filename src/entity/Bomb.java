package entity;

import application.GameSingleton;
import application.Tile;
import entity.base.BlastBlockable;
import entity.base.Bombable;
import entity.base.Entity;
import entity.base.Pushable;
import entity.base.StillObject;
import entity.base.Updatable;
import gui.Sprite;

public class Bomb extends StillObject implements Pushable,Updatable,Bombable {
	final Player placer;
	final int radius;
	double remainingFrames = 150;
	boolean exploded = false;
	int explodedRadius = 0;
	double explodedFrames = 0;
	public Bomb(Player placer) {
		this.placer = placer;
		this.radius = placer.getBombRadius();
	}
	
	@Override
	public void push(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double frameTimeRatio) {
		if (!this.exploded) {
			this.remainingFrames -= frameTimeRatio;
			if (this.remainingFrames <= 0) {
				this.exploded = true;
				this.explode(0);
				GameSingleton.addEntity(this);
			}
		}
		else {
			this.explodedFrames += frameTimeRatio;
			this.explodedRadius = (int) (this.explodedFrames);
			if (explodedRadius <= this.radius) this.explode(this.explodedRadius);
			else {
				GameSingleton.removeEntity(this);
			}
		}
	}
	
	private boolean explodeAt(int x, int y) {
		Tile tile = GameSingleton.world.getTile(x, y);
		StillObject tileObject = tile.getObject();
		if (tileObject instanceof Bombable) {
			GameSingleton.world.setTileObject(x, y, new BombFlame(placer));
			((Bombable) tileObject).bombed();
			if (!(tileObject instanceof BlastBlockable)) {
				return false;
			}
		}
		return true;
	}
	
	private void explode(int radius) {
		Tile containingTile = super.getTile();
		int x = containingTile.x;
		int y = containingTile.y;
		this.explodeAt(x, y);
		for (int i = x + 1; i <= x + radius; i++) {
			if (this.explodeAt(i, y)) break;
		}
		for (int i = x - 1; i >= x - radius; i--) {
			if (this.explodeAt(i, y)) break;
		}
		for (int j = y + 1; j <= y + radius; j++) {
			if (this.explodeAt(x, j)) break;
		}
		for (int j = y - 1; j >= y - radius; j--) {
			if (this.explodeAt(x, j)) break;
		}
	}

	private static final Sprite sprite = new Sprite(5);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void bombed() {
		this.exploded = true;
		GameSingleton.addEntity(this);
	}

}
