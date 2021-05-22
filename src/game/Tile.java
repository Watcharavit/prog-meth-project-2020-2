package game;

import java.util.HashSet;

import entity.Being;
import entity.StillObject;

/**
 * A cell in the 2D map.
 * Each tile has an object ({@link entity.terrains.PermWall}, for example).
 * We also store a list of beings that are in this tile to allow quick lookup of beings near an area (see {@link BeingsManager}).
 */
public class Tile {
	/** The object on this tile. */
	private StillObject object;
	/** Set of beings on this tile. */
	private HashSet<Being> beings;
	/** Position of this tile. */
	public final int x, y;

	/**
	 * Construct the tile.
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.object = null;
		this.beings = new HashSet<Being>();
	}

	/**
	 * Get the {@link #object}.
	 * Should be called through {@link GameSingleton#getTileObject(int, int)}.
	 * @return The object on the tile.
	 */
	protected StillObject getObject() {
		return this.object;
	}

	/**
	 * Get a set of beings on this tile ({@link #beings}).
	 * @return A set of beings on this tile.
	 */
	protected HashSet<Being> getBeings() {
		return this.beings;
	}

	/**
	 * Set the {@link #object}.
	 * MUST be called through {@link GameSingleton#setTileObject(int, int, StillObject)}.
	 * @param object The object to put on this tile.
	 */
	protected void setObject(StillObject object) {
		this.object = object;
		object.setTile(this);
	}

	/**
	 * Add a being to this tile's beings set.
	 * Should only be called by {@link BeingsManager}.
	 * @param being The being to add.
	 */
	protected void addBeing(Being being) {
		this.beings.add(being);
	}

	/**
	 * Remove a being from this tile's beings set.
	 * Should only be called by {@link BeingsManager}.
	 * @param being The being to remove.
	 * @return Whether or not the being was found (and removed) from this tile's beings set.
	 */
	protected boolean removeBeing(Being being) {
		return this.beings.remove(being);
	}
}
