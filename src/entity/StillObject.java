package entity;

import game.Tile;

/**
 * This class is super class for those classes that cannot be move.
 *
 */
public abstract class StillObject extends PhysicalEntity {

	/**
	 * Its tile.
	 */
	private Tile tile;

	/**
	 * Set tile.
	 * 
	 * @param tile Its tile.
	 */
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	/**
	 * 
	 * @return Tile.
	 */
	public Tile getTile() {
		return this.tile;
	}

}
