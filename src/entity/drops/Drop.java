package entity.drops;

import entity.Being;
import entity.StillObject;
import entity.livings.Player;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import interfaces.Bombable;
import interfaces.Passable;
import resources.SoundsLibrary;

abstract class Drop extends StillObject implements Passable, Bombable {
	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			SoundsLibrary.ITEM_PICK.play();
			onPicked((Player) character);
			Tile tile = super.getTile();
			int x = tile.x;
			int y = tile.y;
			GameSingleton.setTileObject(x, y, new Floor());
		}
	}

	protected abstract void onPicked(Player player);

	@Override
	public StillObject getAfterBombed() {
		return new Floor();
	}
}
