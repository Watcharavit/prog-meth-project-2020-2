package entity.bomb;

import entity.StillObject;
import entity.livings.Player;
import game.Tile;
import graphics.Sprite;
import interfaces.Bombable;
import interfaces.Updatable;

public class BombObject extends StillObject implements Updatable, Bombable {
	private final Player placer;
	public final int radius;
	private double remainingTicks = 60;
	private boolean explosionLock = false;

	public BombObject(Player placer, int radius) {
		this.placer = placer;
		this.radius = radius;
	}

	@Override
	public void update(double ticksPassed) {
		this.remainingTicks -= ticksPassed;
		if (this.remainingTicks <= 0) {
			if (prepareForExplosion())
				startExplosion();
		}
	}

	private void startExplosion() {
		Tile tile = super.getTile();
		BombPhantom phantom = new BombPhantom(placer, radius, tile.x, tile.y);
		phantom.startExplosion();
	}

	public boolean prepareForExplosion() {
		if (!explosionLock) {
			explosionLock = true;
			placer.returnBomb();
			return true;
		} else
			return false;
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
		if (prepareForExplosion())
			startExplosion();
	}

}
