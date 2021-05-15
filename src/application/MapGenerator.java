package application;

import entity.Floor;
import entity.PermWall;
import entity.base.StillObject;
import gui.Tile;

public class MapGenerator {
	public static void generateMap(Tile[][] tiles) {
		int height = tiles.length;
		int width = tiles[0].length;
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				StillObject inside;
				if (i % 2 == 0 && j % 2 == 0) {
					inside = new PermWall();
				}
				else {
					inside = new Floor();
				}
				tiles[i][j] = new Tile(inside);
			}
		}
	}
}
