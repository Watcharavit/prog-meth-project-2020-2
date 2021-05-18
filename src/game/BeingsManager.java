package game;

import java.util.Arrays;
import java.util.HashSet;

import entity.base.Being;
import entity.base.Collidable;
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
		// Trigger Passable, Collidable, etc..
		moveBeing(being, 0, 0);
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
		double beingRadius = being.halfSize;
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
			// floor-old-X, floor-old-Y, floor-new-X, floor-new-Y. 
			int foX = (int) oldX;
			int foY = (int) oldY;
			int fnX = (int) newX;
			int fnY = (int) newY;
			
			// Collide with other beings.
			boolean hasCollision = false;
			for (int i = fnX-1; i <= fnX+1; i++) {
				for (int j = fnY-1; j <= fnY+1; j++) {
					for (Being otherBeing : tiles[i][j].getBeings()) {
						if (otherBeing != being) {
							double colDistance = being.halfSize + otherBeing.halfSize;
							if (Math.abs(newX - otherBeing.getX()) <= colDistance && Math.abs(newY - otherBeing.getY()) <= colDistance) {
								boolean allowPass = false;
								if (being instanceof Collidable) {
									Collidable casted = ((Collidable) being);
									casted.collide(otherBeing);
									allowPass |= casted.getCanPassThrough(); 
								}
								if (otherBeing instanceof Collidable) {
									Collidable casted = ((Collidable) otherBeing);
									casted.collide(being);
									allowPass |= casted.getCanPassThrough();
								}
								hasCollision |= !allowPass;
							}
						}
					}
				}
			}
			// Abort move if collision happened.
			if (hasCollision) return false;
			
			// Pass over Passable tiles.
			for (Tile tile : cornerTiles) {
				StillObject so = tile.getObject();
				if (so instanceof Passable) ((Passable) so).pass(being);
			}
			
			// Move the beings.
			being.setX(newX);
			being.setY(newY);
			
			// Move to correct containing tile.
			if (fnX != foX || fnY != foY) {
				tiles[foX][foY].removeBeing(being);
				tiles[fnX][fnY].addBeing(being);
			}
			
			// Return true because move was made.
			return true;
		}
		else {
			// Return false because move was not made.
			return false;
		}
	}
	protected void updateBeingsAroundTile(Tile tile) {
		int x = tile.x;
		int y = tile.y;
		for (int i = Math.max(0, x-1); i <= Math.min(x+1, GameSingleton.WIDTH-1); i++) {
			for (int j = Math.max(0, y-1); j <= Math.min(y+1, GameSingleton.HEIGHT-1); j++) {
				for (Being being : tiles[i][j].getBeings()) {
					// Trigger passable.
					moveBeing(being, 0, 0);
				}
			}
		}
	}
}
