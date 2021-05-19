package game;

import java.util.HashMap;
import java.util.Map;

import entity.livings.Player;
import entity.terrains.Floor;
import entity.terrains.TempWall;
import graphics.Sprite;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
		player1Control.put(PlayerControl.USE_EQUIPMENT, KeyCode.C);

		player2Control.put(PlayerControl.MOVE_UP, KeyCode.UP);
		player2Control.put(PlayerControl.MOVE_LEFT, KeyCode.LEFT);
		player2Control.put(PlayerControl.MOVE_DOWN, KeyCode.DOWN);
		player2Control.put(PlayerControl.MOVE_RIGHT, KeyCode.RIGHT);
		player2Control.put(PlayerControl.PLACE_BOMB, KeyCode.ENTER);
		player2Control.put(PlayerControl.USE_EQUIPMENT, KeyCode.SLASH);
	}
	private final static Sprite player1NormalSprite = new Sprite(5);
	private final static Sprite player1DyingSprite = new Sprite(0);
	private final static Sprite player2NormalSprite = new Sprite(6);
	private final static Sprite player2DyingSprite = new Sprite(1);

	private final VBox uiContainer;

	protected PlayersManager(Pane pane) {
		addPlayers();
		this.uiContainer = new VBox();
		pane.getChildren().add(uiContainer);
	}

	private void addPlayers() {
		addPlayer("Player 1", player1Control, GameSingleton.tiles[2][2], player1NormalSprite, player1DyingSprite);
		addPlayer("Player 2", player2Control, GameSingleton.tiles[GameSingleton.WIDTH - 3][GameSingleton.HEIGHT - 3],
				player2NormalSprite, player2DyingSprite);
	}

	private void clearTilesAroundPlayer(Player player) {
		int x = (int) player.getX();
		int y = (int) player.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (GameSingleton.getTileObject(i, j) instanceof TempWall)
					GameSingleton.setTileObject(i, j, new Floor());
			}
		}
	}

	private void addPlayer(String name, Map<PlayerControl, KeyCode> keyMap, Tile spawnTile, Sprite normalSprite,
			Sprite dyingSprite) {
		Pane playerPane = new Pane();
		Player player = new Player(name, keyMap, spawnTile, normalSprite, dyingSprite, playerPane);
		uiContainer.getChildren().add(playerPane);

		GameSingleton.addBeing(player);
		clearTilesAroundPlayer(player);
	}
}
