package entity;

import application.GameSingleton;
import application.Tile;
import entity.base.Being;
import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import entity.base.Updatable;
import gui.Sprite;

public class BombFlame extends StillObject implements Updatable,Passable,Bombable {
	final Player placer;
	double remainingFrame;
	boolean stopBlast;
	public BombFlame(Player placer, boolean stopBlast) {
		this.placer = placer;
		this.remainingFrame = 60;
		this.stopBlast = stopBlast;
	}
	@Override
	public void pass(Being character, Tile currentTile) {
		
	}
	private static final Sprite sprite = new Sprite(6);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void update(double frameTimeRatio) {
		this.remainingFrame -= frameTimeRatio;
		if (this.remainingFrame <= 0) {
			this.remove();
		}
	}
	private void remove() {
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		GameSingleton.world.setTileObject(x, y, new Floor());
	}
	
	@Override
	public boolean getCanStopBlast() {
		return this.stopBlast;
	}

}
