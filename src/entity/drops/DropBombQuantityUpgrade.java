package entity.drops;

import entity.livings.Player;
import resources.Sprite;
import resources.SpritesLibrary;

public class DropBombQuantityUpgrade extends Drop {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB_QUANTITY;
	}

	@Override
	protected void onPicked(Player player) {
		player.incrementBombsNumber();
	}

}
