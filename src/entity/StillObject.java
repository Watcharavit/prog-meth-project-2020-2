package entity;

import game.Tile;

/**
 * Non-moving object found in each {@link game.Tile}.
 */
public abstract class StillObject extends PhysicalEntity {

	/**
	 * The containing tile.
	 */
	private Tile tile;

	/**
	 * Set the containing tile. Should only be called from
	 * {@link game.Tile#setObject}.
	 * 
	 * @param tile Its tile.
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * Getter for {@link #tile}.
	 * 
	 * @return Tile.
	 */
	public Tile getTile() {
		return this.tile;
	}

}
