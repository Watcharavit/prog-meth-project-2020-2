package entity.terrains;

import java.util.HashSet;

import entity.Being;
import entity.livings.Player;
import game.Tile;
import graphics.Sprite;
import interfaces.Passable;
import interfaces.Updatable;

public class KingFloor extends Floor implements Updatable, Passable {
	private static final Sprite sprite = new Sprite(1);
	private HashSet<Player> standingPlayers;

	public KingFloor() {
		this.standingPlayers = new HashSet<Player>();
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	// Java is dumb so we need to provide this to set.toArray() to get the correct
	// type.
	private static final Player[] emptyPlayersArray = {};

	@Override
	public void update(double ticksPassed) {
		Tile tile = super.getTile();
		int x = tile.x;
		int y = tile.y;
		for (Player player : standingPlayers.toArray(emptyPlayersArray)) {
			double playerX = player.getX();
			double playerY = player.getY();
			if ((int) playerX != x || (int) playerY != y) {
				standingPlayers.remove(player);
			} else {
				player.incrementKingTime(ticksPassed);
			}
		}
	}

	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			Player player = (Player) character;
			standingPlayers.add(player);
		}
	}

}
