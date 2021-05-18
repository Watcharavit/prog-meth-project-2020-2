package game;

import java.util.HashMap;
import java.util.Map;

import entity.Floor;
import entity.Player;
import entity.TempWall;
import javafx.scene.input.KeyCode;
import logic.PlayerControl;

class PlayersManager {
	private final static HashMap<PlayerControl, KeyCode> player1Control = new HashMap<PlayerControl, KeyCode>();
	private final static HashMap<PlayerControl, KeyCode> player2Control = new HashMap<PlayerControl, KeyCode>();
	static {
		player1Control.put(PlayerControl.MOVE_UP, KeyCode.W);
		player1Control.put(PlayerControl.MOVE_LEFT, KeyCode.A);
		player1Control.put(PlayerControl.MOVE_DOWN, KeyCode.S);
		player1Control.put(PlayerControl.MOVE_RIGHT, KeyCode.D);
		player1Control.put(PlayerControl.PLACE_BOMB, KeyCode.SPACE);
		
		player2Control.put(PlayerControl.MOVE_UP, KeyCode.UP);
		player2Control.put(PlayerControl.MOVE_LEFT, KeyCode.LEFT);
		player2Control.put(PlayerControl.MOVE_DOWN, KeyCode.DOWN);
		player2Control.put(PlayerControl.MOVE_RIGHT, KeyCode.RIGHT);
		player2Control.put(PlayerControl.PLACE_BOMB, KeyCode.ENTER);
	}
	
	protected PlayersManager() {
		addPlayers();
	}
	
	private void addPlayers() {
		addPlayer("Player 1", player1Control, 2.5, 2.5);
		addPlayer("Player 2", player2Control, GameSingleton.WIDTH - 2.5, GameSingleton.HEIGHT- 2.5);
	}
	private void clearTilesAroundPlayer(Player player) {
		int x = (int) player.getX();
		int y = (int) player.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (GameSingleton.getTileObject(i, j) instanceof TempWall) GameSingleton.setTileObject(i, j, new Floor());
			}
		}
	}
	private void addPlayer(String name, Map<PlayerControl, KeyCode> keyMap, double posX, double posY) {
		Player player = new Player(name, posX, posY, keyMap);
		GameSingleton.addBeing(player);
		clearTilesAroundPlayer(player);
	}
}
