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

/**
 * Drop Item that can be picked by Player.
 * @author Watch
 *
 */
public abstract class Drop extends StillObject implements Passable, Bombable {
	
	/**
	 * If being that pass item is Player, then Player collects it.
	 */
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

	/**
	 * 
	 * @param player Player that walk pass.
	 */
	protected abstract void onPicked(Player player);

	/**
	 * Object after it picked.
	 */
	@Override
	public StillObject getAfterBombed() {
		return new Floor();
	}
}
