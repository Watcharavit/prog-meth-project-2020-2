package game;

import entity.livings.LivingBeing;
import entity.livings.MonsterGhost;
import entity.livings.MonsterWallThorn;
import entity.terrains.Floor;
import entity.terrains.TempWall;
import logic.Direction;

public class MonsterGenerator {

	protected static void generateMonsters() {
		addMonster();
	}

	private static void addMonster() {
		addWallThornMonster("WallThorn1", GameSingleton.tiles[GameSingleton.WIDTH / 2 - 1][GameSingleton.HEIGHT / 2],
				Direction.LEFT);
		addWallThornMonster("WallThorn2", GameSingleton.tiles[GameSingleton.WIDTH / 2][GameSingleton.HEIGHT / 2 + 1],
				Direction.UP);
		addGhostMonster("Ghost1", GameSingleton.tiles[(int) Math
				.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)), 5)][GameSingleton.HEIGHT / 4]);
		addGhostMonster("Ghost2", GameSingleton.tiles[(int) Math
				.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)), 5)][GameSingleton.HEIGHT / 4]);
		addGhostMonster("Ghost3", GameSingleton.tiles[(int) Math
				.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)), 5)][GameSingleton.HEIGHT * 3 / 4]);
		addGhostMonster("Ghost4", GameSingleton.tiles[(int) Math
				.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)), 5)][GameSingleton.HEIGHT * 3 / 4]);

	}

	private static void addWallThornMonster(String name, Tile spawnTile, Direction direction) {
		MonsterWallThorn wallThorn = new MonsterWallThorn(spawnTile, direction);
		GameSingleton.addBeing(wallThorn);
		clearTileAroundMonster(wallThorn);

	}

	private static void addGhostMonster(String name, Tile spawnTile) {
		MonsterGhost ghost = new MonsterGhost(spawnTile);
		GameSingleton.addBeing(ghost);
		clearTileAroundMonster(ghost);
	}

	private static void clearTileAroundMonster(LivingBeing being) {
		int x = (int) being.getX();
		int y = (int) being.getY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (GameSingleton.getTileObject(i, j) instanceof TempWall)
					GameSingleton.setTileObject(i, j, new Floor());
			}
		}
	}
}
