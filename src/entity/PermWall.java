package entity;

import entity.base.StillObject;
import gui.Sprite;

public class PermWall extends StillObject {
	private static final Sprite sprite = new Sprite(9);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
