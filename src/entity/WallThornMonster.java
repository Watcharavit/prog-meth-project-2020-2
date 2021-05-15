package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Updatable;
import gui.Sprite;
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
	public void update(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

}
