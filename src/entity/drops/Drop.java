package entity.drops;

import entity.Being;
import entity.StillObject;
import entity.livings.Player;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Bombable;
import interfaces.Passable;

abstract class Drop extends StillObject implements Passable,Bombable {
	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			onPicked((Player) character);
			Tile tile = super.getTile();
			int x = tile.x;
			int y = tile.y;
			GameSingleton.setTileObject(x, y, new Floor());
		}
	}
	
	protected abstract void onPicked(Player player);

}
