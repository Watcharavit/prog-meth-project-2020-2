package entity;

import entity.base.Being;
import entity.base.ItemDrop;
import game.Tile;
import graphics.Sprite;

public class BombFlameUpgrade extends ItemDrop {

	@Override
	public void pass(Being character) {
		// TODO Auto-generated method stub
		if (character instanceof Player) {
			((Player) character).setBombRadius(((Player) character).getBombRadius()+1);
		}
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}


}
