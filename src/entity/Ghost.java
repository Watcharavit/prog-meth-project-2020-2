package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.Tile;
import graphics.Sprite;

public class Ghost extends LivingBeing implements Collidable,Updatable{

	public Ghost(Tile spawnTile, double size) {
		super(spawnTile, size);
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
			((Player) otherCharacter).die();
		}
		// Ghost lose WallThorn
		// implement in Wall thorn class
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}


}
