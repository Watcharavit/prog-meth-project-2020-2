package game;

import java.util.Arrays;
import java.util.HashSet;

import entity.Being;
import entity.StillObject;
import interfaces.Collidable;
import interfaces.Passable;

/**
 * Handles addition/removal and movement of {@link entity.Being}s in the game.
 * 
 * We store beings in a set, but also store each one as reference in the
 * {@link Tile} it is on. This allow us to only look at beings in nearby tiles
 * when checking for collision.
 */
class BeingsManager {
	/** All tiles in the game. Reference to {@link GameSingleton#tiles}. */
	Tile[][] tiles;
	/** All beings in the game. Reference to {@link GameSingleton#allBeings}. */
	HashSet<Being> allBeings;

	/**
	 * Construct the instance. Initialize the fields.
	 */
	protected BeingsManager() {
		this.tiles = GameSingleton.tiles;
		this.allBeings = GameSingleton.allBeings;
	}

	/**
	 * Add a being to the game world. Trigger {@link interfaces.Passable} and/or
	 * {@link interfaces.Collidable} if it is on some objects and/or colliding with
	 * some other beings.
	 * 
	 * @param being The being to add.
	 */
	protected void addBeing(Being being) {
		allBeings.add(being);
		double x = being.getX();
		double y = being.getY();
		tiles[(int) x][(int) y].addBeing(being);
		// Let moveBeing trigger Passable, Collidable, etc..
		moveBeing(being, 0, 0);
	}

	/**
	 * Remove a being from the game world.
	 * 
	 * @param being The being to remove.
	 */
	protected void removeBeing(Being being) {
		allBeings.remove(being);
		double x = being.getX();
		double y = being.getY();
		tiles[(int) x][(int) y].removeBeing(being);
	}

	/**
	 * A utility function to get an array of tiles a being might be on.
	 * 
	 * @param x           The x position of the being.
	 * @param y           The y position of the being.
	 * @param beingRadius The radius (size / 2) of the being.
	 * @return An array of tiles at the top-left, top-right, bottom-left, and
	 *         bottom-right corner of the being.
	 */
	private Tile[] getCornerTiles(double x, double y, double beingRadius) {
		int l = (int) (x - beingRadius);
		int r = (int) (x + beingRadius);
		int u = (int) (y - beingRadius);
		int d = (int) (y + beingRadius);
		Tile lu = tiles[l][u];
		Tile ld = tiles[l][d];
		Tile ru = tiles[r][u];
		Tile rd = tiles[r][d];
		Tile[] s = { lu, ld, ru, rd };
		return s;
	}

	/** A simple array for specifying type to set.toArray(). */
	private static final Being[] emptyBeingsArray = {};

	/**
	 * Move a being. This function does the following:
	 * <ul>
	 * <li>Check that moving to the new position would not move any of the being's
	 * four corner into an not-{@link interfaces.Passable} tile.</li>
	 * <li>If some of the tiles are not pass-able, give up and return false.</li>
	 * <li>Check for collision with beings around this one and call
	 * {@link interfaces.Collidable#collide(Being)} accordingly.</li>
	 * <li>If any collision is not pass-through
	 * ({@link interfaces.Collidable#getCanPassThrough()}), give up and return
	 * false.</li>
	 * <li>Call {@link interfaces.Passable#pass(Being)} for tiles that the being
	 * will be on.</li>
	 * <li>Set the being's new X and Y position.</li>
	 * <li>Move the player reference to a new tile if needed.</li>
	 * </ul>
	 * 
	 * @param being The being to be moved.
	 * @param dx    Change in X axis.
	 * @param dy    Change in Y axis.
	 * @return Whether or not the move was successful.
	 */
	protected boolean moveBeing(Being being, double dx, double dy) {
		double oldX = being.getX();
		double oldY = being.getY();
		double newX = oldX + dx;
		double newY = oldY + dy;
		double beingRadius = being.halfSize;
		Tile[] cornerTiles = getCornerTiles(newX, newY, beingRadius);
		HashSet<Tile> currentCornerTiles = new HashSet<Tile>(Arrays.asList(getCornerTiles(oldX, oldY, beingRadius)));
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

			int le = Math.max(fnX - 1, 0);
			int re = Math.min(fnX + 1, GameSingleton.WIDTH - 1);
			int ue = Math.max(fnY - 1, 0);
			int de = Math.min(fnY + 1, GameSingleton.HEIGHT - 1);

			// Collide with other beings.
			boolean hasCollision = false;
			for (int i = le; i <= re; i++) {
				for (int j = ue; j <= de; j++) {
					Being[] beingsCloned = tiles[i][j].getBeings().toArray(emptyBeingsArray);
					for (Being otherBeing : beingsCloned) {
						if (otherBeing != being) {
							double colDistance = being.halfSize + otherBeing.halfSize;
							if (Math.abs(newX - otherBeing.getX()) <= colDistance
									&& Math.abs(newY - otherBeing.getY()) <= colDistance) {
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
			if (hasCollision)
				return false;

			// Pass over Passable tiles.
			for (Tile tile : cornerTiles) {
				StillObject so = tile.getObject();
				if (so instanceof Passable)
					((Passable) so).pass(being);
			}

			// Move the beings.
			being.setX(newX);
			being.setY(newY);

			// Move to correct containing tile.
			if (fnX != foX || fnY != foY) {
				if (tiles[foX][foY].removeBeing(being))
					tiles[fnX][fnY].addBeing(being);
			}

			// Return true because move was made.
			return true;
		} else {
			// Return false because move was not made.
			return false;
		}
	}

	/**
	 * Trigger {@link interfaces.Passable} and/or {@link interfaces.Collidable} if
	 * for beings around a given tile. This is useful when the tile object changes
	 * (e.g. {@link entity.terrains.Floor} changes to
	 * {@link entity.bomb.BombFlame}).
	 * 
	 * @param tile The tile around which to update beings.
	 */
	protected void updateBeingsAroundTile(Tile tile) {
		int x = tile.x;
		int y = tile.y;
		for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, GameSingleton.WIDTH - 1); i++) {
			for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, GameSingleton.HEIGHT - 1); j++) {
				for (Being being : tiles[i][j].getBeings()) {
					// Trigger passable.
					moveBeing(being, 0, 0);
				}
			}
		}
	}
}
