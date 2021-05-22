package game;

import entity.livings.LivingBeing;
import entity.livings.MonsterGhost;
import entity.livings.MonsterWallThorn;
import entity.terrains.Floor;
import entity.terrains.TempWall;
import logic.Direction;

/**
 * Generate monsters.
 *
 */
public class MonsterGenerator {

	/**
	 * Generate monsters. Add 2 {@link entity.livings.MonsterWallThorn} crossing the
	 * map center (where the {@link entity.terrains.KingFloor} is) one vertical and
	 * one horizontal. Add 4 {@link entity.livings.MonsterGhost}, one in each
	 * quadrant of the map.
	 */
	protected static void generateMonsters() {
		addWallThornMonster(GameSingleton.tiles[GameSingleton.WIDTH / 2 - 1][GameSingleton.HEIGHT / 2], Direction.LEFT);
		addWallThornMonster(GameSingleton.tiles[GameSingleton.WIDTH / 2][GameSingleton.HEIGHT / 2 + 1], Direction.UP);
		addGhostMonster(GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)),
				5)][GameSingleton.HEIGHT / 4]);
		addGhostMonster(GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)),
				5)][GameSingleton.HEIGHT / 4]);
		addGhostMonster(GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)),
				5)][GameSingleton.HEIGHT * 3 / 4]);
		addGhostMonster(GameSingleton.tiles[(int) Math.max(Math.floor(Math.random() * (GameSingleton.WIDTH - 6)),
				5)][GameSingleton.HEIGHT * 3 / 4]);
	}

	/**
	 * Add a {@link entity.livings.MonsterWallThorn}.
	 * 
	 * @param spawnTile The tile where this monster should spawn.
	 * @param direction The direction this monster should move in.
	 */
	private static void addWallThornMonster(Tile spawnTile, Direction direction) {
		MonsterWallThorn wallThorn = new MonsterWallThorn(spawnTile, direction);
		GameSingleton.addBeing(wallThorn);
		clearTileAroundMonster(wallThorn);

	}

	/**
	 * Add a {@link entity.livings.MonsterGhost}
	 * 
	 * @param spawnTile The tile where this monster should spawn.
	 */
	private static void addGhostMonster(Tile spawnTile) {
		MonsterGhost ghost = new MonsterGhost(spawnTile);
		GameSingleton.addBeing(ghost);
		clearTileAroundMonster(ghost);
	}

	/**
	 * Clear the tile where the given monster will spawn.
	 * 
	 * @param being The monster around which to clear tile.
	 */
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
