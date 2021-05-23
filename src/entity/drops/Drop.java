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
 * Item that can be picked by Player.
 */
abstract class Drop extends StillObject implements Passable, Bombable {

	/**
	 * If the being the pass item is a Player, run {@link #onPicked(Player)} for
	 * that player.
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
	 * Ran when a player picks this item up. Usually increment some of the player's
	 * stats.
	 * 
	 * @param player The player that picks this item up.
	 */
	protected abstract void onPicked(Player player);

	/**
	 * Return a floor, because item should be destroyed and replaced with a floor
	 * when bombed.
	 * 
	 * @return A new {@link entity.terrains.Floor} object.
	 */
	@Override
	public StillObject getAfterBombed() {
		return new Floor();
	}
}
