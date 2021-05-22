package entity.drops;

import entity.livings.Player;
import resources.Sprite;
import resources.SpritesLibrary;

public class DropBombFlameUpgrade extends Drop {

	/**
	 * @return Specific sprite.
	 */
	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB_FLAME;
	}

	/**
	 * Increase player's bomb radius by 1.
	 */
	@Override
	protected void onPicked(Player player) {
		player.incrementBombRadius();
	}

}
