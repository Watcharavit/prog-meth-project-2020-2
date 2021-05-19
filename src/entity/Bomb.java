package entity;

import entity.base.Bombable;
import entity.base.PhysicalEntity;
import entity.base.Pushable;
import entity.base.StillObject;
import entity.base.Updatable;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;

public class Bomb extends StillObject implements Pushable,Updatable,Bombable {
	final Player placer;
	final int radius;
	private double remainingTicks = 60;
	private boolean exploded = false;
	
	public Bomb(Player placer, int radius) {
		this.placer = placer;
		this.radius = radius;
	}
	
	@Override
	public void push(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		if (this.remainingTicks <= 0) {
			Tile tile = super.getTile();
			// Check that the bomb still exists (has not exploded yet).
			if (GameSingleton.getTileObject(tile.x, tile.y) == this) startExplosion();
		}
	}
	
	private void startExplosion() {
		if (exploded) return;
		exploded = true;
		Tile tile = super.getTile();
		BombPhantom phantom = new BombPhantom(placer, radius, tile.x, tile.y);
		phantom.startExplosion();
		placer.returnBomb();
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
