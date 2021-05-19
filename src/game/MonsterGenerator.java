package game;

import java.util.Map;

import entity.Floor;
import entity.Ghost;
import entity.KingFloor;
import entity.Player;
import entity.TempWall;
import entity.WallThornMonster;
import entity.base.LivingBeing;
import javafx.scene.input.KeyCode;
import logic.PlayerControl;

public class MonsterGenerator {

	protected MonsterGenerator() {
		addMonster();
	}

	protected void addMonster() {
		addWallThornMonster("WallThorn1", GameSingleton.tiles[GameSingleton.WIDTH / 2 - 1][GameSingleton.HEIGHT / 2]);
		addWallThornMonster("WallThorn2", GameSingleton.tiles[GameSingleton.WIDTH / 2][GameSingleton.HEIGHT / 2 - 1]);
		addGhost("Ghost1", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)), 5)][GameSingleton.HEIGHT / 4]);
		addGhost("Ghost2", GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 4)), 5)][GameSingleton.HEIGHT / 4]);
		addGhost("Ghost3", GameSingleton.tiles[GameSingleton.WIDTH*3/4][(int) Math.max(Math.floor(Math.random() * (GameSingleton.HEIGHT - 4)), 5)]);
		addGhost("Ghost4", GameSingleton.tiles[GameSingleton.WIDTH*3/4][(int) Math.max(Math.floor(Math.random() * (GameSingleton.HEIGHT - 4)), 5)]);
		
	}

	private void addWallThornMonster(String name, Tile spawnTile) {
		WallThornMonster wallThorn = new WallThornMonster(spawnTile);
		GameSingleton.addBeing(wallThorn);
		clearTileAtMonster(wallThorn);

	}

	private void addGhost(String name, Tile spawnTile) {
		Ghost ghost = new Ghost(spawnTile);
		GameSingleton.addBeing(ghost);
		clearTileAtMonster(ghost);
	}

	private void clearTileAtMonster(LivingBeing being) {
		int x = (int) being.getX();
		int y = (int) being.getY();
		if (GameSingleton.getTileObject(x, y) instanceof TempWall)
			GameSingleton.setTileObject(x, y, new Floor());
	}

}
