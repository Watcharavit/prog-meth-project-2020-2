package entity;

import application.GameSingleton;
import application.Tile;
import entity.base.Bombable;
import entity.base.Entity;
import entity.base.Pushable;
import entity.base.StillObject;
import entity.base.Updatable;
import gui.Sprite;

public class Bomb extends StillObject implements Pushable,Updatable,Bombable {
	final Player placer;
	final int radius;
	double remainingTicks = 60;
	
	public Bomb(Player placer) {
		this.placer = placer;
		this.radius = placer.getBombRadius();
	}
	
	@Override
	public void push(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		if (this.remainingTicks <= 0) {
			startExplosion();
		}
	}
	
	private void startExplosion() {
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		GameSingleton.setTileObject(x, y, new BombCenter(placer, radius));
	}

	private static final Sprite sprite = new Sprite(5);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void bombed() {
		startExplosion();
	}

}
