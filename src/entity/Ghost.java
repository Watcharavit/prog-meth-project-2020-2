package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.Updatable;
import graphics.Sprite;

public class Ghost extends Being implements Collidable,Updatable{

	public Ghost(double x, double y, double size) {
		super(x, y, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Being otherCharacter) {
		// TODO Auto-generated method stub
		
		// Ghost win player
		if (otherCharacter instanceof Player) {

		}
		// Ghost lose WallThorn
		else if (otherCharacter instanceof WallThornMonster) {

		}
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}


}
