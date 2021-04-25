package entity;

import gui.Tile;
import logic.Sprite;

public class BombFlameUpgrade extends ItemDrop {

	@Override
	public void pass(Character character, Tile currentTile) {
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
