package entity;

import entity.base.Being;
import entity.base.ItemDrop;
import game.Tile;
import graphics.Sprite;

public class BombQuantityUpgrade extends ItemDrop {

	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			((Player) character).setBombsNumber(((Player) character).getBombsNumber()+1);
		}
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

}
