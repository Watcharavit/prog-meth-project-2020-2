package entity.drops;

import entity.livings.Player;
import resources.Sprite;
import resources.SpritesLibrary;

public class DropBombQuantityUpgrade extends Drop {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB_QUANTITY;
	}

	/**
	 * Increase player's bomb available by 1.
	 */
	@Override
	protected void onPicked(Player player) {
		player.incrementBombsNumber();
	}

}
