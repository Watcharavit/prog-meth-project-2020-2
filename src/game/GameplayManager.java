package game;

import java.util.HashMap;
import java.util.Map;

import entity.daemons.EquipmentsGranterDaemon;
import entity.daemons.GameTimerDaemon;
import entity.livings.Player;
import entity.terrains.Floor;
import entity.terrains.TempWall;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.PlayerControl;
import resources.Sprite;
import resources.SpritesLibrary;

class GameplayManager {
	protected static void setupGameplay(Pane uiPane, Pane overlayUiPane) {
		VBox uiRoot = new VBox();
		uiPane.getChildren().add(uiRoot);
		Pane playersUiPane = new Pane();
		Pane timerUiPane = new Pane();
		uiRoot.getChildren().addAll(playersUiPane, timerUiPane);
		uiRoot.setMinWidth(180);
		uiRoot.setMaxWidth(180);
		Player[] players = PlayersAdder.createPlayers(playersUiPane);
		GameSingleton.addPhantomEntity(new GameTimerDaemon(players, timerUiPane, overlayUiPane));
		GameSingleton.addPhantomEntity(new EquipmentsGranterDaemon(players));
	}

	static class PlayersAdder {
		private final static HashMap<PlayerControl, KeyCode> player1Control = new HashMap<PlayerControl, KeyCode>();
		private final static HashMap<PlayerControl, KeyCode> player2Control = new HashMap<PlayerControl, KeyCode>();
		static {
			player1Control.put(PlayerControl.MOVE_UP, KeyCode.W);
			player1Control.put(PlayerControl.MOVE_LEFT, KeyCode.A);
			player1Control.put(PlayerControl.MOVE_DOWN, KeyCode.S);
			player1Control.put(PlayerControl.MOVE_RIGHT, KeyCode.D);
			player1Control.put(PlayerControl.PLACE_BOMB, KeyCode.SPACE);
			player1Control.put(PlayerControl.USE_EQUIPMENT, KeyCode.C);

			player2Control.put(PlayerControl.MOVE_UP, KeyCode.UP);
			player2Control.put(PlayerControl.MOVE_LEFT, KeyCode.LEFT);
			player2Control.put(PlayerControl.MOVE_DOWN, KeyCode.DOWN);
			player2Control.put(PlayerControl.MOVE_RIGHT, KeyCode.RIGHT);
			player2Control.put(PlayerControl.PLACE_BOMB, KeyCode.ENTER);
			player2Control.put(PlayerControl.USE_EQUIPMENT, KeyCode.L);
		}

		protected static Player[] createPlayers(Pane uiPane) {
			VBox containerBox = new VBox();
			Player[] allPlayers = {
					createPlayer("Player 1", player1Control, GameSingleton.tiles[2][2], SpritesLibrary.PLAYER_ONE,
							SpritesLibrary.PLAYER_ONE_DYING, containerBox),
					createPlayer("Player 2", player2Control,
							GameSingleton.tiles[GameSingleton.WIDTH - 3][GameSingleton.HEIGHT - 3],
							SpritesLibrary.PLAYER_TWO, SpritesLibrary.PLAYER_TWO_DYING, containerBox) };

			uiPane.getChildren().add(containerBox);

			return allPlayers;
		}

		private static void clearTilesAroundPlayer(Player player) {
			int x = (int) player.getX();
			int y = (int) player.getY();
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (GameSingleton.getTileObject(i, j) instanceof TempWall)
						GameSingleton.setTileObject(i, j, new Floor());
				}
			}
		}

		private static Player createPlayer(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile,
				Sprite normalSprite, Sprite dyingSprite, VBox containingUiBox) {
			Pane playerPane = new Pane();
			Player player = new Player(name, keyMap, spawnTile, normalSprite, dyingSprite, playerPane);
			containingUiBox.getChildren().add(playerPane);

			GameSingleton.addBeing(player);
			clearTilesAroundPlayer(player);

			return player;
		}
	}
}
