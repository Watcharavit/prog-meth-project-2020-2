package entity.drops;

import entity.livings.Player;
import graphics.Sprite;
import graphics.SpritesLibrary;

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
