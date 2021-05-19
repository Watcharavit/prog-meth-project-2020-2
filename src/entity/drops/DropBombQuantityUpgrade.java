package entity.drops;

import entity.Being;
import entity.StillObject;
import entity.livings.Player;
import entity.terrains.Floor;
import game.GameSingleton;
import game.Tile;
import graphics.Sprite;
import interfaces.Bombable;
import interfaces.Passable;

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
