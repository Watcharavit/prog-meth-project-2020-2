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

/**
 * Manage the players and spawn the daemons.
 * 
 */
class GameplayManager {
	/**
	 * Set up the game. Add two players using {@link PlayersAdder}. Spawn
	 * {@link entity.daemons.GameTimerDaemon} and
	 * {@link entity.daemons.EquipmentsGranterDaemon}.
	 * 
	 * @param uiPane        A JavaFX Pane to display the players' info UI.
	 * @param overlayUiPane A JavaFX Pane to display the game end UI.
	 */
	protected static void setupGameplay(Pane uiPane, Pane overlayUiPane) {
		VBox uiRoot = new VBox();
		uiPane.getChildren().add(uiRoot);
		Pane playersUiPane = new Pane();
		Pane timerUiPane = new Pane();
		uiRoot.getChildren().addAll(playersUiPane, timerUiPane);
		Player[] players = PlayersAdder.createPlayers(playersUiPane);
		GameSingleton.addPhantomEntity(new GameTimerDaemon(players, timerUiPane, overlayUiPane));
		GameSingleton.addPhantomEntity(new EquipmentsGranterDaemon(players));
	}

	/**
	 * Handle adding players to the game. Currently add two players. Can be modified
	 * to add more very easily.
	 */
	static class PlayersAdder {
		/** Keymap for the player. */
		private final static HashMap<PlayerControl, KeyCode> player1Control = new HashMap<PlayerControl, KeyCode>();
		/** Keymap for the player. */
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

		/**
		 * Create two players and add them to the game.
		 * 
		 * @param uiPane The containing Pane in which we will display players' info UI.
		 * @return Array of created players.
		 */
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

		/**
		 * Clear the map tiles around the given player.
		 * 
		 * @param player The player.
		 */
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

		/**
		 * Create a player and add them to the game.
		 * 
		 * @param name            The name of this player. (usually "Player {1,2}")
		 * @param keyMap          This player's controls Keymap.
		 * @param spawnTile       The tile at which this player should spawn
		 * @param normalSprite    The sprite for this player.
		 * @param dyingSprite     The sprite for this player when dying (see
		 *                        {@link entity.livings.LivingBeing}).
		 * @param containingUiBox The JavaFX VBox to display this player's information
		 *                        in.
		 * @return The player object.
		 */
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
