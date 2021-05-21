package entity.daemons;

import entity.PhantomEntity;
import entity.equipments.EquipmentBombKicker;
import entity.equipments.EquipmentPuncher;
import entity.livings.Player;
import interfaces.Updatable;

public class EquipmentsGranterDaemon extends PhantomEntity implements Updatable {
	private final Player[] players;
	private Player lastTopPlayer;
	private Player lastBottomPlayer;

	public EquipmentsGranterDaemon(Player[] players) {
		this.players = players;
	}

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
