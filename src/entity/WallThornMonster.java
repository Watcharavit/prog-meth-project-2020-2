package entity;

import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Updatable;
import logic.Direction;
import logic.Sprite;

public class WallThornMonster extends Character implements Updatable, Collidable {

	public WallThornMonster() {
		this.setAlive(true);
	}

	@Override
	public void collide(Character otherCharacter) {
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
