package application;

import java.util.Arrays;
import java.util.HashSet;

import entity.base.Being;
import entity.base.Passable;
import entity.base.StillObject;

class BeingsManager {
	final Tile[][] tiles;
	protected BeingsManager(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	protected void addBeing(Being being) {
		double x = being.getX();
		double y = being.getY();
		tiles[(int) x][(int) y].addBeing(being);
	}
	protected void removeBeing(Being being) {
		double x = being.getX();
		double y = being.getY();
		tiles[(int) x][(int) y].removeBeing(being);
	}
	
	private Tile[] getCornerTiles(double x, double y, double beingRadius) {
		int l = (int) (x - beingRadius);
		int r = (int) (x + beingRadius);
		int u = (int) (y - beingRadius);
		int d = (int) (y + beingRadius);
		Tile lu = tiles[l][u];
		Tile ld = tiles[l][d];
		Tile ru = tiles[r][u];
		Tile rd = tiles[r][d];
		Tile[] s = {lu, ld, ru, rd};
		return s;
	}
	protected boolean moveBeing(Being being, double dx, double dy) {
		double oldX = being.getX();
		double oldY = being.getY();
		double newX = oldX + dx;
		double newY = oldY + dy;
		double beingRadius = being.size / 2;
		Tile[] cornerTiles = getCornerTiles(newX, newY, beingRadius);
		HashSet<Tile> currentCornerTiles = new HashSet<Tile>(Arrays.asList(getCornerTiles(oldX, oldY, beingRadius )));
		boolean allPassable = true;
		for (Tile tile : cornerTiles) {
			if (!(tile.getObject() instanceof Passable) && !currentCornerTiles.contains(tile)) {
				allPassable = false;
				break;
			}
		}
		if (allPassable) {
			for (Tile tile : cornerTiles) {
				StillObject so = tile.getObject();
				if (so instanceof Passable) ((Passable) so).pass(being);
			}
			being.setX(newX);
			being.setY(newY);
			int foX = (int) oldX;
			int foY = (int) oldY;
			int fnX = (int) newX;
			int fnY = (int) newY;
			if (fnX != foX || fnY != foY) {
				tiles[foX][foY].removeBeing(being);
				tiles[fnX][fnY].addBeing(being);
			}
			return true;
		}
		else {
			return false;
		}
	}
	protected void updateBeingsAroundTile(Tile tile) {
		int x = tile.x;
		int y = tile.y;
		for (int i = Math.max(0, x-1); i <= Math.min(x+1, GameSingleton.WIDTH-1); i++) {
			for (int j = Math.max(0, y-1); j <= Math.min(y+1, GameSingleton.HEIGHT-1); j++) {
				for (Being being : tiles[i][j].getBeings()) {
					moveBeing(being, 0, 0);
				}
			}
		}
	}
}
