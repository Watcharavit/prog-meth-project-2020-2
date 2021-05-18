package entity;

import entity.base.Being;
import entity.base.Bombable;
import entity.base.Passable;
import entity.base.StillObject;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;

public class BombFlameUpgrade extends StillObject implements Passable,Bombable{
	private static final Sprite sprite = new Sprite(1);
	
	@Override
	public void pass(Being character) {
		// TODO Auto-generated method stub
		if (character instanceof Player) {
			((Player) character).incrementBombRadius();
			Tile tile = super.getTile();
			int x = tile.x;
			int y = tile.y;
			GameSingleton.setTileObject(x, y, new Floor());
		}
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}


}
