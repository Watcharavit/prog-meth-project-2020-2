package entity;

import entity.base.Bombable;
import entity.base.Entity;
import entity.base.Pushable;
import entity.base.StillObject;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;

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

	private static final Sprite firstSprite = new Sprite(4);
	private static final Sprite secondSprite = new Sprite(3);
	private static final Sprite thirdSprite = new Sprite(0);
	@Override
	public Sprite getSprite() {
		return firstSprite;
	}

	@Override
	public void bombed() {
		startExplosion();
	}

}
