package application;

import entity.Floor;
import entity.PermWall;
import entity.base.StillObject;

public class MapGenerator {
	public static void generateMap(Tile[][] tiles) {
		int width = tiles.length;
		int height = tiles[0].length;
		for (int i=1; i<width-1; i++) {
			for (int j=1; j<height-1; j++) {
				StillObject inside;
				if (i % 2 == 1 && j % 2 == 1) {
					inside = new PermWall();
				}
				else {
					inside = new Floor();
				}
				tiles[i][j] = new Tile(inside);
			}
		}
		for (int i=0; i<width; i++) {
			tiles[i][0] = new Tile(new PermWall());
			tiles[i][height-1] = new Tile(new PermWall());
		}
		for (int j=0; j<height; j++) {
			tiles[0][j] = new Tile(new PermWall());
			tiles[width-1][j] = new Tile(new PermWall());
		}
	}
}
