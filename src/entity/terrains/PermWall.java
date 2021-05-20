package entity.terrains;

import entity.StillObject;
import graphics.Sprite;
import graphics.SpritesLibrary;

public class PermWall extends StillObject {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.PERM_WALL;
	}

}
