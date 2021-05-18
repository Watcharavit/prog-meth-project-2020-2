package entity;

import entity.base.Bombable;
import entity.base.StillObject;
import gui.Sprite;

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

}
