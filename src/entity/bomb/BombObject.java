package entity.bomb;

import entity.StillObject;
import entity.livings.Player;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import graphics.SpritesLibrary;
import interfaces.Bombable;
import interfaces.Updatable;

public class BombObject extends StillObject implements Updatable, Bombable {
	private final Player placer;
	public final int radius;
	private double remainingTicks = 60;
	private boolean explosionLock = false;
	private final Bombable originalObject;

	public BombObject(Player placer, int radius, Bombable originalObject) {
		this.placer = placer;
		this.radius = radius;
		this.originalObject = originalObject;
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
			Tile tile = super.getTile();
			GameSingleton.setTileObject(tile.x, tile.y, originalObject.getAfterBombed());
			return true;
		} else
			return false;
	}

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB;
	}

	@Override
	public void bombed() {
		if (prepareForExplosion())
			startExplosion();
	}

	@Override
	public StillObject getAfterBombed() {
		return originalObject.getAfterBombed();
	}

}
