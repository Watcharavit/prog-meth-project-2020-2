package entity;

import entity.base.Terrain;
import gui.Sprite;

public class PermWall extends Terrain {
	private static final Sprite sprite = new Sprite(2);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
