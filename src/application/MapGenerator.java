package application;

import entity.Floor;
import entity.PermWall;
import entity.base.StillObject;

public class MapGenerator {
	public static void generateMap(Tile[][] tiles) {
		int width = tiles.length;
		int height = tiles[0].length;
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
		for (int i=1; i<width-1; i++) {
			for (int j=1; j<height-1; j++) {
				if (i % 2 == 1 && j % 2 == 1) {
					tiles[i][j].setObject(new PermWall());
				}
				else {
					tiles[i][j].setObject(new Floor());
				}
			}
		}
		for (int i=0; i<width; i++) {
			tiles[i][0].setObject(new PermWall());
			tiles[i][height-1].setObject(new PermWall());
		}
		for (int j=0; j<height; j++) {
			tiles[0][j].setObject(new PermWall());
			tiles[width-1][j].setObject(new PermWall());
		}
	}
}
