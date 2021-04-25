package entity;

import entity.base.Collidable;
import entity.base.Entity;
import entity.base.Updatable;
import logic.Direction;
import logic.Sprite;

public class BalloonMonster extends Character implements Collidable, Updatable {

	public BalloonMonster() {
		this.isAlive = true;
	}

	@Override
	public void update(Entity entity) {

	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collide(Character otherCharacter) {

		// Balloon win player
		if (otherCharacter instanceof Player) {
			otherCharacter.setAlive(false);
		}
		// Balloon lose WallThorn
		else if (otherCharacter instanceof WallThornMonster) {
			this.setAlive(false);
		}
	}

	@Override
	public boolean move(Direction dir) {
		// TODO Auto-generated method stub
		return false;
	}

}
