package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Updatable;
import graphics.Sprite;
import logic.Direction;

public class WallThornMonster extends Being implements Updatable, Collidable {

	public WallThornMonster() {
		this.setAlive(true);
	}

	@Override
	public void collide(Being otherCharacter) {
		otherCharacter.setAlive(false);
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


}
