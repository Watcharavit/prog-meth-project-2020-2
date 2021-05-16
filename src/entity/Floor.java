package entity;

import entity.base.StillObject;
import entity.base.Terrain;
import gui.Sprite;

public class Floor extends Terrain {
	private static final Sprite sprite = new Sprite(1);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
