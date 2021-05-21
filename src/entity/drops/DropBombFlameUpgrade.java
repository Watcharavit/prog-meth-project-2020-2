package entity.drops;

import entity.livings.Player;
import resources.Sprite;
import resources.SpritesLibrary;

public class DropBombFlameUpgrade extends Drop {

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.BOMB_FLAME;
	}

	@Override
	protected void onPicked(Player player) {
		player.incrementBombRadius();
	}

}
