package entity;

import application.Tile;
import entity.base.BlastBlockable;
import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import gui.Sprite;

public class TempWall extends StillObject implements Bombable,BlastBlockable {
	private static final Sprite sprite = new Sprite(3);
	@Override
	public Sprite getSprite() {
		return sprite;
	}

}
