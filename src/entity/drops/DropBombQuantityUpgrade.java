package entity.drops;

import entity.livings.Player;
import graphics.Sprite;

public class DropBombQuantityUpgrade extends Drop {
	private static final Sprite sprite = new Sprite(0);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	protected void onPicked(Player player) {
		player.incrementBombsNumber();
	}

}
