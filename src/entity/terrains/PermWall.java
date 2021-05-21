package entity.terrains;

import entity.StillObject;
import resources.Sprite;
import resources.SpritesLibrary;

public class PermWall extends StillObject {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.PERM_WALL;
	}

}
