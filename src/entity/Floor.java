package entity;

import application.Tile;
import entity.base.Being;
import entity.base.Passable;
import entity.base.StillObject;
import gui.Sprite;

public class Floor extends StillObject implements Passable {
	private static final Sprite sprite = new Sprite(1);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void pass(Being character, Tile currentTile) {
		// TODO Auto-generated method stub
		
	}

}
