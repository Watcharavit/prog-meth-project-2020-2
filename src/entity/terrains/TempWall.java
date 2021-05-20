package entity.terrains;

import entity.StillObject;
import entity.drops.DropBombFlameUpgrade;
import entity.drops.DropBombQuantityUpgrade;
import graphics.Sprite;
import interfaces.Bombable;

public class TempWall extends StillObject implements Bombable {
	private static final Sprite sprite = new Sprite(10);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public boolean getCanStopBlast() {
		return true;
	}

	@Override
	public StillObject getAfterBombed() {
		double random = Math.random();
		if (random > 0.8) {
			return new DropBombFlameUpgrade();
		} else if (random < 0.2) {
			return new DropBombQuantityUpgrade();
		} else {
			return new Floor();
		}
	}

}
