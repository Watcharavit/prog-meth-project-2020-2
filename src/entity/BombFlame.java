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
	double remainingTicks;
	public BombFlame(Player placer, double lifetime) {
		this.placer = placer;
		this.remainingTicks = lifetime;
	}
	@Override
	public void pass(Being character) {
		
	}
	private static final Sprite sprite = new Sprite(1);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		if (this.remainingTicks <= 0) {
			this.remove();
		}
	}
	private void remove() {
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		GameSingleton.setTileObject(x, y, new Floor());
	}
}
