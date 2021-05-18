package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Updatable;
import graphics.Sprite;

public class BalloonMonster extends Being implements Collidable, Updatable {

	public BalloonMonster() {
		this.isAlive = true;
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void collide(Being otherCharacter) {

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
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub

	}

}
