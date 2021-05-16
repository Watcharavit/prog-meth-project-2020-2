package entity;

import application.Tile;
import entity.base.Destroyable;
import entity.base.Passable;
import entity.base.StillObject;
import gui.Sprite;

public class TempWall extends StillObject implements Destroyable {
	private static final Sprite sprite = new Sprite(3);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void bombed(Tile currentTile) {
		// TODO Auto-generated method stub
		
	}

}
