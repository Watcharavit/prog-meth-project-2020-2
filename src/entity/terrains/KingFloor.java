package entity.terrains;

import java.util.HashSet;

import entity.Being;
import entity.livings.Player;
import game.Tile;
import interfaces.Passable;
import interfaces.Updatable;
import resources.Sprite;
import resources.SpritesLibrary;

/**
 * Floor that increase the score of the players standing on it.
 *
 */
public class KingFloor extends Floor implements Updatable, Passable {
	/**
	 * Players that are on this specific floor.
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

	/**
	 * An empty array to specify type for set.toArray().
	 */
	private static final Player[] emptyPlayersArray = {};

	/**
	 * Update on every frame: Check if any players stand on this floor. If yes, then
	 * increase score of that player.
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
	 * Add player to {@link #standingPlayers} if it pass this floor.
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
