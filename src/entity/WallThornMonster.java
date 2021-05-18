package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Updatable;
import gui.Sprite;
import logic.Direction;

public class WallThornMonster extends Being implements Updatable, Collidable {


	public WallThornMonster(double x, double y, double size) {
		super(x, y, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub
		
	}


}
