package entity.terrains;

import entity.StillObject;
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
	public boolean getShouldSpawnDrop() {
		return true;
	}

}
