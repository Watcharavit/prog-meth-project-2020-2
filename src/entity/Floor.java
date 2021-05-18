package entity;

import entity.base.Being;
import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import gui.Sprite;

public class Floor extends StillObject implements Passable,Bombable {
	private static final Sprite sprite = new Sprite(7);
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	@Override
	public void pass(Being character) {
		
	}

}
