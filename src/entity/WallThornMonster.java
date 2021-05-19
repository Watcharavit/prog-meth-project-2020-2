package entity;

import entity.base.Being;
import entity.base.Collidable;
import entity.base.LivingBeing;
import entity.base.Updatable;
import game.Tile;
import graphics.Sprite;

public class WallThornMonster extends LivingBeing implements Updatable, Collidable {
	private static final Sprite sprite = new Sprite(2);
	public static final double SIZE = 1;

	public WallThornMonster(Tile spawnTile) {
		super(spawnTile, SIZE);
		
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void update(double ticksPassed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Being otherCharacter) {
		if(otherCharacter instanceof Player) {
			((Player) otherCharacter).die();
		}else if(otherCharacter instanceof Ghost) {
			((Ghost) otherCharacter).die();
		}else {
			// move wallMon to the opposite side
		}
	}
	
	private void move() {
		
	}

	

}
