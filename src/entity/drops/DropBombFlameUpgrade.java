package entity.drops;

import entity.livings.Player;
import graphics.Sprite;

public class DropBombFlameUpgrade extends Drop {
	private static final Sprite sprite = new Sprite(1);

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	protected void onPicked(Player player) {
		player.incrementBombRadius();
	}

}
