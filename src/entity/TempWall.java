package entity;

import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import game.Tile;
import graphics.Sprite;

public class TempWall extends StillObject implements Bombable {
	private static final Sprite sprite = new Sprite(9);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	@Override
	public boolean getCanStopBlast() {
		return true;
	}

}
