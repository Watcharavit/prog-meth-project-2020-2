package entity;

import entity.base.Being;
import entity.base.Passable;
import entity.base.StillObject;
import gui.Sprite;

public class KingFloor extends StillObject implements Passable{

	@Override
	public void pass(Being character) {
		// TODO Auto-generated method stub
		if(character instanceof Player) {
			//((Player)character).setKingTime(((Player)character).getKingTime()+ fps?);
		}
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
