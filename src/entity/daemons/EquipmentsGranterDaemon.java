package entity.daemons;

import entity.PhantomEntity;
import entity.equipments.EquipmentBombKicker;
import entity.equipments.EquipmentPuncher;
import entity.livings.Player;
import interfaces.Updatable;

/**
 * This class provides system to change equipment of each player.
 *
 */
public class EquipmentsGranterDaemon extends PhantomEntity implements Updatable {

	/**
	 * Array of players in the game.
	 */
	private final Player[] players;

	/**
	 * Player with highest score.
	 */
	private Player lastTopPlayer;

	/**
	 * Player with lowest score.
	 */
	private Player lastBottomPlayer;

	/**
	 * Initialize array list of player.
	 * 
	 * @param players Array list of players in the game.
	 */
	public EquipmentsGranterDaemon(Player[] players) {
		this.players = players;
	}

	/**
	 * Update every 1/60 second. Set highest score player to equip BombKicker
	 * {@link entity.equipments.EquipmentBombKicker}. Set lowest score player to
	 * equip Puncher {@link entity.equipments.EquipmentPuncher}
	 */
	@Override
	public void update(double ticksPassed) {
		Player topPlayer = players[0];
		Player bottomPlayer = players[0];
		for (Player player : players) {
			double kingTime = player.getKingTime();
			if (kingTime > topPlayer.getKingTime())
				topPlayer = player;
			if (kingTime < bottomPlayer.getKingTime())
				bottomPlayer = player;
		}
		if (topPlayer != bottomPlayer) {
			if (topPlayer != lastTopPlayer && lastTopPlayer != null)
				lastTopPlayer.setEquipment(null);
			if (bottomPlayer != lastBottomPlayer && lastBottomPlayer != null)
				lastBottomPlayer.setEquipment(null);
			if (topPlayer != lastTopPlayer)
				topPlayer.setEquipment(new EquipmentBombKicker(topPlayer));
			if (bottomPlayer != lastBottomPlayer)
				bottomPlayer.setEquipment(new EquipmentPuncher(bottomPlayer));
			lastTopPlayer = topPlayer;
			lastBottomPlayer = bottomPlayer;
		}
	}
}
