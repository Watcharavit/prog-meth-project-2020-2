package game;

import entity.Floor;
import entity.KingFloor;
import entity.PermWall;
import entity.TempWall;
import entity.base.StillObject;

class MapGenerator {
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
		for (int i = 0; i < width; i++) {
			GameSingleton.setTileObject(i, 0, new PermWall());
			GameSingleton.setTileObject(i, height - 1, new PermWall());
		}
		for (int j = 0; j < height; j++) {
			GameSingleton.setTileObject(0, j, new PermWall());
			GameSingleton.setTileObject(width - 1, j, new PermWall());
		}
		GameSingleton.setTileObject(width /2 , height/2 , new KingFloor());
	}
}