package entity;

import entity.base.Being;
import entity.base.Passable;
import entity.base.StillObject;
import entity.base.Updatable;
import graphics.Sprite;


public class KingFloor extends StillObject implements Updatable {
	private static final Sprite sprite = new Sprite(1);
	private Player player;

	
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		
	}
	
}
