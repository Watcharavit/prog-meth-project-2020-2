package game;

import entity.terrains.Floor;
import entity.terrains.KingFloor;
import entity.terrains.PermWall;
import entity.terrains.TempWall;

/**
 * Generate the game map.
 *
 */
class MapGenerator {
	/**
	 * Generate the game map. Basic rules:
	 * <ul>
	 * <li>Tiles at the edge are {@link entity.terrains.PermWall}.</li>
	 * <li>Tiles at x-odd+y-odd positions are {@link entity.terrains.PermWall}.</li>
	 * <li>The rest have 30% chance to be {@link entity.terrains.TempWall} and 70%
	 * to be {@link entity.terrains.Floor}.</li>
	 * <li>The center tile is {@link entity.terrains.KingFloor}.</li>
	 * </ul>
	 */
	protected static void generateMap() {
		Tile[][] tiles = GameSingleton.tiles;
		int width = tiles.length;
		int height = tiles[0].length;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {
				if (i % 2 == 1 && j % 2 == 1) {
					GameSingleton.setTileObject(i, j, new PermWall());
				} else {
					if (Math.random() > 0.7) {
						GameSingleton.setTileObject(i, j, new TempWall());
					} else {
						GameSingleton.setTileObject(i, j, new Floor());
					}
				}
			}
		}

		int[] ys = { 0, height - 1 };
		for (int i = 0; i < width; i++) {
			for (int j : ys) {
				GameSingleton.setTileObject(i, j, new PermWall());
			}
		}
		int[] xs = { 0, width - 1 };
		for (int j = 0; j < height; j++) {
			for (int i : xs) {
				GameSingleton.setTileObject(i, j, new PermWall());
			}
		}
		GameSingleton.setTileObject(width / 2, height / 2, new KingFloor());
	}
}
