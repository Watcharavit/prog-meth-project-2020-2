package entity.terrains;

import entity.StillObject;
import entity.drops.DropBombFlameUpgrade;
import entity.drops.DropBombQuantityUpgrade;
import interfaces.Bombable;
import resources.Sprite;
import resources.SpritesLibrary;

public class TempWall extends StillObject implements Bombable {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.TEMP_WALL;
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
