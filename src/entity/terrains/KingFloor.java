package entity.terrains;

import java.util.HashSet;

import entity.Being;
import entity.livings.Player;
import game.Tile;
import interfaces.Passable;
import interfaces.Updatable;
import resources.Sprite;
import resources.SpritesLibrary;

public class KingFloor extends Floor implements Updatable, Passable {
	/**
	 * PLayer that is on this specific floor.
	 */
	private HashSet<Player> standingPlayers;

	/**
	 * Initialize field.
	 */
	public KingFloor() {
		this.standingPlayers = new HashSet<Player>();
	}

	@Override
	public Sprite getSprite() {
		return SpritesLibrary.KING_FLOOR;
	}

	// Java is not smart so we need to provide this to set.toArray() to get the
	// correct
	// type.
	/**
	 * Array of players.
	 */
	private static final Player[] emptyPlayersArray = {};

	/**
	 * Update every 1/60 second. Check if any players stand on this floor. If yes,
	 * then increase score of that player.
	 */
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

	/**
	 * Add player to standingPlayer if it pass this floor.
	 * 
	 * @param character Character that pass this floor.
	 */
	@Override
	public void pass(Being character) {
		if (character instanceof Player) {
			Player player = (Player) character;
			standingPlayers.add(player);
		}
	}

}
